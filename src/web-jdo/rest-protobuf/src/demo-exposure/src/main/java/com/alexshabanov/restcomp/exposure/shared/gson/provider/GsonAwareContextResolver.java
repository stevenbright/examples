package com.alexshabanov.restcomp.exposure.shared.gson.provider;


import com.alexshabanov.restcomp.exposure.shared.DataTraits;
import com.alexshabanov.restcomp.exposure.shared.gson.GsonAware;
import com.google.gson.GsonBuilder;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Gson configuration provider.
 * Encapsulates all the information for gson builder configuration.
 */
@Provider
public final class GsonAwareContextResolver implements ContextResolver<GsonAware> {

    /**
     * Encapsulates all the configurations applied to the Gson Builder.
     * @param gsonBuilder Gson builder to be configured.
     */
    public static void configureGsonBuilder(GsonBuilder gsonBuilder) {
        // configure gson builder

        //gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat(DataTraits.DATE_FORMAT_PATTERN);
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
