package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

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
    @FXML private TextField projectTitle;
    @FXML private TextField requirementTitle;
    
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
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
        
        searchBar.setText("");
        errorLabel.setText("");
        projectTitle.setText(project.getName());
        requirementTitle.setText(requirement.getUserStory());
        viewModel.update(0);
    }
    
    public Region getRoot() {
        return root;
    }
    
    @FXML private void searching() {
        try {
            errorLabel.setText("");
            if (searchBar.getText().equals("")) {
                reset();
            }
            else {
                int id;
                try {
                    id = Integer.parseInt(searchBar.getText());
                    viewModel.update(id);
                }
                catch (NumberFormatException e) {
                    throw new IllegalStateException("The id to search for has to be numeric");
                }
            }
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
    
    @FXML private void clear() {
        searchBar.setText("");
        errorLabel.setText("");
        viewModel.update(0);
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
        TaskViewModel selectedItem = taskTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            errorLabel.setText("Please select a task first");
        }
        else {
            if (confirmation()) {
                Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
                Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
                Task task = model.getTaskList(project, requirement).getTaskById(selectedItem.getIdProperty().get());
                model.removeTask(requirement, task);
                viewModel.remove(task);
                taskTable.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML private void goBack() {
        viewState.setSelectedRequirement(-1);
        viewHandler.openView("requirementList");
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
