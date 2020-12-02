package model;

import java.util.ArrayList;

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
    public void addProject(Project project)
    {
        projects.add(project);
    }
    public void removeProject(Project project)
    {
        projects.remove(project);
    }
    public Project getProject(int index)
    {
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
        return projectsSameStatus;
    }
    public boolean contains(Project project)
    {
        return projects.contains(project);
    }
    public boolean containsID(String ID)
    {
        //TODO: GENERATED ID
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
