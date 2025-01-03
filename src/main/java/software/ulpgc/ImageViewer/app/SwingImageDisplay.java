package software.ulpgc.ImageViewer.app;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;
import software.ulpgc.ImageViewer.mvc.io.ImageDeserializer;
import software.ulpgc.ImageViewer.mvc.view.ImageDisplay;
import software.ulpgc.ImageViewer.mvc.view.ViewPort;


public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final ImageDeserializer deserializer;
    private final List<ImageDisplay.PaintOrder> paintOrders;
    private ImageDisplay.Shift shift;
    private ImageDisplay.Released released;
    private int lastMouseX;

    public SwingImageDisplay(ImageDeserializer deserializer) {
        this.shift = Shift.Null;
        this.released = Released.Null;
        this.deserializer = deserializer;
        this.paintOrders = new ArrayList<>();
        this.addMouseListener(this.mouseListener());
        this.addMouseMotionListener(this.mouseMotionListener());
    }

    public void on(ImageDisplay.Shift shift) {
        this.shift = shift;
    }

    public void on(ImageDisplay.Released released) {
        this.released = released;
    }

    public void paint(ImageDisplay.PaintOrder... orders) {
        removeAll();
        this.paintOrders.clear();
        Collections.addAll(paintOrders, orders);
        repaint();
    }

    public int width() {
        return this.getWidth();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        for (PaintOrder order : paintOrders) {
            paintOrder(g, order);
        }
    }


    private void paintOrder(Graphics g, ImageDisplay.PaintOrder order) {
        BufferedImage image = (BufferedImage) deserializer.deserialize(order.content());
        ViewPort viewPort = ViewPort.ofSize(width(), this.getHeight()).fit(image.getWidth(), image.getHeight());
        g.drawImage(image, viewPort.x() + order.offset(), viewPort.y(), viewPort.width(), viewPort.height(), null);
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                int offset = e.getX() - lastMouseX;
                shift.offset(offset);
            }

            public void mouseMoved(MouseEvent e) {
            }
        };
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
            }

            public void mouseReleased(MouseEvent e) {
                int offset = e.getX() - lastMouseX;
                released.offset(offset);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        };
    }
}