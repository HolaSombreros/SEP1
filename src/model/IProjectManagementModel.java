package model;

import java.util.ArrayList;

public interface IProjectManagementModel {
    void addProject();
    void addRequirement(Project project);
    void addTask(Project project, Requirement requirement);
    void addTeamMember(Project project);
    void addTeamMember(Project project, Requirement requirement);
    void addTeamMember(Project project, Requirement requirement, Task task);
    void editProject(Project project);
    void editRequirement(Project project, Requirement requirement);
    void editTask(Project project, Requirement requirement, Task task);
    void removeProject(Project project);
    void removeRequirement(Project project, Requirement requirement);
    void removeTask(Project project, Requirement requirement, Task task);
    void removeTeamMember(Project project);
    void removeTeamMember(Project project, Requirement requirement);
    void removeTeamMember(Project project, Requirement requirement, Task task);
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
