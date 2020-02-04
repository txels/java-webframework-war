package feedbacka.repos;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import feedbacka.models.Comment;
import feedbacka.models.TextItem;

@Singleton
public class FileStorage implements Storage {

	@ConfigProperty(name = "storage.location")
	String location;

	private final ObjectMapper mapper = new ObjectMapper();

	public FileStorage() {
	}

	public String getLocation() {
		return location;
	}

	public <T> T get(String id, Class<T> clazz) throws IOException {
		return mapper.readValue(readFromDisc(id), clazz);
	}

	public String put(TextItem item) throws IOException {
		String jsonItem = mapper.writeValueAsString(item);
		saveToDisc(item.getId(), jsonItem);
		return item.getId();
	}

	@Override
	public String put(Comment comment) throws IOException {
		String jsonComment = mapper.writeValueAsString(comment);
		String id = comment.getWorkId() + File.separator + comment.getId();
		saveToDisc(id, jsonComment);
		return id;
	}

	private String makePath(String name) {
		return location + File.separator + name;
	}

	private void saveToDisc(String name, String text) throws IOException {
		String filename = makePath(name) + ".txt";
		Path filepath = Paths.get(filename);
		Files.createDirectories(filepath.getParent());
		try (OutputStream outputStream = new FileOutputStream(filename)) {
			outputStream.write(text.getBytes(UTF_8));
		}
	}

	private String readFromDisc(String name) throws IOException {
		String filename = makePath(name) + ".txt";
		return new String(Files.readAllBytes(Paths.get(filename)), UTF_8).trim();
	}

	@Override
	public Iterable<Comment> getAll(String workid) throws IOException {
		String path = makePath(workid);
		List<Comment> comments = new ArrayList<Comment>();
		String commentFiles[] = new File(path).list();
		if (commentFiles != null) {
			for (String commentFile : Arrays.asList(commentFiles)) {
				comments.add(
						this.get(workid + File.separator + commentFile.split("\\.")[0],
								Comment.class));
			}
		}
		return comments;
	}

}
