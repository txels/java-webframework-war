package feedbacka;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import feedbacka.models.Comment;
import feedbacka.repos.Storage;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/work/{workid}/comments")
class CommentController {

	@Inject
	Storage storage;

	@Get("/{id}")
	public Comment getComment(String workid, String id) throws IOException {
		return storage.get(workid + File.separator + id, Comment.class);
	}

	@Get("/")
	public Iterable<Comment> getAllComments(String workid) throws Exception {
		return storage.getAll(workid);
	}

	@Post("/")
	public String addComment(String workid, @Body String text) throws Exception {
		return storage.put(new Comment(workid, text)).split("/")[1];
	}
}
