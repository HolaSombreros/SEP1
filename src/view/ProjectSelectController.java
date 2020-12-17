package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.IProjectManagementModel;

import java.util.Optional;

public class ProjectSelectController {

    private Region root;
    private ViewHandler viewHandler;
    private ViewState state;
    private IProjectManagementModel model;
    private ProjectListViewModel viewModel;

    @FXML private Label errorLabel;
    @FXML private TableView<ProjectViewModel> projectTable;
    @FXML private TableColumn<ProjectViewModel,String> idColumn;
    @FXML private TableColumn<ProjectViewModel,String> nameColumn;
    @FXML private TableColumn<ProjectViewModel,String> deadlineColumn;

    public ProjectSelectController(){

    }

    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root,ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
        this.viewModel = new ProjectListViewModel(model,state);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        projectTable.setItems(viewModel.getList());
        reset();

    }

    public void reset(){
        viewModel.update();
        errorLabel.setText("");
    }

    public Region getRoot(){
        return root;
    }


    public void backButtonPressed() {
        viewHandler.openView("detailsTeamMember");
    }

    public void unassignButtonPressed() {
        errorLabel.setText("");
        try{
            ProjectViewModel selectedItem = projectTable.getSelectionModel().getSelectedItem();
            state.setSelectedProject(selectedItem.getIDProperty().getValue());
            boolean remove = confirmation();
            if(remove){
               try {
                    model.removeTeamMember(model.getProjectList().getProjectByID(state.getSelectedProject()),
                            model.getProjectList().getProjectByID(state.getSelectedProject()).getTeamMemberList().getByID(state.getSelectedTeamMember()));
                    errorLabel.setText("Team Member successfully unassigned!");
               }
               catch (IllegalArgumentException e){
                      // errorLabel.setText("You cannot unassign a team member with a special role!");
                       errorLabel.setText(e.getMessage());
               }
            }
        }
        catch (Exception e){
            errorLabel.setText("Select a Project First!");
           // e.printStackTrace();
        }

    }
    public boolean confirmation(){
        int index = projectTable.getSelectionModel().getSelectedIndex();
        ProjectViewModel selectedItem = projectTable.getItems().get(index);
        if (index >= projectTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Unnassigning team member - id: " + state.getSelectedTeamMember());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    /**
     * Selects a project from the table and opens the Requremet Select window containing the requirements of the related project
     * if a project isn't selected update the error message
     * */
    public void requirementButtonPressed() {
        try {
            ProjectViewModel selectedItem = projectTable.getSelectionModel().getSelectedItem();
            state.setSelectedProject(selectedItem.getIDProperty().getValue());
            viewHandler.openView("requirementSelect");
        }
        catch (Exception e){
            errorLabel.setText("Project has to be selected first!");
        }
    }


}
