package connections;

import mediator.IProjectManagementModel;
import model.Project;

import java.io.FileNotFoundException;

public interface IFileConnection {
    void setFileName(String fileName);
    String getFileName();
    String getFilePath();
    IProjectManagementModel loadModel() throws FileNotFoundException;
    void saveModel(IProjectManagementModel model) throws FileNotFoundException;
    void saveProject(Project project) throws FileNotFoundException;
}
