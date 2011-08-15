package org.microblog.exposure.shared.provider;

import org.microblog.exposure.model.BlogComment;
import org.microblog.exposure.model.BlogPost;
import org.microblog.exposure.model.BlogTag;
import org.microblog.exposure.model.Chunk;
import org.microblog.exposure.shared.model.NewBlogComment;
import org.microblog.exposure.shared.model.NewBlogPost;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Represents blog resource.
 */
@Produces(MediaType.APPLICATION_JSON)
public interface BlogResource {

    /**
     * Gets all the registered tags.
     * @return Tags collection.
     */
    @GET
    @Path("/tags")
    Collection<BlogTag> getAllTags();



    @GET
    @Path("/user/{userId}/posts")
    Chunk<Long> getUserPosts(@PathParam("userId") long userId,
                             @QueryParam("offset") Integer offset,
                             @QueryParam("limit") Integer limit);

    @GET
    @Path("/tag/{tagId}/posts")
    Chunk<Long> getTaggedPosts(@PathParam("tagId") long tagId,
                               @QueryParam("offset") Integer offset,
                               @QueryParam("limit") Integer limit);

    @GET
    @Path("/user/{userId}/comments")
    Chunk<Long> getUserComment(@PathParam("userId") long userId,
                               @QueryParam("offset") Integer offset,
                               @QueryParam("limit") Integer limit);

    @GET
    @Path("/post/{postId}/comments")
    Chunk<Long> getPostComment(@PathParam("postId") long postId,
                               @QueryParam("offset") Integer offset,
                               @QueryParam("limit") Integer limit);

    @GET
    @Path("/comment/{commentId}/comments")
    Chunk<Long> getChildComment(@PathParam("commentId") long commentId,
                                @QueryParam("offset") Integer offset,
                                @QueryParam("limit") Integer limit);


    @POST
    @Path("/posts")
    Collection<BlogPost> getBlogPosts(Collection<Long> postIds);

    @POST
    @Path("/comments")
    Collection<BlogComment> getBlogComments(Collection<Long> postIds);



    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    long addBlogPost(NewBlogPost newBlogPost);

    @POST
    @Path("/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    long addBlogComment(NewBlogComment newBlogComment);

    @POST
    @Path("/tag")
    long addBlogTag(@QueryParam("name") String name);
}
