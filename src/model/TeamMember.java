package model;

public class TeamMember extends Person{

    private int id;
    private TimeRegistration hoursWorked;

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
        public void assignToProject(Project project){

        }

        public void assignToRequirement(Requirement requirement){

        }

        public void assignToTask(Task task){

        }
    //UNNASSING

        public void unassignFromProject(Project project){

        }

        public void unnassignFromRequirement(Requirement requirement){

        }

        public void unnassignFromTask(Task task){

        }

    //EQUALS

        public boolean equals(Object obj){
            if(!(obj instanceof TeamMember))
                return false;
            TeamMember other = (TeamMember)obj;
            return getId()==other.getId() &&
                    getLastName().equals(other.getLastName()) &&
                    getFirstName().equals(other.getFirstName());
        }
    //TOSTRING

        public String toString(){

        }
}
