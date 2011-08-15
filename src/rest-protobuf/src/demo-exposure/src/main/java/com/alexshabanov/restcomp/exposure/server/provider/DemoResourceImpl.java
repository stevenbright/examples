package com.alexshabanov.restcomp.exposure.server.provider;

import com.alexshabanov.restcomp.exposure.server.AbstractResource;
import com.alexshabanov.restcomp.exposure.server.service.DemoService;
import com.alexshabanov.restcomp.exposure.shared.DemoResource;
import com.alexshabanov.restcomp.exposure.shared.model.NumHolder;
import com.alexshabanov.restcomp.model.NumHolderProtobuf;

import javax.ws.rs.Path;
import java.util.Collection;

/**
 * Demo resource implementation.
 */
@Path("/demo")
public final class DemoResourceImpl extends AbstractResource implements DemoResource {
    /**
     * {@inheritDoc}
     */
    public Collection<Integer> getFavoriteNumbers() {
        return getService(DemoService.class).getFavoriteNumbers();
    }

    /**
     * {@inheritDoc}
     */
    public void addNumber(NumHolder numHolder) {
        if (numHolder == null) {
            throw new IllegalArgumentException("number is null");
        }

        getService(DemoService.class).addNumber(numHolder.getNumber());
    }

    /**
     * {@inheritDoc}
     */
    public int getSum() {
        return getService(DemoService.class).getSum();
    }


    /**
     * {@inheritDoc}
     */
    public void addPbNumber(NumHolderProtobuf.NumHolder numHolder) {
        if (numHolder == null) {
            throw new IllegalArgumentException("number is null");
        }

        getService(DemoService.class).addNumber(numHolder.getNumber());
    }

    /**
     * {@inheritDoc}
     */
    public NumHolderProtobuf.NumHolder getPbSum() {
        return NumHolderProtobuf.NumHolder.newBuilder()
                .setNumber(getService(DemoService.class).getSum())
                .build();
    }
}
