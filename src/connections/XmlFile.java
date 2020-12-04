package connections;

import model.IProjectManagementModel;
import model.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlFile implements IFileConnection {
    private String fileName;
    private final String filePath = "./src/files/" + getFileName();
    private static final String FILE_EXTENSION = "xml";

    public XmlFile(String fileName) {
        this.fileName = fileName + "." + FILE_EXTENSION;
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
        return null;
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
        for (int i = 0; i < model.getProjectList().size(); i++) {
            xml += "   <project>\n";
            xml += "      <someshit>some data here, right...\n";
            xml += "      <othershit>some other data here...\n";
            xml += "   </project>\n";
        }
        xml += "</projectlist>";
        out.println(xml);
        out.close();
        System.out.println("Wrote to file at [" + file.getAbsolutePath() + "]");
    }

    @Override public void saveProject(Project project) {

    }
}
