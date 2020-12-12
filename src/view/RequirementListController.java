package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.IProjectManagementModel;
import model.Requirement;

import java.util.Optional;

public class RequirementListController
{
  @FXML private Label relatedProjectLabel;
  @FXML private TextField searchInput;
  @FXML private TableView<RequirementViewModel> requirementListTable;
  @FXML private TableColumn<RequirementViewModel, Number> idColumn;
  @FXML private TableColumn<RequirementViewModel, String> priorityColumn;
  @FXML private TableColumn<RequirementViewModel, String> statusColumn;
  @FXML private TableColumn<RequirementViewModel, String> deadlineColumn;
  @FXML private TextArea userStoryShow;
  @FXML private TextField indexInput;
  @FXML private Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private RequirementListViewModel viewModel;
  private ViewState state;

  public RequirementListController()
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
    relatedProjectLabel.setText(model.getProjectList().getProjectByID(state.getSelectedProject()).getName());
    searchInput.setText("");
    errorLabel.setText("");
    userStoryShow.setText("");
    this.viewModel = new RequirementListViewModel(model, state);
    viewModel.update(0);
    idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    priorityColumn.setCellValueFactory(cellData -> cellData.getValue().getPriorityProperty());
    deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
    statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
    requirementListTable.setItems(viewModel.getList());
  }

  /**
   * The method updates the table displaying only the typed requirement
   */
  @FXML private void onKeyTyped()
  {
    try
    {
      errorLabel.setText("");
      if (searchInput.getText().equals(""))
        reset();
      else
      {
        int id;
        try
        {
          id = Integer.parseInt(searchInput.getText());
          viewModel.update(id);
        }
        catch (NumberFormatException e)
        {
          throw new IllegalArgumentException("ID has to be a number");
        }
      }
    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
  }

  /**
   * The method will display the user story of the selected requirement
   */
  @FXML private void onMouseClicked()
  {
    errorLabel.setText("");
    try
    {
      RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
      Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject()))
          .getRequirementById(selectedItem.getIdProperty().getValue());
      userStoryShow.setText(requirement.getUserStory());
    }
    catch (Exception e)
    {
      errorLabel.setText("");
    }
  }

  /**
   * The method allows the user to switch the positions of the requirements in the array
   * The selected index has to be between 0 and the size of the requirement list -1;
   * Switching between 2 requirements with different priority is not allowed
   */
  @FXML private void moveButtonPressed()
  {
    try
    {
      errorLabel.setText("");
      RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
      if (selectedItem == null)
        throw new IllegalArgumentException("Select a requirement");

      if (indexInput.getText().equals(""))
        errorLabel.setText("");
      else
      {
        int index;
        try
        {
          index = Integer.parseInt(indexInput.getText());
          if (index >= model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().size())
            throw new IllegalArgumentException("Not an available position");
          if (index < 0)
            throw new IllegalArgumentException("The index can not be negative");
          Requirement req1 = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject()))
              .getRequirementById(selectedItem.getIdProperty().getValue());
          Requirement req2 = model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirement(index);
          if (req1.getPriority() != req2.getPriority())
            throw new IllegalArgumentException("Movement not allowed");
          model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirements().remove(req1);
          model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirements().add(index, req1);
          model.editProject(model.getProjectList().getProjectByID(state.getSelectedProject()));
          reset();
          indexInput.setText("");
        }
        catch (NumberFormatException e)
        {
          throw new IllegalArgumentException("ID has to be a number");
        }
      }

    }
    catch (Exception e)
    {
      errorLabel.setText(e.getMessage());
    }
  }

  /**
   * The method will send the user to the adding requirement window
   */
  @FXML private void addNewRequirementButtonPressed()
  {
    viewHandler.openView("addRequirement");
  }

  /**
   * The method will send the user to the selected requirement's details
   */
  @FXML private void viewRequirementButtonPressed()
  {
    try
    {
      RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
      state.setSelectedRequirement(selectedItem.getIdProperty().getValue());
      viewHandler.openView("detailsAndEditRequirement");
    }
    catch (Exception e)
    {
      errorLabel.setText("Select a requirement");
    }
  }

  /**
   * The method will send the user to the selected requirement's task list
   */
  @FXML private void viewTasksButtonPressed()
  {
    try
    {
      RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
      state.setSelectedRequirement(selectedItem.getIdProperty().getValue());
      viewHandler.openView("taskList");
    }
    catch (Exception e)
    {
      errorLabel.setText("Select a requirement");
    }
  }

  /**
   * The method will ask for confirmation
   * The method will remove the selected requirement
   */
  @FXML private void removeRequirementButtonPressed()
  {
    errorLabel.setText("");
    try
    {
      RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
      boolean remove = confirmation();
      if (remove)
      {
        Requirement requirement = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject()))
            .getRequirementById(selectedItem.getIdProperty().getValue());
        model.removeRequirement(requirement.getRelatedProject(), requirement);
        viewModel.remove(requirement);
        requirementListTable.getSelectionModel().clearSelection();
      }
    }
    catch (Exception e)
    {
      errorLabel.setText("Select a requirement");
    }
  }

  /**
   * The method will go back to the project's details
   */
  @FXML private void backButtonPressed()
  {
    viewHandler.openView("detailsAndEditProject");
  }

  private boolean confirmation()
  {
    int index = requirementListTable.getSelectionModel().getSelectedIndex();
    RequirementViewModel selectedItem = requirementListTable.getItems().get(index);
    if (index >= requirementListTable.getItems().size())
    {
      return false;
    }
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText("Removing requirement - id: " + selectedItem.getIdProperty().get());
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}
