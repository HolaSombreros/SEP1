package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import model.IProjectManagementModel;

public class AddProjectController
{
    @FXML private TextField nameInput;
    @FXML private TextField IDInput;
    @FXML private DatePicker startingDateInput;
    @FXML private DatePicker deadlineInput;
    @FXML private ToggleGroup statusChoice;
    @FXML private ToggleGroup methodologyChoice;
    @FXML private Label errorLabel;

    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;

    public AddProjectController()
    {
        //LOADED BY THE FXML LOADER
    }
    public void init(ViewHandler viewHandler, Region root, IProjectManagementModel model)
    {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        errorLabel.setText("");
        nameInput.setText("");
        IDInput.setText("");

    }
}

