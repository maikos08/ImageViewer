package software.ulpgc.ImageViewer.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import software.ulpgc.ImageViewer.mvc.io.ImageDeserializer;

public class SwingImageDeserializer implements ImageDeserializer {
    public SwingImageDeserializer() {
    }

    public Object deserialize(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}