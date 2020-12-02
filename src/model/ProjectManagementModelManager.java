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

    /**
     * @return projectList - the list of projects in the system
     * */
    @Override public ProjectList getProjectList() {

        return projectList;
    }

    /**
     * if project not in ProjectList throws an exception
     * @param project - a project chosen from the ProjectList
     * @return the requirementList of the project
     * */
    @Override public RequirementList getRequirementList(Project project) {
        if(!projectList.contains(project))
            throw new IllegalArgumentException("Project not found!");
        return project.getProjectRequirementList();
    }

    /**
     * if the requirement is not within the project throws an exception
     * @param project - a project chosen from the ProjectList
     * @param requirement - a requirement chosen from the chosen project's RequirementList
     * @return the taskList of the requirement
     * */
    @Override public TaskList getTaskList(Project project, Requirement requirement) {
        if(!project.getProjectRequirementList().contains(requirement))
            throw new IllegalArgumentException("Requirement not found!");
        else
            return requirement.getTaskList();
    }

    /**
     * @param project - a project chosen from the ProjectList
     * @return TeamMemberlist object containing the list of the team members working on the given project
     * */
    @Override public TeamMemberList getTeamMemberList(Project project) {

        return project.getTeamMemberList();
    }


    /**
     * checks if the given requirement exists within the given project
     * if yes, returns a TeamMemberList object, else throws an exception
     * @param project a project chosen from the ProjectList
     * @param requirement a requirement chosen from the chosen project's RequirementList
     * @return a TeamMember object containing the list of the team members working on the given requirement
     * */
    @Override public TeamMemberList getTeamMemberList(Project project, Requirement requirement) {
        if(!project.getProjectRequirementList().contains(requirement))
            throw new IllegalArgumentException("Requirement not found!");
        else
            return requirement.getTeamMemberList();

    }

    /**
     * checks if the given project contains the given requirement
     * if no, throws an exception,
     * if yes checks if the requirement contains the given task
     * if no, throws an exception
     * if yes returns a TeamMemberList object
     * @param project a project chosen from the ProjectList
     * @param requirement a requirement chosen from the project's RequirementList
     * @param task a task chosen from the requirement's TaskList
     * @return a TeamMember object containing the list of the team members working on the given task
     */
    @Override public TeamMemberList getTeamMemberList(Project project, Requirement requirement, Task task) {
        if(!project.getProjectRequirementList().contains(requirement))
            throw new IllegalArgumentException("Requirement not found!");
        else if (!requirement.getTaskList().contains(task))
            throw new IllegalArgumentException("Task not found!");
        else
            return task.getTeamMemberList();
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
