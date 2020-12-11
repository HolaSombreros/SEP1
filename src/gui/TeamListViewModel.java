package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Mediator.IProjectManagementModel;
import model.TeamMember;

public class TeamListViewModel {

    private ObservableList<TeamViewModel> list;
    private IProjectManagementModel model;
    private ViewState viewState;

    public TeamListViewModel(IProjectManagementModel model, ViewState viewState) {
        this.model = model;
        this.viewState = viewState;
        this.list = FXCollections.observableArrayList();
        update();
    }

    public void update(){
        list.clear();
        for(int i = 0; i < model.getTeam().size(); i++)
            list.add(new TeamViewModel(model.getTeam().getByIndex(i),model));
    }

    public ObservableList<TeamViewModel> getList() {
        return list;
    }


    public void update(String name){
        list.clear();
        if(name == null)
            for(TeamMember teamMember : model.getTeam().getTeamMembers())
                list.add(new TeamViewModel(teamMember,model));
        else
            //for(TeamMember teamMember : model.getTeam().getByNameUpgraded(name))
               // list.add(new TeamViewModel(teamMember,model));
            for(TeamMember teamMember : model.getTeam().getTeamMembers())
                if(teamMember.getFullName().contains(name))
                    list.add(new TeamViewModel(teamMember,model));
    }

    public void update(int id){
        list.clear();
        if(id == 0)
            for(TeamMember teamMember : model.getTeam().getTeamMembers())
                list.add(new TeamViewModel(teamMember,model));
        else
            for(TeamMember teamMember : model.getTeam().getTeamMembers())
                if(teamMember.getId() == id)
                    list.add(new TeamViewModel(teamMember,model));

    }

}
