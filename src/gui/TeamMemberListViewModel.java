package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IProjectManagementModel;
import model.TeamMember;

public class TeamMemberListViewModel {
    private ObservableList<TeamMemberViewModel> list;
    private IProjectManagementModel model;
    
    public TeamMemberListViewModel(IProjectManagementModel model) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
    }
    
    public ObservableList<TeamMemberViewModel> getList() {
        return list;
    }
    
    public void update() {
        list.clear();
        // TODO - add Project and Requirement as parameter - also what the hell to do here, really. if variables are null, ignore them? as in if we're displaying list for a Project:
        // for (int i = 0; i < model.getTeamMemberList().size(); i++) {
        // list.add(new TeamMemberViewModel(model.getTeamMemberList().getTeamMember(i)));
        // }
    }
    
    public void add(TeamMember teamMember) {
        list.add(new TeamMemberViewModel(teamMember));
    }
    
    public void remove(TeamMember teamMember) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdProperty().get() == teamMember.getId() &&
                list.get(i).getNameProperty().get().equals(teamMember.getFullName())) {
                    list.remove(i);
                    break;
            }
        }
    }
}
