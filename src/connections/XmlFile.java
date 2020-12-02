package connections;

import model.IProjectManagementModel;
import model.Project;

public class XmlFile implements IFileConnection {
    private String fileName;
    private static final String FILE_EXTENSION = "xml";

    public XmlFile(String fileName) {
        this.fileName = fileName + "." + FILE_EXTENSION;
    }

    @Override public void setFileName(String fileName) {

    }

    @Override public String getFileName() {
        return null;
    }

    @Override public IProjectManagementModel loadModel() {
        return null;
    }

    @Override public void saveModel(IProjectManagementModel model) {

    }

    @Override public void saveProject(Project project) {

    }
}
