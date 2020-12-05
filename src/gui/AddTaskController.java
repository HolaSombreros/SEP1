package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
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
        titleInput.setText("");
        startingDateInput.setValue(null);
        deadlineInput.setValue(null);
        estimatedHoursInput.setText("");
        errorLabel.setText("");
        
        /* THIS CODE IS FOR LIMITING TO FORWARD DATES ONLY! in case we want to use it... TODO - remove this
        deadlineInput.setDayCellFactory(date -> new DateCell() {    // this shit makes it so dates before TODAY are disabled and can't be selected.
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                java.time.LocalDate today = java.time.LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        */
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
