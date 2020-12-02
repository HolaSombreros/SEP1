package model;

import java.util.ArrayList;

public class TaskList {
    private int idCounter;
    private ArrayList<Task> tasks;

    /**
     * No-argument constructor for the TaskList class.
     */
    public TaskList() {
        this.idCounter = 1;
        this.tasks = new ArrayList<>();
    }

    /**
     * Getter for 'tasks' instance variable.
     * @return Arraylist of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Standard ArrayList size() method for the 'tasks' instance variable.
     * @return The size of the arraylist as an integer.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Method to add a task to the list of tasks.
     * @param task The Task object that will be added to the list.
     */
    public void add(Task task) {
        if (contains(task)) {
            throw new IllegalArgumentException("The task is already in the list of tasks");
        }
        tasks.add(task);
        task.setId(idCounter++);
    }

    /**
     * Method to remove a task from the list of tasks.
     * @param task The Task object that will be removed from the list.
     */
    public void remove(Task task) {
        if (!contains(task)) {
            throw new IllegalArgumentException("The task is not in the list of tasks");
        }
        tasks.remove(task);
    }

    /**
     * Standard ArrayList get(int index) method for the 'tasks' instance variable.
     * @param index The index to get from.
     * @return The Task object at the specified index in the list of tasks.
     */
    public Task getTask(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Index is out of bounds");
        }
        return tasks.get(index);
    }

    /**
     * Method to get a Task object based on an identifier.
     * @param id The id the search will be based by.
     * @return The Task object with the specified identifier.
     */
    public Task getTaskById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id cannot be less than 1");
        }
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new IllegalArgumentException("There is no task with that id in the list of tasks");
    }

    /**
     * Method to get an arraylist of Task objects based on a status.
     * @param status The Status enum the search will be based by.
     * @return The arraylist of tasks with the specified status.
     */
    public ArrayList<Task> getTasksByStatus(Status status) {
        ArrayList<Task> taskArr = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == status) {
                taskArr.add(task);
            }
        }
        return taskArr;
    }

    /**
     * Standard ArrayList contains(Object obj) method for the 'tasks' instance variable.
     * @param task The task to search for.
     * @return A boolean value representing whether or not the specified task is in the list of tasks.
     */
    public boolean contains(Task task) {
        return tasks.contains(task);
    }

    /**
     * Standard overridden Object equals() method that checks everything.
     * @param obj The object to compare with.
     * @return A boolean value representing whether or not the two TaskList objects are identical.
     */
    @Override public boolean equals(Object obj) {
        if (!(obj instanceof TaskList)) {
            return false;
        }
        TaskList taskList = (TaskList)obj;
        if (taskList.idCounter != idCounter || taskList.size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!taskList.getTask(i).equals(getTask(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Standard overridden toString() method that displays all object data.
     * @return A string representation of the TaskList object.
     */
    @Override public String toString() {
        String str = String.format("TaskList #%d %nTasks:", idCounter);
        str += String.format("%nNumber of tasks in list: %d", size());
        for (Task task : tasks) {
            str += String.format("%n%s", task.toString());
        }
        return str;
    }
}
