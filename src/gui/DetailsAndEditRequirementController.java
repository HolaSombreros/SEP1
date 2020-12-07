package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;
import java.util.Optional;

public class DetailsAndEditRequirementController
{
  @FXML private Label idLabel;
  @FXML private Label relatedProjectLabel;
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
  @FXML private TableView teamMembersTable;
  @FXML private TableColumn idColumn;
  @FXML private TableColumn nameColumn;
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

  public void init(ViewHandler viewHandler, IProjectManagementModel model,
      Region root, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    this.state = state;
    reset();
    this.viewModel = new TeamMemberListViewModel(model, state);
    //idColumn
       // .setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    //nameColumn
       // .setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
  }

  public void reset()
  {
    Requirement requirement = model.getRequirementList(
        model.getProjectList().getProjectByID(state.getSelectedProject()))
        .getRequirementById(state.getSelectedRequirement());
    idLabel.setText(state.getSelectedRequirement() + "");
    relatedProjectLabel.setText("Not SEP1");
    userStoryInput.setText(requirement.getUserStory());
    startingDateShow
        .setText("Now: " + requirement.getStartingDate().toString());
    deadlineShow.setText("Now:" + requirement.getDeadline().toString());
    estimatedTimeInput.setText(requirement.getEstimatedTime() + "");
    hoursWorkedShow
        .setText("Number of Hours Worked: " + requirement.getHoursWorked());
    if (requirement.getResponsibleTeamMember() != null)
      responsibleTeamMemberInput.setText(
          requirement.getResponsibleTeamMember().getId() + " " + requirement
              .getResponsibleTeamMember().getFullName());

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

  @FXML private void editRequirementButtonPressed()
  {
    Requirement requirement = model.getRequirementList(
        model.getProjectList().getProjectByID(state.getSelectedProject()))
        .getRequirementById(state.getSelectedRequirement());
    try
    {
      if (userStoryInput.getText().equals(""))
        throw new IllegalArgumentException("User Story can not be empty");
      requirement.setUserStory(userStoryInput.getText());

      if (startingDateInput.getValue() == null)
        throw new IllegalArgumentException("Starting date can not be empty");
      int day1 = startingDateInput.getValue().getDayOfMonth();
      int month1 = startingDateInput.getValue().getMonthValue();
      int year1 = startingDateInput.getValue().getYear();
      requirement.setStartingDate(new Date(day1, month1, year1));
      startingDateShow
          .setText("Now: " + requirement.getStartingDate().toString());

      if (deadlineInput.getValue() == null)
        throw new IllegalArgumentException("Deadline can not be empty");
      int day2 = deadlineInput.getValue().getDayOfMonth();
      int month2 = deadlineInput.getValue().getMonthValue();
      int year2 = deadlineInput.getValue().getYear();
      requirement.setDeadline(new Date(day2, month2, year2));
      deadlineShow.setText("Now: " + requirement.getDeadline().toString());

      Date.checkDates(new Date(day1, month1, year1),
          new Date(day2, month2, year2));

      double estimatedTime = 0;
      if (estimatedTimeInput.getText().equals(""))
        throw new IllegalArgumentException("Estimated Time can not be empty");
      try
      {
        requirement
            .setEstimatedTime(Double.parseDouble(estimatedTimeInput.getText()));
      }
      catch (NumberFormatException e)
      {
        throw new IllegalArgumentException("Estimated Time has to be a number");
      }

      Priority priority = null;
      if (priorityInput.getValue().equals("Critical"))
        requirement.setPriority(Priority.CRITICAL);
      if (priorityInput.getValue().equals("High"))
        requirement.setPriority(Priority.HIGH);
      if (priorityInput.getValue().equals("Low"))
        requirement.setPriority(Priority.LOW);

      Type type = null;
      if (typeInput.getValue().equals("Functional"))
        requirement.setType(Type.FUNCTIONAL);
      if (typeInput.getValue().equals("Non Functional"))
        requirement.setType(Type.NON_FUNCTIONAL);
      if (typeInput.getValue().equals("Project Related"))
        requirement.setType(Type.PROJECT_RELATED);

      RequirementStatus status = null;
      if (statusInput.getValue().equals("Not Started"))
        requirement.setStatus(RequirementStatus.NOT_STARTED);
      if (statusInput.getValue().equals("Started"))
        requirement.setStatus(RequirementStatus.STARTED);
      if (statusInput.getValue().equals("Ended"))
        requirement.setStatus(RequirementStatus.ENDED);
      if (statusInput.getValue().equals("Approved"))
        requirement.setStatus(RequirementStatus.APPROVED);
      if (statusInput.getValue().equals("Rejected"))
        requirement.setStatus(RequirementStatus.REJECTED);
    }

    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }

  }

  @FXML private void makeResponsibleButtonPressed()
  {
    //TODO
  }

  @FXML private void removeRequirementButtonPressed()
  {
    Requirement requirement = model.getRequirementList(
        model.getProjectList().getProjectByID(state.getSelectedProject()))
        .getRequirementById(state.getSelectedRequirement());
    boolean remove = confirmation();
    if (remove)
    {
      model.removeRequirement(
          model.getProjectList().getProjectByID(state.getSelectedProject()),
          requirement);
      state.setSelectedRequirement(-1);
      viewHandler.openView("requirement");
    }

  }

  @FXML private void backButtonPressed()
  {
    state.setSelectedRequirement(-1);
    viewHandler.openView("requirement");
  }

  private boolean confirmation()
  {
    Requirement requirement = model.getRequirementList(
        model.getProjectList().getProjectByID(state.getSelectedProject()))
        .getRequirementById(state.getSelectedRequirement());
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Removing requirement - id: " + requirement.getId());
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}