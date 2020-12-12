package view;

import javafx.beans.property.*;
import mediator.IProjectManagementModel;
import model.TeamMember;

public class TeamViewModel {
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    private DoubleProperty productivityProperty;
    private IntegerProperty taskProperty;
    private IProjectManagementModel model;

    public TeamViewModel(TeamMember teamMember, IProjectManagementModel model) {
        this.model = model;
        idProperty = new SimpleIntegerProperty(teamMember.getId());
        nameProperty = new SimpleStringProperty(teamMember.getFullName());
        productivityProperty = new SimpleDoubleProperty(model.getProductivity(teamMember));
        taskProperty = new SimpleIntegerProperty(model.getWorkingTasks(teamMember));

    }

    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public DoubleProperty getProductivityProperty(){
        return productivityProperty;
    }

    public IntegerProperty getTaskProperty(){
        return taskProperty;
    }
}
