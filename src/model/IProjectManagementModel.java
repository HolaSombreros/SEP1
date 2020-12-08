package model;

import java.util.ArrayList;

public interface IProjectManagementModel {
    void saveModel();
    void addProject(Project project);
    void addRequirement(Project project,Requirement requirement);
    void addTask(Requirement requirement, Task task);
    void addTeamMember(Project project, Requirement requirement, Task task,TeamMember teamMember);
    void editProject(Project project);
    void editRequirement(Project project, Requirement requirement);
    void editTask(Project project, Requirement requirement, Task task);
    void removeProject(Project project);
    void removeRequirement(Project project, Requirement requirement);
    void removeTask(Requirement requirement, Task task);
    void removeTeamMember(Project project,TeamMember teamMember);
    void removeTeamMember(Project project, Requirement requirement,TeamMember teamMember);
    void removeTeamMember(Project project, Requirement requirement, Task task,TeamMember teamMember);
    ProjectList getProjectList();
    RequirementList getRequirementList(Project project);
    TaskList getTaskList(Project project, Requirement requirement);
    TeamMemberList getTeamMemberList(Project Project);
    TeamMemberList getTeamMemberList(Project Project, Requirement requirement);
    TeamMemberList getTeamMemberList(Project Project, Requirement requirement, Task task);
    ArrayList<Project> getRelatedProjects(TeamMember teamMember);
    TeamMember getMostFrequentTeamMember(TeamMember teamMember);
    double getProductivity(TeamMember teamMember);
}
