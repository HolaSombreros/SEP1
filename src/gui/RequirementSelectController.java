package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import Mediator.IProjectManagementModel;

import java.util.Optional;

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
        this.viewModel = new RequirementListViewModel(model,viewState);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().getPriorityProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        requirementTable.setItems(viewModel.getList());
        reset();


    }

    public void reset(){

        viewModel.update(0);
        errorLabel.setText("");

    }

    public Region getRoot(){
        return root;
    }



    public void backButtonPressed() {
        viewHandler.openView("projectSelect");
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
            viewState.setSelectedRequirement(selectedItem.getIdProperty().getValue());
            boolean remove = confirmation();
            if(remove) {
               // try {
                    model.removeTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                            model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                            model.getProjectList().getProjectByID(viewState.getSelectedProject()).getTeamMemberList().getByID(viewState.getSelectedTeamMember()));
                    errorLabel.setText("Team Member successfully unassigned!");
              //  }
               // catch (IllegalArgumentException e){
                //    errorLabel.setText("You cannot unnasign the responsible team member!");
                //}
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public boolean confirmation(){
        int index = requirementTable.getSelectionModel().getSelectedIndex();
        RequirementViewModel selectedItem = requirementTable.getItems().get(index);
        if (index >= requirementTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Unnassigning team member - id: " + viewState.getSelectedTeamMember());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

}

