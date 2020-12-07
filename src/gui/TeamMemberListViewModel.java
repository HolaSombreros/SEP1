package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

public class TeamMemberListViewModel
{
  private ObservableList<TeamMemberViewModel> list;
  private IProjectManagementModel model;
  private ViewState viewState;

  public TeamMemberListViewModel(IProjectManagementModel model, ViewState viewState) {
    this.model = model;
    this.viewState = viewState;
    this.list = FXCollections.observableArrayList();
  }

  public ObservableList<TeamMemberViewModel> getList() {
    return list;
  }

  public void update(Project project, Requirement requirement) {
    list.clear();
    for (int i = 0; i < model.getTaskList(project, requirement)
        .getTaskById(viewState.getSelectedTask()).getTeamMemberList()
        .size(); i++)
      list.add(new TeamMemberViewModel(model.getTaskList(project, requirement)
          .getTaskById(viewState.getSelectedTask()).getTeamMemberList()
          .getByIndex(i)));
  }

  public void update(Project project) {
    list.clear();
    for (int i = 0; i < model.getRequirementList(project)
        .getRequirementById(viewState.getSelectedRequirement())
        .getTeamMemberList().size(); i++)
      list.add(new TeamMemberViewModel(model.getRequirementList(project)
          .getRequirement(viewState.getSelectedRequirement())
          .getTeamMemberList().getByIndex(i)));
  }

  public void update() {
    list.clear();
    for (int i = 0; i < model.getProjectList()
        .getProjectByID(viewState.getSelectedProject()).getTeamMemberList()
        .size(); i++)
      list.add(new TeamMemberViewModel(
          model.getProjectList().getProjectByID(viewState.getSelectedProject())
              .getTeamMemberList().getByIndex(i)));
  }



  public void add(TeamMember teamMember) {
    list.add(new TeamMemberViewModel(teamMember));
  }

  public void remove(TeamMember teamMember) {
    for (int i = 0; i < list.size(); i++)
    {
      if (list.get(i).getIdProperty().get() == teamMember.getId() && list.get(i)
          .getNameProperty().get().equals(teamMember.getFullName()))
      {
        list.remove(i);
        break;
      }
    }
  }
}
