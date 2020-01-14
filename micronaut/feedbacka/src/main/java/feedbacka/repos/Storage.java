package feedbacka.repos;

import java.io.IOException;

import feedbacka.models.Comment;
import feedbacka.models.TextItem;

public interface Storage {
	public TextItem get(String id) throws IOException;

	public String put(TextItem item) throws IOException;

	public String put(Comment comment) throws IOException;

	public Iterable<String> getAll(String workid) throws IOException;
}
