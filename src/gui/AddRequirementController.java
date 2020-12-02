package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;
import model.Priority;
import model.ProjectManagementModelManager;
import model.Type;

import java.awt.*;

public class AddRequirementController
{
  @FXML TextField userStoryInput;
  @FXML DatePicker deadlineInput;
  @FXML DatePicker startingDateInput;
  @FXML TextField estimatedTimeInput;
  @FXML ChoiceBox<Priority> priorityInput;
  @FXML ChoiceBox<Type> typeInput;
  @FXML Label errorLabel;
  private Region root;
  private ProjectManagementModelManager model;
  private ViewHandler viewHandler;
}
