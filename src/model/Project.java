package model;

public class Project
{

    private String name;
    private String ID;
    private Status status;
    private Methodology methodology;
    private Date startingDate;
    private Date deadline;
    private TeamMember scrumMaster;
    private TeamMember productOwner;
    private TeamMemberList projectTeamMemberList;
    private RequirementList projectRequirementList;

    //CONSTRUCTOR

    public Project(String name, String ID, Date startingDate, Date deadline, Methodology methodology)
    {
        this.name = name;
        this.ID = ID;
        this.startingDate = startingDate;
        this.deadline = deadline;
        this.methodology = methodology;
        this.status = Status.NOT_STARTED;
        this.teamMemberList = new TeamMemberList();
    }
    //GETTERS

    public String getName()
    {
        return name;
    }
    public String getID()
    {
        return ID;
    }
    public Status getStatus()
    {
        return status;
    }
    public Methodology getMethodology()
    {
        return methodology;
    }
    public Date getStartingDate()
    {
        return startingDate.copy();
    }
    public Date getDeadline()
    {
        return deadline.copy();
    }
    public TeamMember getScrumMaster()
    {
        return scrumMaster;
    }
    public TeamMember getProductOwner()
    {
        return productOwner;
    }
    public TeamMemberList getTeamMemberList()
    {
        return teamMemberList;
    }

    //SETTERS

    public void setName(String name)
    {
        this.name = name;
    }
    public void setID(String ID)
    {
        this.ID = ID;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }
    public void setMethodology(Methodology methodology)
    {
        this.methodology = methodology;
    }
    public void setStartingDate(Date startingDate)
    {
        this.startingDate = startingDate.copy();
    }
    public void setDeadline(Date deadline)
    {
        this.deadline = deadline.copy();
    }

    public void setTeamMemberList(TeamMemberList teamMemberList)
    {
        this.teamMemberList = teamMemberList;
    }
    public void assignTeamMember(TeamMember teamMember)
    {
        this.projectTeamMemberList.add(teamMember);
    }
    public void unassignTeamMember(TeamMember teamMember)
    {
        //TODO: UNASSIGN TEAM MEMBER WITH SPECIAL ROLE
        this.projectTeamMemberList.remove(teamMember);
    }
    public TeamMemberList getTeamMembers()
    {
        return projectTeamMemberList;
    }
    public void assignScrumMaster(TeamMember teamMember)
    {
        this.scrumMaster = teamMember;
    }
    public void assignProductOwner(TeamMember teamMember)
    {
        this.productOwner = productOwner;
    }
    public void addRequirement(RequirementList requirement)
    {
        this.projectRequirementList.add(requirement);
    }
    public void removeRequirement(RequirementList requirement)
    {
        this.projectRequirementList.remove(requirement);
    }
    public RequirementList getProjectRequirementList()
    {
        return projectRequirementList;
    }
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Project))
        {
            return false;
        }
        Project other = (Project) obj;
/*
        if(projectTeamMemberList.size() !=other.projectTeamMemberList.size() )
        {
            return false;
        }

        for(TeamMember teamMember : projectTeamMemberList.getTeamMembers())
        for(int index = 0; index < projectTeamMemberList.size(); index++)
        {
            if(!(projectTeamMemberList.getProject(index).equals(other.projectTeamMemberList.getProject(index)))
            {
                return false;
            }
        }
        */


        return this.ID.equals(other.ID) &&
                this.name.equals(other.name) &&
                this.startingDate.equals(other.startingDate) &&
                this.deadline.equals(other.deadline) &&
                this.status.equals(other.status) &&
                this.methodology.equals(other.methodology) &&
                this.scrumMaster.equals(other.scrumMaster) &&
                this.productOwner.equals(other.productOwner) &&
                this.projectTeamMemberList.equals(other.projectTeamMemberList) &&
                this.projectRequirementList.equals(other.projectRequirementList);

    }
    public String toString()
    {
        return "ID: " + getID() + "\n" +
                "Name: " + getName() + "\n" +
                "Starting date: " + getStartingDate() + "\n" +
                "Deadline: " + getDeadline() + "\n" +
                "Status: " + getStatus() + "\n" +
                "Methodology: " + getMethodology() + "\n" +
                "Scrum Master: " + getScrumMaster() + "\n" +
                "Product Owner: " + getProductOwner() + "\n"+
                "Team Member List: " + getTeamMemberList() + "\n" +
                "Requirement List: " + getProjectRequirementList();
    }



}

