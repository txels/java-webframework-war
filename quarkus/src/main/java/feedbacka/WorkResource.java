package feedbacka;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import feedbacka.models.TextItem;
import feedbacka.repos.Storage;

@Path("/work")
public class WorkResource {

	@Inject
	Storage storage;

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TextItem getItem(@PathParam("id") String id) throws IOException {
		return storage.get(id, TextItem.class);
	}

	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String createItem(TextItem item) throws Exception {
		//HashMap<String, String> json = new HashMap<String, String>();
		String id = storage.put(item);
		//json.put("id", id);
		return id;
	}
}
