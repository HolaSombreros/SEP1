package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RequirementListController
{

  @FXML private TextField searchInput;
  @FXML private TableView<RequirementListViewModel> requirementListTable;
  @FXML private TableColumn<RequirementViewModel,Number> idColumn;
  @FXML private TableColumn<RequirementViewModel, String> statusColumn;
  @FXML private TableColumn<RequirementViewModel, String> deadlineColumn;
  @FXML private TextArea userStoryShow;
  @FXML private Label errorLabel;
  private Region root;
  private IProjectManagementModel model;
  private ViewHandler viewHandler;
  private RequirementListViewModel viewModel;

}
