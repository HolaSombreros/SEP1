package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Project;
import model.Status;
import model.Task;

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
        // TODO - dont actually reset it to nothing, but instead it should be set to the task's details...
        titleInput.setText("");
        estimatedHoursInput.setText("");
        startingDateInput.setValue(null);
        deadlineInput.setValue(null);
        //statusInput.setValue(model.getTaskList(model.getProjectList().getProject(0), model.getRequirementList(model.getProjectList().getProject(0)).getRequirement(0)).toString());
        statusInput.setValue(statusInput.getItems().get(0));
        responsibleTeamMemberInput.setText("");
        hoursWorkedInput.setText("");
        errorLabel.setText("");
        
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        viewModel.update(project, model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement()));
        
        /* basically something like this:
        Task task = model.getTaskList(viewState.getSelectedProject(), viewState.getSelectedRequirement()).getTask(viewState.getSelectedTask()); // TODO - this one is wrong though...
        titleInput.setText(task.getTitle());
        estimatedHoursInput.setText(String.valueOf(task.getEstimatedTime()));
         */
    }
    
    public Region getRoot() {
        return root;
    }
    
    @FXML private void editDetails() {
        // change tons of stuff
    }
    
    @FXML private void makeResponsible() {
        // change very little stuff
        errorLabel.setText("");
        try {
            TeamMemberViewModel selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                errorLabel.setText("Please select a team member first");
            }
            else {
                if (confirmation()) {
                    responsibleTeamMemberInput.setText(selectedItem.getNameProperty().toString());
                }
            }
        }
        catch (Exception e) {
            errorLabel.setText("Team member not found: " + e.getMessage());
        }
    }
    
    @FXML private void removeTask() {
        // remove stuff
    }

    @FXML private void goBack() {
        viewState.setSelectedTask(-1);
        viewHandler.openView("taskList");
    }
    
    private boolean confirmation() {
        int index = teamTable.getSelectionModel().getSelectedIndex();
        TeamMemberViewModel selectedItem = teamTable.getItems().get(index);
        if (index < 0 || index > teamTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm changing responsible team member");
        alert.setHeaderText("Changing responsible team member for task #" + idField.getText() + " to: " + selectedItem.getNameProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}