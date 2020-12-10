package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.TeamMember;
import model.TeamMemberList;

public class TeamMembersViewController {

    private Region root;
    private ViewState viewState;
    private ViewHandler viewHandler;
    private TeamMemberListViewModel viewModel;
    private IProjectManagementModel model;

    @FXML private TableView<TeamMemberViewModel> teamMemberTable;
    @FXML private TableColumn<TeamMemberViewModel,Number> idColumn;
    @FXML private TableColumn<TeamMemberViewModel,String> nameColumn;
    @FXML private TableColumn<TeamMemberViewModel,Number> productivityColumn;
    @FXML private Label errorLabel;
    @FXML private TextField searchField;



    public TeamMembersViewController(){

    }


    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState viewState){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TeamMemberListViewModel(model,viewState);
        errorLabel.setText("");
        searchField.setText("");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        productivityColumn.setCellValueFactory(cellData -> cellData.getValue().getProductivityProperty());
        teamMemberTable.setItems(viewModel.getList());

        reset();
    }

    public void reset(){
        errorLabel.setText("");
        searchField.setText("");
        //viewModel.update();


    }

    public Region getRoot(){
        return root;
    }

    public void backButtonPressed(){
        viewHandler.openView("projectList");
    }

    public void viewButtonPressed(){
        try{
            TeamMemberViewModel selectedItem = teamMemberTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedTeamMember(selectedItem.getIdProperty().getValue());
            viewHandler.openView("detailsTeamMember");
        }
        catch (Exception e){
            errorLabel.setText("Select a Team Member first!");
        }
    }

    public void searchKeyReleased(KeyEvent keyEvent) {
    }
}
