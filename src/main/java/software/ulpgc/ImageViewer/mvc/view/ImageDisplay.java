package software.ulpgc.ImageViewer.mvc.view;

import software.ulpgc.ImageViewer.mvc.model.Image;

public interface ImageDisplay {
    void on(Shift shift);
    void on(Released released);

    void paint(PaintOrder... orders);
    record PaintOrder(byte[] content, int offset) {}

    int width();

    interface Shift {
        Shift Null = offset -> {};
        void offset(int offset);
    }

    interface Released {
        Released Null = offset -> {};
        void offset(int offset);
    }
}
