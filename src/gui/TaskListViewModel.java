package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.IProjectManagementModel;
import model.Project;
import model.Requirement;
import model.Task;

public class TaskListViewModel {
    private ObservableList<TaskViewModel> list;
    private IProjectManagementModel model;
    private ViewState viewState;
    
    public TaskListViewModel(IProjectManagementModel model, ViewState viewState) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
        this.viewState = viewState;
    }
    
    public ObservableList<TaskViewModel> getList() {
        return list;
    }
    
    public void update(int id) {
        list.clear();
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        Requirement requirement = model.getRequirementList(project).getRequirementById(viewState.getSelectedRequirement());
        if (id == 0) {
            for (Task task : model.getTaskList(project, requirement).getTasks()) {
                list.add(new TaskViewModel(task));
            }
        }
        else {
            list.add(new TaskViewModel(model.getTaskList(project, requirement).getTaskById(id)));
        }
    }
    
    public void add(Task task) {
        list.add(new TaskViewModel(task));
    }
    
    public void remove(Task task) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdProperty().get() == task.getId() &&
                list.get(i).getTitleProperty().get().equals(task.getTitle()) &&
                list.get(i).getDeadlineProperty().get().equals(task.getDeadline().toString()) &&
                list.get(i).getStatusProperty().get().equals(task.getStatus().getName())) {
                    list.remove(i);
                    break;
            }
        }
    }
}
