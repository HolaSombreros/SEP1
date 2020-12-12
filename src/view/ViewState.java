package view;

public class ViewState
{
  private String selectedProject;
  private int selectedRequirement;
  private int selectedTask;
  private int selectedTeamMember;

  /**
   * All the variables will be initialised with -1 in the constructor
   * The String variable will be "-1" also
   */
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
