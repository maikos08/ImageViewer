package software.ulpgc.ImageViewer.mvc.model;

import java.io.IOException;

public interface Image {
    byte[] content() throws IOException;

    Image next();

    Image previous();

    public static enum Format {
        png,
        jpeg,
        jpg;
    }
}