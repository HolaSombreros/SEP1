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


        TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
        viewState.setSelectedTask(selectedItem.getIdProperty().getValue());
        try {
            model.addTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                    model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()),
                    model.getTeam().getByID(viewState.getSelectedTeamMember()));
            if (viewState.getSelectedTask() == -1)
                errorLabel.setText("Task has to be selected first!");
            else
                errorLabel.setText("TeamMember successfully assigned!");
        }
        catch(IllegalArgumentException e){
            errorLabel.setText("TeamMember already assigned!");
        }

    }

    /**
     * removes the selected teamMember from the selected task, requirement, project
     * if not task is selected, displays an error message
     * */
    public void unassignButtonPressed() {
        errorLabel.setText("");
        try {
            TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedTask(selectedItem.getIdProperty().getValue());
            boolean remove = confirmation();
            if(remove){
                try {
                    model.removeTeamMember(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
                            model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()),
                            model.getProjectList().getProjectByID(viewState.getSelectedProject()).getProjectRequirementList().getRequirementById(viewState.getSelectedRequirement()).getTaskList().getTaskById(viewState.getSelectedTask()),
                            model.getProjectList().getProjectByID(viewState.getSelectedProject()).getTeamMemberList().getByID(viewState.getSelectedTeamMember()));
                    errorLabel.setText("Team Member successfully unassigned!");
                }
                catch (IllegalArgumentException e){
                    errorLabel.setText(e.getMessage());
                }
            }
        } catch (Exception e) {
            errorLabel.setText("Select a task first!");

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