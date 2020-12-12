package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Project;

public class ProjectViewModel
{
    private StringProperty IDProperty;
    private StringProperty nameProperty;
    private StringProperty deadlineProperty;
    private StringProperty statusProperty;
    private StringProperty methodologyProperty;

    public ProjectViewModel(Project project)
    {
        IDProperty = new SimpleStringProperty(project.getID());
        nameProperty = new SimpleStringProperty(project.getName());
        deadlineProperty = new SimpleStringProperty(project.getDeadline().toString());
        statusProperty = new SimpleStringProperty(project.getStatus().getName());
        methodologyProperty = new SimpleStringProperty(project.getMethodology().getMethodology());

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
    public StringProperty getMethodologyProperty() {
        return methodologyProperty;
    }
}
