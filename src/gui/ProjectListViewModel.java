package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IProjectManagementModel;
import model.Project;

public class ProjectListViewModel {

    private ObservableList<ProjectViewModel> list;
    private IProjectManagementModel model;

    public ProjectListViewModel(IProjectManagementModel model)
    {
        this.model = model;
        this.list = FXCollections.observableArrayList();
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
                    list.get(index).getNameProperty().equals(project.getName()) &&
                    list.get(index).getStatusProperty().equals(project.getStatus().toString()) &&
                    list.get(index).getDeadlineProperty().equals(project.getDeadline()))
            {
                list.remove(index);
                break;
            }
        }
    }
    public void update()
    {
        list.clear();
        list.add(new ProjectViewModel(model.getProjectList().getProject(0)));

    }

}
