package model;

import connections.IFileConnection;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectManagementModelManager implements IProjectManagementModel {
    private ProjectList projectList;
    private ArrayList<IFileConnection> fileConnections;

    public ProjectManagementModelManager() {
        this.projectList = new ProjectList();
        this.fileConnections = new ArrayList<>();
        /*
        createDummyData(); // TODO - eventually (maybe?!) remove this and the method below...*/
    }
    
    private void createDummyData() {
        projectList.addProject(new Project("Project Management System for Colour IT", generateProjectId(), new Date(6, 12, 2020), new Date(13, 12, 2020), Methodology.WATERFALL));
    }
    
    private String generateProjectId() {
        return "hoogabooga";
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

    /**
     * searches through all the projects in the projectList and if the teamMemberList related
     * to the project contains the teamMember adds the project to the new list that is then returned
     *
     * @param teamMember - a given teamMember selected by the user
     * @return an ArrayList of related projects that contain the teamMember
     * */
    @Override public ArrayList<Project> getRelatedProjects(TeamMember teamMember) {
        ArrayList<Project> relatedProjects = new ArrayList<>();
        for(int i = 0; i < projectList.size(); i++)
            if(projectList.getProject(i).getTeamMemberList().contains(teamMember))
                relatedProjects.add(projectList.getProject(i));
        return relatedProjects;
    }


    /**
     * @param teamMember a given teamMember selected by the user
     * @return another teamMemeber with the most interactions with the given one
     *          or null if the teamMember has not worked on a project yet
     * loops through all the projects within the projectList,
     *                all the requirements of every project,
     *                all the tasks of every requirement
     *                all the teamMembers of every task
     *                that the given teamMember has worked on and creates a hashmap with all the other teamMembers in the list as key
     *                and the number of lists they were in.
     *                the teamMember with the maximum number value is returned
     *
     * */
    @Override public TeamMember getMostFrequentTeamMember(TeamMember teamMember) {
        HashMap<TeamMember, Integer> frequentTeamMembers = new HashMap<TeamMember, Integer>();
        for(int i = 0; i < projectList.size(); i++)
            if(projectList.getProject(i).getTeamMemberList().contains(teamMember)) {
                Project project = projectList.getProject(i);
                for (int j = 0; j < project.getProjectRequirementList().size(); j++)
                    if (project.getProjectRequirementList().getRequirement(i).getTeamMemberList().contains(teamMember)) {
                        Requirement requirement = project.getProjectRequirementList().getRequirement(i);
                        for (int k = 0; k < requirement.getTaskList().size(); k++)
                            if (requirement.getTaskList().getTask(i).getTeamMemberList().contains(teamMember)) {
                                Task task = requirement.getTaskList().getTask(i);
                                for (int m = 0; m < task.getTeamMemberList().size(); m++){
                                    TeamMember member = task.getTeamMemberList().getByIndex(m);
                                    if(!frequentTeamMembers.containsKey(member) && !member.equals(teamMember))
                                        frequentTeamMembers.put(member,1);
                                    else
                                        frequentTeamMembers.put(member, frequentTeamMembers.get(member) + 1);
                            }
                        }
                }
            }
        int max = 0;
            for(int i : frequentTeamMembers.values())
                if(max < i)
                    max = i;
            for(TeamMember i : frequentTeamMembers.keySet())
                if(i.equals(frequentTeamMembers.containsValue(max)))
                    return i;
        return null;
    }

    @Override public double getProductivity(TeamMember teamMember) {
        return 0;
    }
}
