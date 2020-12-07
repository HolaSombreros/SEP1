package gui;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.*;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProjectListController
{
    @FXML private TextField searchInput;
    @FXML private TableView<ProjectViewModel> projectListTable;
    @FXML private TableColumn<ProjectViewModel, String> projectIDColumn;
    @FXML private TableColumn<ProjectViewModel, String> projectNameColumn;
    @FXML private TableColumn<ProjectViewModel, String> projectDeadlineColumn;
    @FXML private TableColumn<ProjectViewModel, String> projectStatusColumn;
    @FXML private Label errorLabel;

    private Region root;
    private ViewHandler viewHandler;
    private ProjectListViewModel projectListViewModel;
    private IProjectManagementModel model;

    public ProjectListController()
    {
        //LOADED BY THE FXML LOADER
    }
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root)
    {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.projectListViewModel = new ProjectListViewModel(model);

        projectIDColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        projectDeadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        projectStatusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        projectListTable.setItems(projectListViewModel.getList());
        reset();
    }
    public void reset()
    {
        searchInput.setText("");
        errorLabel.setText("");
        projectListViewModel.update();

    }
    public Region getRoot()
    {
        return root;
    }
    @FXML private void addProjectButtonPressed()
    {
        viewHandler.openView("addProject");
    }
    @FXML private void viewProjectButtonPressed()
    {
        viewHandler.openView("detailsAndEditProject");
    }

    @FXML private void assignTeamMembersButtonPressed()
    {
        viewHandler.openView("detailsTeamMember");
    }
    @FXML private void removeProjectButtonPressed()
    {

    }

}
