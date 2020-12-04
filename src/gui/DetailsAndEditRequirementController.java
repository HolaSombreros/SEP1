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
  @FXML private ChoiceBox<Priority> priorityInput;
  @FXML private ChoiceBox<Type> typeInput;
  @FXML private ChoiceBox<RequirementStatus> statusInput;
  @FXML private TextField hoursWorkedInput;
  @FXML private TextField responsibleTeamMemberInput;
  @FXML private TableView<TeamMemberListViewModel> teamMembersTable;
  @FXML private TableColumn<TeamMemberListViewModel, String> nameColumn;
  @FXML private TableColumn<TeamMemberListViewModel, String> idColumn;
  @FXML private Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private TeamMemberListViewModel viewModel;

  public DetailsAndEditRequirementController()
  {
  }

  public void init(ViewHandler viewHandler, IProjectManagementModel model, Region root)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    idLabel.setText("");
    errorLabel.setText("");
    relatedProjectLabel.setText("");
  }

  public Region getRoot() {
    return root;
  }

  public void reset()
  {

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
