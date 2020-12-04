package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import model.Priority;
import model.ProjectManagementModelManager;
import model.Type;

public class AddRequirementController
{
  @FXML TextArea userStoryInput;
  @FXML DatePicker deadlineInput;
  @FXML DatePicker startingDateInput;
  @FXML TextField estimatedTimeInput;
  @FXML ChoiceBox<Priority> priorityInput;
  @FXML ChoiceBox<Type> typeInput;
  @FXML Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;

  public AddRequirementController()
  {
  }

  public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    errorLabel.setText("");
  }

  public Region getRoot() {
    return root;
  }

  public void reset()
  {

  }

  @FXML private void createRequirementButtonPressed()
  {

  }

  @FXML private void cancelButtonPressed()
  {
    viewHandler.openView("requirement");
  }

}
