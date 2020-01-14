package feedbacka;

import java.io.IOException;

import javax.inject.Inject;

import feedbacka.models.TextItem;
import feedbacka.repos.Storage;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/work")
class WorkController {

	@Inject
	Storage storage;

	@Get(value = "/{id}", produces = MediaType.TEXT_PLAIN)
	public String getItem(String id) throws IOException {
		return storage.get(id).getText();
	}

	@Post(value = "/", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
	public String putItem(@Body String text) throws Exception {
		return storage.put(new TextItem(text));
	}
}
