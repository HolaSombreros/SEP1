package connections;

import model.IProjectManagementModel;

public interface IFileConnection {
    void setFileName(String fileName);
    String getFileName();
    IProjectManagementModel loadModel();
    void saveModel(IProjectManagementModel model);
    void saveProject(Project project);
}
