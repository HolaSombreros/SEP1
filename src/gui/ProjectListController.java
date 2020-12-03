package gui;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.*;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProjectListController
{
    @FXML private TextField searchInput;
    @FXML private TableView<ProjectListViewModel> projectListTable;
    @FXML private TableColumn<ProjectViewModel, String> projectIDColumn;
    @FXML private TableColumn<ProjectViewModel, String> projectNameColumn;
    @FXML private TableColumn<ProjectViewModel, Date> projectDateColumn;
    @FXML private TableColumn<ProjectViewModel, Status> projectStatusColumn;
    @FXML private Label errorLabel;

    private Region root;
    private ViewHandler viewHandler;
    private ProjectListViewModel projectListViewModel;
    private IProjectManagementModel model;



}
