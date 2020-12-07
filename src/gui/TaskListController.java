package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Project;
import model.Requirement;
import model.Task;

import java.util.Optional;

public class TaskListController {
    private ViewHandler viewHandler;
    private Region root;
    private IProjectManagementModel model;
    private ViewState viewState;
    private TaskListViewModel viewModel;
    
    // Window data variables:
    @FXML private TextField searchBar;
    @FXML private TableView<TaskViewModel> taskTable;
    @FXML private TableColumn<TaskViewModel, Number> idColumn;
    @FXML private TableColumn<TaskViewModel, String> titleColumn;
    @FXML private TableColumn<TaskViewModel, String> deadlineColumn;
    @FXML private TableColumn<TaskViewModel, String> statusColumn;
    @FXML private Label errorLabel;
    
    public TaskListController() { }
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TaskListViewModel(model, viewState);
        
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        taskTable.setItems(viewModel.getList());
        
        reset();
    }
    
    public void reset() {
        searchBar.setText("");
        errorLabel.setText("");
        viewModel.update();
    }
    
    public Region getRoot() {
        return root;
    }

    @FXML private void addNewTask() {
        viewHandler.openView("addTask");
    }

    @FXML private void viewTask() {
        errorLabel.setText("");
        try {
            TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                errorLabel.setText("Please select a task first");
            }
            else {
                viewState.setSelectedTask(selectedItem.getIdProperty().get());
                viewHandler.openView("detailsAndEditTask");
            }
        }
        catch (Exception e) {
            errorLabel.setText("Task not found: " + e.getMessage());
        }
    }
    
    @FXML private void removeTask() {
        errorLabel.setText("");
        try {
            TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                errorLabel.setText("Please select a task first");
            }
            else {
                if (confirmation()) {
                    Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
                    Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
                    Task task = model.getTaskList(project, requirement).getTaskById(selectedItem.getIdProperty().get());
                    viewModel.remove(task);
                    model.removeTask(requirement, task);
                    taskTable.getSelectionModel().clearSelection();
                }
            }
        }
        catch (Exception e) {
            errorLabel.setText("Task not found: " + e.getMessage());
        }
    }

    @FXML private void goBack() {
        viewState.setSelectedRequirement(-1);
<<<<<<< HEAD
        viewHandler.openView("requirementList");
=======
        viewHandler.openView("requirement");
>>>>>>> f18c2a01160d527288900bf8eb0e0b655d128fa7
    }
    
    private boolean confirmation() {
        int index = taskTable.getSelectionModel().getSelectedIndex();
        TaskViewModel selectedItem = taskTable.getItems().get(index);
        if (index < 0 || index > taskTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm removing task");
        alert.setHeaderText("Removing task #" + selectedItem.getIdProperty().get() + ": " + selectedItem.getTitleProperty().get());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}
