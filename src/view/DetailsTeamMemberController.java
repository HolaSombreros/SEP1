package view;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import mediator.IProjectManagementModel;




public class DetailsTeamMemberController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState viewState;
    private IProjectManagementModel model;
    private ProjectListViewModel viewModel;


    @FXML private Label productivityLabel;
    @FXML private Label frequentTeamMemberLabel;
    @FXML private Label errorLabel;
    @FXML private Label nameLabel;
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
        reset();


    }

    public void reset() {
       errorLabel.setText("");
       try {
           productivityLabel.setText("Productivity: " + model.getProductivity(model.getTeam().getByID(viewState.getSelectedTeamMember())));
           if(model.getMostFrequentTeamMember(model.getTeam().getByID(viewState.getSelectedTeamMember()))==null)
               frequentTeamMemberLabel.setText("None");
           else
               frequentTeamMemberLabel.setText("" + model.getMostFrequentTeamMember(model.getTeam().getByID(viewState.getSelectedTeamMember())));
           nameLabel.setText("#" + model.getTeam().getByID(viewState.getSelectedTeamMember()).getId() + " " + model.getTeam().getByID(viewState.getSelectedTeamMember()).getFullName());
       }
       catch (Exception e){
            errorLabel.setText(e.getMessage());
       }

       projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
       teamMemberViewTable.setItems(viewModel.getList());
       viewModel.update(viewState.getSelectedTeamMember());

    }

    public Region getRoot(){
        return root;
    }

    /**
     * Opens the SelectProject window
     * */
    public void assignButtonPressed(){
        if(model.getProjectList().size() != 0)
            viewHandler.openView("projectSelect");
        else
            errorLabel.setText("Please create a project first!");
    }

    /**
     * opens the ProjectList window
     * */
    public void backButtonPressed() {
        viewHandler.openView("teamMembers");
    }





}




