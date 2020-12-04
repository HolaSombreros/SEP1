package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class AddTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;

    // Inputs:

    public AddTaskController() { }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    @FXML private void cancel() {
        viewHandler.openView("taskList");
    }
}
