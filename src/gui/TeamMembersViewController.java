package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

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
    @FXML private TableColumn<TeamViewModel,Number> taskColumn;
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
        taskColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskProperty());
        teamMemberTable.setItems(viewModel.getList());
        System.out.println(model.getTeam().size());

        reset();
    }

    public void reset(){
        errorLabel.setText("");
        searchField.setText("");
        viewModel.update();

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

    public void searchKeyTyped() {
        errorLabel.setText("");
        try {
            if(searchField.getText().equals("")){
                reset();
            }
            else {

                String name = null;
                int id = 0;
                try {
                    id = Integer.parseInt(searchField.getText());
                    viewModel.update(id);
                }
                catch (NumberFormatException e) {
                    name = searchField.getText();
                    viewModel.update(name);
                }
            }

        }
        catch (Exception e){
            errorLabel.setText("No team members with this name");
        }

    }

    public void assignButtonPressed(){
        try{
            TeamViewModel selectedItem = teamMemberTable.getSelectionModel().getSelectedItem();
            viewState.setSelectedTeamMember(selectedItem.getIdProperty().getValue());
            viewHandler.openView("projectSelect");
        }
        catch (Exception e){
            errorLabel.setText("Select a Team Member first!");
        }
    }
}
