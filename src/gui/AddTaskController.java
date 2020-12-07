package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.Date;
import model.IProjectManagementModel;
import model.Task;

import java.time.LocalDate;

public class AddTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState viewState;
    
    // Window data variables:
    @FXML private TextField titleInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private TextField estimatedHoursInput;
    @FXML private Label errorLabel;

    public AddTaskController() { }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState state) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = state;
        
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
        errorLabel.setText("");
        try {
            String startingDateArr[] = startingDateInput.getValue().toString().split("-");
            String deadlineArr[] = deadlineInput.getValue().toString().split("-");
            Date startingDate = new Date(Integer.parseInt(startingDateArr[2]), Integer.parseInt(startingDateArr[1]), Integer.parseInt(startingDateArr[0]));
            Date deadline = new Date(Integer.parseInt(deadlineArr[2]), Integer.parseInt(deadlineArr[1]), Integer.parseInt(deadlineArr[0]));
            // TODO - fix line below.
            //model.addTask(model.getRequirementList(), new Task(titleInput.getText(), startingDate, deadline, Double.parseDouble(estimatedHoursInput.getText()), model.getProjectList().getProject(0).getProjectRequirementList().getRequirement(0)));
            model.saveModel();
            cancel();
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML private void cancel() {
        viewHandler.openView("taskList");
    }
}
