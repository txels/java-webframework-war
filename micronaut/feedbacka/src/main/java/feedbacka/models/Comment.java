package feedbacka.models;

public class Comment {

	public Comment() {
	};

	public Comment(String workId, String text) {
		this.workId = workId;
		this.text = text;
	}

	public String getWorkId() {
		return this.workId;
	}

	public String getText() {
		return this.text;
	}

	public String createId() {
		return Long.toHexString(System.currentTimeMillis());
	}

	private String workId;
	private String text;
}
