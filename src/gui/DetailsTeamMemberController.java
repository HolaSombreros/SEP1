package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.TeamMember;

import java.io.FileNotFoundException;


public class DetailsTeamMemberController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState viewState;
    private IProjectManagementModel model;
    private ProjectListViewModel viewModel;

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private Label productivityLabel;
    @FXML private Label frequentTeamMemberLabel;
    @FXML private Label errorLabel;
    @FXML private TableView<ProjectViewModel> teamMemberViewTable;
    @FXML private TableColumn<ProjectViewModel,String> projectNameColumn;
    @FXML private TableColumn<ProjectViewModel,String> deadlineColumn;

    public DetailsTeamMemberController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState viewState){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new ProjectListViewModel(model,viewState);
        errorLabel.setText("");
        productivityLabel.setText("");
        frequentTeamMemberLabel.setText("");

        reset();


    }

    public void reset(){
       errorLabel.setText("");
       productivityLabel.setText("");
       frequentTeamMemberLabel.setText("");
       idField.setText("");
       nameField.setText("");
       projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
       teamMemberViewTable.setItems(viewModel.getList());

    }

    public Region getRoot(){
        return root;
    }

    /**
     * Opens the SelectProject window
     * */
    public void assignButtonPressed(){
        if(viewState.getSelectedTeamMember() != -1)
            viewHandler.openView("projectSelect");
        else
            errorLabel.setText("Pick a team member first!");
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
                    for(TeamMember teamMember : model.getTeam().getTeamMembers())
                        if(teamMember.getFullName().equals(nameField.getText())) {
                            int id = teamMember.getId();
                            viewState.setSelectedTeamMember(teamMember.getId());
                            idField.setText(teamMember.getId() + "");
                            if(model.getMostFrequentTeamMember(teamMember) == null)
                                frequentTeamMemberLabel.setText("Hasn't worked on tasks yet! ");
                            else
                                frequentTeamMemberLabel.setText("Frequent Team Member: " + model.getMostFrequentTeamMember(teamMember));
                            productivityLabel.setText("Productivity: " + model.getProductivity(teamMember));
                            viewModel.update(id);
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
    public void searchByIDButtonPressed() throws FileNotFoundException {
            errorLabel.setText("");
            if (idField.getText().equals(""))
                reset();
            else {
                int id = 0;
                try {
                    for (TeamMember teamMember : model.getTeam().getTeamMembers())
                        if (teamMember.getId() == Integer.parseInt(idField.getText())) {
                            id = Integer.parseInt(idField.getText());
                            viewState.setSelectedTeamMember(teamMember.getId());
                            nameField.setText(teamMember.getFullName());
                            if(model.getMostFrequentTeamMember(teamMember) == null)
                                frequentTeamMemberLabel.setText("Hasn't worked on tasks yet! ");
                            else
                                frequentTeamMemberLabel.setText("Frequent Team Member: " + model.getMostFrequentTeamMember(teamMember));
                            productivityLabel.setText("Productivity: " + model.getProductivity(teamMember));
                            viewModel.update(id);
                        }
                }
                catch (NumberFormatException e){
                    errorLabel.setText("Id has to be a number!");
                }

            }
        }

    public void viewButtonPressed(){
        try{
            ProjectViewModel selectedItem = teamMemberViewTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedProject(selectedItem.getIDProperty().getValue());
            viewHandler.openView("detailsAndEditProject");
        }
        catch (Exception e){
            errorLabel.setText("Select a project first!");
        }
    }

}




