package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import model.IProjectManagementModel;
import model.ProjectManagementModelManager;

public class ProjectManagementApp extends Application {
    @Override public void start(Stage stage) throws Exception {
    
        IProjectManagementModel model = ProjectManagementModelManager.loadModel();
        ViewHandler view = new ViewHandler(model);
    
        //ViewHandler view = new ViewHandler(new ProjectManagementModelManager());
        view.start(stage);
    }
}
