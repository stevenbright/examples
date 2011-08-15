package org.microblog.exposure.shared.provider;

import com.google.gson.*;
import org.microblog.exposure.shared.GsonAware;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Gson configuration provider.
 * Encapsulates all the information for gson builder configuration.
 */
@Provider
public final class GsonAwareContextResolver implements ContextResolver<GsonAware> {

    private static final class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull()) {
                return null;
            }

            final String dateStr = json.getAsString();
            try {
                return new Date(Long.parseLong(dateStr));
            } catch (NumberFormatException e) {
                throw new JsonParseException("Invalid date string: " + dateStr, e);
            }
        }

        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(src.getTime()));
        }
    }

    /**
     * Encapsulates all the configurations applied to the Gson Builder.
     * @param gsonBuilder Gson builder to be configured.
     */
    public static void configureGsonBuilder(GsonBuilder gsonBuilder) {
        // configure gson builder


        //gsonBuilder.serializeNulls();
        //gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.registerTypeAdapter(Date.class, new DateAdapter());
    }


    /**
     * {@inheritDoc}
     */
    public GsonAware getContext(Class<?> type) {
        if (!GsonAware.class.equals(type)) {
            return null;
        }

        return new GsonAware() {
            public void setGsonBuilder(GsonBuilder gsonBuilder) {
                configureGsonBuilder(gsonBuilder);
            }
        };
    }
}
