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
    private TeamListViewModel viewModel;
    private IProjectManagementModel model;

    @FXML private TableView<TeamViewModel> teamMemberTable;
    @FXML private TableColumn<TeamViewModel,Number> idColumn;
    @FXML private TableColumn<TeamViewModel,String> nameColumn;
    @FXML private TableColumn<TeamViewModel,Number> productivityColumn;
    @FXML private Label errorLabel;
    @FXML private TextField searchField;



    public TeamMembersViewController(){

    }


    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState viewState){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.viewModel = new TeamListViewModel(model,viewState);
        errorLabel.setText("");
        searchField.setText("");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        productivityColumn.setCellValueFactory(cellData -> cellData.getValue().getProductivityProperty());
        teamMemberTable.setItems(viewModel.getList());
        System.out.println(model.getTeam().size());

        reset();
    }

    public void reset(){
        errorLabel.setText("");
        searchField.setText("");

    }

    public Region getRoot(){
        return root;
    }

    public void backButtonPressed(){
        viewHandler.openView("projectList");
    }

    public void viewButtonPressed(){
        try{
            TeamViewModel selectedItem = teamMemberTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedTeamMember(selectedItem.getIdProperty().getValue());
            viewHandler.openView("detailsTeamMember");
        }
        catch (Exception e){
            errorLabel.setText("Select a Team Member first!");
        }
    }

    public void searchKeyReleased() {
    }
}
