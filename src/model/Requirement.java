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
    if (relatedProject == null)
      throw new IllegalArgumentException("Related project cannot be null");
    this.relatedProject = relatedProject;
    Date.checkDates(startingDate, deadline);
    setStartingDate(startingDate);
    setDeadline(deadline);
    setUserStory(userStory);
    setEstimatedTime(estimatedTime);
    setPriority(priority);
    setType(type);
    this.id = 0;
    this.hoursWorked = 0;
    this.status = RequirementStatus.NOT_STARTED;
    this.taskList = new TaskList();
    this.teamMemberList = new TeamMemberList();
    this.responsibleTeamMember = null;
  }

  //    SETTERS
  public void setId(int id)
  {
    if (id == 0)
      throw new IllegalArgumentException("Id cannot be 0");
    else if (id < 0)
      throw new IllegalArgumentException("Id cannot be negative");
    this.id = id;
  }

  public void setUserStory(String userStory)
  {
    if (userStory == null || userStory.equals(""))
      throw new IllegalArgumentException("User story cannot be empty");
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
    if (startingDate == null)
      throw new IllegalArgumentException("Starting date cannot be null");
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
    if (deadline == null)
      throw new IllegalArgumentException("Deadline cannot be null");
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

  /**
   * The method sorts the requirements by id if this is edited
   * @param priority the new priority
   */
  public void setPriority(Priority priority)
  {
    if (priority == null)
      throw new IllegalArgumentException("Priority cannot be null");
    if (this.priority != priority)
    {
      this.priority = priority;
      relatedProject.getProjectRequirementList().remove(this);
      int critical = 0;
      int high = 0;
      int low = 0;
      for (int i = 0; i < relatedProject.getProjectRequirementList().size(); i++)
      {
        if (relatedProject.getProjectRequirementList().getRequirements().get(i).getPriority() == Priority.CRITICAL)
          critical = i;
        else if (relatedProject.getProjectRequirementList().getRequirements().get(i).getPriority() == Priority.HIGH)
          high = i;
        else
          low = i;
      }
      if (critical > high)
        high = critical;
      if (high > low)
        low = high;
      if (low == 0)
        relatedProject.getProjectRequirementList().getRequirements().add(this);
      else if (this.getPriority() == Priority.CRITICAL)
        relatedProject.getProjectRequirementList().getRequirements().add(critical + 1, this);
      else if (this.getPriority() == Priority.HIGH)
        relatedProject.getProjectRequirementList().getRequirements().add(high + 1, this);
      else
        relatedProject.getProjectRequirementList().getRequirements().add(this);
    }
  }

  public void setType(Type type)
  {
    if (type == null)
      throw new IllegalArgumentException("Type cannot be null");
    this.type = type;
  }

  public void setStatus(RequirementStatus status)
  {
    if (status == null)
      throw new IllegalArgumentException("Requirement status cannot be null");
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
      if (status == RequirementStatus.NOT_STARTED || status == RequirementStatus.STARTED)
      {
        boolean f = true;
        for (Task task : taskList.getTasks())
          if (task.getStatus() != Status.NOT_STARTED)
          {
            f = false;
            break;
          }
        if (!f)
          setStatus(RequirementStatus.STARTED);
        else
          setStatus(RequirementStatus.NOT_STARTED);
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
    if (task == null)
      throw new IllegalArgumentException("Task cannot be null");
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
    if (teamMember == null)
      throw new IllegalArgumentException("Team member cannot be null");
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
      throw new IllegalArgumentException("You can not unassign a responsible team member - Requirement: " + id);
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
    String str = "Requirement Id: " + id + "\n" + "Related Project Id: " + getRelatedProject().getID() + "\n" + "User Story: " + getUserStory() + "\n" + "Estimated Time: " + getEstimatedTime() + "\n"
        + "Hours Worked: " + getHoursWorked() + "\n" + "Starting date: " + getStartingDate().toString() + "\n" + "Deadline: " + getDeadline().toString() + "\n" + "Status" + getStatus().getName() + "\n"
        + "Priority: " + getPriority().getName() + "\n" + "Type: " + getType().getName() + "\n";
    if (getResponsibleTeamMember() != null)
      str += "Responsible Team Member: #" + getResponsibleTeamMember().getId() + " " + getResponsibleTeamMember().getFullName() + "\n";
    if (taskList.size() > 0)
      str += "Tasks: " + taskList.toString() + "\n";
    if (teamMemberList.size() > 0)
      str += "Team Members: " + teamMemberList.toString();
    return str;
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
