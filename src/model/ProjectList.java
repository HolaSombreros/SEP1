package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProjectList
{
    private ArrayList<Project> projects;
    
    /**
     * constructor
     */
    public ProjectList()
    {
        this.projects = new ArrayList<>();
    }
    
    /**
     * getter for instance variable
     * @return the projects
     */
    public ArrayList<Project> getProjects()
    {
        return projects;
    }
    
    /**
     * method to get the size
     * @return the size
     */
    public int size()
    {
        return projects.size();
    }

    /**
     * method to add a project
     * @throws IllegalStateException the project is already in the project list
     * @param project the project to add
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
    
    /**
     * method to remove a project
     * @param project the project
     */
    public void removeProject(Project project)
    {
        projects.remove(project);
    }

    /**
     * method to get a project by index
     * @throws IndexOutOfBoundsException if the index does not exist
     * @param index the index
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
    
    /**
     * method to get number of projects by methodology
     * @param methodology the methodology
     * @return the number of projects
     */
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
    
    /**
     * method to get projects by on methodology
     * @param methodology the methodology
     * @return the arraylist of projects
     */
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
    
    /**
     * gets the number of projects by a status
     * @param status the status
     * @return the number of projects
     */
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
    
    /**
     * gets the projects by a status
     * @param status the status
     * @return the arraylist of projects
     */
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
    
    /**
     * method to get a project by id
     * @param ID the id
     * @return the project
     */
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
        return projects.stream().filter(project -> project.getID().toLowerCase().contains(ID.toLowerCase()) || project.getName().toLowerCase().contains(ID.toLowerCase())).collect(Collectors
                .toCollection(ArrayList::new));
    }
    
    /**
     * standard contains method
     * @param project the project
     * @return whether or not the project is in the list
     */
    public boolean contains(Project project)
    {
        return projects.contains(project);
    }
    
    /**
     * contains method but by id
     * @param ID the id
     * @return whether or not the project is in the list
     */
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
    
    /**
     * standard equals method
     * @param obj the object
     * @return if the two objects are equal
     */
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
