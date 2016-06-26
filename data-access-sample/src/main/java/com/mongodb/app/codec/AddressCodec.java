package com.mongodb.app.codec;

import com.mongodb.app.collection.Address;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Address codec.
 *
 * @author nico.arianto
 */
public class AddressCodec extends AbstractCodec<Address> {

    /**
     * Constructor.
     *
     * @param registry registry
     */
    public AddressCodec(CodecRegistry registry) {
        super(registry, Address.class);
    }
}