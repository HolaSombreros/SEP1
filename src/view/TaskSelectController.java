package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.IProjectManagementModel;

import java.util.Optional;

public class TaskSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState viewState;
    private IProjectManagementModel model;
    private TaskListViewModel viewModel;


    @FXML private Label errorLabel;
    @FXML
    private TableView<TaskViewModel> taskTable;
    @FXML
    private TableColumn<TaskViewModel, Number> idColumn;
    @FXML
    private TableColumn<TaskViewModel, String> titleColumn;
    @FXML
    private TableColumn<TaskViewModel, String> deadlineColumn;

    public TaskSelectController() {

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TaskListViewModel(model, viewState);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        taskTable.setItems(viewModel.getList());
        reset();

    }

    public void reset() {
        viewModel.update(0);
        errorLabel.setText("");

    }

    public Region getRoot() {
        return root;
    }


    public void backButtonPressed() {
        viewHandler.openView("requirementSelect");
    }



    public void assignButtonPressed()  {



        try {
            try {
                TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
                viewState.setSelectedTask(selectedItem.getIdProperty().getValue());
            }
            catch (Exception e){
                throw new IllegalArgumentException("Task has to be selected first!");
            }

            model.addTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()),
                    model.getTeam().getByID(viewState.getSelectedTeamMember()));
            errorLabel.setText("TeamMember successfully assigned!");
            }
       catch(Exception e){
                errorLabel.setText(e.getMessage());
        }

    }

    /**
     * removes the selected teamMember from the selected task, requirement, project
     * if not task is selected, displays an error message
     * */
    public void unassignButtonPressed() {
        errorLabel.setText("");
        try {
            try {
                TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
                viewState.setSelectedTask(selectedItem.getIdProperty().getValue());
            }
            catch (Exception e){
                throw new IllegalArgumentException("Select a task first!");
            }
            boolean remove = confirmation();
            if(remove){
                try {
                    if(!model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).
                            getTaskList().getTaskById(viewState.getSelectedTask()).getTeamMemberList().contains(model.getProjectList().getProjectByID(viewState.getSelectedProject()).
                            getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()).getTeamMemberList().
                            getByID(viewState.getSelectedTeamMember())))
                        errorLabel.setText("There is no team member in the related list");
                    else{
                        model.removeTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                                model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                                model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()),
                                model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()).getTeamMemberList().getByID(viewState.getSelectedTeamMember()));
                        errorLabel.setText("Team Member successfully unassigned!");
                    }
                }
                catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Team member already unassigned!");
                }
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());

        }
    }

    public void homeButtonPressed(){
        viewHandler.openView("teamMembers");
    }


    public boolean confirmation(){
        int index = taskTable.getSelectionModel().getSelectedIndex();
        TaskViewModel selectedItem = taskTable.getItems().get(index);
        if (index >= taskTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Unnassigning team member - id: " + viewState.getSelectedTeamMember());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}