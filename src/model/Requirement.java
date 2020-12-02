package model;

public class Requirement
{
  private static int idCounter;
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

  public Requirement(String userStory, Date startingDate, Date deadline, double estimatedTime, Priority priority, Type type)
  {
    
  }
}
