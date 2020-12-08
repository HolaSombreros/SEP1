package model;

import connections.IFileConnection;
import connections.XmlFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ProjectManagementModelManager implements IProjectManagementModel {
    private ProjectList projectList;
    private ArrayList<IFileConnection> fileConnections;

    public ProjectManagementModelManager() {
        this.projectList = new ProjectList();
        this.fileConnections = new ArrayList<>();
        
        createDummyData(); // TODO - eventually (maybe?!) remove this and the two methods below...
        fileConnections.add(new XmlFile("projects"));
        saveModel();
    }
    
    public void saveModel() {
        for (IFileConnection file : fileConnections) {
            try {
                file.saveModel(this);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void createDummyData() {
        Project project = new Project("Movies", "12", new Date(12,12, 2001), new Date(25, 10, 2021), Methodology.SCRUM);
        projectList.addProject(new Project("Project Management System for Colour IT", generateProjectId(), Date.today(), new Date(29, 12, 2021), Methodology.WATERFALL));
        projectList.addProject(new Project("Some other thing for whoever", generateProjectId(), Date.today(), new Date(18, 05, 2021), Methodology.SCRUM));
    
        projectList.getProject(0).addRequirement(new Requirement("As a Project Creator, I want to add a new project with a name, id, deadline, starting date and methodology, so that work on that project can start",
            new Date(12, 3, 2021),
            new Date(21, 5, 2021),
            24,
            Priority.CRITICAL,
            Type.FUNCTIONAL,
            projectList.getProject(0)));
    
        projectList.getProject(0).getProjectRequirementList().getRequirement(0).addTask(new Task("Do some stuff",
            new Date(15, 3, 2021),
            new Date(19, 4, 2021),
            5,
            projectList.getProject(0).getProjectRequirementList().getRequirement(0)));
    
        projectList.getProject(0).getTeamMemberList().add(new TeamMember("Joseph","Joestar",0));
        projectList.getProject(0).getTeamMemberList().add(new TeamMember("Giorno","Giovanna",1));
        projectList.getProject(1).getTeamMemberList().add(new TeamMember("Pizza", "Pasta",0));
        TeamMember m1 = new TeamMember("Jojo", "Rabbit", 0);
        project.assignScrumMaster(m1);



    }

    /**
     * creates a new string of length 8 from letters of the alphabet picked randomly
     * */
    private String generateProjectId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String id = "";
        Random random = new Random();
        for(int i = 0; i < 9; i++)
            id += chars.charAt(random.nextInt(chars.length()));
        return id;
    }

    // Model methods from IProjectManagementModel:
    @Override public void addProject(Project project) {
        projectList.addProject(project);

    }

    @Override public void addRequirement(Project project,Requirement requirement) {
        project.addRequirement(requirement);
    }

    @Override public void addTask(Requirement requirement, Task task) {
        requirement.addTask(task);
    }

    @Override public void addTeamMember(Project project, Requirement requirement, Task task,TeamMember teamMember) {
        project.assignTeamMember(teamMember);
        requirement.assignTeamMember(teamMember);
        task.assignTeamMember(teamMember);
    }

    //TODO do we even need these 3 :-?
    @Override public void editProject(Project project) {

    }

    @Override public void editRequirement(Project project, Requirement requirement) {

    }

    @Override public void editTask(Project project, Requirement requirement, Task task) {

    }

    @Override public void removeProject(Project project) {
        projectList.removeProject(project);
    }

    @Override public void removeRequirement(Project project, Requirement requirement) {
        project.removeRequirement(requirement);
    }

    @Override public void removeTask(Requirement requirement, Task task) {

        requirement.removeTask(task);
    }

    @Override public void removeTeamMember(Project project, TeamMember teamMember) {
        project.unassignTeamMember(teamMember);

    }

    @Override public void removeTeamMember(Project project, Requirement requirement,TeamMember teamMember) {
        requirement.unassignTeamMember(teamMember);
        boolean unassign=true;
        for (Requirement requirement1: requirement.getRelatedProject().getProjectRequirementList().getRequirements())
            if (requirement1.getTeamMemberList().contains(teamMember))
            {
                unassign = false;
                break;
            }
        if(unassign)
            requirement.getRelatedProject().unassignTeamMember(teamMember);
    }

    @Override public void removeTeamMember(Project project, Requirement requirement, Task task,TeamMember teamMember) {
        task.unassignTeamMember(teamMember);
        boolean unassign = true;
        for (Task task1 : task.getRelatedRequirement().getTaskList().getTasks())
            if (task1.getTeamMemberList().contains(teamMember))
            {
                unassign = false;
                break;
            }
        if (unassign)
            removeTeamMember(project,requirement,teamMember);
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
        /*if(!projectList.contains(project))
            throw new IllegalArgumentException("Project not found!");*/
        return project.getProjectRequirementList();
    }

    /**
     * if the requirement is not within the project throws an exception
     * @param project - a project chosen from the ProjectList
     * @param requirement - a requirement chosen from the chosen project's RequirementList
     * @return the taskList of the requirement
     * */
    @Override public TaskList getTaskList(Project project, Requirement requirement) {
        /*if(!project.getProjectRequirementList().contains(requirement))
            throw new IllegalArgumentException("Requirement not found!");
        else*/
            return requirement.getTaskList();
    }

    /**
     * @param project - a project chosen from the ProjectList
     * @return TeamMemberList object containing the list of the team members working on the given project
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
     * @return another teamMember with the most interactions with the given one
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
        HashMap<TeamMember, Integer> frequentTeamMembers = new HashMap<>();
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

    @Override public double getProductivity(TeamMember teamMember)
    {
        double total = 0;
        for (Project project : projectList.getProjects())
            if (project.getTeamMemberList().contains(teamMember))
                for (Requirement requirement : project.getProjectRequirementList().getRequirements())
                    if (requirement.getTeamMemberList().contains(teamMember))
                        for (Task task : requirement.getTaskList().getTasks())
                            if (task.getTeamMemberList().contains(teamMember))
                                total += task.getEstimatedTime();
        return teamMember.getTimeRegistration().getHoursWorked()*100/total;
    }
}
