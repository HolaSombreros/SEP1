package gui;

import javafx.beans.property.*;
import model.IProjectManagementModel;
import model.Project;
import model.TeamMember;

public class TeamViewModel {
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    private DoubleProperty productivityProperty;
    private IProjectManagementModel model;

    public TeamViewModel(TeamMember teamMember, IProjectManagementModel model) {
        this.model = model;
        idProperty = new SimpleIntegerProperty(teamMember.getId());
        nameProperty = new SimpleStringProperty(teamMember.getFullName());
        productivityProperty = new SimpleDoubleProperty(model.getProductivity(teamMember));

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
}
