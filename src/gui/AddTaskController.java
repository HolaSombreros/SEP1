package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.*;

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
    
    @FXML private TextField requirementStart;
    @FXML private TextField requirementEnd;

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
        
        
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
        requirementStart.setText(requirement.getStartingDate().toString());
        requirementEnd.setText(requirement.getDeadline().toString());
        
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
            if (titleInput.getText().equals("")) {
                throw new IllegalStateException("The title cannot be empty");
            }
            if (startingDateInput.getValue() == null) {
                throw new IllegalStateException("Starting date cannot be empty");
            }
            if (deadlineInput.getValue() == null) {
                throw new IllegalStateException("Starting date cannot be empty");
            }
            Date startingDate = new Date(startingDateInput.getValue().getDayOfMonth(), startingDateInput.getValue().getMonthValue(), startingDateInput.getValue().getYear());
            Date deadline = new Date(deadlineInput.getValue().getDayOfMonth(), deadlineInput.getValue().getMonthValue(), deadlineInput.getValue().getYear());
            double estimatedTime = 0;
            if (estimatedHoursInput.getText().equals("")) {
                throw new IllegalStateException("Estimated time cannot be empty");
            }
            try {
                estimatedTime = Double.parseDouble(estimatedHoursInput.getText());
            }
            catch (NumberFormatException e) {
                throw new IllegalStateException("Estimated time has to be a number");
            }
            Requirement relatedRequirement = model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement());
            Task task = new Task(titleInput.getText(), startingDate, deadline, estimatedTime, relatedRequirement);
            model.addTask(relatedRequirement, task);
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
