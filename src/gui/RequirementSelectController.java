package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.ProjectList;
import model.RequirementList;

public class RequirementSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private TeamMemberListViewModel viewModel;

    @FXML
    private Label requirementLabel;
    @FXML private TableView<RequirementListViewModel> requirementTable;
    @FXML private TableColumn<RequirementList,Integer> idColumn;
    @FXML private TableColumn<RequirementList,String> priorityColumn;
    @FXML private TableColumn<RequirementList,String> deadlineColumn;

    public RequirementSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model);

    }

    public void reset(){
        requirementLabel.setText("");
    }

    public Region getRoot(){
        return root;
    }

    public void selectButtonPressed() {
    }

    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }
}
