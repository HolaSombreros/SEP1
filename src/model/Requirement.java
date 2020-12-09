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

  public Requirement(String userStory, Date startingDate, Date deadline, double estimatedTime, Priority priority, Type type, Project relatedProject)
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

  //    SETTERS
  public void setId(int id)
  {
    this.id = id;
  }

  public void setUserStory(String userStory)
  {
    this.userStory = userStory;
  }

  /**
   * The system checks if the starting date is after the project's starting
   * date and before the project's deadline
   * @param startingDate
   */
  public void setStartingDate(Date startingDate)
  {
    if (startingDate.isBefore(relatedProject.getStartingDate()))
      throw new IllegalArgumentException("Starting date can not be before project's starting date: " + relatedProject.getStartingDate().toString());
    if (!(startingDate.isBefore(relatedProject.getDeadline())))
      throw new IllegalArgumentException("Starting date can not be after project's deadline: " + relatedProject.getDeadline().toString());
    this.startingDate = startingDate.copy();
  }

  /**
   * The system checks if the deadline is before the project's deadline
   * @param deadline
   */
  public void setDeadline(Date deadline)
  {
    if (!(deadline.isBefore(relatedProject.getDeadline())))
      throw new IllegalArgumentException("Deadline can not be after project's deadline: " + relatedProject.getDeadline());
    this.deadline = deadline.copy();
  }

  /**
   * The estimated time is not allowed to be less or equal to 0
   * @param estimatedTime
   */
  public void setEstimatedTime(double estimatedTime)
  {
    if (estimatedTime <= 0)
      throw new IllegalArgumentException("Estimated time has to be higher than 0");
    this.estimatedTime = estimatedTime;
  }

  //   GETTERS

  public void setPriority(Priority priority)
  {
    this.priority = priority;
  }

  public void setType(Type type)
  {
    this.type = type;
  }

  public void setStatus(RequirementStatus status)
  {
    this.status = status;
  }

  public int getId()
  {
    return id;
  }

  public String getUserStory()
  {
    return userStory;
  }

  public Date getStartingDate()
  {
    return startingDate.copy();
  }

  public Date getDeadline()
  {
    return deadline.copy();
  }

  public double getEstimatedTime()
  {
    return estimatedTime;
  }

  public Priority getPriority()
  {
    return priority;
  }

  public Type getType()
  {
    return type;
  }

  /**
   * The system checks if all tasks are ended
   * If yes, the status changes to ended
   * @return status
   */
  public RequirementStatus getStatus()
  {
    if (status == RequirementStatus.STARTED)
    {
      boolean d = true;
      for (Task task : taskList.getTasks())
        if (task.getStatus() != Status.ENDED)
        {
          d = false;
          break;
        }
      if (d)
        setStatus(RequirementStatus.ENDED);
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

  //   TASK RELATED

  public void addTask(Task task)
  {
    taskList.add(task);
  }

  public void removeTask(Task task)
  {
    taskList.remove(task);
  }

  public TaskList getTaskList()
  {
    return taskList;
  }

  //   TEAMMEMBER RELATED

  public TeamMemberList getTeamMemberList()
  {
    return teamMemberList;
  }

  /**
   * The system will assign the team member in a requirement and the related project as well if they are not already there
   * @param teamMember
   */
  public void assignTeamMember(TeamMember teamMember)
  {
    if (!(teamMemberList.contains(teamMember)))
      teamMemberList.add(teamMember);
    if(!(relatedProject.getTeamMemberList().contains(teamMember)))
      relatedProject.assignTeamMember(teamMember);
  }

  /**
   * The system checks if the team member is a responsible one
   * If yes, it can not be removed
   * If no, the system will unassign him from the requirement and the related tasks
   * @param teamMember
   */
  public void unassignTeamMember(TeamMember teamMember)
  {
    if (teamMember.equals(getResponsibleTeamMember()))
      throw new IllegalArgumentException("You can not unassign a responsible team member");
    teamMemberList.remove(teamMember);
    for (Task task : taskList.getTasks())
    {
      task.unassignTeamMember(teamMember);
    }
  }

  /**
   * The system unassigns first the responsible team member, then assigns the other one
   * @param teamMember
   */
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


  //   OTHER METHODS

  /**
   * The method sets all the editable variables of the requirement
   * @param userStory
   * @param estimatedTime
   * @param responsibleTeamMember
   * @param startingDate
   * @param deadline
   * @param status
   * @param type
   * @param priority
   */
  public void edit(String userStory, double estimatedTime, TeamMember responsibleTeamMember, Date startingDate, Date deadline, RequirementStatus status, Type type,
      Priority priority)
  {
    setUserStory(userStory);
    setEstimatedTime(estimatedTime);
    if (responsibleTeamMember == null)
      unassignResponsibleTeamMember();
    else
      assignResponsibleTeamMember(responsibleTeamMember);
    setStartingDate(startingDate);
    setDeadline(deadline);
    setStatus(status);
    setType(type);
    setPriority(priority);
  }

  public String toString()
  {
    return "Id: " + id + "\n" + "Related Project: " + relatedProject.getName() + "\n" + "User Story: " + userStory + "\n" + "Estimated Time: " + estimatedTime + "\n"
        + "Hours Worked: " + hoursWorked + "\n" + "Starting date: " + startingDate.toString() + "\n" + "Deadline: " + deadline + "\n" + "Status" + getStatus().getName() + "\n"
        + "Priority: " + getPriority().getName() + "\n" + "Type: " + getType().getName() + "\n" + "Responsible Team Member: " + getResponsibleTeamMember() + "\n" + "Tasks: "
        + taskList.toString() + "\n" + "Team Members: " + teamMemberList.toString();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Requirement))
      return false;
    Requirement other = (Requirement) obj;

    if (other.getResponsibleTeamMember() != null)
    {
      if (getResponsibleTeamMember() != null && !other.getResponsibleTeamMember().equals(getResponsibleTeamMember()))
      {
        return false;
      }
    }
    return id == other.id && relatedProject.getID() == other.relatedProject.getID() && userStory.equals(other.userStory) && estimatedTime == other.estimatedTime
        && hoursWorked == other.hoursWorked && startingDate.equals(other.startingDate) && deadline.equals(other.deadline) && status == other.status && type == other.type
        && priority == other.priority && taskList.equals(other.taskList) && teamMemberList.equals(other.teamMemberList);
  }

}
