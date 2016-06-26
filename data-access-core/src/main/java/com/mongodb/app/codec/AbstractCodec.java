package com.mongodb.app.codec;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Abstract codec.
 *
 * @param <T> collection class
 * @author nico.arianto
 */
public abstract class AbstractCodec<T> implements Codec<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCodec.class);
    private final CodecRegistry registry;
    private final Class<T> collectionClass;
    private Map<String, PropertyDescriptor> collectionDescriptors;

    /**
     * Constructor.
     *
     * @param registry        registry
     * @param collectionClass collection class
     */
    protected AbstractCodec(CodecRegistry registry, Class<T> collectionClass) {
        this.registry = registry;
        this.collectionClass = collectionClass;
        initCollectionDesriptors();
    }

    /**
     * Initialize collection descriptors.
     *
     * @throws CodecConfigurationException if {@link Introspector} cannot read {@link BeanInfo} from collection class
     */
    private void initCollectionDesriptors() {
        final BeanInfo collectionInfo;
        try {
            collectionInfo = Introspector.getBeanInfo(collectionClass, Object.class);
        } catch (IntrospectionException exception) {
            final String message = "Failed to read the bean info of " + collectionClass + "!";
            LOGGER.error(message, exception);
            throw new CodecConfigurationException(message);
        }
        final PropertyDescriptor[] descriptors = collectionInfo.getPropertyDescriptors();
        this.collectionDescriptors = new HashMap<>(descriptors.length);
        for (PropertyDescriptor descriptor : descriptors) {
            if (Objects.isNull(descriptor.getWriteMethod()) && Objects.isNull(descriptor.getReadMethod())) {
                continue;
            }
            collectionDescriptors.put(descriptor.getName(), descriptor);
        }
    }

    /**
     * Returns a codec for specific target property type.
     *
     * @param sourceClass target property type
     * @param <S>         attribute class
     * @return codec
     */
    protected <S> Codec<S> getCodec(final Class<S> sourceClass) {
        if (registry == null) {
            throw new CodecConfigurationException("CodecRegistry is empty!");
        }
        return registry.get(sourceClass);
    }

    /**
     * Returns target attribute name.
     * <p>
     * Override this method if the BSON element name is different with target attribute name.
     *
     * @param bsonElementName BSON element name
     * @return target attribute name
     */
    protected String getAttributeName(final String bsonElementName) {
        return bsonElementName;
    }

    /**
     * Returns the element value.
     *
     * @param elementType    element type
     * @param reader         reader
     * @param decoderContext context
     * @return element value
     */
    private Object readElement(Class<?> elementType, final BsonReader reader, final DecoderContext decoderContext) {
        return getCodec(elementType).decode(reader, decoderContext);
    }

    /**
     * Reads the element value.
     *
     * @param descriptor     property descriptor
     * @param reader         reader
     * @param decoderContext context
     * @return element value
     */
    private Object readElement(final PropertyDescriptor descriptor, final BsonReader reader, final DecoderContext decoderContext) {
        final Class<?> propertyType = descriptor.getPropertyType();
        if (propertyType.isArray()) {
            return readArray(propertyType.getComponentType(), reader, decoderContext);
        } else if (Collection.class.isAssignableFrom(propertyType)) {
            final Class<?> propertyElementType = (Class<?>) ((ParameterizedType) descriptor.getWriteMethod().
                    getGenericParameterTypes()[0]).getActualTypeArguments()[0];
            return readCollection((Class<Collection>) propertyType, propertyElementType, reader,
                    decoderContext);
        }
        return readElement(propertyType, reader, decoderContext);
    }

    /**
     * Returns a collection of element.
     *
     * @param collectionType collection type
     * @param elementType    element type
     * @param reader         reader
     * @param decoderContext context
     * @param <E>            element class
     * @param <S>            collection class
     * @return collection of element
     * @throws CodecConfigurationException if the collectionType is not been supported
     */
    private <E, S extends Collection<E>> S readCollection(final Class<S> collectionType, final Class<E> elementType, final BsonReader reader, final DecoderContext decoderContext) {
        final Collection<E> elements;
        if (List.class.isAssignableFrom(collectionType)) {
            elements = new ArrayList<E>();
        } else if (Set.class.isAssignableFrom(collectionType)) {
            elements = new HashSet<E>();
        } else {
            throw new CodecConfigurationException(collectionType + " is not been supported!");
        }
        if (reader.getCurrentBsonType() == BsonType.ARRAY) {
            reader.readStartArray();
            while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                elements.add((E) readElement(elementType, reader, decoderContext));
            }
            reader.readEndArray();
        } else {
            elements.add((E) readElement(elementType, reader, decoderContext));
        }
        return (S) elements;
    }

    /**
     * Reads an array of element.
     *
     * @param elementType    element type
     * @param reader         reader
     * @param decoderContext context
     * @param <E>            element class
     * @return array of elements
     */
    private <E> E[] readArray(final Class<E> elementType, final BsonReader reader, final DecoderContext decoderContext) {
        Collection<E> elements = readCollection(List.class, elementType, reader, decoderContext);
        return elements.toArray((E[]) Array.newInstance(elementType, elements.size()));
    }

    /**
     * Reads the document properties.
     *
     * @param target         document POJO
     * @param reader         reader
     * @param decoderContext context
     */
    protected void readDocument(final T target, final BsonReader reader, final DecoderContext decoderContext) {
        if (target == null) {
            return;
        }
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            final String elementName = reader.readName();
            final String attributeName = getAttributeName(elementName);
            final PropertyDescriptor descriptor = collectionDescriptors.get(attributeName);
            if (descriptor == null) {
                reader.skipValue();
                continue;
            }
            final Object propertyValue = readElement(descriptor, reader, decoderContext);
            try {
                descriptor.getWriteMethod().invoke(target, propertyValue);
            } catch (ReflectiveOperationException exception) {
                LOGGER.warn("Failed to write to the target property [" + attributeName + "]!", exception);
            }
        }
        reader.readEndDocument();
    }

    /**
     * Decode a collection object.
     *
     * @param reader         reader
     * @param decoderContext context
     * @return collection object
     * @throws CodecConfigurationException if failed to decode
     */
    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        T target = null;
        try {
            target = (T) collectionClass.newInstance();
        } catch (ReflectiveOperationException exception) {
            LOGGER.error("Failed to instantiate a class " + collectionClass + " with default constructor!", exception);
            throw new CodecConfigurationException("Failed to instantiate the collection class [" + collectionClass + "]");
        }
        readDocument(target, reader, decoderContext);
        return target;
    }

    /**
     * Returns BSON element name.
     * <p>
     * Override this method if the BSON element name is different with target attribute name.
     *
     * @param attributeName attribute name
     * @return BSON element name
     */
    protected String getBSONElementName(final String attributeName) {
        return attributeName;
    }

    /**
     * Writes the element value.
     *
     * @param writer         writer
     * @param elementValue   element value
     * @param encoderContext context
     * @param <S>            element class
     */
    private <S> void writeElement(final BsonWriter writer, final S elementValue, final EncoderContext encoderContext) {
        if (elementValue == null) {
            return;
        }
        Class<?> elementType = elementValue.getClass();
        if (elementType.isArray()) {
            writer.writeStartArray();
            int length = Array.getLength(elementValue);
            for (int index = 0; index < length; index++) {
                writeElement(writer, Array.get(elementValue, index), encoderContext.getChildContext());
            }
            writer.writeEndArray();
        } else if (Collection.class.isAssignableFrom(elementType)) {
            writer.writeStartArray();
            for (Object element : (Iterable) elementValue) {
                writeElement(writer, element, encoderContext.getChildContext());
            }
            writer.writeEndArray();
        } else {
            encoderContext.encodeWithChildContext(getCodec((Class<S>) elementType), writer, elementValue);
        }
    }

    /**
     * Writes the document properties.
     *
     * @param writer         writer
     * @param source         source
     * @param encoderContext context
     */
    protected void writeDocument(final BsonWriter writer, final T source, final EncoderContext encoderContext) {
        if (source == null) {
            return;
        }
        writer.writeStartDocument();
        for (PropertyDescriptor descriptor : collectionDescriptors.values()) {
            final String attributeName = descriptor.getName();
            final Object elementValue;
            try {
                elementValue = descriptor.getReadMethod().invoke(source);
            } catch (ReflectiveOperationException exception) {
                LOGGER.error("Failed to read the target property [" + attributeName + "]!", exception);
                continue;
            }
            final String elementName = getBSONElementName(attributeName);
            writer.writeName(elementName);
            writeElement(writer, elementValue, encoderContext);
        }
        writer.writeEndDocument();
    }

    /**
     * Encode a collection object.
     *
     * @param writer         writer
     * @param value          value
     * @param encoderContext context
     * @throws CodecConfigurationException if failed to encode
     */
    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
        writeDocument(writer, value, encoderContext);
    }

    /**
     * Returns a collection class.
     *
     * @return collection class
     */
    @Override
    public Class<T> getEncoderClass() {
        return collectionClass;
    }
}