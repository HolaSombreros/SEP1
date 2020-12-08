package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

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
    @FXML private TableView<TeamMemberViewModel> teamMembersTable;
    @FXML private TableColumn<TeamMemberViewModel, Number> IDColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    @FXML private TextField scrumMaster;
    @FXML private TextField productOwner;

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private ViewState viewState;
    private TeamMemberListViewModel teamMemberListViewModel;
    public static final LocalDate LOCAL_DATE (Date dateString){
       LocalDate localDate = LocalDate.of(dateString.getYear(), dateString.getMonth(), dateString.getDay());
        return localDate;
    }

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
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        teamMembersTable.setItems(teamMemberListViewModel.getList());
        reset();
    }
    public void reset()
    {
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        IDInput.setText(project.getID());
        nameInput.setText(project.getName());
        startingDateInput.setValue(LOCAL_DATE(project.getStartingDate()));
        deadlineInput.setValue(LOCAL_DATE(project.getDeadline()));
        methodologyInput.setText(project.getMethodology().toString());
        statusInput.setText(project.getStatus().toString());
        if(project.getProductOwner() != null)
        productOwner.setText(project.getProductOwner().toString());
        else
            productOwner.setText("");
        if(project.getScrumMaster() != null)
            scrumMaster.setText(project.getScrumMaster().toString());
        else
            scrumMaster.setText("");
        errorLabel.setText("");

    }
    @FXML private void assignScrumMaster()
    {
        TeamMemberViewModel selectedTeamMember = teamMembersTable.getSelectionModel().getSelectedItem();
        //model.getProjectList().getProjectByID(selectedTeamMember.getIdProperty().toString()).assignScrumMaster(selectedTeamMember);
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
