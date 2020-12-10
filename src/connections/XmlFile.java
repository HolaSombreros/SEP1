package connections;

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
        Scanner sc = new Scanner(file);
        IProjectManagementModel model = new ProjectManagementModelManager();
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created file at [" + file.getAbsolutePath() + "]");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        
        // Project variables:
        String projectId = "";
        String projectTitle = "";
        Methodology methodology = null;
        Date projectStartingDate = null;
        Date projectDeadline = null;
        Status status = null;
        
        
        TeamMember scrumMaster = null;
        TeamMember productOwner = null;
        TeamMemberList projectTeamMemberList = null;
        RequirementList requirementList = null;
        
        // Requirement variables:
        int reqId = 0;
        String userStory = "";
        double reqEstimatedTime = 0;
        double reqHoursWorked = 0;
        Project relatedProject = null;
        TaskList taskList = null;
        TeamMemberList reqTeamMemberList = null;
        TeamMember reqRTM = null;
        Date reqStartDate = null;
        Date reqDeadline = null;
        RequirementStatus reqStatus = null;
        Type type = null;
        Priority priority = null;
        int i = 0;
        
        // Task variables:
        int taskId = 0;
        String taskTitle = "";
        Date taskStartDate = null;
        Date taskDeadline = null;
        double taskEstimatedTime = 0;
        Status taskStatus = null;
        TeamMemberList taskTeamMemberList = null;
        TeamMember taskRTM = null;
        TimeRegistration timeRegistration = null;
        Requirement relatedRequirement = null;
        int j = 0;
        
        // Teammember related:
        String firstName;
        String lastName;
        int id;
        double hoursWorked;
        TeamMember teamMember;
        
        
        
        Project project = null;
        Requirement requirement = null;
        Task task = null;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            
            // Project data:
            if (line.contains("<project>")) {
                line = sc.nextLine();
                line = line.replace("<id>", "");
                line = line.replace("</id>", "");
                projectId = line.trim();
                
                line = sc.nextLine();
                line = line.replace("<title>", "");
                line = line.replace("</title>", "");
                projectTitle = line.trim();
                
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
                String[] temp = line.trim().split("/");
                projectStartingDate = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                
                line = sc.nextLine();
                line = line.replace("<deadline>", "");
                line = line.replace("</deadline>", "");
                temp = line.trim().split("/");
                projectDeadline = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                
                model.addProject(new Project(projectTitle, projectId, projectStartingDate, projectDeadline, methodology));
                project = model.getProjectList().getProjectByID(projectId);
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
                    teamMember = new TeamMember(firstName, lastName, id);
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
                    teamMember = new TeamMember(firstName, lastName, id);
                    project.assignProductOwner(teamMember);
                    
                    sc.nextLine();
                    line = sc.nextLine();
                    line = line.replace("<hoursworked>", "");
                    line = line.replace("</hoursworked>", "");
                    teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                    sc.nextLine();
                    sc.nextLine();
                }
                sc.nextLine();
                sc.nextLine(); // <teammemberlist>
                line = sc.nextLine(); // <teammember> OR </teammemberlist>
                if (line.contains("<teammember>")) {
                    while (sc.hasNext()) {
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
                            teamMember = new TeamMember(firstName, lastName, id);
                            project.assignTeamMember(teamMember);
                            
                            sc.nextLine();
                            line = sc.nextLine();
                            line = line.replace("<hoursworked>", "");
                            line = line.replace("</hoursworked>", "");
                            teamMember.getTimeRegistration().setHoursWorked(Double.parseDouble(line.trim()));
                        }
                        else if (line.contains("</teammemberlist>")) {
                            break;
                        }
                    }
                }
                
                sc.nextLine(); // <requirementlist>
                line = sc.nextLine(); // <requirement> OR </requirementlist>
                if (line.contains("<requirement>")) {
                    while (sc.hasNext()) {
                        line = sc.nextLine();
                        if (line.contains("<id>")) {
                            // do all the stuff, holy fuck yo
                            
                            // eventually create the requirement when hitting '<relatedproject>'
                        }
                        else if (line.contains("</requirementlist>")) {
                            break;
                        }
                    }
                }
                
                // next line will be </project> so go back up
                // will loop through the whole thing again if there are more projects
            }
            
            // return the ENTIRE model.
            // now we just need to somehow overwrite THIS model with the other one...
            return model;
        }
        
        
        
        return null;
    }
    
    @Override public void saveModel(IProjectManagementModel model) throws FileNotFoundException {
        File file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created file at [" + file.getAbsolutePath() + "]");
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
            xml += "      <methodology>" + project.getMethodology() + "</methodology>\n";
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
                xml += "            <relatedproject>" + requirement.getRelatedProject().getID() + "</relatedproject>\n"; // storing the id should be good enough...?
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
                xml += "            <startingdate>" + requirement.getStartingDate().toString() + "</startingdate>\n";
                xml += "            <deadline>" + requirement.getDeadline().toString() + "</deadline>\n";
                xml += "            <status>" + requirement.getStatus().getName() + "</status>\n";
                xml += "            <type>" + requirement.getType().getName() + "</type>\n";
                xml += "            <priority>" + requirement.getPriority().getName() + "</priority>\n";
                xml += "            <tasklist>\n";
                for (Task task : requirement.getTaskList().getTasks()) {
                    xml += "               <task>\n";
                    xml += "                  <id>" + task.getId() + "</id>\n";
                    xml += "                  <title>" + task.getTitle() + "</title>\n";
                    xml += "                  <startingdate>" + task.getStartingDate().toString() + "</startingdate>\n";
                    xml += "                  <deadline>" + task.getDeadline().toString() + "</deadline>\n";
                    xml += "                  <estimatedtime>" + task.getEstimatedTime() + "</estimatedtime>\n";
                    xml += "                  <status>" + task.getStatus().getName() + "</status>\n";
                    xml += "                  <timeregistration>\n";
                    xml += "                     <hoursworked>" + task.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                    xml += "                  </timeregistration>\n";
                    xml += "                  <responsibleteammember>\n";
                    if (task.getResponsibleTeamMember() != null) {
                        xml += "                  <firstname>" + task.getResponsibleTeamMember().getFirstName() + "</firstname>\n";
                        xml += "                  <lastname>" + task.getResponsibleTeamMember().getLastName() + "</lastname>\n";
                        xml += "                  <id>" + task.getResponsibleTeamMember().getId() + "</id>\n";
                        xml += "                  <timeregistration>\n";
                        xml += "                     <hoursworked>" + task.getResponsibleTeamMember().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                        xml += "                  </timeregistration>\n";
                    }
                    xml += "                  </responsibleteammember>\n";
                    xml += "                  <relatedrequirement>" + requirement.getId() + "</relatedrequirement>\n"; // storing the id should be good enough...?
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
        
        System.out.println("Wrote to file at [" + file.getAbsolutePath() + "]"); // TODO - eventually remove...
    }
    
    @Override public void saveProject(Project project) throws FileNotFoundException {
        File file = new File(getFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created file at [" + file.getAbsolutePath() + "]");
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
        xml += "   <methodology>" + project.getMethodology() + "</methodology>\n";
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
        else {
            xml += "   </scrummaster>\n";
        }
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
            xml += "         <relatedproject>" + requirement.getRelatedProject().getID() + "</relatedproject>\n"; // storing the id should be good enough...?
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
                xml += "         </teammember>\n";
            }
            xml += "         </teammemberlist>\n";
            xml += "         <startingdate>" + requirement.getStartingDate().toString() + "</startingdate>\n";
            xml += "         <deadline>" + requirement.getDeadline().toString() + "</deadline>\n";
            xml += "         <status>" + requirement.getStatus().getName() + "</status>\n";
            xml += "         <type>" + requirement.getType().getName() + "</type>\n";
            xml += "         <priority>" + requirement.getPriority().getName() + "</priority>\n";
            xml += "         <tasklist>\n";
            for (Task task : requirement.getTaskList().getTasks()) {
                xml += "            <task>\n";
                xml += "               <id>" + task.getId() + "</id>\n";
                xml += "               <title>" + task.getTitle() + "</title>\n";
                xml += "               <startingdate>" + task.getStartingDate().toString() + "</startingdate>\n";
                xml += "               <deadline>" + task.getDeadline().toString() + "</deadline>\n";
                xml += "               <estimatedtime>" + task.getEstimatedTime() + "</estimatedtime>\n";
                xml += "               <status>" + task.getStatus().getName() + "</status>\n";
                xml += "               <timeregistration>\n";
                xml += "                  <hoursworked>" + task.getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "               </timeregistration>\n";
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
                xml += "               <relatedrequirement>" + requirement.getId() + "</relatedrequirement>\n"; // storing the id should be good enough...?
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
        
        System.out.println("Wrote to file at [" + file.getAbsolutePath() + "]"); // TODO - eventually remove...
    }
}
