package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import model.Priority;
import model.ProjectManagementModelManager;
import model.RequirementStatus;
import model.Type;

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
  private ProjectManagementModelManager model;
  private ViewHandler viewHandler;
  private TeamMemberListViewModel viewModel;

}
