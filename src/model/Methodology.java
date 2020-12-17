package model;

public enum Methodology
{
    WATERFALL, SCRUM;

    /**
     * Method to get the string representation of the project's methodology.
     * @return The string representation of a methodology in a proper format.
     */
    public String getMethodology()
    {
        switch(this)
        {
            case WATERFALL:
                return "Waterfall";
            case SCRUM:
                return "Scrum";
            default:
                throw new IllegalStateException("Encountered an illegal methodology value");
        }
    }
}