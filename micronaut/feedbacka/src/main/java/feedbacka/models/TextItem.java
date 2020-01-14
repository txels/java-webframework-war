package feedbacka.models;

import java.util.UUID;

public class TextItem {

	public TextItem() {
	};

	public TextItem(String content) {
		this.text = content;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String content) {
		this.text = content;
	}

	public String createId() {
		return UUID.randomUUID().toString();
	}

	private String text;
}
