package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Project;

public class ProjectViewModel
{
    private StringProperty IDProperty;
    private StringProperty nameProperty;
    private StringProperty deadlineProperty;
    private StringProperty statusProperty;

    public ProjectViewModel(Project project)
    {
        IDProperty = new SimpleStringProperty(project.getID());
        nameProperty = new SimpleStringProperty(project.getName());
        deadlineProperty = new SimpleStringProperty(project.getDeadline().toString());
        statusProperty = new SimpleStringProperty(project.getStatus().toString());
    }
    public StringProperty getIDProperty()
    {
        return IDProperty;
    }
    public StringProperty getNameProperty()
    {
        return nameProperty;
    }
    public StringProperty getDeadlineProperty()
    {
        return deadlineProperty;
    }
    public StringProperty getStatusProperty()
    {
        return statusProperty;
    }
}