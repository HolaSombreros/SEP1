package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Task;

import java.util.Optional;

public class TaskListController {
    private ViewHandler viewHandler;
    private Region root;
    private IProjectManagementModel model;
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
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TaskListViewModel(model);
        
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
        viewHandler.openView("detailsAndEditTask");
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
                    // TODO - how the fuck?
                    /*Task task = new Task(selectedItem.getIdProperty().get(), selectedItem.getTitleProperty().get());
                    model.removeTask(project, requirement, task);
                    viewModel.remove(task);*/
                    taskTable.getSelectionModel().clearSelection();
                }
            }
        }
        catch (Exception e) {
            errorLabel.setText("Item not found: " + e.getMessage());
        }
    }

    @FXML private void goBack() {
        viewHandler.openView("detailsAndEditRequirement");
    }
    
    private boolean confirmation() {
        int index = taskTable.getSelectionModel().getSelectedIndex();
        TaskViewModel selectedItem = taskTable.getItems().get(index);
        if (index < 0 || index > taskTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm removing task");
        alert.setHeaderText("Removing task #" + selectedItem.getIdProperty() + ": " + selectedItem.getTitleProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}
