package at.spengergasse.fivepanels.model.common;

import static at.spengergasse.fivepanels.foundation.Assert.*;

public class Media extends Content {

    // not null, not blank, max 255 characters
    private String path;
    // not null, not blank, max 128 characters
    private String mime;
    // not null, 0 or greater, max 50 000 000
    private Integer fileSize;

    public Media(String path, String mime, Integer fileSize) {
        setPath(path);
        setMime(mime);
        setFileSize(fileSize);
    }

    public void setPath(String path) {
        this.path = hasMaxLength(path, 256, "path");
    }

    public void setMime(String mime) {
        this.mime = hasMaxLength(mime, 129, "mime");
    }

    public void setFileSize(Integer fileSize) {
        isGreaterThanOrEqual(fileSize, "fileSize", 0, "0");
        this.fileSize = isLowerThanOrEqual(fileSize, "fileSize", 50000000, "50000000");
    }

    @Override
    public String toString() {
        if (path.contains("/"))
            return path.substring(path.lastIndexOf("/"));
        return path;
    }
}
