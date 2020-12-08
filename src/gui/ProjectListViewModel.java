package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IProjectManagementModel;
import model.Project;
import model.TeamMember;

import javax.swing.text.View;

public class ProjectListViewModel {

    private ObservableList<ProjectViewModel> list;
    private IProjectManagementModel model;
    private ViewState viewState;

    public ProjectListViewModel(IProjectManagementModel model, ViewState viewState)
    {
        this.model = model;
        this.list = FXCollections.observableArrayList();
        this.viewState = viewState;
        update();
        //TODO: UPDATE
    }
    public ObservableList<ProjectViewModel> getList()
    {
        return list;
    }
    public void add(Project project)
    {
        list.add(new ProjectViewModel(project));
    }
    public void remove(Project project)
    {
        for(int index = 0; index < list.size(); index++)
        {
            if(list.get(index).getIDProperty().get().equals(project.getID()) &&
                    list.get(index).getNameProperty().get().equals(project.getName()) &&
                    list.get(index).getStatusProperty().get().equals(project.getStatus().getName()) &&
                    list.get(index).getDeadlineProperty().get().equals(project.getDeadline().toString()))
            {
                list.remove(index);

                break;
            }
        }
    }
    public void update()
    {
        list.clear();
        for(Project project: model.getProjectList().getProjects())
        {
            list.add(new ProjectViewModel(project));
        }


    }

    //TODO update by id and name for TeamMember and get project role for teamMember (Adriana)

}
