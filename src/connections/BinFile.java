package connections;

import model.IProjectManagementModel;
import model.Project;

public class BinFile implements IFileConnection {
    private String fileName;
    private static final String FILE_EXTENSION = "bin";

    public BinFile(String fileName) {
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