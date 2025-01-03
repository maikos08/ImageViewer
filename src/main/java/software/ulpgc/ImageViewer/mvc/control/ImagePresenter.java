package software.ulpgc.ImageViewer.mvc.control;

import java.io.IOException;
import software.ulpgc.ImageViewer.mvc.io.ImageLoader;
import software.ulpgc.ImageViewer.mvc.model.Image;
import software.ulpgc.ImageViewer.mvc.view.ImageDisplay;
import software.ulpgc.ImageViewer.mvc.view.ImageDisplay.PaintOrder;

public class ImagePresenter {
    private final ImageDisplay display;
    private final ImageLoader loader;
    private Image image;
    private Image nextImage;
    private Image prevImage;

    public ImagePresenter(ImageDisplay display, ImageLoader loader) {
        this.display = display;
        this.loader = loader;
        this.display.on(shift());
        this.display.on(released());
        this.obtainFirstImage();
    }

    private void obtainFirstImage() {
        this.image = loader.load();
        this.getConsecutiveImages(image);

        try {
            this.display.paint(new PaintOrder(this.image.content(), 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getConsecutiveImages(Image current) {
        this.prevImage = current.previous();
        this.nextImage = current.next().next();
    }

    private ImageDisplay.Shift shift() {
        return new ImageDisplay.Shift() {
            public void offset(int offset) {
                if (offset > 0) {
                    display.paint(paintOrderForCurrentImageWith(offset), paintOrderForPreviousImageWith(offset - display.width()));
                } else if (offset < 0) {
                    display.paint(paintOrderForCurrentImageWith(offset), paintOrderForNextImageWith(offset + display.width()));
                }

            }
        };
    }

    private ImageDisplay.Released released() {
        return new ImageDisplay.Released() {
            public void offset(int offset) {
                int threshold = display.width() / 2;
                if (offsetSurpassLimit(offset, threshold)) {
                    if (offset > 0) {
                        image = image.previous();
                    } else {
                        image = image.next();
                    }
                }

                getConsecutiveImages(image);
                display.paint(paintOrderForCurrentImageWith(0));
            }

            private static boolean offsetSurpassLimit(int offset, int threshold) {
                return Math.abs(offset) > threshold;
            }
        };
    }

    private PaintOrder paintOrderForNextImageWith(int offset) {
        try {
            return new PaintOrder(this.nextImage.content(), offset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PaintOrder paintOrderForPreviousImageWith(int offset) {
        try {
            return new PaintOrder(this.prevImage.content(), offset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PaintOrder paintOrderForCurrentImageWith(int offset) {
        try {
            return new PaintOrder(this.image.content(), offset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showPreviousImage() {
        image = image.previous();
        getConsecutiveImages(this.image);
        display.paint(paintOrderForCurrentImageWith(0));
    }

    public void showNextImage() {
        image = image.next();
        getConsecutiveImages(this.image);
        display.paint(paintOrderForCurrentImageWith(0));
    }
}