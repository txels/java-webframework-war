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
import io.micronaut.http.annotation.Produces;

@Controller("/work")
class WorkController {

	@Inject
	Storage storage;

	@Get("/{id}")
	public TextItem getItem(String id) throws IOException {
		return storage.get(id, TextItem.class);
	}

	@Post("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String createItem(@Body TextItem item) throws Exception {
		return storage.put(item);
	}
}
