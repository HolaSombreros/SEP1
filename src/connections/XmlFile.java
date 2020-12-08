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
    
    @Override public IProjectManagementModel loadModel() {
//        File file = new File(getFilePath());
//        Scanner sc = new Scanner(file);
//        IProjectManagementModel model = new ProjectManagementModelManager();
//
//        int state = 0;
//
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            if (line.contains("<project>")) {
//                state = 1;
//            }
//
//            if (line.contains("<requirement>")) {
//                state = 2;
//            }
//
//            if (line.contains("<task>")) {
//                state = 3;
//            }
//        }
//
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
            xml += "      <scrummaster>";
            if (project.getScrumMaster() != null) {
                xml += "\n         <firstname>" + project.getScrumMaster().getFirstName() + "</firstname>\n";
                xml += "         <lastname>" + project.getScrumMaster().getLastName() + "</lastname>\n";
                xml += "         <id>" + project.getScrumMaster().getId() + "</id>\n";
                xml += "         <timeregistration>\n";
                xml += "            <hoursworked>" + project.getScrumMaster().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "         </timeregistration>\n";
            }
            xml += "</scrummaster>\n";
            xml += "      <productowner>";
            if (project.getProductOwner() != null) {
                xml += "\n         <firstname>" + project.getProductOwner().getFirstName() + "</firstname>\n";
                xml += "         <lastname>" + project.getProductOwner().getLastName() + "</lastname>\n";
                xml += "         <id>" + project.getProductOwner().getId() + "</id>\n";
                xml += "         <timeregistration>\n";
                xml += "            <hoursworked>" + project.getProductOwner().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
                xml += "         </timeregistration>\n";
            }
            xml += "</productowner>\n";
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
                    xml += "            </teammember>\n";
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
        xml += "   <scrummaster>";
        if (project.getScrumMaster() != null) {
            xml += "\n      <firstname>" + project.getScrumMaster().getFirstName() + "</firstname>\n";
            xml += "      <lastname>" + project.getScrumMaster().getLastName() + "</lastname>\n";
            xml += "      <id>" + project.getScrumMaster().getId() + "</id>\n";
            xml += "      <timeregistration>\n";
            xml += "         <hoursworked>" + project.getScrumMaster().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
            xml += "      </timeregistration>\n";
            xml += "   </scrummaster>\n";
        }
        else {
            xml += "</scrummaster>\n";
        }
        xml += "   <productowner>\n";
        if (project.getProductOwner() != null) {
            xml += "\n      <firstname>" + project.getProductOwner().getFirstName() + "</firstname>\n";
            xml += "      <lastname>" + project.getProductOwner().getLastName() + "</lastname>\n";
            xml += "      <id>" + project.getProductOwner().getId() + "</id>\n";
            xml += "      <timeregistration>\n";
            xml += "         <hoursworked>" + project.getProductOwner().getTimeRegistration().getHoursWorked() + "</hoursworked>\n";
            xml += "      </timeregistration>\n";
        }
        xml += "</productowner>\n";
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
