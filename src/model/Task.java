package model;

public class Task {
    private int id;
    private String title;
    private Date startingDate;
    private Date deadline;
    private double estimatedTime;
    private Status status;
    private final TeamMemberList teamMemberList;
    private TeamMember responsibleTeamMember;
    private final TimeRegistration timeRegistration;
    private Requirement relatedRequirement;

    /**
     * Constructor for the Task class.
     * @param title The title of the task.
     * @param startingDate The task's starting date as a Date object.
     * @param deadline The task's deadline as a Date object.
     * @param estimatedTime The estimated time for the task to be finished.
     * @param relatedRequirement The Requirement object which the task is related to.
     */
    public Task(String title, Date startingDate, Date deadline, double estimatedTime, Requirement relatedRequirement) {
        this.relatedRequirement = relatedRequirement;
        Date.checkDates(startingDate, deadline);
        setStartingDate(startingDate);
        setDeadline(deadline);
        setEstimatedTime(estimatedTime);
        this.id = 0;
        this.title = title;
        this.status = Status.NOT_STARTED;
        this.teamMemberList = new TeamMemberList();
        this.responsibleTeamMember = null;
        this.timeRegistration = new TimeRegistration();
    }

    // Getters for instance variables:
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public Date getStartingDate() {
        return startingDate.copy();
    }

    public Date getDeadline() {
        return deadline.copy();
    }

    public Status getStatus() {
        return status;
    }

    public TeamMemberList getTeamMemberList() {
        return teamMemberList;
    }

    public TeamMember getResponsibleTeamMember() {
        return responsibleTeamMember;
    }

    public TimeRegistration getTimeRegistration() {
        return timeRegistration;
    }

    public Requirement getRelatedRequirement() {
        return relatedRequirement;
    }

    // Setters for instance variables:
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEstimatedTime(double estimatedTime) {
        if (estimatedTime <= 0) {
            throw new IllegalArgumentException("The estimated time cannot be less than or equal to 0");
        }
        this.estimatedTime = estimatedTime;
    }

    public void setStartingDate(Date startingDate) {
        if (startingDate.isBefore(relatedRequirement.getStartingDate())) {
            throw new IllegalArgumentException("The starting date cannot be before the requirement's starting date (" + relatedRequirement.getStartingDate().toString() + ")");
        }
        else if (!startingDate.isBefore(relatedRequirement.getDeadline())) {
            throw new IllegalArgumentException("The starting date cannot be after the requirement's deadline (" + relatedRequirement.getDeadline().toString() + ")");
        }
        this.startingDate = startingDate.copy();
    }

