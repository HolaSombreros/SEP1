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
    private ViewState state;
    private IProjectManagementModel model;
    private RequirementListViewModel viewModel;


    @FXML private TableView<RequirementViewModel> requirementTable;
    @FXML private TableColumn<RequirementViewModel,Number> idColumn;
    @FXML private TableColumn<RequirementViewModel,String> priorityColumn;
    @FXML private TableColumn<RequirementViewModel,String> deadlineColumn;

    public RequirementSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new RequirementListViewModel(model,state);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().getPriorityProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        requirementTable.setItems(viewModel.getList());

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

    public void taskButtonPressed() {
        viewHandler.openView("taskSelect");
    }

    public void unassignButtonPressed() {
    }
}
