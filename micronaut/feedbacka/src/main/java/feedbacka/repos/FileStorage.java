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

import feedbacka.models.Comment;
import feedbacka.models.TextItem;
import io.micronaut.context.annotation.Value;

@Singleton
public class FileStorage implements Storage {

	@Value("${storage.location}")
	String location;

	public FileStorage() {
	}

	public TextItem get(String id) throws IOException {
		return new TextItem(readFromDisc(id));
	}

	public String put(TextItem item) throws IOException {
		String id = item.createId();
		saveToDisc(id, item.getText());
		return id;
	}

	@Override
	public String put(Comment comment) throws IOException {
		String id = comment.getWorkId() + File.separator + comment.createId();
		saveToDisc(id, comment.getText());
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
		return new String(Files.readAllBytes(Paths.get(filename)), UTF_8);
	}

	@Override
	public Iterable<String> getAll(String workid) throws IOException {
		String path = makePath(workid);
		String contents[] = new File(path).list();
		List<String> comments = new ArrayList<String>();
		for (String commentFile : Arrays.asList(contents)) {
			comments.add(this.get(workid + File.separator + commentFile.split("\\.")[0]).getText());
		}
		return comments;
	}

}