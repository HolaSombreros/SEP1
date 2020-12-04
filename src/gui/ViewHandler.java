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
  private ProjectListController projectListController;
  private RequirementListController requirementListController;
  private TaskListController taskListController;
  //private TeamMemberListController teamMemberListController;
  private AddProjectController addProjectController;
  private AddRequirementController addRequirementController;
  private AddTaskController addTaskController;
  private DetailsAndEditProjectController detailsAndEditProjectController;
  private DetailsAndEditRequirementController detailsAndEditRequirementController;
  private DetailsAndEditTaskController detailsAndEditTaskController;
  //private DetailsTeamMemberController detailsTeamMemberController;
  //private AssignAndUnassignTeamMemberController assignAndUnassignTeamMemberController;

  public ViewHandler(IProjectManagementModel model)
  {
    this.currentScene = new Scene(new Region());
    this.model = model;
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
        root = loadProjectList("fxml/ProjectListView.fxml");
        break;
      case "requirement":
      //  root = loadRequirementList("fxml/RequirementListView.fxml");
        break;
      case "taskList":
        root = loadTaskList("fxml/TaskListView.fxml");
        break;
      case "teamMemberList":
       // root = loadTeamMemberList("fxml/TeamMemberListView.fxml");
        break;
      case "addProject":
        root = loadAddProject("fxml/AddProjectView.fxml");
        break;
      case "addRequirement":
       // root = loadAddRequirement("fxml/AddRequirementView.fxml");
        break;
      case "addTask":
        root = loadAddTask("fxml/AddTaskView.fxml");
        break;
      case "detailsAndEditProject":
        root = loadDetailsAndEditProject("fxml/DetailsAndEditProjectView.fxml");
        break;
      case "detailsAndEditRequirement":
       //root = loadDetailsAndEditRequirement("fxml/DetailsAndEditRequirementView.fxml");
        break;
      case "detailsAndEditTask":
        root = loadDetailsAndEditTask("fxml/DetailsAndEditTaskView.fxml");
        break;
      case "detailsTeamMember":
        //root = loadDetailsTeamMember("fxml/DetailsTeamMemberView.fxml");
        break;
      case "assignAndUnassignTeamMember":
        //root = loadAssignAndUnassignTeamMember("fxml/AssignAndUnassignTeamMemberView.fxml");
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

  private Region loadProjectList(String fxmlFile)
  {
    if (projectListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        projectListController = loader.getController();
        projectListController.init(this, model, root);
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

  /*private Region loadRequirementList(String fxmlFile)
  {
    if (requirementListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        requirementListController = loader.getController();
        requirementListController.init(this, model, root);
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
*/
  private Region loadTaskList(String fxmlFile)
  {
    if (taskListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        taskListController = loader.getController();
        taskListController.init(this, model, root);
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
/*
  private Region loadTeamMemberList(String fxmlFile)
  {
    if (teamMemberListController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        teamMemberListController = loader.getController();
        teamMemberListController.init(this, model, root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      teamMemberListController.reset();
    }
    return teamMemberListController.getRoot();
  } */

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

  /*private Region loadAddRequirement(String fxmlFile)
  {
    if (addRequirementController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        addRequirementController = loader.getController();
        addRequirementController.init(this, model, root);
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
*/
  private Region loadAddTask(String fxmlFile)
  {
    if (addTaskController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        addTaskController = loader.getController();
        addTaskController.init(this, model, root);
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

  private Region loadDetailsAndEditProject(String fxmlFile)
  {
    if (detailsAndEditProjectController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditProjectController = loader.getController();
        detailsAndEditProjectController.init(this, model, root);
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
/*
  private Region loadDetailsAndEditRequirement(String fxmlFile)
  {
    if (detailsAndEditRequirementController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditRequirementController = loader.getController();
        detailsAndEditRequirementController.init(this, model, root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      detailsAndEditRequirementController.reset();
    }
    return detailsAndEditRequirementController.getRoot();
  }
*/
  private Region loadDetailsAndEditTask(String fxmlFile)
  {
    if (detailsAndEditTaskController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsAndEditTaskController = loader.getController();
        detailsAndEditTaskController.init(this, model, root);
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
/*
  private Region loadDetailsTeamMember(String fxmlFile)
  {
    if (detailsTeamMemberController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        detailsTeamMemberController = loader.getController();
        detailsTeamMemberController.init(this, model, root);
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

  private Region loadAssignAndUnassignTeamMember(String fxmlFile)
  {
    if (assignAndUnassignTeamMemberController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        assignAndUnassignTeamMemberController = loader.getController();
        assignAndUnassignTeamMemberController.init(this, model, root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      assignAndUnassignTeamMemberController.reset();
    }
    return assignAndUnassignTeamMemberController.getRoot();
  }
*/

}