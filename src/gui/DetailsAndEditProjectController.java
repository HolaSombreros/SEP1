package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;
import java.util.Optional;

public class DetailsAndEditProjectController
{
    @FXML private TextField IDInput;
    @FXML private TextField nameInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private ChoiceBox<String> methodologyChoice;
    @FXML private ChoiceBox<String> statusChoice;
    @FXML private Label errorLabel;
    @FXML private TableView<TeamMemberViewModel> teamMembersTable;
    @FXML private TableColumn<TeamMemberViewModel, Number> IDColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> roleColumn;
    @FXML private TextField scrumMaster;
    @FXML private TextField productOwner;
    @FXML private Button editDetailsButton;

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    private ViewState viewState;
    private TeamMemberListViewModel teamMemberListViewModel;
    public static final LocalDate LOCAL_DATE (Date dateString){
       LocalDate localDate = LocalDate.of(dateString.getYear(), dateString.getMonth(), dateString.getDay());
        return localDate;
    }

    public DetailsAndEditProjectController()
    {
        //LOADED BY THE FXML LOADER
    }
    public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState viewState)
    {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.viewState = viewState;
        this.teamMemberListViewModel = new TeamMemberListViewModel(model, viewState);

        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
        teamMembersTable.setItems(teamMemberListViewModel.getList());
        reset();
    }
    public void reset()
    {
        Project project = model.getProjectList().getProjectByID(viewState.getSelectedProject());
        IDInput.setText(project.getID());
        nameInput.setText(project.getName());
        startingDateInput.setValue(LOCAL_DATE(project.getStartingDate()));
        deadlineInput.setValue(LOCAL_DATE(project.getDeadline()));
        methodologyChoice.setValue(project.getMethodology().getMethodology());
        statusChoice.setValue(project.getStatus().getName());
        if(project.getProductOwner() != null)
           productOwner.setText("#" + project.getProductOwner().getId() + " " + project.getProductOwner().getFullName());
        else
            productOwner.setText("");

        if(project.getScrumMaster() != null)
            scrumMaster.setText("#" + project.getScrumMaster().getId() + " " + project.getScrumMaster().getFullName());
        else
            scrumMaster.setText("");
        errorLabel.setText("");
        teamMemberListViewModel.update();

    }

    public Region getRoot()
    {
        return root;
    }

    @FXML private void editDetailsButtonPressed()
    {
        Project selectedProject = model.getProjectList().getProjectByID(viewState.getSelectedProject());
         boolean editedProject = false;
         try{
             // EDIT ID
             if(IDInput.getText().isEmpty() || IDInput.getText().trim().isEmpty())
             {
                 throw new IllegalStateException("ID cannot be empty");
             }
             if(!IDInput.getText().equals(selectedProject.getID()))
             {
                 editedProject = true;
             }
             //EDIT NAME
             if(nameInput.getText().isEmpty() || nameInput.getText().trim().isEmpty())
             {
                 throw new IllegalStateException("Name cannot be empty");
             }
             if(!nameInput.getText().equals(selectedProject.getName()))
             {
                 editedProject = true;
             }
            /* //EDIT STATUS
            if(statusChoice.getValue().equals(""))
             {
                 editDetailsButton.setDisable(true);
             }*/
             if(!statusChoice.getValue().equals(selectedProject.getStatus()))
             {
                 editedProject = true;
             }
             //EDIT METHODOLOGY
             /*if(methodologyChoice.getValue().equals(""))
             {
                 editDetailsButton.setDisable(true);
             }*/
             if(!methodologyChoice.getValue().equals(selectedProject.getMethodology()))
             {
                 editedProject = true;
             }
             //STARTING DATE
             Date startingDate = new Date(startingDateInput.getValue().getDayOfMonth(), startingDateInput.getValue().getMonthValue(), startingDateInput.getValue().getYear());

             if(!startingDateInput.getValue().equals(selectedProject.getStartingDate()))
             {
                 editedProject = true;
             }
             //DEADLINE
             Date deadline = new Date(deadlineInput.getValue().getDayOfMonth(), deadlineInput.getValue().getMonthValue(), deadlineInput.getValue().getYear());

             if(!deadlineInput.getValue().equals(selectedProject.getDeadline()))
             {
                 editedProject = true;
             }
             Date.checkDates(startingDate,deadline);
             //TODO: INITIAL SCRUM MASTER IS NOT THERE AFTER MAKING CHANGES TO THE PR

             //SCRUM MASTER
            /* TeamMember SM = selectedProject.getScrumMaster();
             if (SM  != null) {
                 TeamMember teamMember = selectedProject.getTeamMemberList().getByID(Integer.parseInt(scrumMaster.getText().split(" ")[0].substring(1)));
                 if (!SM.equals(teamMember)) {
                     editedProject = true;
                 }
             }
             else if (!scrumMaster.getText().equals("") && selectedProject.getScrumMaster() == null ||
                     scrumMaster.getText().equals("") && selectedProject.getScrumMaster() != null) {
                 editedProject = true;
             }
             TeamMember PO = selectedProject.getProductOwner();
             if(PO != null)
             {

                 TeamMember teamMember = selectedProject.getTeamMemberList().getByID(Integer.parseInt(productOwner.getText().split(" ")[0].substring(1)));
                 if(!PO.equals(teamMember))
                 {
                     editedProject = true;
                 }
             }*/
             Status status = null;
             Methodology methodology = null;
             switch(statusChoice.getValue())
             {
                 case "Not Started":
                     status = Status.NOT_STARTED;
                     break;
                 case "Started":
                     status = Status.STARTED;
                     break;
                 case "Ended":
                     status = Status. ENDED;
                     break;
             }
             switch(methodologyChoice.getValue())
             {

                 case "Waterfall":
                     methodology = Methodology.WATERFALL;
                     break;
                 case "Scrum":
                     methodology = Methodology.SCRUM;
                     break;
             }
             if(editedProject && confirmation("edit"))
             {
                 TeamMember scrumM = null;
                 if(!scrumMaster.getText().equals(""))
                 {
                     scrumM = selectedProject.getTeamMemberList().getByID(Integer.parseInt(scrumMaster.getText().split(" ")[0].substring(1)));
                 }
                 TeamMember prodO = null;
                 if(!productOwner.getText().equals(""))
                 {
                     prodO = selectedProject.getTeamMemberList().getByID(Integer.parseInt(productOwner.getText().split(" ")[0].substring(1)));
                 }


                 selectedProject.edit(nameInput.getText(), IDInput.getText(), startingDate, deadline,status, methodology,scrumM,prodO);
                 model.editProject(selectedProject);
                 backButtonPressed();
             }

         }
         catch(Exception e)
         {
             errorLabel.setText(e.getMessage());
         }

    }
    @FXML private void removeProjectButtonPressed()
    {
        errorLabel.setText("");
        try
        {
            Project selectedProject = model.getProjectList().getProjectByID(viewState.getSelectedProject());

            if (confirmation("remove"))
            {

                model.removeProject(selectedProject);
                backButtonPressed();
            }
        }
        catch(Exception e)
        {
            errorLabel.setText("Project not selected");
        }
    }
    private boolean confirmation(String id)
    {
        Project selectedProject = model.getProjectList().getProjectByID(viewState.getSelectedProject());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        switch(id)
        {
            case "remove":
                alert.setTitle("Confirm removing project");
                alert.setHeaderText("Are you sure you want to remove the selected project #" + selectedProject.getID() + "?");
                break;
            case "edit":
                alert.setTitle("Confirm editing project");
                alert.setHeaderText("Are you sure you want to edit the project #" + selectedProject.getID() + "?");
                break;
        }
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
    @FXML private void viewRelatedReqButtonPressed()
    {
        viewHandler.openView("requirementList");
    }
    @FXML private void backButtonPressed()
    {
        viewState.setSelectedProject("-1");
        viewHandler.openView("projectList");
    }
    @FXML private void assignScrumMasterButtonPressed()
    {
        try
        {
            TeamMemberViewModel selectedTeamMember = teamMembersTable.getSelectionModel().getSelectedItem();
            if(selectedTeamMember == null)
            {
                scrumMaster.setText("");
            }
            else
            {
                scrumMaster.setText("#" + selectedTeamMember.getIdProperty().get() + " " + selectedTeamMember.getNameProperty().get());
            }
        }
        catch (Exception e)
        {
            errorLabel.setText(e.getMessage());
        }
    }
    @FXML private void assignProductOwnerButtonPressed()
    {
        try
        {
            TeamMemberViewModel selectedTeamMember = teamMembersTable.getSelectionModel().getSelectedItem();
            if(selectedTeamMember == null)
            {
                productOwner.setText("");
            }
            else
            {
                productOwner.setText("#" + selectedTeamMember.getIdProperty().get() + " " + selectedTeamMember.getNameProperty().get());
            }
        }
        catch (Exception e)
        {
            errorLabel.setText(e.getMessage());
        }

    }
    @FXML
    public void handleKeyReleased() //**
    {
        String name = nameInput.getText();
        String ID = IDInput.getText();
        boolean disableButtons = (name.isEmpty() || name.trim().isEmpty()) || (ID.isEmpty() || ID.trim().isEmpty());
        editDetailsButton.setDisable(disableButtons);

    }

}
