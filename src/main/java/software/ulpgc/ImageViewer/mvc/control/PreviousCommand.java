package software.ulpgc.ImageViewer.mvc.control;

public class PreviousCommand implements Command {
    private final ImagePresenter presenter;

    public PreviousCommand(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    public void execute() {
        presenter.showPreviousImage();
    }
}
