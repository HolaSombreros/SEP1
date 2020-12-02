package model;

import connections.IFileConnection;

import java.util.ArrayList;

public class ProjectManagementModelManager implements IProjectManagementModel {
    private ProjectList projectList;
    private ArrayList<IFileConnection> fileConnections;

    public ProjectManagementModelManager() {
        this.projectList = new ProjectList();
        this.fileConnections = new ArrayList<>();
    }

    // Model methods from IProjectManagementModel:
    @Override public void addProject() {

    }

    @Override public void addRequirement(Project project) {

    }

    @Override public void addTask(Project project, Requirement requirement) {

    }

    @Override public void addTeamMember(Project project) {

    }

    @Override public void addTeamMember(Project project, Requirement requirement) {

    }

    @Override public void addTeamMember(Project project, Requirement requirement, Task task) {

    }

    @Override public void editProject(Project project) {

    }

    @Override public void editRequirement(Project project, Requirement requirement) {

    }

    @Override public void editTask(Project project, Requirement requirement, Task task) {

    }

    @Override public void removeProject(Project project) {

    }

    @Override public void removeRequirement(Project project, Requirement requirement) {

    }

    @Override public void removeTask(Project project, Requirement requirement, Task task) {

    }

    @Override public void removeTeamMember(Project project) {

    }

    @Override public void removeTeamMember(Project project, Requirement requirement) {

    }

    @Override public void removeTeamMember(Project project, Requirement requirement, Task task) {

    }

    @Override public ProjectList getProjectList() {
        return null;
    }

    @Override public RequirementList getRequirementList(Project project) {
        return null;
    }

    @Override public TaskList getTaskList(Project project, Requirement requirement) {
        return null;
    }

    @Override public TeamMemberList getTeamMemberList(Project Project) {
        return null;
    }

    @Override public TeamMemberList getTeamMemberList(Project Project, Requirement requirement) {
        return null;
    }

    @Override public TeamMemberList getTeamMemberList(Project Project, Requirement requirement, Task task) {
        return null;
    }

    @Override public ArrayList<Project> getRelatedProjects(TeamMember teamMember) {
        return null;
    }

    @Override public TeamMember getMostFrequentTeamMember(TeamMember teamMember) {
        return null;
    }

    @Override public double getProductivity(TeamMember teamMember) {
        return 0;
    }
}
