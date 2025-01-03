package software.ulpgc.ImageViewer.mvc.control;

public class NextCommand implements Command {
    private final ImagePresenter presenter;

    public NextCommand(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    public void execute() {
        presenter.showNextImage();
    }
}