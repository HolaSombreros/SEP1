package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.ProjectManagementModelManager;
import model.TeamMember;


public class DetailsTeamMemberController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState viewState;
    private IProjectManagementModel model;
    private TeamMemberListViewModel viewModel;

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private Label productivityLabel;
    @FXML private Label frequentTeamMemberLabel;
    @FXML private Label errorLabel;
    @FXML private TableView<TeamMemberViewModel> teamMemberViewTable;
    @FXML private TableColumn<ProjectViewModel,String> projectNameColumn;
    @FXML private TableColumn<ProjectViewModel,String> roleColumn;

    public DetailsTeamMemberController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState viewState){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TeamMemberListViewModel(model,viewState);

        //projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        //roleColumn.setCellValueFactory(cellData -> cellData.getValue().);

    }

    public void reset(){

        errorLabel.setText("");
        productivityLabel.setText("");
        frequentTeamMemberLabel.setText("");

    }

    public Region getRoot(){
        return root;
    }

    /**
     * Opens the SelectProject window
     * */
    public void assignButtonPressed(){
        viewHandler.openView("projectSelect");
    }

    /**
     * opens the ProjectList window
     * */
    public void backButtonPressed() {
        viewHandler.openView("projectList");
    }

    public void searchByNameButtonPressed() {
        try{
            errorLabel.setText("");
            if(nameField.getText().equals(""))
                reset();
            else
                try {
                    for(TeamMember teamMember : model.addTeamMembersToTheSystem().getTeamMembers())
                        if(teamMember.getFullName().equals(nameField.getText())) {
                            viewState.setSelectedTeamMember(teamMember.getId());
                            idField.setText(teamMember.getId() + "");
                            frequentTeamMemberLabel.setText("Frequent Team Member: " + model.getMostFrequentTeamMember(teamMember));
                            productivityLabel.setText("Productivity: " + model.getProductivity(teamMember));
                        }
                }
                catch (Exception e){
                    errorLabel.setText("Invalid name!");
                }

        }
        catch (Exception e){
            errorLabel.setText("No team member with this id in the system");
        }
    }



    /**
     * searches a TeamMember by the given Id and updates the
     *          nameField,
     *          frequent teamMember,
     *          productivity
     *          table data with the related information
     * */
    public void searchByIDButtonPressed() {
        try {
            errorLabel.setText("");
            if (idField.getText().equals(""))
                reset();
            else {
                int id = 0;
                try {
                    for (TeamMember teamMember : model.addTeamMembersToTheSystem().getTeamMembers())
                        if (teamMember.getId() == Integer.parseInt(idField.getText())) {
                            id = Integer.parseInt(idField.getText());
                            viewState.setSelectedTeamMember(teamMember.getId());
                            nameField.setText(teamMember.getFullName());
                            frequentTeamMemberLabel.setText("Frequent Team Member: " + model.getMostFrequentTeamMember(teamMember));
                            productivityLabel.setText("Productivity: " + model.getProductivity(teamMember));
                        }
                }
                catch (NumberFormatException e){
                    errorLabel.setText("Id has to be a number!");
                }

            }
        }
        catch (Exception e){
            errorLabel.setText("No team member with this id in the system");
        }
    }

}

