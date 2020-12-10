package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IProjectManagementModel;

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


}
