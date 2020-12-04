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
        
        for (Status status : Status.values()) {
            statusInput.getItems().add(status.getName());
        }
        reset();
    }

    public void reset() {
        statusInput.setValue(statusInput.getItems().get(0));    // Sets default selected value as 1st element of string array.
        errorLabel.setText("");
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