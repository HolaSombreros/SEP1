package gui;

import javafx.collections.*;
import model.*;

public class RequirementListViewModel
{
  private ObservableList<RequirementViewModel> list;
  private IProjectManagementModel model;
  private ViewState state;

  public RequirementListViewModel(IProjectManagementModel model, ViewState viewState)
  {
    this.model = model;
    this.state = viewState;
    this.list = FXCollections.observableArrayList();
    update(0);
  }

  public ObservableList<RequirementViewModel> getList()
  {
    return list;
  }

  /**
   * The method takes an id and display only the requirement with the selected id
   * If the id equals 0, then all the requirements will be in the table
   * @param id
   */
  public void update(int id)
  {
    list.clear();
    if (id == 0)
      for (int i = 0; i < model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).size(); i++)
        list.add(new RequirementViewModel(model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirement(i)));
    else
      list.add(new RequirementViewModel(model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirementById(id)));
  }

  public void add(Requirement requirement)
  {
    list.add(new RequirementViewModel(requirement));
  }

  public void remove(Requirement requirement)
  {
    for (int i = 0; i < list.size(); i++)
      if (list.get(i).getIdProperty().getValue() == (requirement.getId()) && list.get(i).getPriorityProperty().getValue().equals(requirement.getPriority().getName()) && list.get(i)
          .getStatusProperty().getValue().equals(requirement.getStatus().getName()) && list.get(i).getDeadlineProperty().getValue().equals(requirement.getDeadline().toString()))
      {
        list.remove(i);
        break;
      }
  }

}
