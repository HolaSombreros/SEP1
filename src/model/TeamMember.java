package model;


public class TeamMember extends Person{

    private int id;
    private TimeRegistration hoursWorked;


    /**
     * initializes the variables.
     * @param id is given a random id
     * hoursWorked - is initialized as a new TimeRegistration object
     * */
    public TeamMember(String firstName, String lastName, int id){
        super(firstName, lastName);
        this.id = id;
        hoursWorked = new TimeRegistration();
    }

    //GETTERS
    public int getId(){
        return id;
    }

    public TimeRegistration getTimeRegistration(){
        return hoursWorked;
    }

    //ASSIGN
    /**
     * checks if the team member is already assigned to the given project
     * if not, it assigns him, else throws an exception
     * */
    public void assignToProject(Project project){
        if(!project.getTeamMemberList().contains(this))
            project.getTeamMemberList().add(this);
        else
            throw new IllegalArgumentException("Team Member already assigned to project");
    }
    /**
     * checks if the team member is already assigned to the given requirement
     * if not, it assigns him, else throws an exception
     * */
    public void assignToRequirement(Requirement requirement){
        if(!requirement.getTeamMemberList().contains(this))
            requirement.getTeamMemberList().add(this);
        else
            throw new IllegalArgumentException("Team Member already assigned to requirement");
    }
    /**
     * checks if the team member is already assigned to the given task
     * if not, it assigns him, else throws an exception
     * */
    public void assignToTask(Task task){
        if(!task.getTeamMemberList().contains(this))
            task.getTeamMemberList().add(this);
        else
            throw new IllegalArgumentException("Team Member already assigned to task");
    }
    //UNNASSING
    /**
     * checks if the teamMember has a special role
     * if yes, throws an exception, else unassigns him from the project
     * */
    public void unassignFromProject(Project project){
        if(project.getScrumMaster().equals(this) || project.getProductOwner().equals(this) )
            throw new IllegalArgumentException("Go to unassing in project and unassign the special role");
        else
            project.unassignTeamMember(this);
    }
    /**checks if the teamMember has a special role
     * if yes, throws an exception, else unassigns him from the requirement
     * */
    public void unnassignFromRequirement(Requirement requirement){
        if(requirement.getResponsibleTeamMember().equals(this))
            throw new IllegalArgumentException("Go to unassing in requirement and unassign the special role");
        else
            requirement.unassignTeamMember(this);
    }
    /**checks if the teamMember has a special role
     * if yes, throws an exception, else unassigns him from the task
     * */
    public void unnassignFromTask(Task task){
        if(task.getResponsibleTeamMember().equals(this))
            throw new IllegalArgumentException("Go to unassing in task and unnassign the special role");
        else
            task.unassignTeamMember(this);
    }

    //EQUALS

    public boolean equals(Object obj){
        if(!(obj instanceof TeamMember))
            return false;
        TeamMember other = (TeamMember)obj;
        return  getId()==other.getId() &&
                getLastName().equals(other.getLastName()) &&
                getFirstName().equals(other.getFirstName());
        }
    //TOSTRING

    public String toString(){
        return getId() + " : " + getFullName() + " has worked " + getTimeRegistration() + "\n";
    }
}
