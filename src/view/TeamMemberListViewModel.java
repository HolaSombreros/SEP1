package view;

import mediator.IProjectManagementModel;
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
    update();
  }

  public ObservableList<TeamMemberViewModel> getList() {
    return list;
  }

  public void update(Project project, Requirement requirement) {
    list.clear();
    for (int i = 0; i < model.getTaskList(project, requirement)
        .getTaskById(viewState.getSelectedTask()).getTeamMemberList()
        .size(); i++)
      list.add(new TeamMemberViewModel(project,model.getTaskList(project, requirement)
          .getTaskById(viewState.getSelectedTask()).getTeamMemberList()
          .getByIndex(i),model));
  }

  public void update(Project project) {
    list.clear();
    for (int i = 0; i < model.getRequirementList(project)
        .getRequirementById(viewState.getSelectedRequirement())
        .getTeamMemberList().size(); i++)
      list.add(new TeamMemberViewModel(project, model.getRequirementList(project)
          .getRequirementById(viewState.getSelectedRequirement())
          .getTeamMemberList().getByIndex(i),model));
  }

  public void update() {
    list.clear();
    for (int i = 0; i < model.getProjectList().getProjectByID(viewState.getSelectedProject()).getTeamMemberList().size(); i++)
      list.add(new TeamMemberViewModel(model.getProjectList().getProjectByID(viewState.getSelectedProject()),
          model.getProjectList().getProjectByID(viewState.getSelectedProject())
              .getTeamMemberList().getByIndex(i),model));
  }

  public void add( TeamMember teamMember) {
    list.add(new TeamMemberViewModel(model.getProjectList().getProjectByID(viewState.getSelectedProject()), teamMember,model));
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
