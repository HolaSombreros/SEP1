package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;
import java.util.Optional;

public class DetailsAndEditTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState viewState;
    private TeamMemberListViewModel viewModel;
    
    // Window data variables:
    @FXML private TextField titleInput;
    @FXML private TextField idField;
    @FXML private TextField estimatedHoursInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private ChoiceBox<String> statusInput;
    @FXML private TextField responsibleTeamMemberInput;
    @FXML private TextField hoursWorkedInput;
    @FXML private TableView<TeamMemberViewModel> teamTable;
    @FXML private TableColumn<TeamMemberViewModel, Number> idColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    @FXML private Label errorLabel;
    
    public DetailsAndEditTaskController() { }
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TeamMemberListViewModel(model, viewState);
    
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        teamTable.setItems(viewModel.getList());
        
        // Adding possible statuses to the dropdown menu so they can be selected.
        for (Status status : Status.values()) {
            statusInput.getItems().add(status.getName());
        }
        
        reset();
    }

    public void reset() {
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
        Task task = model.getTaskList(model.getProjectList().getProjectByID(viewState.getSelectedProject()), requirement).getTaskById(viewState.getSelectedTask());

        idField.setText(String.valueOf(task.getId()));
        titleInput.setText(task.getTitle());
        estimatedHoursInput.setText(String.valueOf(task.getEstimatedTime()));
        startingDateInput.setValue(LocalDate.of(task.getStartingDate().getYear(), task.getStartingDate().getMonth(), task.getStartingDate().getDay()));
        deadlineInput.setValue(LocalDate.of(task.getDeadline().getYear(), task.getDeadline().getMonth(), task.getDeadline().getDay()));
        statusInput.setValue(task.getStatus().getName());
        if (task.getResponsibleTeamMember() != null) {
            responsibleTeamMemberInput.setText(task.getResponsibleTeamMember().getFullName());
        }
        hoursWorkedInput.setText(String.valueOf(task.getTimeRegistration().getHoursWorked()));
        errorLabel.setText("");
        viewModel.update(project, model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement()));
    }
    
    public Region getRoot() {
        return root;
    }
    
    @FXML private void editDetails() {
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
        Task task = model.getTaskList(model.getProjectList().getProjectByID(viewState.getSelectedProject()), requirement).getTaskById(viewState.getSelectedTask());
        boolean editedTask = false;
        try {
            // Title:
            if (titleInput.getText().equals("")) {
                throw new IllegalStateException("The title cannot be empty");
            }
            if (!titleInput.getText().equals(task.getTitle())) {
                editedTask = true;
            }
            
            // Estimated time:
            if (estimatedHoursInput.getText().equals("")) {
                throw new IllegalStateException("The estimated time cannot be empty");
            }
            try {
                if (Double.parseDouble(estimatedHoursInput.getText()) <= 0) {
                    throw new NumberFormatException();
                }
                if (Double.parseDouble(estimatedHoursInput.getText()) != task.getEstimatedTime()) {
                    editedTask = true;
                }
            }
            catch (NumberFormatException e) {
                errorLabel.setText("Estimated time has to be a positive number higher than 0");
            }
            
            // Starting date:
            if (startingDateInput.getValue() == null) {
                throw new IllegalStateException("Starting date cannot be empty");
            }
            Date startingDate = new Date(startingDateInput.getValue().getDayOfMonth(), startingDateInput.getValue().getMonthValue(), startingDateInput.getValue().getYear());
            if (!startingDate.equals(task.getStartingDate())) {
                editedTask = true;
            }
            
            // Deadline:
            if (deadlineInput.getValue() == null) {
                throw new IllegalStateException("Deadline cannot be empty");
            }
            Date deadline = new Date(deadlineInput.getValue().getDayOfMonth(), deadlineInput.getValue().getMonthValue(), deadlineInput.getValue().getYear());
            if (!deadline.equals(task.getDeadline())) {
                editedTask = true;
            }
            Date.checkDates(startingDate, deadline);
            
            // Status:
            if (!statusInput.getValue().equals(task.getStatus().getName())) {
                editedTask = true;
            }
            /*
            // Responsible Team Member:
            if (!responsibleTeamMemberInput.getText().equals(task.getResponsibleTeamMember().getFullName())) {
                editedTask = true;
            }
            
            // TODO - is this even editable?!??
            // Hours Worked:
            if (hoursWorkedInput.getText().equals("")) {
                throw new IllegalStateException("The hours worked cannot be empty");
            }
            try {
                if (Double.parseDouble(estimatedHoursInput.getText()) < 0) {
                    throw new NumberFormatException();
                }
                if (Double.parseDouble(estimatedHoursInput.getText()) != task.getEstimatedTime()) {
                    editedTask = true;
                }
            }
            catch (NumberFormatException e) {
                errorLabel.setText("Estimated time has to be a positive number higher than 0");
            }
            
            */
            
            
            
            if (editedTask && confirmation("edit")) {
                task.setTitle(titleInput.getText());
                task.setEstimatedTime(Double.parseDouble(estimatedHoursInput.getText()));
                task.setStartingDate(startingDate);
                task.setDeadline(deadline);
                switch (statusInput.getValue()) {
                    case "Not Started":
                        task.setStatus(Status.NOT_STARTED);
                        break;
                    case "Started":
                        task.setStatus(Status.STARTED);
                        break;
                    case "Ended":
                        task.setStatus(Status.ENDED);
                        break;
                }
                if (responsibleTeamMemberInput.getText().equals("")) {
                    task.unassignResponsibleTeamMember();
                }
                else {
                    task.assignResponsibleTeamMember(task.getTeamMemberList().getByID(0));   // TODO - fix this... somehow
                }
                model.saveModel();
                goBack();
            }
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
    
    @FXML private void makeResponsible() {
        // change very little stuff
        errorLabel.setText("");
        try {
            TeamMemberViewModel selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                responsibleTeamMemberInput.setText("");
            }
            else {
                responsibleTeamMemberInput.setText(selectedItem.getNameProperty().toString());
            }
        }
        catch (Exception e) {
            errorLabel.setText("Team member not found: " + e.getMessage());
        }
    }
    
    @FXML private void removeTask() {
        errorLabel.setText("");
        if (confirmation("remove")) {
            Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
            Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
            Task task = model.getTaskList(project, requirement).getTaskById(viewState.getSelectedTask());
            model.removeTask(requirement, task);
            //model.saveModel();
            goBack();
        }
    }

    @FXML private void goBack() {
        viewState.setSelectedTask(-1);
        viewHandler.openView("taskList");
    }
    
    private boolean confirmation(String id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (id) {
            case "remove":
                alert.setTitle("Confirm removing task");
                alert.setHeaderText("Removing task #" + idField.getText() + " - " + titleInput.getText());
                break;
            case "edit":
                alert.setTitle("Confirm editing task");
                alert.setHeaderText("Editing task #" + idField.getText() + " - " + titleInput.getText());
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}