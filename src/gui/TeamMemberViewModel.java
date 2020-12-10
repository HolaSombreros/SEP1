package gui;

import javafx.beans.property.*;
import model.IProjectManagementModel;
import model.Project;
import model.TeamMember;

public class TeamMemberViewModel {
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    private StringProperty roleProperty;
    private DoubleProperty productivityProperty;
    private IProjectManagementModel model;
    
    public TeamMemberViewModel(Project project, TeamMember teamMember,IProjectManagementModel model) {
        this.model = model;
        idProperty = new SimpleIntegerProperty(teamMember.getId());
        nameProperty = new SimpleStringProperty(teamMember.getFullName());
        roleProperty = new SimpleStringProperty(teamMember.getRole(project));
    }


    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public StringProperty getNameProperty() {
        return nameProperty;
    }
    public StringProperty getRoleProperty() {
        return roleProperty;
    }
}
