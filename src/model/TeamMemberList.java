package model;

import java.util.ArrayList;


public class TeamMemberList {
    public ArrayList<TeamMember> team;
    
    /**
     * No argument constructor
     */
    public TeamMemberList(){
        team = new ArrayList<>();
    }

    /**
     * Searches through the team and compares the teamMember's name with the name searched.
     * If found, it returns the specific teamMember, else a null object.
     * @param name the name of the team member
     * @return the team member
     * */
    public TeamMember getByName(String name) {
        for (TeamMember teamMember : team) {
            if(teamMember.getFullName().equals(name))
                return teamMember;
        }
        return null;
    }
    /**
     * Searches through the team and compares the teamMember's id with the id searched.
     * @param id the id
     * @return If found, it returns the specific teamMember, else a null object.
     * */
    public TeamMember getByID(int id){
        for (TeamMember teamMember : team) {
            if(teamMember.getId() == id)
                return teamMember;
        }
        return null;
    }


    public ArrayList<TeamMember> getTeamMembers(){
        return team;
    }

    /**
     * Searches through the team for a specific teamMember.
     * @param teamMember the team member
     * @return If found, it returns the specific teamMember, else a null object.
     * */
    public TeamMember getTeamMember(TeamMember teamMember){
        for (TeamMember member : team) {
            if(member.equals(teamMember))
                return member;
        }
        return null;
    }

    /**
     * gets a team member from the list by index
     * @param index the index
     * @return the teamMember from a specific index in the team
     * */
    public TeamMember getByIndex(int index){
        return team.get(index);
    }

    /**
     * checks if the given teamMember is in the current list.
     * If not, then it adds him to the list, else throws an exception
     * @param teamMember the team member
     * */
    public void add(TeamMember teamMember) {
        if(!team.contains(teamMember))
            team.add(teamMember);
        else
            throw new IllegalArgumentException("The team member is already in the list!");
    }

    /**
     * checks if the given teamMember is in the current list
     * @param teamMember the team member
     * @return if yes, returns true, else returns false
     * */
    public boolean contains(TeamMember teamMember){
       // for(TeamMember teamMember1 : team)
        //    if(teamMember.equals(teamMember1))
        if(team.contains(teamMember))
                return true;
        return false;

    }

    /**
     * returns the size of the current list (number of teamMembers in the list)
     * @return the size of the list
     * */
    public int size(){
        return team.size();
    }

    /**
     * checks if 2 lists contain the same members(identical)
     * @param obj the object
     * @return if yes, returns true, else returns false
     * */
    public boolean equals(Object obj){
        if(!(obj instanceof TeamMemberList))
            return false;
        TeamMemberList other = (TeamMemberList)obj;
        if(other.size() != team.size())
            return false;
        for(int i = 0; i < team.size(); i++)
            if(!team.get(i).equals(other.getByIndex(i)))
                return false;
        return true;
    }

    /**
     *searches for a given team member in the list
     * if found, removes him from the list, else throws an exception
     * @param teamMember the team member
     * note: when the method is used, beforehand the teamMember needs to be checked whether he has a special role or not
     * if not, then it proceeds to remove him, if yes throws an exception
     * */
    public void remove(TeamMember teamMember){
        team.remove(teamMember);
    }
    
    /**
     * Overridden toString method
     * @return the string representation of the team member list
     */
    public String toString(){
        String s = "";
        for(int i = 0; i < team.size(); i++)
            s += team.get(i).toString();
        return s;
    }


}
