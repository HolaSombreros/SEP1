package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.IProjectManagementModel;
import model.Project;


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
                    list.get(index).getDeadlineProperty().get().equals(project.getDeadline().toString()) &&
                     list.get(index).getMethodologyProperty().get().equals(project.getMethodology().getMethodology()))
            {
                list.remove(index);

                break;
            }
        }
    }
    public void update()
    {
        list.clear();

        {
            for(Project project: model.getProjectList().getProjects())
            {
                list.add(new ProjectViewModel(project));
            }
        }
    }
    public void update(String id)
    {
        list.clear();
        if(id == null)
        {
            for(Project project: model.getProjectList().getProjects())
             {
                list.add(new ProjectViewModel(project));
             }
        }
        else
        {
            for(Project project: model.getProjectList().getProjectByIDBetterVersion(id))
            {
                list.add(new ProjectViewModel(project));
            }
        }
    }

    public void update(int id){
        list.clear();
        for (Project project: model.getProjectList().getProjects())
            if(project.getTeamMemberList().getByID(id) != null)
                list.add(new ProjectViewModel(project));

    }

    //TODO update by id and name for TeamMember and get project role for teamMember (Adriana)

}
