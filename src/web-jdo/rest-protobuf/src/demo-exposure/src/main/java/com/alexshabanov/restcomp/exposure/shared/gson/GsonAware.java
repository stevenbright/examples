package com.alexshabanov.restcomp.exposure.shared.gson;


import com.google.gson.GsonBuilder;

/**
 * The interface that shall be implemented by the GSON configurer in the spring configuration.
 * TODO: [GsonMessageBodyHandler] This is the reusable class.
 */
public interface GsonAware {
    /**
     * Callback that supplies the GSON builder to the bean instance.
     * @param gsonBuilder GSON builder that is used internally in the message body handler.
     */
    public void setGsonBuilder(GsonBuilder gsonBuilder);
}
