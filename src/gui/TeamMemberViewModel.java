package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Project;
import model.TeamMember;

public class TeamMemberViewModel {
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    private StringProperty roleProperty;
    
    public TeamMemberViewModel(Project project, TeamMember teamMember) {
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
