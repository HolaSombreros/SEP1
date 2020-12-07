package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
   // private TeamMemberListViewModel viewModel;
    private ProjectListViewModel viewModel;

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
        this.viewModel = new ProjectListViewModel(model,state);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        projectTable.setItems(viewModel.getList());

    }

    public void reset(){

    }

    public Region getRoot(){
        return root;
    }

    public void selectButtonPressed() {
    }

    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }

    public void unassignButtonPressed() {
    }

    public void requirementButtonPressed() {
        viewHandler.openView("requirementSelect");
    }


}
