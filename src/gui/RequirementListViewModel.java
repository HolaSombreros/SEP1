package gui;

import javafx.collections.*;
import model.*;

public class RequirementListViewModel
{
  private ObservableList<RequirementViewModel> list;
  private IProjectManagementModel model;

  public RequirementListViewModel(IProjectManagementModel model)
  {
    this.model = model;
    this.list = FXCollections.observableArrayList();
    update();
  }

  public ObservableList<RequirementViewModel> getList()
  {
    return list;
  }

 public void update()
  {
    //list.clear();
    //for (int i = 0; i < model.getRequirementList(); i++)
    //  list.add();
  }

  public void add(Requirement requirement)
  {
    list.add(new RequirementViewModel(requirement));
  }

  public void remove(Requirement requirement)
  {
    for (int i = 0; i < list.size(); i++)
      if (list.get(i).getIdProperty().get() == (requirement.getId()) && list
          .get(i).getStatusProperty().get()
          .equals(requirement.getStatus().getName()) && list.get(i)
          .getDeadlineProperty().get()
          .equals(requirement.getDeadline().toString()))
      {
        list.remove(i);
        break;
      }
  }

}
