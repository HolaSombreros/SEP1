package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.*;

public class DetailsAndEditRequirementController
{
  @FXML private Label idLabel;
  @FXML private Label relatedProjectLabel;
  @FXML private TextField userStoryInput;
  @FXML private DatePicker deadlineInput;
  @FXML private DatePicker startingDateInput;
  @FXML private TextField estimatedTimeInput;
  @FXML private ChoiceBox<String> priorityInput;
  @FXML private ChoiceBox<String> typeInput;
  @FXML private ChoiceBox<String> statusInput;
  @FXML private TextField hoursWorkedInput;
  @FXML private TextField responsibleTeamMemberInput;
  @FXML private Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private TeamMemberListViewModel viewModel;

  public DetailsAndEditRequirementController()
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
    this.viewModel = new TeamMemberListViewModel(model);
    reset();
    //idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    //nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
  }

  public void reset()
  {
    idLabel.setText("");

  }

  @FXML private void editRequirementButtonPressed()
  {

  }

  @FXML private void makeResponsibleButtonPressed()
  {

  }

  @FXML private void removeRequirementButtonPressed()
  {

  }

  @FXML private void backButtonPressed()
  {
    viewHandler.openView("requirement");
  }
}
