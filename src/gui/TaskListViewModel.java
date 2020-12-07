package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IProjectManagementModel;
import model.Task;

public class TaskListViewModel {
    private ObservableList<TaskViewModel> list;
    private IProjectManagementModel model;
    
    public TaskListViewModel(IProjectManagementModel model, ViewState state) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
    }
    
    public ObservableList<TaskViewModel> getList() {
        return list;
    }
    
    public void update() {
        list.clear();
        // TODO - add Project and Requirement as parameter:
        for (Task task : model.getTaskList(model.getProjectList().getProject(0), model.getRequirementList(model.getProjectList().getProject(0)).getRequirement(0)).getTasks()) {
        //for (int i = 0; i < model.getTaskList(model.getProjectList().getProject(0), model.getProjectList().getProject(0).getProjectRequirementList().getRequirement(0)).size(); i++) {
            list.add(new TaskViewModel(task));
        }
    
        list.add(new TaskViewModel(model.getProjectList().getProject(0).getProjectRequirementList().getRequirement(0).getTaskList().getTask(0)));
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
