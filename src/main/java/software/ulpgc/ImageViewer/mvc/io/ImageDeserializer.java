package software.ulpgc.ImageViewer.mvc.io;

public interface ImageDeserializer {
    Object deserialize(byte[] image);
}