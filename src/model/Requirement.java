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
      double estimatedTime, Priority priority, Type type)
  {
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

  public String getPriorityAsString()
  {
    switch (getPriority())
    {
      case CRITICAL: return "Critical";
      case HIGH: return "High";
      case LOW: return "Low";
      default: throw new IllegalStateException("Not valid priority");
    }
  }

  public void setType(Type type)
  {
    this.type = type;
  }

  public Type getType()
  {
    return type;
  }

  public String getTypeAsString()
  {
    switch (getType())
    {
      case FUNCTIONAL: return "Functional";
      case NON_FUNCTIONAL: return "Non functional";
      case PROJECT_RELATED: return "Project Related";
      default: throw new IllegalStateException("Not valid type");
    }
  }

  public void setStatus(RequirementStatus status)
  {
    this.status = status;
  }

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

  public String getStatusAsString()
  {
    switch (getStatus())
    {
      case APPROVED: return "Approved";
      case REJECTED: return "Rejected";
      case STARTED: return "Started";
      case NOT_STARTED: return "Not started";
      case ENDED: return "Ended";
      default: throw new IllegalStateException("Not valid status");
    }
  }

  public double getHoursWorked()
  {
    hoursWorked = 0;
    for (Task task : taskList.getTasks())
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
    teamMemberList.add(teamMember);
  }

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
        + "User Story: " + userStory + "\n"
        + "Estimated Time: " + estimatedTime + "\n"
        + "Hours Worked: " + hoursWorked + "\n"
        + "Starting date: " + startingDate.toString() + "\n"
        + "Deadline: " + deadline + "\n"
        + "Status" + getStatusAsString() + "\n"
        + "Priority: " + getPriorityAsString() + "\n"
        + "Type: " + getTypeAsString() + "\n"
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
