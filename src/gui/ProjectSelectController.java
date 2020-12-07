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
    private TeamMemberListViewModel viewModel;

    @FXML private TableView<ProjectListViewModel> projectTable;
    @FXML private TableColumn<ProjectList,String> idColumn;
    @FXML private TableColumn<ProjectList,String> nameColumn;
    @FXML private TableColumn<ProjectList,String> deadlineColumn;

    public ProjectSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model,state);

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
