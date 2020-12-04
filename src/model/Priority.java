package model;

public enum Priority
{
  CRITICAL,
  HIGH,
  LOW;

  /**
   * Method to get the string representation of the task's priority.
   * @return The string representation of a priority in a proper format.
   */
  public String getName() {
    switch (this) {
      case CRITICAL:
        return "Critical";
      case HIGH:
        return "High";
      case LOW:
        return "Low";
      default:
        throw new IllegalStateException("Encountered an illegal priority value");
    }
  }
}
