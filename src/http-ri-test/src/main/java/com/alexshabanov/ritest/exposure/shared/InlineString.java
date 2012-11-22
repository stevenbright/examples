package com.alexshabanov.ritest.exposure.shared;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializableWithType;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.io.IOException;

@JsonDeserialize(using = InlineString.Deserializer.class)
public final class InlineString implements JsonSerializableWithType {
    private final String value;

    public String getValue() {
        return value;
    }

    public InlineString(String value) {
        this.value = value;
    }

    @Override
    public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer)
            throws IOException {
        serialize(jgen, provider);
    }

    @Override
    public void serialize(JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value);
    }

    public static final class Deserializer extends JsonDeserializer<InlineString> {
        @Override
        public InlineString deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return new InlineString(parser.readValueAs(String.class));
        }
    }
}