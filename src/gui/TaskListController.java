package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class TaskListController {
    private IProjectManagementModel model;
    private ViewHandler viewHandler;
    private Region root;
    
    public TaskListController() { }
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
    }
    
    public void reset() {
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
}
