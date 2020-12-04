package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.*;

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

  public AddRequirementController()
  {
  }

  public Region getRoot()
  {
    return root;
  }

  public void init(ViewHandler viewHandler, IProjectManagementModel model,
      Region root)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    reset();
  }

  public void reset()
  {
    userStoryInput.setText("");
    errorLabel.setText("");
    startingDateInput.getEditor().clear();
    deadlineInput.getEditor().clear();
    estimatedTimeInput.setText("");
    priorityInput.getSelectionModel().clearAndSelect(0);
    typeInput.getSelectionModel().clearAndSelect(0);
  }

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

      double estimatedTime = 0;
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

      //model.addRequirement(
        //  new Requirement(userStory, startingDate, deadline, estimatedTime,
          //    priority, type));
      errorLabel.setText("");
    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
  }

  @FXML private void cancelButtonPressed()
  {
    viewHandler.openView("requirement");
  }

}
