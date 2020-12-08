package gui;

import model.TeamMember;

public class ViewState
{
  private String selectedProject;
  private int selectedRequirement;
  private int selectedTask;
  private int selectedTeamMember;


  public ViewState()
  {
    selectedProject = "-1";
    selectedRequirement = -1;
    selectedTask = -1;
    selectedTeamMember = -1;
  }

  public String getSelectedProject()
  {
    return selectedProject;
  }

  public int getSelectedRequirement()
  {
    return selectedRequirement;
  }

  public int getSelectedTask()
  {
    return selectedTask;
  }

  public int getSelectedTeamMember()
  {
    return selectedTeamMember;
  }

  public void setSelectedProject(String id)
  {
    selectedProject = id;
  }

  public void setSelectedRequirement(int id)
  {
    selectedRequirement = id;
  }

  public void setSelectedTask(int id)
  {
    selectedTask = id;
  }

  public void setSelectedTeamMember(int id)
  {
    selectedTeamMember = id;
  }


}
