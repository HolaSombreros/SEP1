package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class AddTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    
    // Window data variables:
    @FXML private TextField titleInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private TextField estimatedHoursInput;
    @FXML private Label errorLabel;

    public AddTaskController() { }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        
        reset();
    }

    public void reset() {
        errorLabel.setText("");
    }

    public Region getRoot() {
        return root;
    }
    
    @FXML private void add() {
        // add task to the list of tasks
    }

    @FXML private void cancel() {
        viewHandler.openView("taskList");
    }
}
