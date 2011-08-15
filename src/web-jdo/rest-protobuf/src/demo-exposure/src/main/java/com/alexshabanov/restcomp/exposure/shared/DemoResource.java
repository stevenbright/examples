package com.alexshabanov.restcomp.exposure.shared;

import com.alexshabanov.restcomp.exposure.shared.model.NumHolder;
import com.alexshabanov.restcomp.model.NumHolderProtobuf;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Demo REST resource - REST service facade.
 */
public interface DemoResource {

    @GET
    @Path("/fav")
    @Produces(MediaType.APPLICATION_JSON)
    Collection<Integer> getFavoriteNumbers();



    @POST
    @Path("/num")
    @Consumes(MediaType.APPLICATION_JSON)
    void addNumber(NumHolder numHolder);

    @GET
    @Path("/sum")
    @Produces(MediaType.APPLICATION_JSON)
    int getSum();



    @POST
    @Path("/pb/num")
    @Consumes(DataTraits.PROTOBUF_MEDIATYPE)
    void addPbNumber(NumHolderProtobuf.NumHolder numHolder);

    @GET
    @Path("/pb/sum")
    @Produces(DataTraits.PROTOBUF_MEDIATYPE)
    NumHolderProtobuf.NumHolder getPbSum();
}
