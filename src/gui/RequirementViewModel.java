package gui;
import javafx.beans.property.*;
import model.Requirement;

public class RequirementViewModel
{
  private IntegerProperty idProperty;
  private StringProperty statusProperty;
  private StringProperty deadlineProperty;

  public RequirementViewModel(Requirement requirement)
  {
    idProperty = new SimpleIntegerProperty(requirement.getId());
    statusProperty = new SimpleStringProperty(requirement.getStatus().getName());
    deadlineProperty = new SimpleStringProperty(requirement.getDeadline().toString());
  }

  public IntegerProperty getIdProperty()
  {
    return idProperty;
  }

  public StringProperty getStatusProperty()
  {
    return statusProperty;
  }

  public StringProperty getDeadlineProperty()
  {
    return deadlineProperty;
  }
}
