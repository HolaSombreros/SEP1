package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class AssignUnassignTeamMemberController {

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private TeamMemberListViewModel viewModel;

    @FXML private Label projectLabel;
    @FXML private Label requirementLabel;
    @FXML private Label taskLabel;

    public AssignUnassignTeamMemberController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model);

    }

    public void reset(){
        projectLabel.setText("");
        requirementLabel.setText("");
        taskLabel.setText("");
    }

    public Region getRoot(){
        return root;
    }


    public void projectButtonPressed() {
        viewHandler.openView("selectProject");
    }

    public void requirementButtonPressed() {
        viewHandler.openView("selectRequirement");
    }

    public void taskButtonPressed() {
        viewHandler.openView("selectTask");
    }

    public void selectButtonPressed() {
    }

    public void assignButtonPressed() {
    }

    public void unassignButtonPressed() {
    }

    public void cancelButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }
}
