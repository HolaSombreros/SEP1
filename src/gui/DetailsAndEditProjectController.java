package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.TeamMember;

public class DetailsAndEditProjectController
{
    @FXML private TextField IDInput;
    @FXML private TextField nameInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private TextField methodologyInput;
    @FXML private TextField statusInput;
    @FXML private Label errorLabel;
    @FXML private TableView<TeamMemberListViewModel> teamMembersTable;
    @FXML private TableColumn<TeamMemberListViewModel, TeamMember> teamMembersColumn;
    @FXML private TextField scrumMasterInput;
    @FXML private TextField productOwnerInput;

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private TeamMemberListViewModel teamMemberListViewModel;
}
