package com.mongodb.app.codec;

import com.mongodb.MongoClient;
import mockit.Deencapsulation;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;
import org.bson.json.JsonWriter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.testng.AssertJUnit.*;

/**
 * Abstract codec - unit test.
 *
 * @author nico.arianto
 */
public class AbstractCodecTest {

    /**
     * Java Bean - test class.
     *
     * @author nico.arianto
     */
    public static class BeanTest {
        private String stringProp;
        private Integer intProp;
        private Long longProp;
        private Double doubleProp;
        private Boolean booleanProp;
        private Date dateProp;
        private Integer[] arrayProp;
        private List<Long> listProp;
        private Object setterProp;
        private Object getterProp;

        /**
         * Returns string property.
         *
         * @return string property
         */
        public String getStringProp() {
            return stringProp;
        }

        /**
         * Sets string property.
         *
         * @param stringProp string property
         */
        public void setStringProp(String stringProp) {
            this.stringProp = stringProp;
        }

        /**
         * Returns integer property.
         *
         * @return integer property
         */
        public Integer getIntProp() {
            return intProp;
        }

        /**
         * Sets integer property.
         *
         * @param intProp integer property
         */
        public void setIntProp(Integer intProp) {
            this.intProp = intProp;
        }

        /**
         * Returns long property.
         *
         * @return long property
         */
        public Long getLongProp() {
            return longProp;
        }

        /**
         * Sets long property.
         *
         * @param longProp long property
         */
        public void setLongProp(Long longProp) {
            this.longProp = longProp;
        }

        /**
         * Returns double property.
         *
         * @return double property
         */
        public Double getDoubleProp() {
            return doubleProp;
        }

        /**
         * Sets double property.
         *
         * @param doubleProp double property
         */
        public void setDoubleProp(Double doubleProp) {
            this.doubleProp = doubleProp;
        }

        /**
         * Returns boolean property.
         *
         * @return boolean property
         */
        public Boolean getBooleanProp() {
            return booleanProp;
        }

        /**
         * Sets boolean property.
         *
         * @param booleanProp boolean property
         */
        public void setBooleanProp(Boolean booleanProp) {
            this.booleanProp = booleanProp;
        }

        /**
         * Returns date property.
         *
         * @return date property
         */
        public Date getDateProp() {
            return dateProp;
        }

        /**
         * Sets date property.
         *
         * @param dateProp date property
         */
        public void setDateProp(Date dateProp) {
            this.dateProp = dateProp;
        }

        /**
         * Returns array property.
         *
         * @return array property
         */
        public Integer[] getArrayProp() {
            return arrayProp;
        }

        /**
         * Sets array property.
         *
         * @param arrayProp array property
         */
        public void setArrayProp(Integer[] arrayProp) {
            this.arrayProp = arrayProp;
        }

        /**
         * Returns list property.
         *
         * @return list property
         */
        public List<Long> getListProp() {
            return listProp;
        }

        /**
         * Sets list property.
         *
         * @param listProp list property
         */
        public void setListProp(List<Long> listProp) {
            this.listProp = listProp;
        }

        /**
         * Sets setter property.
         *
         * @param setterProp setter property
         */
        public void setSetterProp(Object setterProp) {
            this.setterProp = setterProp;
        }

        /**
         * Returns getter property.
         *
         * @return getter property
         */
        public Object getGetterProp() {
            return getterProp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeanTest beanTest = (BeanTest) o;
            if (stringProp != null ? !stringProp.equals(beanTest.stringProp) : beanTest.stringProp != null)
                return false;
            if (intProp != null ? !intProp.equals(beanTest.intProp) : beanTest.intProp != null) return false;
            if (longProp != null ? !longProp.equals(beanTest.longProp) : beanTest.longProp != null) return false;
            if (doubleProp != null ? !doubleProp.equals(beanTest.doubleProp) : beanTest.doubleProp != null)
                return false;
            if (booleanProp != null ? !booleanProp.equals(beanTest.booleanProp) : beanTest.booleanProp != null)
                return false;
            if (dateProp != null ? !dateProp.equals(beanTest.dateProp) : beanTest.dateProp != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(arrayProp, beanTest.arrayProp)) return false;
            return listProp != null ? listProp.equals(beanTest.listProp) : beanTest.listProp == null;
        }

        @Override
        public int hashCode() {
            int result = stringProp != null ? stringProp.hashCode() : 0;
            result = 31 * result + (intProp != null ? intProp.hashCode() : 0);
            result = 31 * result + (longProp != null ? longProp.hashCode() : 0);
            result = 31 * result + (doubleProp != null ? doubleProp.hashCode() : 0);
            result = 31 * result + (booleanProp != null ? booleanProp.hashCode() : 0);
            result = 31 * result + (dateProp != null ? dateProp.hashCode() : 0);
            result = 31 * result + Arrays.hashCode(arrayProp);
            result = 31 * result + (listProp != null ? listProp.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "BeanTest{" +
                    "stringProp='" + stringProp + '\'' +
                    ", intProp=" + intProp +
                    ", longProp=" + longProp +
                    ", doubleProp=" + doubleProp +
                    ", booleanProp=" + booleanProp +
                    ", dateProp=" + dateProp +
                    ", arrayProp=" + Arrays.toString(arrayProp) +
                    ", listProp=" + listProp +
                    '}';
        }
    }

