package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Project;
import model.TeamMember;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @FXML private TableColumn<TeamMemberListViewModel, String> IDColumn;
    @FXML private TableColumn<TeamMemberListViewModel, String> nameColumn;
    @FXML private TextField scrumMasterInput;
    @FXML private TextField productOwnerInput;

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private ViewState viewState;
    private TeamMemberListViewModel teamMemberListViewModel;
    /*public static final LocalDate LOCAL_DATE (String dateString){
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(String.format("%d/%d/%d", dateString));
        LocalDate localDate = LocalDate.parse(dateString,formatter);
        return localDate;
    }*/

    public DetailsAndEditProjectController()
    {
        //LOADED BY THE FXML LOADER
    }
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState)
    {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.teamMemberListViewModel = new TeamMemberListViewModel(model, viewState);
        reset();
    }
    public void reset()
    {
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        IDInput.setText(project.getID());
        nameInput.setText(project.getName());
       // startingDateInput.setValue(LOCAL_DATE(project.getStartingDate().toString()));
    }
    public Region getRoot()
    {
        return root;
    }
    @FXML private void editDetailsButtonPressed()
    {

    }
    @FXML private void removeProjectButtonPressed()
    {

    }
    @FXML private void viewRelatedReqButtonPressed()
    {
        viewHandler.openView("requirementList");
    }
    @FXML private void backButtonPressed()
    {
        viewState.setSelectedProject("-1");
        viewHandler.openView("projectList");
    }

}
