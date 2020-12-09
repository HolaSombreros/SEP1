package gui;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import javafx.fxml.FXML;

import java.util.Optional;

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
    private ViewState viewState;

    public ProjectListController()
    {
        //LOADED BY THE FXML LOADER
    }
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState)
    {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.projectListViewModel = new ProjectListViewModel(model,viewState);
        projectIDColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        projectDeadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        projectStatusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        projectListTable.setItems(projectListViewModel.getList());
        reset();
    }
    public void reset()
    {
        projectListViewModel.update();
        searchInput.setText("");
        errorLabel.setText("");
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
        ProjectViewModel selectedItem = projectListTable
                .getSelectionModel().getSelectedItem();
        viewState.setSelectedProject(selectedItem.getIDProperty().get());
        viewHandler.openView("detailsAndEditProject");
    }

    @FXML private void assignTeamMembersButtonPressed()
    {
            viewHandler.openView("detailsTeamMember");
    }
    @FXML private void removeProjectButtonPressed()
    {
        errorLabel.setText("");
        try
        {
             ProjectViewModel selectedItem = projectListTable
                    .getSelectionModel().getSelectedItem();
            boolean remove = confirmation();
            if (remove)
            {
                Project project = model.getProjectList().getProjectByID(selectedItem.getIDProperty().get());
                model.removeProject(project);
                projectListViewModel.remove(project);
                projectListTable.getSelectionModel().clearSelection();
            }

        }
        catch(Exception e)
        {
            errorLabel.setText("Project not selected");
        }

    }
     private boolean confirmation()
     {
         int index = projectListTable.getSelectionModel().getSelectedIndex();
         ProjectViewModel selectedItem = projectListTable.getItems().get(index);
         if(index < 0 || index >= projectListTable.getItems().size())
         {
             return false;
         }
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Confirmation");
         alert.setHeaderText("Are you sure you want to remove the project: id" + selectedItem.getIDProperty().get());
         Optional<ButtonType> result = alert.showAndWait();
         return (result.isPresent()) && (result.get() == ButtonType.OK);
     }

     @FXML private void searchButtonPressed()
     {

         errorLabel.setText("");
         try {
             if (searchInput.getText().equals("")) {
                 reset();
             }
             else {
                 int id = 0;
                 try {
                     id = Integer.parseInt(searchInput.getText());
                     projectListViewModel.update(id);
                 }
                 catch (NumberFormatException e) {
                     errorLabel.setText("The id to search for has to be numeric");
                 }
             }
         }
         catch (Exception e) {
             errorLabel.setText(e.getMessage());
         }
     }
}
