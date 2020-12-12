package view;

import mediator.IProjectManagementModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;

public class AddRequirementController
{
  @FXML TextArea userStoryInput;
  @FXML DatePicker deadlineInput;
  @FXML DatePicker startingDateInput;
  @FXML TextField estimatedTimeInput;
  @FXML ChoiceBox<String> priorityInput;
  @FXML ChoiceBox<String> typeInput;
  @FXML Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private ViewState state;

  public AddRequirementController()
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
    Project project = model.getProjectList().getProjectByID(state.getSelectedProject());
    userStoryInput.setText("");
    errorLabel.setText("");
    startingDateInput.setValue(LocalDate.of(project.getStartingDate().getYear(),project.getStartingDate().getMonth(),project.getStartingDate().getDay()));
    deadlineInput.setValue(LocalDate.of(project.getDeadline().getYear(), project.getDeadline().getMonth(), project.getDeadline().getDay()));
    estimatedTimeInput.setText("");
    priorityInput.getSelectionModel().clearAndSelect(0);
    typeInput.getSelectionModel().clearAndSelect(0);
  }

  /**
   * The method will add a new requirement in the list with the typed details
   */
  @FXML private void createRequirementButtonPressed()
  {
    try
    {
      if (userStoryInput.getText().equals(""))
        throw new IllegalArgumentException("User Story can not be empty");
      String userStory = userStoryInput.getText();

      if (startingDateInput.getValue() == null)
        throw new IllegalArgumentException("Starting date can not be empty");
      int day1 = startingDateInput.getValue().getDayOfMonth();
      int month1 = startingDateInput.getValue().getMonthValue();
      int year1 = startingDateInput.getValue().getYear();
      Date startingDate = new Date(day1, month1, year1);

      if (deadlineInput.getValue() == null)
        throw new IllegalArgumentException("Deadline can not be empty");
      int day2 = deadlineInput.getValue().getDayOfMonth();
      int month2 = deadlineInput.getValue().getMonthValue();
      int year2 = deadlineInput.getValue().getYear();
      Date deadline = new Date(day2, month2, year2);

      double estimatedTime;
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

      Priority priority = null;
      if (priorityInput.getValue().equals("Critical"))
        priority = Priority.CRITICAL;
      if (priorityInput.getValue().equals("High"))
        priority = Priority.HIGH;
      if (priorityInput.getValue().equals("Low"))
        priority = Priority.LOW;

      Type type = null;
      if (typeInput.getValue().equals("Functional"))
        type = Type.FUNCTIONAL;
      if (typeInput.getValue().equals("Non Functional"))
        type = Type.NON_FUNCTIONAL;
      if (typeInput.getValue().equals("Project Related"))
        type = Type.PROJECT_RELATED;

      model.addRequirement(model.getProjectList().getProjectByID(state.getSelectedProject()),
          new Requirement(userStory, startingDate, deadline, estimatedTime, priority, type, model.getProjectList().getProjectByID(state.getSelectedProject())), true);
      errorLabel.setText("");
      viewHandler.openView("requirementList");
    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
  }

  /**
   * The method will send the user to the requirement list
   */
  @FXML private void cancelButtonPressed()
  {
    viewHandler.openView("requirementList");
  }

}
