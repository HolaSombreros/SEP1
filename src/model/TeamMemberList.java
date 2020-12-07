package model;

import java.util.ArrayList;

public class TeamMemberList {
    public ArrayList<TeamMember> team;

    public TeamMemberList(){
        team = new ArrayList<>();
    }


    /**
     * Searches through the team and compares the teamMember's name with the name searched.
     * If found, it returns the specific teamMember, else a null object.
     * @param name
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
     * If found, it returns the specific teamMember, else a null object.
     * @param id
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
     * If found, it returns the specific teamMember, else a null object.
     * @param teamMember
     * */
    public TeamMember getTeamMember(TeamMember teamMember){
        for (TeamMember member : team) {
            if(member.equals(member))
                return member;
        }
        return null;
    }

    /**
     * returns the teamMember from a specific index in the team
     * */
    public TeamMember getByIndex(int index){
        return team.get(index);
    }

    /**
     * checks if the given teamMember is in the current list.
     * If not, then it adds him to the list, else throws an exception
     * @param teamMember
     * */
    public void add(TeamMember teamMember) {
        if(!contains(teamMember))
            team.add(teamMember);
        else
            throw new IllegalArgumentException("The team member is already in the list!");
    }

    /**
     * checks if the given teamMember is in the current list
     * if yes, returns true, else returns false
     * */
    public boolean contains(TeamMember teamMember){
        for(TeamMember teamMember1 : team)
            if(teamMember.equals(teamMember1))
                return true;
        return false;
    }

    /**
     * returns the size of the current list (number of teamMembers in the list)
     * */
    public int size(){
        return team.size();
    }

    /**
     * checks if 2 lists contain the same members(identical)
     * if yes, returns true, else returns false
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
     * @param teamMember
     * note: when the method is used, beforehand the teamMember needs to be checked whether he has a special role or not
     * if not, then it proceeds to remove him, if yes throws an exception
     * */
    public void remove(TeamMember teamMember){
        if(!contains(teamMember))
            throw new IllegalArgumentException("Team Member not found");
        else
            team.remove(teamMember);
    }

    public String toString(){
        String s = team.get(0).toString();
        for(int i = 1; i < team.size(); i++)
            s += team.get(i).toString();
        return s;
    }


}
