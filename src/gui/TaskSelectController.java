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
    private ViewState viewState;
    private IProjectManagementModel model;
    private TaskListViewModel viewModel;


    @FXML private TableView<TaskViewModel> taskTable;
    @FXML private TableColumn<TaskViewModel,Number> idColumn;
    @FXML private TableColumn<TaskViewModel,String> titleColumn;
    @FXML private TableColumn<TaskViewModel,String> deadlineColumn;

    public TaskSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState viewState){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TaskListViewModel(model,viewState);
        reset();

    }

    public void reset(){
        viewModel.update();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        taskTable.setItems(viewModel.getList());
    }

    public Region getRoot(){
        return root;
    }

    public void selectButtonPressed() {
    }

    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }

    public void assignButtonPressed() {
    }

    public void unassignButtonPressed() {
    }
}
