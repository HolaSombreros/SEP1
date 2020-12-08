package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.RequirementList;

public class RequirementSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState viewState;
    private IProjectManagementModel model;
    private RequirementListViewModel viewModel;

    @FXML private Label errorLabel;
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
        this.viewState = state;

        reset();


    }

    public void reset(){
        this.viewModel = new RequirementListViewModel(model,viewState);
        viewModel.update(0);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().getPriorityProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        requirementTable.setItems(viewModel.getList());

    }

    public Region getRoot(){
        return root;
    }



    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }

    public void taskButtonPressed() {
        try {
            RequirementViewModel selectedItem = requirementTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedRequirement(selectedItem.getIdProperty().getValue());
            viewHandler.openView("taskSelect");
        }
        catch (Exception e) {
            errorLabel.setText("Requirement has to be selected first!");
        }
    }

    /**
     * removes the selected teamMember from the requirement related teamMember list
     * */
    public void unassignButtonPressed() {
        errorLabel.setText("");
        try {
            RequirementViewModel selectedItem = requirementTable.getSelectionModel().getSelectedItem();
            model.removeTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getTeamMemberList().getByID(viewState.getSelectedTeamMember()));
        } catch (Exception e) {
            errorLabel.setText("Select a Requirement first!");

        }
    }

}

