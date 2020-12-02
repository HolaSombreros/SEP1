package model;

public class Requirement
{
  private int id;
  private String userStory;
  private double estimatedTime;
  private double hoursWorked;
  private TaskList taskList;
  private TeamMemberList teamMemberList;
  private TeamMember responsibleTeamMember;
  private Date startingDate;
  private Date deadline;
  private RequirementStatus status;
  private Type type;
  private Priority priority;

  public Requirement(String userStory, Date startingDate, Date deadline,
      double estimatedTime, Priority priority, Type type)
  {
    id = 0;
    this.userStory = userStory;
    this.startingDate = startingDate.copy();
    this.deadline = deadline.copy();
    this.estimatedTime = estimatedTime;
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

  public void setStartingDate(Date startingDate)
  {
    this.startingDate = startingDate.copy();
  }

  public Date getStartingDate()
  {
    return startingDate.copy();
  }

  public void setDeadline(Date deadline)
  {
    this.deadline = deadline.copy();
  }

  public Date getDeadline()
  {
    return deadline.copy();
  }

  public void setEstimatedTime(double estimatedTime)
  {
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

  public RequirementStatus getStatus()
  {
    return status;
  }

  public double getHoursWorked()
  {
    hoursWorked = 0;
    for (Task task : taskList)
      hoursWorked += task.getTimeRegistration().getHoursWorked();
    return hoursWorked;
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
    for (Task task : taskList)
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
    teamMemberList.add(teamMember);
  }

  public void unassignTeamMember(TeamMember teamMember)
  {
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
        + "User Story: " + userStory + "\n"
        + "Estimated Time: " + estimatedTime + "\n"
        + "Hours Worked: " + hoursWorked + "\n"
        + "Starting date: " + startingDate.toString() + "\n"
        + "Deadline: " + deadline + "\n"
        + "Status" + status.method() + "\n"
        + "Priority: " + priority.method() + "\n"
        + "Type: " + type.method() + "\n"
        + "Responsible Team Member: " + getResponsibleTeamMember() + "\n"
        + "Tasks: " + taskList.toString() + "\n"
        + "Team Members: " + teamMemberList.toString();
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Requirement))
      return false;
    Requirement other = (Requirement) obj;

    return id == other.id
        && userStory.equals(other.userStory)
        && estimatedTime == other.estimatedTime
        && hoursWorked == other.hoursWorked
        && startingDate.equals(other.startingDate)
        && deadline.equals(other.deadline)
        && status == other.status && type == other.type
        && priority == other.priority
        && responsibleTeamMember.equals(other.responsibleTeamMember)
        && taskList.equals(other.taskList)
        && teamMemberList.equals(other.teamMemberList);
  }

}
