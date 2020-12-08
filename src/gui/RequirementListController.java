package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
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

  @FXML public void searchButtonPressed()
  {
    try
    {
      errorLabel.setText("");
      if (searchInput.getText().equals(""))
        reset();

      else
      {
        int id = 0;
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

  @FXML private void clearButtonPressed()
  {
    errorLabel.setText("");
    searchInput.setText("");
    viewModel.update(0);
  }

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
        int index = 0;
        try
        {
          index = Integer.parseInt(indexInput.getText());
          if (index >= model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().size())
            throw new IllegalArgumentException("Not an available position");
          Requirement req1 = model.getRequirementList(model.getProjectList().getProjectByID(state.getSelectedProject()))
              .getRequirementById(selectedItem.getIdProperty().getValue());
          Requirement req2 = model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirement(index);
          if (req1.getPriority() != req2.getPriority())
            throw new IllegalArgumentException("Movement not allowed");
          Requirement other = req1;
          model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirements().remove(req1);
          model.getProjectList().getProjectByID(state.getSelectedProject()).getProjectRequirementList().getRequirements().add(index, other);
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

  @FXML private void addNewRequirementButtonPressed()
  {
    viewHandler.openView("addRequirement");
  }

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

  @FXML private void backButtonPressed()
  {
    viewHandler.openView("detailsAndEditProject");
  }

  private boolean confirmation()
  {
    int index = requirementListTable.getSelectionModel().getSelectedIndex();
    RequirementViewModel selectedItem = requirementListTable.getItems().get(index);
    if (index < 0 || index >= requirementListTable.getItems().size())
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
