package connections;

import model.IProjectManagementModel;
import model.Project;

import java.io.FileNotFoundException;

public interface IFileConnection {
    void setFileName(String fileName);
    String getFileName();
    String getFilePath();
    IProjectManagementModel loadModel();
    void saveModel(IProjectManagementModel model) throws FileNotFoundException;
    void saveProject(Project project);
}
