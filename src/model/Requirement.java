package model;

public class Requirement
{
  private int id;
  private String userStory;
  private double estimatedTime;
  private double hoursWorked;
  private Project relatedProject;
  private TaskList taskList;
  private TeamMemberList teamMemberList;
  private TeamMember responsibleTeamMember;
  private Date startingDate;
  private Date deadline;
  private RequirementStatus status;
  private Type type;
  private Priority priority;

  public Requirement(String userStory, Date startingDate, Date deadline,
      double estimatedTime, Priority priority, Type type,
      Project relatedProject)
  {
    this.relatedProject = relatedProject;
    Date.checkDates(startingDate, deadline);
    setStartingDate(startingDate);
    setDeadline(deadline);
    setEstimatedTime(estimatedTime);
    id = 0;
    this.userStory = userStory;
    this.priority = priority;
    this.type = type;
    this.hoursWorked = 0;
    this.status = RequirementStatus.NOT_STARTED;
    this.taskList = new TaskList();
    this.teamMemberList = new TeamMemberList();
    this.responsibleTeamMember = null;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public int getId()
  {
    return id;
  }

  public void setUserStory(String userStory)
  {
    this.userStory = userStory;
  }

  public String getUserStory()
  {
    return userStory;
  }

  /**
   * The system checks if the starting date is after the project's starting
   * date and before the project's deadline
   * @param startingDate
   */
  public void setStartingDate(Date startingDate)
  {
    if(startingDate.isBefore(relatedProject.getStartingDate()))
      throw new IllegalArgumentException("Starting date can not be before project's starting date");
    if(!(startingDate.isBefore(relatedProject.getDeadline())))
      throw new IllegalArgumentException("Starting date can not be after project's deadline");
    this.startingDate = startingDate.copy();
  }

  public Date getStartingDate()
  {
    return startingDate.copy();
  }

  /**
   * The system checks if the deadline is before the project's deadline
   * @param deadline
   */
  public void setDeadline(Date deadline)
  {
    if (!(deadline.isBefore(relatedProject.getDeadline())))
      throw new IllegalArgumentException("Deadline can not be after project's deadline");
    this.deadline = deadline.copy();
  }

  public Date getDeadline()
  {
    return deadline.copy();
  }

  /**
   * The estimated time is not allowed to be less or equal to 0
   * @param estimatedTime
   */
  public void setEstimatedTime(double estimatedTime)
  {
    if (estimatedTime<=0)
      throw new IllegalArgumentException("Estimated time has to be higher than 0");
    this.estimatedTime = estimatedTime;
  }

  public double getEstimatedTime()
  {
    return estimatedTime;
  }

  public void setPriority(Priority priority)
  {
    this.priority = priority;
  }

  public Priority getPriority()
  {
    return priority;
  }

  public void setType(Type type)
  {
    this.type = type;
  }

  public Type getType()
  {
    return type;
  }


  public void setStatus(RequirementStatus status)
  {
    this.status = status;
  }

  /**
   * The system checks if all tasks are ended
   * If yes, the status changes to ended
   * @return status
   */
  public RequirementStatus getStatus()
  {
    if(status==RequirementStatus.STARTED)
    {
      boolean d = true;
      for (Task task : taskList.getTasks())
        if (task.getStatus() != Status.ENDED)
        {
          d = false;
          break;
        }
      if (d) setStatus(RequirementStatus.ENDED);
    }
    return status;
  }

  public double getHoursWorked()
  {
    hoursWorked = 0;
    for (Task task : taskList.getTasks())
      hoursWorked += task.getTimeRegistration().getHoursWorked();
    return hoursWorked;
  }

  public Project getRelatedProject()
  {
    return relatedProject;
  }

  public void addTask(Task task)
  {
    taskList.add(task);
  }

  public void removeTask(Task task)
  {
    taskList.remove(task);
  }

  public void removeTaskById(int id)
  {
    for (Task task : taskList.getTasks())
      if (task.getId() == id)
        removeTask(task);
  }

  public TaskList getTaskList()
  {
    return taskList;
  }

  public TeamMemberList getTeamMemberList()
  {
    return teamMemberList;
  }

  public void assignTeamMember(TeamMember teamMember)
  {
    if (!(teamMemberList.contains(teamMember)))
    teamMemberList.add(teamMember);
  }

  /**
   * The system checks if the team member is a responsible one
   * If yes, it can not be removed
   * @param teamMember
   */
  public void unassignTeamMember(TeamMember teamMember)
  {
    if (teamMember.equals(getResponsibleTeamMember()))
      throw new IllegalArgumentException("You can not unassign a responsible team member");
    teamMemberList.remove(teamMember);
  }

  public void assignResponsibleTeamMember(TeamMember teamMember)
  {
    unassignResponsibleTeamMember();
    responsibleTeamMember = teamMember;
  }

  public void unassignResponsibleTeamMember()
  {
    responsibleTeamMember = null;
  }

  public TeamMember getResponsibleTeamMember()
  {
    return responsibleTeamMember;
  }

  public String toString()
  {
    return "Id: " + id + "\n"
        + "Related Project: "
        + "User Story: " + userStory + "\n"
        + "Estimated Time: " + estimatedTime + "\n"
        + "Hours Worked: " + hoursWorked + "\n"
        + "Starting date: " + startingDate.toString() + "\n"
        + "Deadline: " + deadline + "\n"
        + "Status" + getStatus().getName() + "\n"
        + "Priority: " + getPriority().getName() + "\n"
        + "Type: " + getType().getName() + "\n"
        + "Responsible Team Member: " + getResponsibleTeamMember() + "\n"
        + "Tasks: " + taskList.toString() + "\n"
        + "Team Members: " + teamMemberList.toString();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Requirement))
      return false;
    Requirement other = (Requirement) obj;

    if (other.getResponsibleTeamMember() != null) {
      if (getResponsibleTeamMember() != null && !other.getResponsibleTeamMember().equals(getResponsibleTeamMember())) {
        return false;
      }
    }
    return id == other.id
        && relatedProject.equals(other.relatedProject)
        && userStory.equals(other.userStory)
        && estimatedTime == other.estimatedTime
        && hoursWorked == other.hoursWorked
        && startingDate.equals(other.startingDate)
        && deadline.equals(other.deadline)
        && status == other.status
        && type == other.type
        && priority == other.priority
        && taskList.equals(other.taskList)
        && teamMemberList.equals(other.teamMemberList);
  }

}
