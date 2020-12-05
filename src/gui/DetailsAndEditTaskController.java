package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Status;

public class DetailsAndEditTaskController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    private TeamMemberListViewModel viewModel;
    
    // Window data variables:
    @FXML private TextField titleInput;
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
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model);
    
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
        viewModel.update();
    }
    
    public Region getRoot() {
        return root;
    }
    
    @FXML private void editDetails() {
        // change tons of stuff
    }
    
    @FXML private void makeResponsible() {
        // change very little stuff
    }
    
    @FXML private void removeTask() {
        // remove stuff
    }

    @FXML private void goBack() {
        viewHandler.openView("taskList");
    }
}