package gui;

import model.IProjectManagementModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler
{
  private Scene currentScene;
  private Stage primaryStage;
  private IProjectManagementModel model;
  private ViewState viewState;
  
  // View Controllers:
  private ProjectListController projectListController;
  private RequirementListController requirementListController;
  private TaskListController taskListController;
  private AddProjectController addProjectController;
  private AddRequirementController addRequirementController;
  private AddTaskController addTaskController;
  private DetailsAndEditProjectController detailsAndEditProjectController;
  private DetailsAndEditRequirementController detailsAndEditRequirementController;
  private DetailsAndEditTaskController detailsAndEditTaskController;
  private DetailsTeamMemberController detailsTeamMemberController;
  private ProjectSelectController projectSelectController;
  private RequirementSelectController requirementSelectController;
  private TaskSelectController taskSelectController;

  

  public ViewHandler(IProjectManagementModel model)
  {
    this.currentScene = new Scene(new Region());
    this.model = model;
    this.viewState = new ViewState();
  }

  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView("projectList");
  }

  public void openView(String id)
  {
    Region root = null;
    switch (id)
    {
      case "projectList":
        root = loadProjectList("fxml/ProjectListView.fxml", viewState);
        break;
      case "requirement":
        root = loadRequirementList("fxml/RequirementListView.fxml", viewState);
        break;
      case "taskList":
        root = loadTaskList("fxml/TaskListView.fxml", viewState);
        break;
      case "teamMemberList":
       // root = loadTeamMemberList("fxml/TeamMemberListView.fxml", viewState);
        break;
      case "addProject":
        root = loadAddProject("fxml/AddProjectView.fxml");
        break;
      case "addRequirement":
        root = loadAddRequirement("fxml/AddRequirementView.fxml", viewState);
        break;
      case "addTask":
        root = loadAddTask("fxml/AddTaskView.fxml", viewState);
        break;
      case "detailsAndEditProject":
        root = loadDetailsAndEditProject("fxml/DetailsAndEditProjectView.fxml", viewState);
        break;
      case "detailsAndEditRequirement":
        root = loadDetailsAndEditRequirement("fxml/DetailsAndEditRequirementView.fxml", viewState);
        break;
      case "detailsAndEditTask":
        root = loadDetailsAndEditTask("fxml/DetailsAndEditTaskView.fxml", viewState);
        break;
      case "detailsTeamMember":
        root = loadDetailsTeamMember("fxml/TeamMemberDetailsView.fxml", viewState);
        break;
      case "projectSelect":
        root = loadProjectSelect("fxml/ProjectSelectView.fxml",viewState);
        break;
      case "requirementSelect":
        root = loadRequirementSelect("fxml/RequirementSelectView.fxml",viewState);
        break;
      case "taskSelect":
        root = loadAddTask("fxml/TaskSelectView.fxml",viewState);
        break;
    }
    currentScene.setRoot(root);
    String title = "";
    if (root.getUserData() != null)
      title += root.getUserData();
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());;
    primaryStage.setMinWidth(root.getPrefWidth());
    primaryStage.setMinHeight(root.getPrefHeight());;
    primaryStage.setMaxWidth(root.getPrefWidth());
    primaryStage.setMaxHeight(root.getPrefHeight());
    primaryStage.show();
  }

  public void closeView()
  {
    primaryStage.close();
  }

  private Region loadProjectList(String fxmlFile, ViewState viewState)
  {
    if (projectListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        projectListController = loader.getController();
        projectListController.init(this, model, root, viewState);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      projectListController.reset();
    }
    return projectListController.getRoot();
  }

  private Region loadRequirementList(String fxmlFile, ViewState state)
  {
    if (requirementListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        requirementListController = loader.getController();
        requirementListController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      requirementListController.reset();
    }
    return requirementListController.getRoot();
  }

  private Region loadTaskList(String fxmlFile, ViewState state)
  {
    if (taskListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        taskListController = loader.getController();
        taskListController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      taskListController.reset();
    }
    return taskListController.getRoot();
  }

  private Region loadAddProject(String fxmlFile)
  {
    if (addProjectController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        addProjectController = loader.getController();
        addProjectController.init(this, model, root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      addProjectController.reset();
    }
    return addProjectController.getRoot();
  }

  private Region loadAddRequirement(String fxmlFile, ViewState state)
  {
    if (addRequirementController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        addRequirementController = loader.getController();
        addRequirementController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      addRequirementController.reset();
    }
    return addRequirementController.getRoot();
  }

  private Region loadAddTask(String fxmlFile, ViewState state)
  {
    if (addTaskController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        addTaskController = loader.getController();
        addTaskController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      addTaskController.reset();
    }
    return addTaskController.getRoot();
  }

  private Region loadDetailsAndEditProject(String fxmlFile, ViewState state)
  {
    if (detailsAndEditProjectController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditProjectController = loader.getController();
        detailsAndEditProjectController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      detailsAndEditProjectController.reset();
    }
    return detailsAndEditProjectController.getRoot();
  }

  private Region loadDetailsAndEditRequirement(String fxmlFile, ViewState state)
  {
    if (detailsAndEditRequirementController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditRequirementController = loader.getController();
        detailsAndEditRequirementController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // TODO - uncomment line below
      //detailsAndEditRequirementController.reset();
    }
    return detailsAndEditRequirementController.getRoot();
  }

  private Region loadDetailsAndEditTask(String fxmlFile, ViewState state)
  {
    if (detailsAndEditTaskController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditTaskController = loader.getController();
        detailsAndEditTaskController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      detailsAndEditTaskController.reset();
    }
    return detailsAndEditTaskController.getRoot();
  }

  private Region loadDetailsTeamMember(String fxmlFile, ViewState state)
  {
    if (detailsTeamMemberController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsTeamMemberController = loader.getController();
        detailsTeamMemberController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      detailsTeamMemberController.reset();
    }
    return detailsTeamMemberController.getRoot();
  }
  
private Region loadProjectSelect(String fxmlFile, ViewState state){
  if (projectSelectController == null)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(fxmlFile));
      Region root = loader.load();
      projectSelectController = loader.getController();
      projectSelectController.init(this, model, root, state);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  else
  {
    projectSelectController.reset();
  }
  return projectSelectController.getRoot();
}
private Region loadRequirementSelect(String fxmlFile, ViewState state){
    if (requirementSelectController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        requirementSelectController = loader.getController();
        requirementSelectController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      requirementSelectController.reset();
    }
    return requirementSelectController.getRoot();
  }
private Region loadTaskSelect(String fxmlFile, ViewState state){
    if (taskSelectController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        taskSelectController = loader.getController();
        taskSelectController.init(this, model, root, state);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      taskSelectController.reset();
    }
    return taskSelectController.getRoot();
  }

}