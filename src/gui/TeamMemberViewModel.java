package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.TeamMember;

public class TeamMemberViewModel {
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    
    public TeamMemberViewModel(TeamMember teamMember) {
        idProperty = new SimpleIntegerProperty(teamMember.getId());
        nameProperty = new SimpleStringProperty(teamMember.getFullName());
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public StringProperty getNameProperty() {
        return nameProperty;
    }
}
