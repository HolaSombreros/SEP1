package model;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectList
{
    private ArrayList<Project> projects;

    public ProjectList()
    {
        this.projects = new ArrayList<>();
    }
    public ArrayList<Project> getProjects()
    {
        return projects;
    }
    public int size()
    {
        return projects.size();
    }

    /**
     * @throws IllegalStateException: the project is already in the project list
     * @param project
     */
    public void addProject(Project project)
    {
        if(!containsID(project.getID()))
        {
            projects.add(project);
        }
        else
            throw new IllegalStateException("The project already exists");
    }
    public void removeProject(Project project)
    {
        projects.remove(project);
    }

    /**
     * @throws IndexOutOfBoundsException
     * @param index
     * @return the desired project
     */
    public Project getProject(int index)
    {
        if(index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException("The index does not exist");
        }
        return projects.get(index);
    }
    public int getNumberOfProjectsByMethodology(Methodology methodology)
    {
        int count = 0;
        for(Project project: projects)
        {
            if(project.getMethodology().equals(methodology))
            {
                count++;
            }
        }
        if(count == 0) throw new IllegalArgumentException("No projects found with this methodology");
        return count;
    }
    public ArrayList<Project> getProjectsByMethodology(Methodology methodology)
    {
        ArrayList<Project> projectsSameMethodology = new ArrayList<>();

        for(Project project : projects)
        {
            if(project.getMethodology().equals(methodology))
            {
                projectsSameMethodology.add(project);
            }
        }
        if(projectsSameMethodology.size() == 0)
            throw new IllegalArgumentException("No projects found with this methodology");
        return projectsSameMethodology;
    }
    public int getNumberOfProjectsByStatus(Status status)
    {
        int count = 0;
        for(Project project: projects)
        {
            if(project.getStatus().equals(status))
            {
                count++;
            }
        }
        if(count ==0)
            throw new IllegalArgumentException("No projects found with this status");
        return count;
    }
    public ArrayList<Project> getProjectsByStatus(Status status)
    {
        ArrayList<Project> projectsSameStatus = new ArrayList<>();

        for(Project project : projects)
        {
            if(project.getStatus().equals(status))
            {
                projectsSameStatus.add(project);
            }
        }
        if(projectsSameStatus.size() == 0)
            throw new IllegalArgumentException("No projects found with this status");
        return projectsSameStatus;
    }
    public Project getProjectByID(String ID){
        for(Project project : projects)
        {
            if(project.getID().contains(ID))
            {
                return project;
            }
        }
        throw new IllegalArgumentException("There is no project with this ID");
    }

    /**A better method which uses the stream filters and creates two lists of projects
     * It is used for the search bar in the GUI: search by ID and at the same time for Name
     * @param ID is a String which is used for both the actual ID and the Name of the project
     * @return project's list based on the ID
     */
    public ArrayList<Project> getProjectByIDBetterVersion(String ID)
    {
        /*ProjectList list = new ProjectList();
        for(Project project : projects)
        {
            if(project.getID().contains(ID))
            {
                list.addProject(project);
            }
        }
        return list.getProjects().size() == 0?null:list;*/
        ArrayList<Project> project1 = projects.stream().filter(project -> project.getID().toLowerCase().contains(ID.toLowerCase())).collect(Collectors
                .toCollection(ArrayList::new));
        ArrayList<Project> project2 = projects.stream().filter(project -> project.getName().toLowerCase().contains(ID.toLowerCase())).collect(Collectors
                .toCollection(ArrayList::new));
        return Stream.of(project1, project2)
                .flatMap(x -> x.stream()).collect(Collectors
                .toCollection(ArrayList::new));
    }

    public boolean contains(Project project)
    {
        return projects.contains(project);
    }
    public boolean containsID(String ID)
    {
        for(int index = 0; index < projects.size(); index++)
        {
            if(projects.get(index).getID().equals(ID))
            {
                return true;
            }
        }
        return false;
    }
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ProjectList))
        {
            return false;
        }
        ProjectList other = (ProjectList) obj;
        if(projects.size() != other.projects.size())
        {
            return false;
        }
        for(int index = 0; index < projects.size(); index++)
        {
            if(!projects.get(index).equals(other.getProject(index)))
            {
                return false;
            }
        }
        return true;
    }
}
