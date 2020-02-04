package feedbacka;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import feedbacka.models.Comment;
import feedbacka.repos.Storage;

@Path("/work/{workid}/comments")
public class CommentResource {

	@Inject
	Storage storage;

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getComment(@PathParam("workid") String workid,
			@PathParam("id") String id) throws IOException {
		return storage.get(workid + File.separator + id, Comment.class);
	}

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<Comment> getAllComments(@PathParam("workid") String workid)
			throws Exception {
		return storage.getAll(workid);
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addComment(@PathParam("workid") String workid, Comment comment)
			throws Exception {
		comment.setWorkId(workid);
		String commentId = storage.put(comment).split("/")[1];
		// comment.setId(commentId);
		return commentId;
	}
}
