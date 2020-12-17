package model;

public enum Type
{
  FUNCTIONAL, NON_FUNCTIONAL, PROJECT_RELATED;

  /**
   * Method to get the string representation of the requirement's type.
   * @return The string representation of a type in a proper format.
   */
  public String getName()
  {
    switch (this)
    {
      case FUNCTIONAL:
        return "Functional";
      case NON_FUNCTIONAL:
        return "Non Functional";
      case PROJECT_RELATED:
        return "Project Related";
      default:
        throw new IllegalStateException("Encountered an illegal type value");
    }
  }
}
