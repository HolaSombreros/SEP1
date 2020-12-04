package model;

public enum RequirementStatus
{
    NOT_STARTED,
    STARTED,
    ENDED,
    APPROVED,
    REJECTED;
  
    /**
     * Method to get the string representation of the task's status.
     * @return The string representation of a status in a proper format.
     */
    public String getName() {
        switch (this) {
            case NOT_STARTED:
                return "Not Started";
            case STARTED:
                return "Started";
            case ENDED:
                return "Ended";
            case APPROVED:
                return "Approved";
            case REJECTED:
                return "Rejected";
            default:
                throw new IllegalStateException("Encountered an illegal status value");
        }
    }
}