    final AbstractCodec<BeanTest> codec = new AbstractCodec<BeanTest>(MongoClient.getDefaultCodecRegistry(), BeanTest.class) {
    };
    final String stringProp = "stringProp";
    final Integer intProp = 9;
    final Long longProp = 99l;
    final Double doubleProp = 99.9;
    final Boolean booleanProp = true;
    final Date dateProp = Calendar.getInstance().getTime();
    final Integer[] arrayProp = new Integer[]{1, 2, 3, 4, 5};
    final List<Long> listProp = Arrays.asList(10l, 20l, 30l, 40l, 50l);
    final BeanTest bean = new BeanTest();
    String json;

    /**
     * Initialize input document.
     */
    @BeforeClass
    public void init() {
        final Document document = new Document();
        document.append("stringProp", stringProp);
        document.append("intProp", intProp);
        document.append("longProp", longProp);
        document.append("doubleProp", doubleProp);
        document.append("booleanProp", booleanProp);
        document.append("dateProp", dateProp);
        document.append("arrayProp", Arrays.asList(arrayProp));
        document.append("listProp", listProp);
        bean.setStringProp(stringProp);
        bean.setIntProp(intProp);
        bean.setLongProp(longProp);
        bean.setDoubleProp(doubleProp);
        bean.setBooleanProp(booleanProp);
        bean.setDateProp(dateProp);
        bean.setArrayProp(arrayProp);
        bean.setListProp(listProp);
        json = document.toJson();
    }

    /**
     * Test decode({@link BsonReader}, {@link DecoderContext}) method.
     */
    @Test
    public void testDecode() {
        final BeanTest actualBean = codec.decode(new JsonReader(json), DecoderContext.builder().build());
        assertNotNull(actualBean);
        assertEquals(bean, actualBean);
    }

    /**
     * Test encode({@link BsonWriter}, T, {@link EncoderContext}) method.
     */
    @Test
    public void testEncode() {
        final StringWriter writer = new StringWriter();
        codec.encode(new JsonWriter(writer), bean, EncoderContext.builder().build());
        final String actualJSON = writer.toString();
        assertNotNull(actualJSON);
        assertFalse(actualJSON.isEmpty());
        assertTrue(json.length() == actualJSON.length());
        final BeanTest actualBean = codec.decode(new JsonReader(actualJSON), DecoderContext.builder().build());
        assertNotNull(actualBean);
        assertEquals(bean, actualBean);
    }

    /**
     * Test decode({@link BsonReader}, {@link DecoderContext}) method with a NULL {@link CodecRegistry}.
     */
    @Test(expectedExceptions = CodecConfigurationException.class)
    public void testDecodeWithNullCodecRegistry() {
        final AbstractCodec<BeanTest> codec = new AbstractCodec<BeanTest>(null, BeanTest.class) {
        };
        codec.decode(new JsonReader(json), DecoderContext.builder().build());
    }

    /**
     * Test decode({@link BsonReader}, {@link DecoderContext}) method with a NULL {@link BsonReader}.
     */
    @Test
    public void testDecodeWithNullDocument() {
        final BeanTest actualBean = Deencapsulation.invoke(codec, "readDocument",
                new Class<?>[]{Object.class, BsonReader.class, DecoderContext.class}, null, new JsonReader(json),
                DecoderContext.builder().build());
        assertNull(actualBean);
    }

    /**
     * Test decode({@link BsonReader}, {@link DecoderContext}) method with an unsupported attribute name.
     */
    @Test
    public void testDecodeWithUnsupportedAttributeName() {
        final Document document = new Document();
        document.append("stringProp", stringProp);
        document.append("numberProp", doubleProp);
        final BeanTest expectedBean = new BeanTest();
        expectedBean.setStringProp(stringProp);
        final BeanTest actualBean = codec.decode(new JsonReader(document.toJson()), DecoderContext.builder().build());
        assertNotNull(actualBean);
        assertEquals(expectedBean, actualBean);
    }

    /**
     * Test AbstractCodec({@link CodecRegistry}, Class) constructor throw {@link CodecConfigurationException}.
     */
    @Test(expectedExceptions = CodecConfigurationException.class)
    public void testConstructorThrowCodecConfigurationException() {
        new AbstractCodec<List>(MongoClient.getDefaultCodecRegistry(), List.class) {
        };
    }
}