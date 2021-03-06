package mediator;

import model.*;

import java.io.FileNotFoundException;

public interface IProjectManagementModel
{
  void addProject(Project project, boolean doSave);
  void addRequirement(Project project, Requirement requirement, boolean doSave);
  void addTask(Requirement requirement, Task task, boolean doSave);
  void addTeamMember(Project project, Requirement requirement, Task task, TeamMember teamMember);
  void editProject(Project project);
  void editRequirement(Project project, Requirement requirement, String userStory, double estimatedTime, TeamMember responsibleTeamMember, Date startingDate, Date deadline,
      RequirementStatus status, Type type, Priority priority);
  void editTask(Task task, String title, double estimatedTime, Date startingDate, Date deadline, Status status, TeamMember responsibleTeamMember, double hoursWorked, TeamMember teamMember);
  void removeProject(Project project);
  void removeRequirement(Project project, Requirement requirement);
  void removeTask(Requirement requirement, Task task);
  void removeTeamMember(Project project, TeamMember teamMember);
  void removeTeamMember(Project project, Requirement requirement, TeamMember teamMember);
  void removeTeamMember(Project project, Requirement requirement, Task task, TeamMember teamMember);
  ProjectList getProjectList();
  RequirementList getRequirementList(Project project);
  TaskList getTaskList(Project project, Requirement requirement);
  TeamMemberList getTeamMemberList(Project Project);
  TeamMemberList getTeamMemberList(Project Project, Requirement requirement);
  TeamMemberList getTeamMemberList(Project Project, Requirement requirement, Task task);
  int getWorkingTasks(TeamMember teamMember);
  TeamMember getMostFrequentTeamMember(TeamMember teamMember) throws FileNotFoundException;
  double getProductivity(TeamMember teamMember);
  TeamMemberList addTeamMembersToTheSystem() throws FileNotFoundException;
  TeamMemberList getTeam();
}
