import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementTest {
    private Project project;
    private Requirement requirement;
    private Task task;
    private TeamMember teamMember;
    
    @BeforeEach void setUp() {
        project = new Project("Project Title", "ProjectID", new Date(), new Date(1, 1, 2022), Methodology.WATERFALL);
        requirement = new Requirement("User Story", new Date(), new Date(1, 1, 2022), 99, Priority.CRITICAL, Type.FUNCTIONAL, project);
        task = new Task("Title", new Date(), new Date(1, 1, 2022), 99, requirement);
        requirement.addTask(task);
        
        teamMember = new TeamMember("Team", "Member", 1);
        requirement.assignTeamMember(teamMember);
    }
    
    @AfterEach void tearDown() {
    }
    
    // Id:
    @Test void setId() {
        requirement.setId(1);
        assertEquals(1, requirement.getId());
    
        requirement.setId(45);
        assertEquals(45, requirement.getId());
    }
    
    @Test void setIdZero() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setId(0);
        });
    }
    
    @Test void setIdNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setId(-44);
        });
        
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setId(-2);
        });
    }
    
    // User story:
    @Test void setUserStory() {
        requirement.setUserStory("Some value");
        assertEquals("Some value", requirement.getUserStory());
    
        requirement.setUserStory("some other Value");
        assertEquals("some other Value", requirement.getUserStory());
    }
    
    @Test void setUserStoryEmpty() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setUserStory("");
        });
    }
    
    @Test void setUserStoryNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setUserStory(null);
        });
    }
    
    // Starting date:
    @Test void setStartingDate() {
        Date date = new Date(1, 1, 2021);
        requirement.setStartingDate(date);
        assertEquals(date.toString(), requirement.getStartingDate().toString());
        
        date.set(3, 4, 2021);
        requirement.setStartingDate(date);
        assertEquals(date.toString(), requirement.getStartingDate().toString());
    }
    
    @Test void setStartingDateBeforeProjectStart() {
        Date date = new Date(1, 1, 2020);
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setStartingDate(date);
        });
    }
    
    @Test void setStartingDateAfterProjectEnd() {
        Date date = new Date(1, 1, 2023);
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setStartingDate(date);
        });
    }
    
    @Test void setStartingDateNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setStartingDate(null);
        });
    }
    
    // Deadline:
    @Test void setDeadline() {
        Date date = new Date(1, 1, 2021);
        requirement.setDeadline(date);
        assertEquals(date.toString(), requirement.getDeadline().toString());
    }
    
    @Test void setDeadlineAfterProjectEnd() {
        Date date = new Date(1, 1, 2023);
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setDeadline(date);
        });
    }
    
    @Test void setDeadlineNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setDeadline(null);
        });
    }
    
    // Estimated time:
    @Test void setEstimatedTime() {
        requirement.setEstimatedTime(1);
        assertEquals(99, requirement.getEstimatedTime());
        
        requirement.setEstimatedTime(125);
        assertEquals(125, requirement.getEstimatedTime());
    }
    
    @Test void setEstimatedTimeZero() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setEstimatedTime(0);
        });
    }
    
    @Test void setEstimatedTimeNegative() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setEstimatedTime(-1);
        });
        
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setEstimatedTime(-55);
        });
    }
    
    // Priority:
    @Test void setPriority() {
        requirement.setPriority(Priority.LOW);
        assertEquals(Priority.LOW, requirement.getPriority());
    
        requirement.setPriority(Priority.CRITICAL);
        assertEquals(Priority.CRITICAL, requirement.getPriority());
    }
    
    @Test void setPriorityNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setPriority(null);
        });
    }
    
    // Type:
    @Test void setType() {
        requirement.setType(Type.PROJECT_RELATED);
        assertEquals(Type.PROJECT_RELATED, requirement.getType());
        
        requirement.setType(Type.NON_FUNCTIONAL);
        assertEquals(Type.NON_FUNCTIONAL, requirement.getType());
    }
    
    @Test void setTypeNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setType(null);
        });
    }
    
    // Status:
    @Test void setStatusEnded() {
        task.setStatus(Status.ENDED);
        requirement.setStatus(RequirementStatus.ENDED);
        assertEquals(RequirementStatus.ENDED, requirement.getStatus());
    }
    
    @Test void setStatusEndedButTasksNotEnded() {
        requirement.setStatus(RequirementStatus.ENDED);
        assertEquals(RequirementStatus.NOT_STARTED, requirement.getStatus());
    }
    
    @Test void setStatusApprovedTasksEnded() {
        task.setStatus(Status.ENDED);
        requirement.setStatus(RequirementStatus.APPROVED);
        assertEquals(RequirementStatus.APPROVED, requirement.getStatus());
    }
    
    @Test void setStatusNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.setStatus(null);
        });
    }
    
    // Add Task
    @Test void addTaskNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.addTask(null);
        });
    }
    
    // Team Member
    @Test void assignTeamMemberNull() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
            requirement.assignTeamMember(null);
        });
    }
    
    @Test void assignResponsibleTeamMemberNull() {
        requirement.assignResponsibleTeamMember(null);
        assertNull(requirement.getResponsibleTeamMember());
    }
    
    @Test void unassignTeamMemberAndIsResponsible() {
        requirement.assignResponsibleTeamMember(teamMember);
        assertThrows(java.lang.IllegalArgumentException.class, () -> {
           requirement.unassignTeamMember(teamMember);
        });
    }
}