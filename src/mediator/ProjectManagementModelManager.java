package mediator;

import connections.IFileConnection;
import connections.XmlFile;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ProjectManagementModelManager implements IProjectManagementModel
{
  private ProjectList projectList;
  private ArrayList<IFileConnection> fileConnections;
  private TeamMemberList team;

  public ProjectManagementModelManager() throws FileNotFoundException
  {
    this.fileConnections = new ArrayList<>();
    this.projectList = new ProjectList();
    this.team = addTeamMembersToTheSystem();
    fileConnections.add(new XmlFile("model"));
  }

  public static IProjectManagementModel loadModel() throws FileNotFoundException
  {
    IFileConnection xml = new XmlFile("model");
    return xml.loadModel();
  }

  public void saveModel()
  {
    for (IFileConnection file : fileConnections)
    {
      try
      {
        file.saveModel(this);
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage());
      }
    }
  }

  public void saveProject(Project project)
  {
    try
    {
      IFileConnection projectFile = new XmlFile(project.getID());
      projectFile.saveProject(project);
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  private void createDummyData()
  {
    Project project = new Project("Movies", "12", new Date(12, 12, 2020), new Date(25, 10, 2022), Methodology.SCRUM);
    projectList.addProject(new Project("Project Management System for Colour IT", generateProjectId(), Date.today(), new Date(29, 12, 2021), Methodology.WATERFALL));
    projectList.addProject(new Project("Some other thing for whoever", generateProjectId(), Date.today(), new Date(18, 5, 2021), Methodology.SCRUM));
    projectList.addProject(new Project("test", "1", Date.today(), new Date(20, 10, 2025), Methodology.SCRUM));
    projectList.addProject(new Project("jhdajh", "test", Date.today(), new Date(20, 10, 2025), Methodology.SCRUM));
    projectList.addProject(new Project("test", "13", Date.today(), new Date(20, 10, 2025), Methodology.SCRUM));
    projectList.addProject(new Project("test55", "155", Date.today(), new Date(20, 10, 2025), Methodology.SCRUM));
    projectList.addProject(new Project("random", "3", Date.today(), new Date(20, 10, 2025), Methodology.SCRUM));

    projectList.addProject(project);

    projectList.getProject(0).addRequirement(
        new Requirement("As a Project Creator, I want to add a new project with a name, id, deadline, starting date and methodology, so that work on that project can start",
            new Date(12, 3, 2021), new Date(21, 5, 2021), 24, Priority.CRITICAL, Type.FUNCTIONAL, projectList.getProject(0)));

    projectList.getProject(0).getProjectRequirementList().getRequirement(0)
        .addTask(new Task("Do some stuff", new Date(15, 3, 2021), new Date(19, 4, 2021), 5, projectList.getProject(0).getProjectRequirementList().getRequirement(0)));
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

  public TeamMemberList getTeam()
  {
    return team;
  }

  /**
   * creates a new string of length 8 from letters of the alphabet picked randomly
   **/
  public static String generateProjectId()
  {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String id = "";
    Random random = new Random();
    for (int i = 0; i < 9; i++)
      id += chars.charAt(random.nextInt(chars.length()));
    return id;
  }

  // Model methods from IProjectManagementModel:
  @Override public void addProject(Project project, boolean doSave)
  {
    projectList.addProject(project);

    if (doSave)
      saveModel();
  }

  @Override public void addRequirement(Project project, Requirement requirement, boolean doSave)
  {
    project.addRequirement(requirement);

    if (doSave)
      saveModel();
  }

  @Override public void addTask(Requirement requirement, Task task, boolean doSave)
  {
    requirement.addTask(task);

    if (doSave)
      saveModel();
  }

  @Override public void addTeamMember(Project project, Requirement requirement, Task task, TeamMember teamMember)
  {
    project.assignTeamMember(teamMember);
    requirement.assignTeamMember(teamMember);
    task.assignTeamMember(teamMember);
  }

  @Override public void editProject(Project project)
  {
    saveModel();
  }

  @Override public void editRequirement(Project project, Requirement requirement, String userStory, double estimatedTime, TeamMember responsibleTeamMember, Date startingDate,
      Date deadline, RequirementStatus status, Type type, Priority priority)
  {
    requirement.edit(userStory, estimatedTime, responsibleTeamMember, startingDate, deadline, status, type, priority);
    saveModel();
    if (project.getStatus() == Status.ENDED)
      saveProject(project);
  }

  @Override public void editTask(Task task, String title, double estimatedTime, Date startingDate, Date deadline, Status status, TeamMember responsibleTeamMember,
      double hoursWorked, TeamMember teamMember)
  {
    task.edit(title, estimatedTime, startingDate, deadline, status, responsibleTeamMember, hoursWorked, teamMember);
    saveModel();
  }

  @Override public void removeProject(Project project)
  {
    projectList.removeProject(project);
    saveModel();
  }

  /**
   * The method removes the requirement and all the unnecessary team members from the project
   *
   * @param project     the selected project
   * @param requirement the selected requirement
   */
  @Override public void removeRequirement(Project project, Requirement requirement)
  {
    for (TeamMember teamMember : requirement.getTeamMemberList().getTeamMembers())
    {
      boolean d = true;
      for (Requirement requirement1 : project.getProjectRequirementList().getRequirements())
      {
        if (requirement1.getTeamMemberList().contains(teamMember) && !requirement1.equals(requirement))
        {
          d = false;
          break;
        }
      }
      if (d && !teamMember.equals(requirement.getRelatedProject().getProductOwner()) && !teamMember.equals(requirement.getRelatedProject().getScrumMaster()))
        project.getTeamMemberList().remove(teamMember);
    }
    project.removeRequirement(requirement);
    saveModel();
  }

  /**
   * The method removes the task and all the unnecessary team members from the project/requirement
   *
   * @param requirement the selected requirement
   * @param task        the selected task
   */
  @Override public void removeTask(Requirement requirement, Task task)
  {
    for (TeamMember teamMember : task.getTeamMemberList().getTeamMembers())
    {
      boolean d = true;
      for (Task task1 : requirement.getTaskList().getTasks())
      {
        if (task1.getTeamMemberList().contains(teamMember) && !task1.equals(task))
        {
          d = false;
          break;
        }
      }
      if (d && !teamMember.equals(requirement.getResponsibleTeamMember()))
      {
        requirement.getTeamMemberList().remove(teamMember);
        boolean f = true;
        for (Requirement requirement1 : requirement.getRelatedProject().getProjectRequirementList().getRequirements())
        {
          if (requirement1.getTeamMemberList().contains(teamMember) && !requirement1.equals(requirement))
          {
            f = false;
            break;
          }
        }
        if (f && !teamMember.equals(requirement.getRelatedProject().getProductOwner()) && !teamMember.equals(requirement.getRelatedProject().getScrumMaster()))
          requirement.getRelatedProject().getTeamMemberList().remove(teamMember);
      }
    }
    requirement.removeTask(task);
    saveModel();

  }

  @Override public void removeTeamMember(Project project, TeamMember teamMember)
  {
    project.unassignTeamMember(teamMember);
    saveModel();
  }

  @Override public void removeTeamMember(Project project, Requirement requirement, TeamMember teamMember)
  {
    requirement.unassignTeamMember(teamMember);
    boolean unassign = true;
    for (Requirement requirement1 : requirement.getRelatedProject().getProjectRequirementList().getRequirements())
      if (requirement1.getTeamMemberList().contains(teamMember))
      {
        unassign = false;
        break;
      }
    if (unassign)
      project.unassignTeamMember(teamMember);
    saveModel();
  }

  @Override public void removeTeamMember(Project project, Requirement requirement, Task task, TeamMember teamMember)
  {
    task.unassignTeamMember(teamMember);
    boolean unassign = true;
    for (Task task1 : task.getRelatedRequirement().getTaskList().getTasks())
      if (task1.getTeamMemberList().contains(teamMember))
      {
        unassign = false;
        break;
      }
    if (unassign)
      removeTeamMember(project, requirement, teamMember);
    saveModel();
  }

  /**
   * @return projectList - the list of projects in the system
   */
  @Override public ProjectList getProjectList()
  {
    return projectList;
  }

  /**
   * if project not in ProjectList throws an exception
   *
   * @param project - a project chosen from the ProjectList
   * @return the requirementList of the project
   */
  @Override public RequirementList getRequirementList(Project project)
  {
    if (!projectList.contains(project))
      throw new IllegalArgumentException("Project not found!");
    return project.getProjectRequirementList();
  }

  /**
   * if the requirement is not within the project throws an exception
   *
   * @param project     - a project chosen from the ProjectList
   * @param requirement - a requirement chosen from the chosen project's RequirementList
   * @return the taskList of the requirement
   */
  @Override public TaskList getTaskList(Project project, Requirement requirement)
  {
    if (!project.getProjectRequirementList().contains(requirement))
      throw new IllegalArgumentException("Requirement not found!");
    return requirement.getTaskList();
  }

  /**
   * @param project - a project chosen from the ProjectList
   * @return TeamMemberList object containing the list of the team members working on the given project
   */
  @Override public TeamMemberList getTeamMemberList(Project project)
  {

    return project.getTeamMemberList();
  }

  /**
   * checks if the given requirement exists within the given project
   * if yes, returns a TeamMemberList object, else throws an exception
   *
   * @param project     a project chosen from the ProjectList
   * @param requirement a requirement chosen from the chosen project's RequirementList
   * @return a TeamMember object containing the list of the team members working on the given requirement
   */
  @Override public TeamMemberList getTeamMemberList(Project project, Requirement requirement)
  {
    if (!project.getProjectRequirementList().contains(requirement))
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
   *
   * @param project     a project chosen from the ProjectList
   * @param requirement a requirement chosen from the project's RequirementList
   * @param task        a task chosen from the requirement's TaskList
   * @return a TeamMember object containing the list of the team members working on the given task
   */
  @Override public TeamMemberList getTeamMemberList(Project project, Requirement requirement, Task task)
  {
    if (!project.getProjectRequirementList().contains(requirement))
      throw new IllegalArgumentException("Requirement not found!");
    else if (!requirement.getTaskList().contains(task))
      throw new IllegalArgumentException("Task not found!");
    else
      return task.getTeamMemberList();
  }

  /**
   * the method searches through all the started projects, requirements and tasks and if the team member
   * is working there, the counter of the working tasks will increase
   *
   * @param teamMember the selected team member
   * @return the number of tasks the member is working on
   */
  public int getWorkingTasks(TeamMember teamMember)
  {
    int total = 0;
    for (Project project : projectList.getProjects())
      if (project.getTeamMemberList().contains(teamMember) && project.getStatus() == Status.STARTED)
        for (Requirement requirement : project.getProjectRequirementList().getRequirements())
          if (requirement.getTeamMemberList().contains(teamMember) && requirement.getStatus() == RequirementStatus.STARTED)
            for (Task task : requirement.getTaskList().getTasks())
              if (task.getTeamMemberList().contains(teamMember) && task.getStatus() == Status.STARTED)
                total++;
    return total;
  }

  /**
   * @param teamMember a given teamMember selected by the user
   * @return another teamMember with the most interactions with the given one
   * or null if the teamMember has not worked on a project yet
   * loops through all the projects within the projectList,
   * all the requirements of every project,
   * all the tasks of every requirement
   * all the teamMembers of every task
   * that the given teamMember has worked on and creates a hashmap with all the other teamMembers in the list as key
   * and the number of lists they were in.
   * the teamMember with the maximum number value is returned
   */

  @Override public TeamMember getMostFrequentTeamMember(TeamMember teamMember)
  {
    int[] frequentTeamMembers = new int[getTeam().size() + 1];
    for (int i = 1; i < frequentTeamMembers.length; i++)
      frequentTeamMembers[i] = 0;
    for (Project project : projectList.getProjects())
      if (project.getTeamMemberList().getTeamMember(teamMember) != null)
        for (Requirement requirement : project.getProjectRequirementList().getRequirements())
          if (requirement.getTeamMemberList().getTeamMember(teamMember) != null)
            for (Task task : requirement.getTaskList().getTasks())
              if (task.getTeamMemberList().getTeamMember(teamMember) != null)
                for (TeamMember member : task.getTeamMemberList().getTeamMembers())
                  if (!member.equals(teamMember))
                  {
                    frequentTeamMembers[member.getId()]++;
                  }
    int max = 0, p = 0;
    for (int i = 1; i < frequentTeamMembers.length; i++)
    {
      if (frequentTeamMembers[i] > max && i != teamMember.getId())
      {
        max = frequentTeamMembers[i];
        p = i;
      }
    }

    if (max != 0)
      return getTeam().getByID(p);
    else
      return null;
  }

  /**
   * returns a double number that represents the productivity of the selected teamMember
   *
   * @param teamMember - the selected teamMember
   *                   loops through all the tasks within all the requirements within all the projects that the teamMember is working on
   *                   and calculates the sum of the total hours worked and the total number of tasks the teamMember has worked on
   * @return sum of total hours worked divided by the total number of tasks, multiplied by 100 to show the percentange
   */
  @Override public double getProductivity(TeamMember teamMember)
  {
    double totalEstimate = 0, totalWork = 0;
    for (Project project : projectList.getProjects())
      if (project.getTeamMemberList().contains(teamMember))
        for (Requirement requirement : project.getProjectRequirementList().getRequirements())
          if (requirement.getTeamMemberList().contains(teamMember))
            for (Task task : requirement.getTaskList().getTasks())
              if (task.getStatus() == Status.ENDED && task.getTeamMemberList().contains(teamMember))
              {
                totalEstimate += task.getEstimatedTime();
                totalWork += task.getTimeRegistration().getHoursWorked();
              }
    if (totalWork != 0)
      return Math.round(totalEstimate / totalWork * 100);
    else
      return 0;
  }

  /**
   * reads a series of names from the given file and adds each teamMember to the system
   * creates and id that is incremented with each teamMember
   * creates a TeamMember object every time a full name is read
   * doesn't assign to projects
   * returns a TeamMemberList object containing all the TeamMembers that are now in the system
   */
  @Override public TeamMemberList addTeamMembersToTheSystem() throws FileNotFoundException
  {
    TeamMemberList team = new TeamMemberList();
    File file = new File("src/files/teamMembers.txt");
    Scanner input = new Scanner(file);

    while (input.hasNext())
    {
      String line = input.nextLine();
      String element[] = line.split(" ");
      TeamMember teamMember = new TeamMember(element[1], element[2], Integer.parseInt(element[0]));
      team.add(teamMember);
    }
    input.close();
    return team;
  }
}
