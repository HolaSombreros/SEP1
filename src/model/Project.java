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
        Date.checkDates(startingDate, deadline);
        this.startingDate = startingDate;
        this.deadline = deadline;
        this.methodology = methodology;
        this.status = Status.NOT_STARTED;
        this.scrumMaster = null;
        this.productOwner = null;
        this.projectTeamMemberList = new TeamMemberList();
        this.projectRequirementList = new RequirementList();
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
        boolean notEnded = true;
        for(Requirement requirement : getProjectRequirementList().getRequirements())
        {
            if(!requirement.getStatus().equals(RequirementStatus.APPROVED) )
            {
                notEnded = false;
                break;
            }

        }
        if(notEnded = true)
        {
            this.status = Status.ENDED;
        }
        return status;
    }
    public String getStatusAsString()
    {
        switch(status)
        {
            case NOT_STARTED:
                return "Not started";
            case STARTED:
                return "Started";
            case ENDED:
                return "Ended";
            default: throw new IllegalArgumentException("Not valid status");

        }
    }
    public Methodology getMethodology()
    {
        return methodology;
    }
    public String getMethodologyAsString()
    {
        switch(methodology)
        {
            case WATERFALL: return "Waterfall";
            case SCRUM: return "Scrum";
            default: throw new IllegalArgumentException("Not valid methodology");
        }
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
        return projectTeamMemberList;
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
        this.projectTeamMemberList = teamMemberList;
    }
    public void assignTeamMember(TeamMember teamMember)
    {
        if(!projectTeamMemberList.contains(teamMember))
        this.projectTeamMemberList.add(teamMember);
        else
            throw new IllegalArgumentException("Team member is already in the project list");

    }
    public void unassignTeamMember(TeamMember teamMember)
    {
        if(teamMember.equals(getProductOwner()))
        {
            throw new IllegalArgumentException("You cannot unassign a Product Owner as an ordinary team member" + "\n Go to unassign Product Owner");
        }
        else if(teamMember.equals(getScrumMaster()))
        {
            throw new IllegalArgumentException("You cannot unassign a Scrum Master as an ordinary team member" + "\n Go to unassign Scrum Master");
        }
        else this.projectTeamMemberList.remove(teamMember);
    }
    public TeamMemberList getTeamMembers()
    {
        return projectTeamMemberList;
    }
    public void assignScrumMaster(TeamMember teamMember)
    {
        unassignScrumMaster();
        this.scrumMaster = teamMember;
    }
    public void assignProductOwner(TeamMember teamMember)
    {
        unassignProductOwner();
        this.productOwner = productOwner;
    }
    public void unassignScrumMaster()
    {
        this.scrumMaster = null;
    }
    public void unassignProductOwner()
    {
        this.productOwner = null;
    }
    public void addRequirement(Requirement requirement)
    {
        this.projectRequirementList.add(requirement);
    }
    public void removeRequirement(Requirement requirement)
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

        return  this.ID.equals(other.ID) &&
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
                "Status: " + getStatusAsString() + "\n" +
                "Methodology: " + getMethodologyAsString() + "\n" +
                "Scrum Master: " + getScrumMaster() + "\n" +
                "Product Owner: " + getProductOwner() + "\n"+
                "Team Member List: " + getTeamMemberList() + "\n" +
                "Requirement List: " + getProjectRequirementList();
    }



}

