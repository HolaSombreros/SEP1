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
   *
   * @param startingDate starting date of the requirement
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
   *
   * @param deadline the deadline of the requirement
   */
  public void setDeadline(Date deadline)
  {
    if (relatedProject.getDeadline().isBefore(deadline))
      throw new IllegalArgumentException("Deadline can not be after project's deadline: " + relatedProject.getDeadline());
    else
      this.deadline = deadline.copy();

  }

  /**
   * The estimated time is not allowed to be less or equal to 0
   *
   * @param estimatedTime the estimated time of the requirement
   */
  public void setEstimatedTime(double estimatedTime)
  {
    if (estimatedTime <= 0)
      throw new IllegalArgumentException("Estimated time has to be higher than 0");
    this.estimatedTime = estimatedTime;
  }

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

  //   GETTERS

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
    double tasks = 0;
    for (Task task : taskList.getTasks())
      tasks += task.getEstimatedTime();
    if (tasks > estimatedTime)
      setEstimatedTime(tasks);
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
   *
   * @return status
   */
  public RequirementStatus getStatus()
  {
    if (taskList.size() == 0)
      status = RequirementStatus.NOT_STARTED;
    else
    {
      if (status == RequirementStatus.STARTED || status == RequirementStatus.ENDED)
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
        else
          setStatus(RequirementStatus.STARTED);
      }
      if (status == RequirementStatus.NOT_STARTED)
      {
        boolean f = true;
        for (Task task : taskList.getTasks())
          if (task.getStatus() != Status.STARTED)
          {
            f = false;
            break;
          }
        if (!f)
          setStatus(RequirementStatus.STARTED);
      }
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
   *
   * @param teamMember the team member which will be assigned
   */
  public void assignTeamMember(TeamMember teamMember)
  {
    if (!(teamMemberList.contains(teamMember)))
      teamMemberList.add(teamMember);
    if (!(relatedProject.getTeamMemberList().contains(teamMember)))
      relatedProject.assignTeamMember(teamMember);
  }

  /**
   * The system checks if the team member is a responsible one
   * If yes, it can not be removed
   * If no, the system will unassign him from the requirement and the related tasks
   *
   * @param teamMember the team member which will be unassigned
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
   *
   * @param teamMember the team member which will be responsible
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
   *
   * @param userStory             the new user story
   * @param estimatedTime         the new estimated time
   * @param responsibleTeamMember the new responsible member
   * @param startingDate          the new starting date
   * @param deadline              the new deadline
   * @param status                the new status
   * @param type                  the new type
   * @param priority              the new priority
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
    return id == other.id && relatedProject.getID().equals(other.relatedProject.getID()) && userStory.equals(other.userStory) && estimatedTime == other.estimatedTime
        && hoursWorked == other.hoursWorked && startingDate.equals(other.startingDate) && deadline.equals(other.deadline) && status == other.status && type == other.type
        && priority == other.priority && taskList.equals(other.taskList) && teamMemberList.equals(other.teamMemberList);
  }

}
