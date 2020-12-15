import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementTest {
    private Project project;
    private Requirement requirement;
    
    @BeforeEach void setUp() {
        project = new Project("Project Title", "ProjectID", new Date(), new Date(1, 1, 2022), Methodology.WATERFALL);
        requirement = new Requirement("User Story", new Date(), new Date(1, 1, 2021), 99, Priority.CRITICAL, Type.FUNCTIONAL, project);
    }
    
    @AfterEach void tearDown() {
    }
    
    @Test void setUserStory() {
        requirement.setUserStory("Some value");
        assertEquals("Some value", requirement.getUserStory());
    }
    
    @Test void setEstimatedTimeNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setEstimatedTime(-1);
        });
    }
}