package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class EditAndViewTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    
    // Inputs:
    
    public EditAndViewTaskController() { }
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        //choiceBoxInput.getItems().addAll("Not Started", "Started", "Ended"); // Takes a string array.
        updateData();
    }

    public void reset() {
        updateData();
    }
    
    public void updateData() {
        //choiceBoxInput.setValue(choiceBoxInput.getItems().get(1));    // Sets default selected value as 1st element of string array.
    }
    
    public Region getRoot() {
        return root;
    }
    
    @FXML private void editTaskDetails() {
        // do stuff
    }

    @FXML private void goBack() {
        viewHandler.openView("taskList");
    }
}