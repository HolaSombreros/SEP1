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
    @FXML private TextField timeRegisterInput;
    @FXML private TextField totalHoursWorked;
    @FXML private TableView<TeamMemberViewModel> teamTable;
    @FXML private TableColumn<TeamMemberViewModel, Number> idColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    @FXML private Label errorLabel;
    
    @FXML private TextField projectTitle;
    @FXML private TextField requirementTitle;
    @FXML private TextField requirementStart;
    @FXML private TextField requirementEnd;
    
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
            responsibleTeamMemberInput.setText("#" + task.getResponsibleTeamMember().getId() + " " + task.getResponsibleTeamMember().getFullName());
        }
        timeRegisterInput.setText("");
        totalHoursWorked.setText(String.valueOf(task.getTimeRegistration().getHoursWorked()));
        errorLabel.setText("");
        
        projectTitle.setText(project.getName());
        requirementTitle.setText(requirement.getUserStory());
        requirementStart.setText(requirement.getStartingDate().toString());
        requirementEnd.setText(requirement.getDeadline().toString());
        
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
            
            // Responsible Team Member:
            if (task.getResponsibleTeamMember() != null) {
                TeamMember responsibleTeamMember = task.getResponsibleTeamMember();
                TeamMember teamMember = task.getTeamMemberList().getByID(Integer.parseInt(responsibleTeamMemberInput.getText().split(" ")[0].substring(1)));
                if (!responsibleTeamMember.equals(teamMember)) {
                    editedTask = true;
                }
            }
            else if (!responsibleTeamMemberInput.getText().equals("") && task.getResponsibleTeamMember() == null ||
                responsibleTeamMemberInput.getText().equals("") && task.getResponsibleTeamMember() != null) {
                editedTask = true;
            }
            
            // Time registration is handled in the registerTime() method as it's a button on its own.
            
            if (editedTask && confirmation("edit")) {
                Status status = null;
                switch (statusInput.getValue()) {
                    case "Not Started":
                        status = Status.NOT_STARTED;
                        break;
                    case "Started":
                        status = Status.STARTED;
                        break;
                    case "Ended":
                        status = Status.ENDED;
                        break;
                }
                TeamMember responsibleTeamMember = null;
                if (!responsibleTeamMemberInput.getText().equals("")) {
                    responsibleTeamMember = task.getTeamMemberList().getByID(Integer.parseInt(responsibleTeamMemberInput.getText().split(" ")[0].substring(1)));
                }
                model.editTask(task, titleInput.getText(), Double.parseDouble(estimatedHoursInput.getText()), startingDate, deadline, status, responsibleTeamMember, 0, null);
                goBack();
            }
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
    
    @FXML private void makeResponsible() {
        errorLabel.setText("");
        try {
            TeamMemberViewModel selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                responsibleTeamMemberInput.setText("");
            }
            else {
                responsibleTeamMemberInput.setText("#" + selectedItem.getIdProperty().get() + " " + selectedItem.getNameProperty().get());
            }
            teamTable.getSelectionModel().clearSelection();
        }
        catch (Exception e) {
            errorLabel.setText("Team member not found: " + e.getMessage());
        }
    }
    
    @FXML private void registerTime() {
        errorLabel.setText("");
        try {
            TeamMemberViewModel selectedTeamMember = teamTable.getSelectionModel().getSelectedItem();
            if (!timeRegisterInput.getText().equals("") && selectedTeamMember == null) {
                throw new IllegalStateException("Please select a team member to register time for");
            }
            else if (timeRegisterInput.getText().equals("") && selectedTeamMember != null) {
                throw new IllegalStateException("Please specify the number of hours #" + selectedTeamMember.getIdProperty().get() + " " + selectedTeamMember.getNameProperty().get() + " worked for");
            }
            else if (timeRegisterInput.getText().equals("") && selectedTeamMember == null) {
                throw new IllegalStateException("Please specify a number of hours and select a team member from the list");
            }
            else {
                if (Double.parseDouble(timeRegisterInput.getText()) < 0) {
                    throw new NumberFormatException("Your time registration has to be a positive number higher than 0");
                }
    
                Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
                Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
                Task task = model.getTaskList(project, requirement).getTaskById(viewState.getSelectedTask());
                double hoursWorked = Double.parseDouble(timeRegisterInput.getText());
                TeamMember teamMember = task.getTeamMemberList().getByID(selectedTeamMember.getIdProperty().get());
                if (confirmation("registertime")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Time registered");
                    alert.setHeaderText("Successfully registered time for #" + teamMember.getId() + " " + teamMember.getFullName());
                    alert.showAndWait();
                    model.editTask(task, task.getTitle(), task.getEstimatedTime(), task.getStartingDate(), task.getDeadline(), task.getStatus(), task.getResponsibleTeamMember(), hoursWorked, teamMember);
                    errorLabel.setText("");
                    timeRegisterInput.setText("");
                    totalHoursWorked.setText(String.valueOf(task.getTimeRegistration().getHoursWorked()));
                    teamTable.getSelectionModel().clearSelection();
                }
            }
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML private void goBack() {
        viewState.setSelectedTask(-1);
        viewHandler.openView("taskList");
    }
    
    private boolean confirmation(String id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch (id) {
            case "registertime":
                alert.setTitle("Confirm registering time");
                alert.setHeaderText("Registering time for #" + teamTable.getSelectionModel().getSelectedItem().getIdProperty().get() + " " + teamTable.getSelectionModel().getSelectedItem().getNameProperty().get());
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