package connections;

import mediator.IProjectManagementModel;
import mediator.ProjectManagementModelManager;
import model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class XmlFile implements IFileConnection {
    private String fileName;
    private String filePath = "src/files/";
    private static final String FILE_EXTENSION = "xml";
    
    public XmlFile(String fileName) {
        this.fileName = fileName + "." + FILE_EXTENSION;
        this.filePath += getFileName();
    }
    
    @Override public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override public String getFileName() {
        return fileName;
    }
    
    @Override public String getFilePath() {
        return filePath;
    }
    
    @Override public IProjectManagementModel loadModel() throws FileNotFoundException {
        File file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scanner sc = new Scanner(file);
        IProjectManagementModel model = new ProjectManagementModelManager();
        TeamMemberList teamMemberList = model.getTeam();
        
        String projectId = "";
        String title = "";
        Methodology methodology = null;
        Date startingDate = null;
        Date deadline = null;
        String temp[] = null; // for the dates
        Status status = null;
        double estimatedTime = 0;
        double hoursWorked = 0;
        RequirementStatus requirementStatus = null;
        Type type = null;
        Priority priority = null;
        String firstName;
        String lastName;
        int id;
        TeamMember teamMember;
        Project project = null;
        Requirement requirement = null;
        Task task = null;
        String line = "";
        
        while (sc.hasNext()) {
            line = sc.nextLine();
            if (line.contains("<project>")) {
                line = sc.nextLine();
                line = line.replace("<id>", "");
                line = line.replace("</id>", "");
                projectId = line.trim();
    
                line = sc.nextLine();
                line = line.replace("<title>", "");
                line = line.replace("</title>", "");
                title = line.trim();
    
                line = sc.nextLine();
                line = line.replace("<status>", "");
                line = line.replace("</status>", "");
                switch (line.trim()) {
                    case "Not Started":
                        status = Status.NOT_STARTED;
                        break;
                    case "Started":
                        status = Status.STARTED;
                        break;
                    case "Ended":
                        status = Status.ENDED;
                        break;
                }
    
                line = sc.nextLine();
                line = line.replace("<methodology>", "");
                line = line.replace("</methodology>", "");
                switch (line.trim()) {
                    case "Scrum":
                        methodology = Methodology.SCRUM;
                        break;
                    case "Waterfall":
                        methodology = Methodology.WATERFALL;
                        break;
                }
    
                line = sc.nextLine();
                line = line.replace("<startingdate>", "");
                line = line.replace("</startingdate>", "");
                temp = line.trim().split("/");
                startingDate = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                line = sc.nextLine();
                line = line.replace("<deadline>", "");
                line = line.replace("</deadline>", "");
                temp = line.trim().split("/");
                deadline = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                project = new Project(title, projectId, startingDate, deadline, methodology);
                model.addProject(project, false);
                project.setStatus(status);
    
                sc.nextLine(); // <scrummaster>
                line = sc.nextLine(); // </scrummaster> OR <firstname>
                if (line.contains("<firstname>")) {
                    line = line.replace("<firstname>", "");
                    line = line.replace("</firstname>", "");
                    firstName = line.trim();
                    line = sc.nextLine();
                    line = line.replace("<lastname>", "");
                    line = line.replace("</lastname>", "");
                    lastName = line.trim();
                    line = sc.nextLine();
                    line = line.replace("<id>", "");
                    line = line.replace("</id>", "");
                    id = Integer.parseInt(line.trim());
                    teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                    project.assignScrumMaster(teamMember);
    
                    sc.nextLine();
                    line = sc.nextLine();
                    line = line.replace("<hoursworked>", "");
                    line = line.replace("</hoursworked>", "");
                    teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                    sc.nextLine();
                    sc.nextLine();
                }
                sc.nextLine(); // <productowner>
                line = sc.nextLine(); // </productowner> OR <firstname>
                if (line.contains("<firstname>")) {
                    line = line.replace("<firstname>", "");
                    line = line.replace("</firstname>", "");
                    firstName = line.trim();
                    line = sc.nextLine();
                    line = line.replace("<lastname>", "");
                    line = line.replace("</lastname>", "");
                    lastName = line.trim();
                    line = sc.nextLine();
                    line = line.replace("<id>", "");
                    line = line.replace("</id>", "");
                    id = Integer.parseInt(line.trim());
                    teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                    project.assignProductOwner(teamMember);
    
                    sc.nextLine();
                    line = sc.nextLine();
                    line = line.replace("<hoursworked>", "");
                    line = line.replace("</hoursworked>", "");
                    teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                    sc.nextLine();
                    sc.nextLine();
                }
                sc.nextLine(); // <teammemberlist>
                while (sc.hasNext()) {
                    line = sc.nextLine(); // <teammember> OR </teammemberlist>
                    if (line.contains("<teammember>")) {
                        line = sc.nextLine();
                        line = line.replace("<firstname>", "");
                        line = line.replace("</firstname>", "");
                        firstName = line.trim();
                        line = sc.nextLine();
                        line = line.replace("<lastname>", "");
                        line = line.replace("</lastname>", "");
                        lastName = line.trim();
                        line = sc.nextLine();
                        line = line.replace("<id>", "");
                        line = line.replace("</id>", "");
                        id = Integer.parseInt(line.trim());
                        teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                        project.assignTeamMember(teamMember);
            
                        sc.nextLine();
                        line = sc.nextLine();
                        line = line.replace("<hoursworked>", "");
                        line = line.replace("</hoursworked>", "");
                        teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                        sc.nextLine();
                        sc.nextLine();
                    }
                    else if (line.contains("</teammemberlist>")) {
                        break;
                    }
                }
                
                sc.nextLine(); // <requirementlist>
                while (sc.hasNext()) {
                    line = sc.nextLine(); // <requirement> OR </requirementlist>
                    if (line.contains("<requirement>")) {
                        line = sc.nextLine();
                        line = line.replace("<id>", "");
                        line = line.replace("</id>", "");
                        id = Integer.parseInt(line.trim());
    
                        line = sc.nextLine();
                        line = line.replace("<userstory>", "");
                        line = line.replace("</userstory>", "");
                        title = line.trim();
    
                        line = sc.nextLine();
                        line = line.replace("<estimatedtime>", "");
                        line = line.replace("</estimatedtime>", "");
                        estimatedTime = Double.parseDouble(line.trim());
    
                        sc.nextLine();
//                        line = sc.nextLine();
//                        line = line.replace("<hoursworked>", "");
//                        line = line.replace("</hoursworked>", "");
//                        hoursWorked = Double.parseDouble(line.trim());
    
                        line = sc.nextLine();
                        line = line.replace("<startingdate>", "");
                        line = line.replace("</startingdate>", "");
                        temp = line.trim().split("/");
                        startingDate = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                        line = sc.nextLine();
                        line = line.replace("<deadline>", "");
                        line = line.replace("</deadline>", "");
                        temp = line.trim().split("/");
                        deadline = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                        line = sc.nextLine();
                        line = line.replace("<status>", "");
                        line = line.replace("</status>", "");
                        switch (line.trim()) {
                            case "Not Started":
                                requirementStatus = RequirementStatus.NOT_STARTED;
                                break;
                            case "Started":
                                requirementStatus = RequirementStatus.STARTED;
                                break;
                            case "Ended":
                                requirementStatus = RequirementStatus.ENDED;
                                break;
                            case "Approved":
                                requirementStatus = RequirementStatus.APPROVED;
                                break;
                            case "Rejected":
                                requirementStatus = RequirementStatus.REJECTED;
                                break;
                        }
    
                        line = sc.nextLine();
                        line = line.replace("<type>", "");
                        line = line.replace("</type>", "");
                        switch (line.trim()) {
                            case "Functional":
                                type = Type.FUNCTIONAL;
                                break;
                            case "Non Functional":
                                type = Type.NON_FUNCTIONAL;
                                break;
                            case "Project Related":
                                type = Type.PROJECT_RELATED;
                                break;
                        }
    
                        line = sc.nextLine();
                        line = line.replace("<priority>", "");
                        line = line.replace("</priority>", "");
                        switch (line.trim()) {
                            case "Critical":
                                priority = Priority.CRITICAL;
                                break;
                            case "High":
                                priority = Priority.HIGH;
                                break;
                            case "Low":
                                priority = Priority.LOW;
                                break;
                        }
    
                        sc.nextLine(); // <relatedproject></relatedproject>
                        requirement = new Requirement(title, startingDate, deadline, estimatedTime, priority, type, project);
                        model.addRequirement(project, requirement, false);
                        requirement.setId(id);
                        requirement.setStatus(requirementStatus);
    
                        sc.nextLine(); // <responsibleteammember>
                        line = sc.nextLine(); // </responsibleteammember> OR <firstname>
                        if (line.contains("<firstname>")) {
                            line = line.replace("<firstname>", "");
                            line = line.replace("</firstname>", "");
                            firstName = line.trim();
                            line = sc.nextLine();
                            line = line.replace("<lastname>", "");
                            line = line.replace("</lastname>", "");
                            lastName = line.trim();
                            line = sc.nextLine();
                            line = line.replace("<id>", "");
                            line = line.replace("</id>", "");
                            id = Integer.parseInt(line.trim());
                            teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                            requirement.assignResponsibleTeamMember(teamMember);
    
                            sc.nextLine();
                            line = sc.nextLine();
                            line = line.replace("<hoursworked>", "");
                            line = line.replace("</hoursworked>", "");
                            teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                            sc.nextLine();
                            sc.nextLine();
                        }
    
                        sc.nextLine(); // <teammemberlist>
                        while (sc.hasNext()) {
                            line = sc.nextLine(); // <teammember> OR </teammemberlist>
                            if (line.contains("<teammember>")) {
                                line = sc.nextLine();
                                line = line.replace("<firstname>", "");
                                line = line.replace("</firstname>", "");
                                firstName = line.trim();
                                line = sc.nextLine();
                                line = line.replace("<lastname>", "");
                                line = line.replace("</lastname>", "");
                                lastName = line.trim();
                                line = sc.nextLine();
                                line = line.replace("<id>", "");
                                line = line.replace("</id>", "");
                                id = Integer.parseInt(line.trim());
                                teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                                requirement.assignTeamMember(teamMember);

                                sc.nextLine();
                                line = sc.nextLine();
                                line = line.replace("<hoursworked>", "");
                                line = line.replace("</hoursworked>", "");
                                teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                                sc.nextLine();
                                sc.nextLine();
                            }
                            else if (line.contains("</teammemberlist>")) {
                                break;
                            }
                        }
    
                        sc.nextLine(); // <tasklist>
                        while (sc.hasNext()) {
                            line = sc.nextLine(); // <task> OR </tasklist>
                            if (line.contains("<task>")) {
                                line = sc.nextLine();
                                line = line.replace("<id>", "");
                                line = line.replace("</id>", "");
                                id = Integer.parseInt(line.trim());
    
                                line = sc.nextLine();
                                line = line.replace("<title>", "");
                                line = line.replace("</title>", "");
                                title = line.trim();
    
                                line = sc.nextLine();
                                line = line.replace("<estimatedtime>", "");
                                line = line.replace("</estimatedtime>", "");
                                estimatedTime = Double.parseDouble(line.trim());
    
                                line = sc.nextLine();
                                line = line.replace("<startingdate>", "");
                                line = line.replace("</startingdate>", "");
                                temp = line.trim().split("/");
                                startingDate = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                                line = sc.nextLine();
                                line = line.replace("<deadline>", "");
                                line = line.replace("</deadline>", "");
                                temp = line.trim().split("/");
                                deadline = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
    
                                line = sc.nextLine();
                                line = line.replace("<status>", "");
                                line = line.replace("</status>", "");
                                switch (line.trim()) {
                                    case "Not Started":
                                        status = Status.NOT_STARTED;
                                        break;
                                    case "Started":
                                        status = Status.STARTED;
                                        break;
                                    case "Ended":
                                        status = Status.ENDED;
                                        break;
                                }
    
                                sc.nextLine(); // <timeregistration>
                                line = sc.nextLine();
                                line = line.replace("<hoursworked>", "");
                                line = line.replace("</hoursworked>", "");
                                hoursWorked = Double.parseDouble(line.trim());
                                sc.nextLine(); // </timeregistration>
    
                                sc.nextLine(); // <relatedrequirement></relatedrequirement>
                                task = new Task(title, startingDate, deadline, estimatedTime, requirement);
                                model.addTask(requirement, task, false);
                                task.setId(id);
                                task.setStatus(status);
                                task.getTimeRegistration().setHoursWorked(hoursWorked);
    
                                sc.nextLine(); // <responsibleteammember>
                                line = sc.nextLine(); // </responsibleteammember> OR <firstname>
                                if (line.contains("<firstname>")) {
                                    line = line.replace("<firstname>", "");
                                    line = line.replace("</firstname>", "");
                                    firstName = line.trim();
                                    line = sc.nextLine();
                                    line = line.replace("<lastname>", "");
                                    line = line.replace("</lastname>", "");
                                    lastName = line.trim();
                                    line = sc.nextLine();
                                    line = line.replace("<id>", "");
                                    line = line.replace("</id>", "");
                                    id = Integer.parseInt(line.trim());
                                    teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                                    task.assignResponsibleTeamMember(teamMember);
        
                                    sc.nextLine();
                                    line = sc.nextLine();
                                    line = line.replace("<hoursworked>", "");
                                    line = line.replace("</hoursworked>", "");
                                    teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                                    sc.nextLine();
                                    sc.nextLine();
                                }
                                
                                sc.nextLine(); // <teammemberlist>
                                while (sc.hasNext()) {
                                    line = sc.nextLine(); // <teammember> OR </teammemberlist>
                                    if (line.contains("<teammember>")) {
                                        line = sc.nextLine();
                                        if (line.contains("<firstname>")) {
                                            line = line.replace("<firstname>", "");
                                            line = line.replace("</firstname>", "");
                                            firstName = line.trim();
                                            line = sc.nextLine();
                                            line = line.replace("<lastname>", "");
                                            line = line.replace("</lastname>", "");
                                            lastName = line.trim();
                                            line = sc.nextLine();
                                            line = line.replace("<id>", "");
                                            line = line.replace("</id>", "");
                                            id = Integer.parseInt(line.trim());
                                            teamMember = teamMemberList.getByID(id);// new TeamMember(firstName, lastName, id);
                                            task.assignTeamMember(teamMember);
                                            sc.nextLine();
                                            line = sc.nextLine();
                                            line = line.replace("<hoursworked>", "");
                                            line = line.replace("</hoursworked>", "");
                                            teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                                            sc.nextLine();
                                            sc.nextLine();
                                        }
                                    }
                                    else if (line.contains("</teammemberlist>")) {
                                        break;
                                    }
                                }
                                sc.nextLine(); // </task>
                            }
                            else if (line.contains("</tasklist>")) {
                                break;
                            }
                        }
                        sc.nextLine(); // </requirement>
                    }
                    else if (line.contains("</requirementlist>")) {
                        break;
                    }
                }
                sc.nextLine(); // </project>
            }
            else if (line.contains("</projectlist>")) {
                break;
            }
        }
        return model;
    }
    
    @Override public void saveModel(IProjectManagementModel model) throws FileNotFoundException {
        File file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = new PrintWriter(file);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
        xml += "<projectlist>\n";
        for (Project project : model.getProjectList().getProjects()) {
            xml += "   <project>\n";
            xml += "      <id>" + project.getID() + "</id>\n";
            xml += "      <title>" + project.getName() + "</title>\n";
            xml += "      <status>" + project.getStatus().getName() + "</status>\n";
            xml += "      <methodology>" + project.getMethodology().getMethodology() + "</methodology>\n";
            xml += "      <startingdate>" + project.getStartingDate().toString() + "</startingdate>\n";
            xml += "      <deadline>" + project.getDeadline().toString() + "</deadline>\n";
            xml += "      <scrummaster>\n";
            if (project.getScrumMaster() != null) {
                xml += "         <firstname>" + project.getScrumMaster().getFirstName() + "</firstname>\n";
                xml += "         <lastname>" + project.getScrumMaster().getLastName() + "</lastname>\n";
                xml += "         <id>" + project.getScrumMaster().getId() + "</id>\n";
                xml += "         <timeregistration>\n";
                xml += "            <hoursworked>" + project.getScrumMaster().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "         </timeregistration>\n";
            }
            xml += "      </scrummaster>\n";
            xml += "      <productowner>\n";
            if (project.getProductOwner() != null) {
                xml += "         <firstname>" + project.getProductOwner().getFirstName() + "</firstname>\n";
                xml += "         <lastname>" + project.getProductOwner().getLastName() + "</lastname>\n";
                xml += "         <id>" + project.getProductOwner().getId() + "</id>\n";
                xml += "         <timeregistration>\n";
                xml += "            <hoursworked>" + project.getProductOwner().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "         </timeregistration>\n";
            }
            xml += "      </productowner>\n";
            xml += "      <teammemberlist>\n";
            for (TeamMember teamMember : project.getTeamMemberList().getTeamMembers()) {
                xml += "         <teammember>\n";
                xml += "            <firstname>" + teamMember.getFirstName() + "</firstname>\n";
                xml += "            <lastname>" + teamMember.getLastName() + "</lastname>\n";
                xml += "            <id>" + teamMember.getId() + "</id>\n";
                xml += "            <timeregistration>\n";
                xml += "               <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "            </timeregistration>\n";
                xml += "         </teammember>\n";
            }
            xml += "      </teammemberlist>\n";
            xml += "      <requirementlist>\n";
            for (Requirement requirement : project.getProjectRequirementList().getRequirements()) {
                xml += "         <requirement>\n";
                xml += "            <id>" + requirement.getId() + "</id>\n";
                xml += "            <userstory>" + requirement.getUserStory() + "</userstory>\n";
                xml += "            <estimatedtime>" + requirement.getEstimatedTime() + "</estimatedtime>\n";
                xml += "            <hoursworked>" + requirement.getHoursWorked() + "</hoursworked>\n";
                xml += "            <startingdate>" + requirement.getStartingDate().toString() + "</startingdate>\n";
                xml += "            <deadline>" + requirement.getDeadline().toString() + "</deadline>\n";
                xml += "            <status>" + requirement.getStatus().getName() + "</status>\n";
                xml += "            <type>" + requirement.getType().getName() + "</type>\n";
                xml += "            <priority>" + requirement.getPriority().getName() + "</priority>\n";
                xml += "            <relatedproject>" + requirement.getRelatedProject().getID() + "</relatedproject>\n";
                xml += "            <responsibleteammember>\n";
                if (requirement.getResponsibleTeamMember() != null) {
                    xml += "               <firstname>" + requirement.getResponsibleTeamMember().getFirstName() + "</firstname>\n";
                    xml += "               <lastname>" + requirement.getResponsibleTeamMember().getLastName() + "</lastname>\n";
                    xml += "               <id>" + requirement.getResponsibleTeamMember().getId() + "</id>\n";
                    xml += "               <timeregistration>\n";
                    xml += "                  <hoursworked>" + requirement.getResponsibleTeamMember().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "               </timeregistration>\n";
                }
                xml += "            </responsibleteammember>\n";
                xml += "            <teammemberlist>\n";
                for (TeamMember teamMember : requirement.getTeamMemberList().getTeamMembers()) {
                    xml += "               <teammember>\n";
                    xml += "                  <firstname>" + teamMember.getFirstName() + "</firstname>\n";
                    xml += "                  <lastname>" + teamMember.getLastName() + "</lastname>\n";
                    xml += "                  <id>" + teamMember.getId() + "</id>\n";
                    xml += "                  <timeregistration>\n";
                    xml += "                     <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "                  </timeregistration>\n";
                    xml += "               </teammember>\n";
                }
                xml += "            </teammemberlist>\n";
                xml += "            <tasklist>\n";
                for (Task task : requirement.getTaskList().getTasks()) {
                    xml += "               <task>\n";
                    xml += "                  <id>" + task.getId() + "</id>\n";
                    xml += "                  <title>" + task.getTitle() + "</title>\n";
                    xml += "                  <estimatedtime>" + task.getEstimatedTime() + "</estimatedtime>\n";
                    xml += "                  <startingdate>" + task.getStartingDate().toString() + "</startingdate>\n";
                    xml += "                  <deadline>" + task.getDeadline().toString() + "</deadline>\n";
                    xml += "                  <status>" + task.getStatus().getName() + "</status>\n";
                    xml += "                  <timeregistration>\n";
                    xml += "                     <hoursworked>" + task.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "                  </timeregistration>\n";
                    xml += "                  <relatedrequirement>" + requirement.getId() + "</relatedrequirement>\n";
                    xml += "                  <responsibleteammember>\n";
                    if (task.getResponsibleTeamMember() != null) {
                        xml += "                     <firstname>" + task.getResponsibleTeamMember().getFirstName() + "</firstname>\n";
                        xml += "                     <lastname>" + task.getResponsibleTeamMember().getLastName() + "</lastname>\n";
                        xml += "                     <id>" + task.getResponsibleTeamMember().getId() + "</id>\n";
                        xml += "                     <timeregistration>\n";
                        xml += "                        <hoursworked>" + task.getResponsibleTeamMember().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                        xml += "                     </timeregistration>\n";
                    }
                    xml += "                  </responsibleteammember>\n";
                    xml += "                  <teammemberlist>\n";
                    for (TeamMember teamMember : task.getTeamMemberList().getTeamMembers()) {
                        xml += "                     <teammember>\n";
                        xml += "                        <firstname>" + teamMember.getFirstName() + "</firstname>\n";
                        xml += "                        <lastname>" + teamMember.getLastName() + "</lastname>\n";
                        xml += "                        <id>" + teamMember.getId() + "</id>\n";
                        xml += "                        <timeregistration>\n";
                        xml += "                           <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                        xml += "                        </timeregistration>\n";
                        xml += "                     </teammember>\n";
                    }
                    xml += "                  </teammemberlist>\n";
                    xml += "               </task>\n";
                }
                xml += "            </tasklist>\n";
                xml += "         </requirement>\n";
            }
            xml += "      </requirementlist>\n";
            xml += "   </project>\n";
        }
        xml += "</projectlist>\n";
        out.println(xml);
        out.close();
    }
    
    @Override public void saveProject(Project project) throws FileNotFoundException {
        File file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = new PrintWriter(file);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
    
        xml += "<project>\n";
        xml += "   <id>" + project.getID() + "</id>\n";
        xml += "   <title>" + project.getName() + "</title>\n";
        xml += "   <status>" + project.getStatus().getName() + "</status>\n";
        xml += "   <methodology>" + project.getMethodology().getMethodology() + "</methodology>\n";
        xml += "   <startingdate>" + project.getStartingDate().toString() + "</startingdate>\n";
        xml += "   <deadline>" + project.getDeadline().toString() + "</deadline>\n";
        xml += "   <scrummaster>\n";
        if (project.getScrumMaster() != null) {
            xml += "      <firstname>" + project.getScrumMaster().getFirstName() + "</firstname>\n";
            xml += "      <lastname>" + project.getScrumMaster().getLastName() + "</lastname>\n";
            xml += "      <id>" + project.getScrumMaster().getId() + "</id>\n";
            xml += "      <timeregistration>\n";
            xml += "         <hoursworked>" + project.getScrumMaster().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
            xml += "      </timeregistration>\n";
        }
        xml += "   </scrummaster>\n";
        xml += "   <productowner>\n";
        if (project.getProductOwner() != null) {
            xml += "      <firstname>" + project.getProductOwner().getFirstName() + "</firstname>\n";
            xml += "      <lastname>" + project.getProductOwner().getLastName() + "</lastname>\n";
            xml += "      <id>" + project.getProductOwner().getId() + "</id>\n";
            xml += "      <timeregistration>\n";
            xml += "         <hoursworked>" + project.getProductOwner().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
            xml += "      </timeregistration>\n";
        }
        xml += "   </productowner>\n";
        xml += "   <teammemberlist>\n";
        for (TeamMember teamMember : project.getTeamMemberList().getTeamMembers()) {
            xml += "      <teammember>\n";
            xml += "         <firstname>" + teamMember.getFirstName() + "</firstname>\n";
            xml += "         <lastname>" + teamMember.getLastName() + "</lastname>\n";
            xml += "         <id>" + teamMember.getId() + "</id>\n";
            xml += "         <timeregistration>\n";
            xml += "            <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
            xml += "         </timeregistration>\n";
            xml += "      </teammember>\n";
        }
        xml += "   </teammemberlist>\n";
        xml += "   <requirementlist>\n";
        for (Requirement requirement : project.getProjectRequirementList().getRequirements()) {
            xml += "      <requirement>\n";
            xml += "         <id>" + requirement.getId() + "</id>\n";
            xml += "         <userstory>" + requirement.getUserStory() + "</userstory>\n";
            xml += "         <estimatedtime>" + requirement.getEstimatedTime() + "</estimatedtime>\n";
            xml += "         <hoursworked>" + requirement.getHoursWorked() + "</hoursworked>\n";
            xml += "         <startingdate>" + requirement.getStartingDate().toString() + "</startingdate>\n";
            xml += "         <deadline>" + requirement.getDeadline().toString() + "</deadline>\n";
            xml += "         <status>" + requirement.getStatus().getName() + "</status>\n";
            xml += "         <type>" + requirement.getType().getName() + "</type>\n";
            xml += "         <priority>" + requirement.getPriority().getName() + "</priority>\n";
            xml += "         <relatedproject>" + requirement.getRelatedProject().getID() + "</relatedproject>\n";
            xml += "         <responsibleteammember>\n";
            if (requirement.getResponsibleTeamMember() != null) {
                xml += "            <firstname>" + requirement.getResponsibleTeamMember().getFirstName() + "</firstname>\n";
                xml += "            <lastname>" + requirement.getResponsibleTeamMember().getLastName() + "</lastname>\n";
                xml += "            <id>" + requirement.getResponsibleTeamMember().getId() + "</id>\n";
                xml += "            <timeregistration>\n";
                xml += "               <hoursworked>" + requirement.getResponsibleTeamMember().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "            </timeregistration>\n";
            }
            xml += "         </responsibleteammember>\n";
            xml += "         <teammemberlist>\n";
            for (TeamMember teamMember : requirement.getTeamMemberList().getTeamMembers()) {
                xml += "            <teammember>\n";
                xml += "               <firstname>" + teamMember.getFirstName() + "</firstname>\n";
                xml += "               <lastname>" + teamMember.getLastName() + "</lastname>\n";
                xml += "               <id>" + teamMember.getId() + "</id>\n";
                xml += "               <timeregistration>\n";
                xml += "                  <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "               </timeregistration>\n";
                xml += "            </teammember>\n";
            }
            xml += "         </teammemberlist>\n";
            xml += "         <tasklist>\n";
            for (Task task : requirement.getTaskList().getTasks()) {
                xml += "            <task>\n";
                xml += "               <id>" + task.getId() + "</id>\n";
                xml += "               <title>" + task.getTitle() + "</title>\n";
                xml += "               <estimatedtime>" + task.getEstimatedTime() + "</estimatedtime>\n";
                xml += "               <startingdate>" + task.getStartingDate().toString() + "</startingdate>\n";
                xml += "               <deadline>" + task.getDeadline().toString() + "</deadline>\n";
                xml += "               <status>" + task.getStatus().getName() + "</status>\n";
                xml += "               <timeregistration>\n";
                xml += "                  <hoursworked>" + task.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "               </timeregistration>\n";
                xml += "               <relatedrequirement>" + requirement.getId() + "</relatedrequirement>\n";
                xml += "               <responsibleteammember>\n";
                if (task.getResponsibleTeamMember() != null) {
                    xml += "                  <firstname>" + task.getResponsibleTeamMember().getFirstName() + "</firstname>\n";
                    xml += "                  <lastname>" + task.getResponsibleTeamMember().getLastName() + "</lastname>\n";
                    xml += "                  <id>" + task.getResponsibleTeamMember().getId() + "</id>\n";
                    xml += "                  <timeregistration>\n";
                    xml += "                     <hoursworked>" + task.getResponsibleTeamMember().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "                  </timeregistration>\n";
                }
                xml += "               </responsibleteammember>\n";
                xml += "               <teammemberlist>\n";
                for (TeamMember teamMember : task.getTeamMemberList().getTeamMembers()) {
                    xml += "                  <teammember>\n";
                    xml += "                     <firstname>" + teamMember.getFirstName() + "</firstname>\n";
                    xml += "                     <lastname>" + teamMember.getLastName() + "</lastname>\n";
                    xml += "                     <id>" + teamMember.getId() + "</id>\n";
                    xml += "                     <timeregistration>\n";
                    xml += "                        <hoursworked>" + teamMember.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "                     </timeregistration>\n";
                    xml += "                  </teammember>\n";
                }
                xml += "               </teammemberlist>\n";
                xml += "            </task>\n";
            }
            xml += "         </tasklist>\n";
            xml += "      </requirement>\n";
        }
        xml += "   </requirementlist>\n";
        xml += "</project>\n";
        
        out.println(xml);
        out.close();
    }
}
