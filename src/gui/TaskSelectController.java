package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.ProjectList;
import model.TaskList;

public class TaskSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private TeamMemberListViewModel viewModel;

    @FXML
    private Label taskLabel;
    @FXML private TableView<TaskListViewModel> taskTable;
    @FXML private TableColumn<TaskList,Integer> idColumn;
    @FXML private TableColumn<TaskList,String> titleColumn;
    @FXML private TableColumn<TaskList,String> deadlineColumn;

    public TaskSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model);

    }

    public void reset(){
        taskLabel.setText("");
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
