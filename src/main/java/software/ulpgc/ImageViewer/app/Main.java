package software.ulpgc.ImageViewer.app;

import java.io.File;
import software.ulpgc.ImageViewer.mvc.control.ImagePresenter;
import software.ulpgc.ImageViewer.mvc.control.NextCommand;
import software.ulpgc.ImageViewer.mvc.control.PreviousCommand;
import software.ulpgc.ImageViewer.mvc.io.FileImageLoader;
import software.ulpgc.ImageViewer.mvc.io.ImageLoader;

public class Main {

    public static void main(String[] args) {
        File folder = new File("C:\\Users\\User\\Universidad\\3º\\IS2\\proyecto2\\images");
        ImageLoader loader = new FileImageLoader(folder);
        MainFrame mainFrame = new MainFrame();
        ImagePresenter presenter = new ImagePresenter(mainFrame.getDisplay(), loader);
        mainFrame
                .put("previous", new PreviousCommand(presenter))
                .put("next", new NextCommand(presenter))
                .setVisible(true);
    }
}