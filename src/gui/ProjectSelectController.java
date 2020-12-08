package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.ProjectList;

public class ProjectSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState state;
    private IProjectManagementModel model;
    private ProjectListViewModel viewModel;

    @FXML private Label errorLabel;
    @FXML private TableView<ProjectViewModel> projectTable;
    @FXML private TableColumn<ProjectViewModel,String> idColumn;
    @FXML private TableColumn<ProjectViewModel,String> nameColumn;
    @FXML private TableColumn<ProjectViewModel,String> deadlineColumn;

    public ProjectSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
        this.viewModel = new ProjectListViewModel(model,state);
        reset();

    }

    public void reset(){
        errorLabel.setText("");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        projectTable.setItems(viewModel.getList());
        viewModel.update();

    }

    public Region getRoot(){
        return root;
    }


    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }

    public void unassignButtonPressed() {

    }

    /**
     * Selects a project from the table and opens the Requremet Select window containing the requirements of the related project
     * if a project isn't selected update the error message
     * */
    public void requirementButtonPressed() {
        try {
            ProjectViewModel selectedItem = projectTable.getSelectionModel().getSelectedItem();
            state.setSelectedProject(selectedItem.getIDProperty().getValue());
            viewHandler.openView("requirementSelect");
        }
        catch (Exception e){
            errorLabel.setText("Project has to be selected first!");
        }
    }


}
