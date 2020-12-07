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
        errorLabel.setText("");
        try
        {
             ProjectViewModel selectedItem = projectListTable
                    .getSelectionModel().getSelectedItem();
            boolean remove = confirmation();
            if (remove)
            {
               /* Project project = new Project(selectedItem.getNameProperty().get(),selectedItem.getIDProperty().get(), selectedItem.getDeadlineProperty().get());
                projectListViewModel.remove();*/
                projectListTable.getSelectionModel().clearSelection();}
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
         return (result.isPresent()) && (result.get() == ButtonType.YES);
     }

}
