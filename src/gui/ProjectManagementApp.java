package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import model.ProjectManagementModelManager;

public class ProjectManagementApp extends Application {
    @Override public void start(Stage stage) throws Exception {
        ViewHandler view = new ViewHandler(new ProjectManagementModelManager());
        view.start(stage);
    }
}
