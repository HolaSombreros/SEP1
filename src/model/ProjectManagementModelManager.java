package model;

import connections.IFileConnection;
import connections.XmlFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ProjectManagementModelManager implements IProjectManagementModel {
    private ProjectList projectList;
    private ArrayList<IFileConnection> fileConnections;

    public ProjectManagementModelManager() {
        this.projectList = new ProjectList();
        this.fileConnections = new ArrayList<>();
        
        createDummyData();
        fileConnections.add(new XmlFile("projects"));
        saveModel();
    }
    
    private void saveModel() {
        for (IFileConnection file : fileConnections) {
            try {
                file.saveModel(this);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void saveProject(Project project) {
        for (IFileConnection file : fileConnections) {
            try {
                file.saveProject(project);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void createDummyData() {
        Project project = new Project("Movies", "12", new Date(12,12, 2020), new Date(25, 10, 2022), Methodology.SCRUM);
        projectList.addProject(new Project("Project Management System for Colour IT", generateProjectId(), Date.today(), new Date(29, 12, 2021), Methodology.WATERFALL));
        projectList.addProject(new Project("Some other thing for whoever", generateProjectId(), Date.today(), new Date(18, 5, 2021), Methodology.SCRUM));
        projectList.addProject(project);
    
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
       /* projectList.getProject(0).getTeamMemberList().add(new TeamMember("Joseph","Joestar",1));
        projectList.getProject(0).getProjectRequirementList().getRequirement(0).getTeamMemberList().add(new TeamMember("Maria","Magdalena",2));
        projectList.getProject(0).getProjectRequirementList().getRequirement(0).getTaskList().getTask(0).getTeamMemberList().add(new TeamMember("Joseph","Joestar",1));
        projectList.getProject(0).getTeamMemberList().add(new TeamMember("Giorno","Giovanna",3));
        projectList.getProject(1).getTeamMemberList().add(new TeamMember("Pizza", "Pasta",4));
        TeamMember m1 = new TeamMember("Jojo", "Rabbit", 0);
        projectList.getProject(0).assignScrumMaster(new TeamMember("Joseph","Joestar",1));
        project.assignScrumMaster(m1);
        projectList.getProject(0).getProjectRequirementList().getRequirement(0).getTaskList().getTask(0).getTeamMemberList().add(new TeamMember("Maria","Magdalena",9));

*/

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
        saveModel();
    }

    @Override public void addRequirement(Project project,Requirement requirement) {
        project.addRequirement(requirement);
        saveModel();
    }

    @Override public void addTask(Requirement requirement, Task task) {
        requirement.addTask(task);
        saveModel();
    }

    @Override public void addTeamMember(Project project, Requirement requirement, Task task,TeamMember teamMember) {
        project.assignTeamMember(teamMember);
        requirement.assignTeamMember(teamMember);
        task.assignTeamMember(teamMember);
        saveModel();
    }

    @Override public void editProject(Project project) {
        saveModel();
    }

    @Override public void editRequirement(Project project, Requirement requirement,String userStory, double estimatedTime, TeamMember responsibleTeamMember, Date startingDate, Date deadline, RequirementStatus status, Type type,
        Priority priority) {
        requirement.edit(userStory,estimatedTime,responsibleTeamMember,startingDate,deadline,status,type,priority);
        saveModel();
        if (project.getStatus()==Status.ENDED)
            saveProject(project);
    }

    @Override public void editTask(Task task, String title, double estimatedTime, Date startingDate, Date deadline, Status status, TeamMember responsibleTeamMember, double hoursWorked, TeamMember teamMember) {
        task.edit(title, estimatedTime, startingDate, deadline, status, responsibleTeamMember, hoursWorked, teamMember);
        saveModel();
    }

    @Override public void removeProject(Project project) {
        projectList.removeProject(project);
        saveModel();
    }

    @Override public void removeRequirement(Project project, Requirement requirement) {
        project.removeRequirement(requirement);
        saveModel();
    }

    @Override public void removeTask(Requirement requirement, Task task) {
        requirement.removeTask(task);
        saveModel();
    }

    @Override public void removeTeamMember(Project project, TeamMember teamMember) {
        project.unassignTeamMember(teamMember);
        saveModel();
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
        saveModel();
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
        saveModel();
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
    
    /*
    loop through every task the person is in (project -> requirement -> task -> teamMemberList)
    loop through every person in the task
    add the team members to a HashMap (if needed) where ™ is key and HW is value.
    compare teamMember’s hoursWorked with X’s hoursWorked.
    if X’s hoursWorked is higher than ™’s, add ™’s hoursWorked to HashMap.
    if X’s hoursWorked is lower than ™’s, add X’s hoursWorked to HashMap.
    
    loop through HashMap and sort values, return ™ object with largest value.
     */
    @Override public TeamMember getMostFrequentTeamMember(TeamMember teamMember) throws FileNotFoundException {
        /*HashMap<TeamMember, Integer> frequentTeamMembers = new HashMap<>();
        for(Project project : projectList.getProjects())
            if(project.getTeamMemberList().getTeamMember(teamMember) != null) {
                for (Requirement requirement : project.getProjectRequirementList().getRequirements())
                    if (requirement.getTeamMemberList().getTeamMember(teamMember) != null) {
                        for (Task task : requirement.getTaskList().getTasks())
                            if (task.getTeamMemberList().getTeamMember(teamMember) != null) {
                                for (TeamMember member : task.getTeamMemberList().getTeamMembers()){
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

         */
        int[] frequentTeamMembers = new int[addTeamMembersToTheSystem().size()];
        for(int i = 1; i < addTeamMembersToTheSystem().size(); i++)
            frequentTeamMembers[i] = 0;
        //loops through all the projects
        for(Project project : projectList.getProjects())
            if(project.getTeamMemberList().getTeamMember(teamMember) != null)

                for (Requirement requirement : project.getProjectRequirementList().getRequirements())
                    if (requirement.getTeamMemberList().getTeamMember(teamMember) != null)

                        for (Task task : requirement.getTaskList().getTasks())
                            if (task.getTeamMemberList().getTeamMember(teamMember) != null)

                                for (TeamMember member : task.getTeamMemberList().getTeamMembers())
                                    if(!member.equals(teamMember)) {
                                        frequentTeamMembers[member.getId()]++;
                                        System.out.println(member.getFullName());
                                    }
        int max = 0,p = 0 ;
        for(int i = 1; i < addTeamMembersToTheSystem().size(); i++)
            if(frequentTeamMembers[i] > max && i != addTeamMembersToTheSystem().getTeamMember(teamMember).getId()){
                max = frequentTeamMembers[i];
                p = i;
                System.out.println(i);
            }

        if(max != 0)
            return addTeamMembersToTheSystem().getByID(p);
        else
            return null;
    }

    /**
     * returns a double number that represents the productivity of the selected teamMember
     * @param teamMember - the selected teamMember
     *
     *   loops through all the tasks within all the requirements within all the projects that the teamMember is working on
     *                   and calculates the sum of the total hours worked and the total number of tasks the teamMember has worked on
     * @return sum of total hours worked divided by the total number of tasks, multiplied by 100 to show the percentange
     * */
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
        if(total != 0)
            return teamMember.getTimeRegistration().getHoursWorked()*100/total;
        else
            return 0;
    }

    /**
     * reads a series of names from the given file and adds each teamMember to the system
     * creates and id that is incremented with each teamMember
     * creates a TeamMember object every time a full name is read
     * doesn't assign to projects
     * returns a TeamMemberList object containing all the TeamMembers that are now in the system
     *
     *
     * */
    @Override public TeamMemberList addTeamMembersToTheSystem() throws FileNotFoundException {
        TeamMemberList team = new TeamMemberList();
        File file = new File("src/files/teamMembers.txt");
        Scanner input = new Scanner(file);

        int id = 1;
        while (input.hasNext()){
            String line = input.nextLine();
            String element[] = line.split(" ");
            team.add(new TeamMember(element[0],element[1],id++));
        }
        input.close();
        //System.out.println(team.getByIndex(0).getFullName());
       // System.out.println(team.size());
        return team;
    }
}