    public void setDeadline(Date deadline) {
        if (startingDate.isBefore(relatedRequirement.getStartingDate())) {
            throw new IllegalArgumentException("The starting date cannot be before the requirement's starting date (" + relatedRequirement.getStartingDate().toString() + ")");
        }
        else if (relatedRequirement.getDeadline().isBefore(deadline)) {
            throw new IllegalArgumentException("The deadline cannot be after the requirement's deadline (" + relatedRequirement.getDeadline().toString() + ")");
        }
        this.deadline = deadline.copy();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void assignResponsibleTeamMember(TeamMember responsibleTeamMember) {
        this.responsibleTeamMember = responsibleTeamMember;
    }

    public void unassignResponsibleTeamMember() {
        this.responsibleTeamMember = null;
    }

    public void setRelatedRequirement(Requirement relatedRequirement) {
        this.relatedRequirement = relatedRequirement;
    }

    /**
     * Method to assign a team member to the task's list of team members.
     * Also assigns a team member to the requirement's list of team members if necessary.
     * @param teamMember The TeamMember object which will be added to the list.
     */
    public void assignTeamMember(TeamMember teamMember) {
        teamMemberList.add(teamMember);
        if (!getRelatedRequirement().getTeamMemberList().getTeamMembers().contains(teamMember)) {
            getRelatedRequirement().assignTeamMember(teamMember);
        }
    }

    /**
     * Method to unassign a team member from the task's list of team members.
     * @param teamMember The TeamMember object which will be removed from the list.
     */
    public void unassignTeamMember(TeamMember teamMember) {
        if (teamMember.equals(getResponsibleTeamMember())) {
            throw new IllegalArgumentException("You cannot unassign a responsible team member from a task");
        }
        teamMemberList.remove(teamMember);
    }

    /**
     * Method to modify the task's TimeRegistration with a specified amount of hours worked.
     * This method also modifies the specified team member's amount of hours worked.
     * @param teamMember The team member who worked on the task.
     * @param hoursWorked The amount of hours the team member worked on the task.
     */
    public void addHoursWorked(TeamMember teamMember, double hoursWorked) {
        if (hoursWorked <= 0) {
            throw new IllegalArgumentException("The amount of hours worked cannot be less than or equal to 0");
        }
        timeRegistration.addHoursWorked(hoursWorked);
        teamMember.getTimeRegistration().addHoursWorked(hoursWorked);
        if (getStatus() == Status.NOT_STARTED) {
            setStatus(Status.STARTED);
        }
    }
    
    /**
     * Method to modify a task's details with the given values.
     * @param title The new task title.
     * @param estimatedTime The new estimated time in hours.
     * @param startingDate The task's new start date.
     * @param deadline The task's new deadline.
     * @param status The new status of the task.
     * @param responsibleTeamMember The new responsible team member for the task.
     * @param hoursWorked The amount of hours of work (if any) to register.
     * @param teamMember The team member (if any) to register time for.
     */
    public void edit(String title, double estimatedTime, Date startingDate, Date deadline, Status status, TeamMember responsibleTeamMember, double hoursWorked, TeamMember teamMember) {
        setTitle(title);
        setEstimatedTime(estimatedTime);
        setStartingDate(startingDate);
        setDeadline(deadline);
        setStatus(status);
        if (responsibleTeamMember == null) {
            unassignResponsibleTeamMember();
        }
        else {
            assignResponsibleTeamMember(responsibleTeamMember);
        }
        if (hoursWorked != 0 && teamMember != null) {
            addHoursWorked(teamMember, hoursWorked);
        }
    }

    /**
     * Standard overridden Object equals() method that checks everything.
     * @param obj The object to compare with.
     * @return A boolean value representing whether or not the two Task objects are identical.
     */
    @Override public boolean equals(Object obj) {
        if (!(obj instanceof Task)) {
            return false;
        }
        Task task = (Task)obj;
        if (task.getResponsibleTeamMember() != null) {
            if (getResponsibleTeamMember() != null && !task.getResponsibleTeamMember().equals(getResponsibleTeamMember())) {
                return false;
            }
        }
        return task.getId() == getId() &&
                task.getTitle().equals(getTitle()) &&
                task.getEstimatedTime() == getEstimatedTime() &&
                task.getStartingDate().equals(getStartingDate()) &&
                task.getDeadline().equals(getDeadline()) &&
                task.getStatus() == getStatus() &&
                task.getTeamMemberList().equals(getTeamMemberList()) &&
                task.getTimeRegistration().equals(getTimeRegistration()) &&
                task.getRelatedRequirement().getId() == getRelatedRequirement().getId();
    }

    /**
     * Standard overridden toString() method that displays all object data.
     * @return A string representation of the Task object.
     */
    @Override public String toString() {
        String str = String.format("Task #%d: ", getId());
        str += String.format("%nTitle: %s", getTitle());
        str += String.format("%nStarting date: %s", startingDate.toString());
        str += String.format("%nDeadline: %s", deadline.toString());
        str += String.format("%nEstimated time: %.1f hours", getEstimatedTime());
        str += String.format("%nTotal hours worked: %.1f", getTimeRegistration().getHoursWorked());
        str += String.format("%nStatus: %s", getStatus().getName());
        if (getResponsibleTeamMember() != null) {
            str += String.format("%nResponsible Team Member: %s", getResponsibleTeamMember().getFullName());
        }
        str += String.format("%nTeam Members:%n%s", getTeamMemberList().toString());
        return str;
    }
}
