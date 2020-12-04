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


public class DetailsTeamMemberController {

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private TeamMemberListViewModel viewModel;
    @FXML private TextField nameField;
    @FXML private Label productivityLabel;
    @FXML private Label frequentTeamMemberLabel;
    @FXML private Label errorLabel;
    @FXML private TableView<TeamMemberViewModel> teamMemberViewTable;
    @FXML private TableColumn<ProjectListViewModel,String> projectNameColumn;
    @FXML private TableColumn<ProjectListViewModel,String> roleColumn;

    public DetailsTeamMemberController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewModel = new TeamMemberListViewModel(model);

    }

    public void reset(){
        errorLabel.setText("");
    }

    public Region getRoot(){
        return root;
    }

    public void assignButtonPressed(){

    }


    public void backButtonPressed() {
    }
}
