package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Task;

public class TaskViewModel {
    private IntegerProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty deadlineProperty;
    private StringProperty statusProperty;
    
    public TaskViewModel(Task task) {
        idProperty = new SimpleIntegerProperty(task.getId());
        titleProperty = new SimpleStringProperty(task.getTitle());
        deadlineProperty = new SimpleStringProperty(task.getDeadline().toString());
        statusProperty = new SimpleStringProperty(task.getStatus().getName());
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public StringProperty getTitleProperty() {
        return titleProperty;
    }
    
    public StringProperty getDeadlineProperty() {
        return deadlineProperty;
    }
    
    public StringProperty getStatusProperty() {
        return statusProperty;
    }
}
