package feedbacka;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import feedbacka.models.Comment;
import feedbacka.repos.Storage;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/work")
class CommentController {

	@Inject
	Storage storage;

	@Get(value = "/{workid}/comments/{id}", produces = MediaType.TEXT_PLAIN)
	public String getItem(String workid, String id) throws IOException {
		return storage.get(workid + File.separator + id).getText();
	}

	@Get(value = "/{workid}/comments/")
	public Iterable<String> putItem(String workid) throws Exception {
		return storage.getAll(workid);
	}

	@Post(value = "/{workid}/comments/", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
	public String putItem(String workid, @Body String text) throws Exception {
		return storage.put(new Comment(workid, text)).split("/")[1];
	}
}
