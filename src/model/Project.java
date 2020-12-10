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

    /** Checks if the all requirements are APPROVED
     * If yes: the project status will be set to ENDED
     *
     * @return
     */
    public Status getStatus()
    {
        if(status.equals(Status.STARTED))
        {
            boolean notEnded = true;
            for (Requirement requirement : getProjectRequirementList().getRequirements())
            {
                if (!requirement.getStatus().equals(RequirementStatus.APPROVED))
                {
                    notEnded = false;
                    break;
                }
            }
            if (notEnded)
            {
                this.status = Status.ENDED;
            }
        }
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

    //ASSIGN AND UNASSIGN

    /**Checks if the team member is in the project, if no: adds him
     * if yes:
     * @throws IllegalArgumentException if the team member was already assigned to the project
     *
     * @param teamMember
     */
    public void assignTeamMember(TeamMember teamMember)
    {
        if(!projectTeamMemberList.contains(teamMember))
        this.projectTeamMemberList.add(teamMember);
    }

    /** Unassigns an ordinary team member
     * @throws IllegalArgumentException when trying to unassign a ScrumMaster or a ProductOwner
     * @param teamMember
     */
    public void unassignTeamMember(TeamMember teamMember)
    {
        if(teamMember.equals(getProductOwner()))
        {
            //TODO: check adriana's unassign and labels
            throw new IllegalArgumentException("You cannot unassign a Product Owner as an ordinary team member" + "\n Go to unassign Product Owner");
        }
        else if(teamMember.equals(getScrumMaster()))
        {
            throw new IllegalArgumentException("You cannot unassign a Scrum Master as an ordinary team member" + "\n Go to unassign Scrum Master");
        }
        else{
            this.projectTeamMemberList.remove(teamMember);
            for(Requirement requirement : projectRequirementList.getRequirements())
            {
                requirement.unassignTeamMember(teamMember);
            }
        }
    }

    /**
     * If you are trying to assign a team member to a special role
     * and that role is already occupied, the previous person is overwritten
     * and the role is taken by the new team member
     * @param teamMember
     */

    public void assignScrumMaster(TeamMember teamMember)
    {
        unassignScrumMaster();
        this.scrumMaster = teamMember;
    }
    /**
     * If you are trying to assign a team member to a special role
     * and that role is already occupied, the previous person is overwritten
     * and the role is taken by the new team member
     * @param teamMember
     */
    public void assignProductOwner(TeamMember teamMember)
    {
        unassignProductOwner();
        this.productOwner = teamMember;
    }
    public void unassignScrumMaster()
    {
        this.scrumMaster = null;
    }
    public void unassignProductOwner()
    {
        this.productOwner = null;
    }

    //EDIT PROJECT

    /**
     * Method for editing the project (a setter in disguise)
     * @param name
     * @param ID
     * @param startingDate
     * @param deadline
     * @param status
     * @param methodology
     * @param scrumMaster
     * @param productOwner
     */
    public void edit(String name, String ID, Date startingDate, Date deadline, Status status, Methodology methodology, TeamMember scrumMaster, TeamMember productOwner)
    {
        setName(name);
        setID(ID);
        setStartingDate(startingDate);
        setDeadline(deadline);
        setStatus(status);
        setMethodology(methodology);
        if(this.scrumMaster == null)
            unassignScrumMaster();
        assignScrumMaster(scrumMaster);
        if(this.productOwner == null) unassignProductOwner();
            assignProductOwner(productOwner);
    }

    //RELATED REQUIREMENTS AND LIST

    /**
     * adds a requirement to the project's requirements list
     * @param requirement
     */
    public void addRequirement(Requirement requirement)
    {
        this.projectRequirementList.add(requirement);
    }
    public void removeRequirement(Requirement requirement)
    {
        this.projectRequirementList.remove(requirement);
    }

    /**
     *
     * @return a list of all the requirements belonging to a project
     */
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
        if(scrumMaster == null)
        {
            if(other.scrumMaster != null)
            {
                return false;
            }
        }
        else
        {
            if(!scrumMaster.equals(other.scrumMaster))
            {
                return false;
            }
        }
        if(productOwner == null)
        {
            if(other.productOwner != null)
            {
                return false;
            }
        }
        else
        {
            if(!productOwner.equals(other.productOwner))
            {
                return false;
            }
        }
        return  this.ID.equals(other.ID) &&
                this.name.equals(other.name) &&
                this.startingDate.equals(other.startingDate) &&
                this.deadline.equals(other.deadline) &&
                this.status.equals(other.status) &&
                this.methodology.equals(other.methodology) &&
                this.projectTeamMemberList.equals(other.projectTeamMemberList) &&
                this.projectRequirementList.equals(other.projectRequirementList);
    }
    public String toString()
    {
        return "ID: " + getID() + "\n" +
                "Name: " + getName() + "\n" +
                "Starting date: " + getStartingDate() + "\n" +
                "Deadline: " + getDeadline() + "\n" +
                "Status: " + getStatus().getName() + "\n" +
                "Methodology: " + getMethodology().getMethodology() + "\n" +
                "Scrum Master: " + getScrumMaster() + "\n" +
                "Product Owner: " + getProductOwner() + "\n"+
                "Team Member List: " + getTeamMemberList() + "\n" +
                "Requirement List: " + getProjectRequirementList();
    }
}

