package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.*;

import java.time.LocalDate;

public class AddProjectController
{
    @FXML
    private TextField nameInput;
    @FXML
    private TextField IDInput;
    @FXML
    private DatePicker startingDateInput;
    @FXML
    private DatePicker deadlineInput;
    @FXML
    private ChoiceBox<String> methodology;
    @FXML
    private Label errorLabel;
    @FXML private Button createProject;
    
    private Region root;
    private ViewHandler viewHandler;
    private IProjectManagementModel model;
    
    public AddProjectController()
    {
        //LOADED BY THE FXML LOADER
    }
    
    public void init(ViewHandler viewHandler, IProjectManagementModel model,Region root)
    {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        errorLabel.setText("");
        createProject.setDisable(true);
    }
    
    public void reset()
    {
        nameInput.setText("");
        IDInput.setText("");
        startingDateInput.getEditor().clear();
        deadlineInput.getEditor().clear();
        errorLabel.setText("");
    }
    public Region getRoot()
    {
        return root;
    }
    
    @FXML
    private void createProjectButtonPressed()
    {
       try
        {
            String name = nameInput.getText();
            String ID = IDInput.getText();
            if(startingDateInput.getValue() == null){
                errorLabel.setText("Starting date cannot be empty");
                    throw new IllegalStateException(errorLabel.getText());}
            int stDay = startingDateInput.getValue().getDayOfMonth();
            int stMonth = startingDateInput.getValue().getMonthValue();
            int stYear = startingDateInput.getValue().getYear();
            Date startingDate = new Date(stDay,stMonth,stYear);
            if(deadlineInput.getValue() == null)
            {
                errorLabel.setText("Deadline cannot be empty");
                throw new IllegalStateException(errorLabel.getText());
            }
            int dlDay = deadlineInput.getValue().getDayOfMonth();
            int dlMonth = deadlineInput.getValue().getMonthValue();
            int dlYear = deadlineInput.getValue().getYear();
            Date deadlineDate = new Date(dlDay,dlMonth,dlYear);

            Methodology chosenMet = null;

            if(methodology.getValue().equals("SCRUM")) chosenMet = Methodology.SCRUM;
            else if(methodology.getValue().equals("Waterfall")) chosenMet = Methodology.WATERFALL;


           model.addProject(new Project(name, ID, startingDate, deadlineDate, chosenMet));

        }
        catch(Exception e)
        {
            errorLabel.setText(e.getMessage());
        }
    
    }
    @FXML private void cancelButtonPressed()
    {
        createProject.setDisable(true);
        reset();
        viewHandler.openView("projectList");
    }
    @FXML
    public void handleKeyReleased() //**
    {
        String name = nameInput.getText();
        String ID = IDInput.getText();
        boolean disableButtons = name.isEmpty() || name.trim().isEmpty() || ID.isEmpty() || ID.trim().isEmpty();
        createProject.setDisable(disableButtons);

    }
}