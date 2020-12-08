package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import java.util.Optional;

public class DetailsAndEditRequirementController
{
  @FXML private Label idLabel;
  @FXML private TextArea relatedProjectInput;
  @FXML private TextField userStoryInput;
  @FXML private DatePicker deadlineInput;
  @FXML private Label deadlineShow;
  @FXML private DatePicker startingDateInput;
  @FXML private Label startingDateShow;
  @FXML private TextField estimatedTimeInput;
  @FXML private ChoiceBox<String> priorityInput;
  @FXML private ChoiceBox<String> typeInput;
  @FXML private ChoiceBox<String> statusInput;
  @FXML private Label hoursWorkedShow;
  @FXML private Label responsibleTeamMemberInput;
  @FXML private TableView<TeamMemberViewModel> teamMembersTable;
  @FXML private TableColumn<TeamMemberViewModel, Number> idColumn;
  @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
  @FXML private Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private TeamMemberListViewModel viewModel;
  private ViewState state;

  public DetailsAndEditRequirementController()
  {
  }

  public Region getRoot()
  {
    return root;
  }

  public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    this.state = state;
    reset();
  }

  public void reset()
  {
    Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirementById(state.getSelectedRequirement());

    this.viewModel = new TeamMemberListViewModel(model, state);
    viewModel.update(requirement.getRelatedProject());
    idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().getNameProperty());
    teamMembersTable.setItems(viewModel.getList());

    idLabel.setText("Requirement Id: " + state.getSelectedRequirement());
    relatedProjectInput.setText(requirement.getRelatedProject().getName());
    userStoryInput.setText(requirement.getUserStory());
    startingDateInput.getEditor().clear();
    startingDateShow.setText("Now: " + requirement.getStartingDate().toString());
    deadlineInput.getEditor().clear();
    deadlineShow.setText("Now: " + requirement.getDeadline().toString());
    estimatedTimeInput.setText(requirement.getEstimatedTime() + "");
    hoursWorkedShow.setText("Number of Hours Worked: " + requirement.getHoursWorked());
    if (requirement.getResponsibleTeamMember() != null)
      responsibleTeamMemberInput.setText(requirement.getResponsibleTeamMember().getId() + " " + requirement.getResponsibleTeamMember().getFullName());
    else
      responsibleTeamMemberInput.setText("");

    switch (requirement.getPriority())
    {
      case CRITICAL:
        priorityInput.getSelectionModel().clearAndSelect(0);
        break;
      case HIGH:
        priorityInput.getSelectionModel().clearAndSelect(1);
        break;
      case LOW:
        priorityInput.getSelectionModel().clearAndSelect(2);
        break;
    }

    switch (requirement.getType())
    {
      case FUNCTIONAL:
        typeInput.getSelectionModel().clearAndSelect(0);
        break;
      case NON_FUNCTIONAL:
        typeInput.getSelectionModel().clearAndSelect(1);
        break;
      case PROJECT_RELATED:
        typeInput.getSelectionModel().clearAndSelect(2);
        break;
    }

    switch (requirement.getStatus())
    {
      case NOT_STARTED:
        statusInput.getSelectionModel().clearAndSelect(0);
        break;
      case STARTED:
        statusInput.getSelectionModel().clearAndSelect(1);
        break;
      case ENDED:
        statusInput.getSelectionModel().clearAndSelect(2);
        break;
      case APPROVED:
        statusInput.getSelectionModel().clearAndSelect(3);
        break;
      case REJECTED:
        statusInput.getSelectionModel().clearAndSelect(4);
        break;
    }

    errorLabel.setText("");
  }

  @FXML private void confirmEditingButtonPressed()
  {
    Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirementById(state.getSelectedRequirement());

    try
    {
      if (userStoryInput.getText().equals(""))
        throw new IllegalArgumentException("User Story can not be empty");
      String userStory = userStoryInput.getText();

      Date d1 = requirement.getStartingDate();
      if (startingDateInput.getValue() != null)
      {
        int day1 = startingDateInput.getValue().getDayOfMonth();
        int month1 = startingDateInput.getValue().getMonthValue();
        int year1 = startingDateInput.getValue().getYear();
        if (startingDateInput.getValue() != null)
          d1 = new Date(day1, month1, year1);
      }

      Date d2 = requirement.getDeadline();
      if (deadlineInput.getValue() != null)
      {
        int day2 = deadlineInput.getValue().getDayOfMonth();
        int month2 = deadlineInput.getValue().getMonthValue();
        int year2 = deadlineInput.getValue().getYear();
        if (deadlineInput.getValue() != null)
          d2 = new Date(day2, month2, year2);
      }

      Date.checkDates(d1, d2);

      double estimatedTime = requirement.getEstimatedTime();
      if (estimatedTimeInput.getText().equals(""))
        throw new IllegalArgumentException("Estimated Time can not be empty");
      try
      {
        estimatedTime = Double.parseDouble(estimatedTimeInput.getText());
      }
      catch (NumberFormatException e)
      {
        throw new IllegalArgumentException("Estimated Time has to be a number");
      }

      TeamMember rtm = requirement.getResponsibleTeamMember();
      if (!(responsibleTeamMemberInput.getText().equals("")))
      {
        String member = responsibleTeamMemberInput.getText();
        String[] member1 = member.split(" ");
        int index = Integer.parseInt(member1[0]);
        rtm = model.getTeamMemberList(requirement.getRelatedProject(), requirement).getByID(index);
      }

      Priority priority = requirement.getPriority();
      if (priorityInput.getValue().equals("Critical"))
        priority = Priority.CRITICAL;
      if (priorityInput.getValue().equals("High"))
        priority = Priority.HIGH;
      if (priorityInput.getValue().equals("Low"))
        priority = Priority.LOW;

      Type type = requirement.getType();
      if (typeInput.getValue().equals("Functional"))
        type = Type.FUNCTIONAL;
      if (typeInput.getValue().equals("Non Functional"))
        type = Type.NON_FUNCTIONAL;
      if (typeInput.getValue().equals("Project Related"))
        type = Type.PROJECT_RELATED;

      RequirementStatus status = requirement.getStatus();
      if (statusInput.getValue().equals("Not Started"))
        status = RequirementStatus.NOT_STARTED;
      if (statusInput.getValue().equals("Started"))
        status = RequirementStatus.STARTED;
      if (statusInput.getValue().equals("Ended"))
        status = RequirementStatus.ENDED;
      if (statusInput.getValue().equals("Approved"))
        status = RequirementStatus.APPROVED;
      if (statusInput.getValue().equals("Rejected"))
        status = RequirementStatus.REJECTED;

      errorLabel.setText("");

      boolean remove = confirmation("edit");
      if (remove)
      {
        model.editRequirement(requirement.getRelatedProject(), requirement,userStory, estimatedTime, rtm, d1, d2, status, type, priority);
        state.setSelectedRequirement(-1);
        viewHandler.openView("requirementList");
      }
    }

    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }

  }

  @FXML private void makeResponsibleButtonPressed()
  {
    try
    {
      TeamMemberViewModel selectedItem = teamMembersTable.getSelectionModel().getSelectedItem();
      responsibleTeamMemberInput.setText(selectedItem.getIdProperty().getValue() + " " + selectedItem.getNameProperty().getValue());
    }
    catch (Exception e)
    {
      errorLabel.setText("Select a team member");
    }
  }

  @FXML private void removeRequirementButtonPressed()
  {
    Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirementById(state.getSelectedRequirement());
    boolean remove = confirmation("remove");
    if (remove)
    {
      model.removeRequirement(requirement.getRelatedProject(), requirement);
      state.setSelectedRequirement(-1);
      viewHandler.openView("requirementList");
    }

  }

  @FXML private void backButtonPressed()
  {
    state.setSelectedRequirement(-1);
    viewHandler.openView("requirementList");
  }

  private boolean confirmation(String string)
  {
    Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject())).getRequirementById(state.getSelectedRequirement());
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    if (string.equals("edit"))
      alert.setHeaderText("Are you sure you want to edit the requirement?");
    else
      alert.setHeaderText("Removing requirement - id: " + requirement.getId());
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}