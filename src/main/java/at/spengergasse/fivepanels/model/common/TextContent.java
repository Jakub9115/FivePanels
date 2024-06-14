package at.spengergasse.fivepanels.model.common;

import static at.spengergasse.fivepanels.foundation.Assert.*;

public class TextContent extends Content {
    // not null, not blank, max 1024 characters
    private String text;

    public TextContent(String text) {
        setText(text);
    }

    public void setText(String text) {
        this.text = hasMaxLength(text, 1025, "text");
    }

    @Override
    public String toString() {
        return text;
    }
}
