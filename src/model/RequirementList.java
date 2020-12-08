package model;

import java.util.ArrayList;

public class RequirementList
{
  private int idCounter;
  private ArrayList<Requirement> requirements;

  public RequirementList()
  {
    idCounter = 1;
    requirements = new ArrayList<>();
  }

  public ArrayList<Requirement> getRequirements()
  {
    return requirements;
  }

  public int size()
  {
    return requirements.size();
  }

  /**
   * the idCounter will keep track of the number of the requirements id
   * it will increase everytime a requirement is added and it will be set as a
   requirement id
   * @param requirement
   */
  public void add(Requirement requirement)
  {
    requirement.setId(idCounter);
    requirements.add(requirement);
    idCounter++;

      }

  public void remove(Requirement requirement)
  {
    requirements.remove(requirement);
  }

  /**
   * The system checks the index before searching in the array
   * @param index
   * @return the requirement in the selected position
   */
  public Requirement getRequirement(int index)
  {
    if (index<0 || index>=size())
      throw new IllegalArgumentException("index out of bounds");
    return requirements.get(index);
  }

  /**
   * @param id
   * @return The system throws an exception if there is not a requirement with
   the mentioned id
   */
  public Requirement getRequirementById(int id)
  {
    for (Requirement requirement : requirements)
      if (requirement.getId() == id)
        return requirement;
    throw new IllegalArgumentException("No requirement found");
  }

  /**
   * @param priority
   * @return The system throws an exception if there are no requirements with
   the selected priority
   */
  public ArrayList<Requirement> getRequirementByPriority(Priority priority)
  {
    ArrayList<Requirement> requirements1 = new ArrayList<>();
    for (Requirement requirement : requirements)
      if (requirement.getPriority() == priority)
        requirements1.add(requirement);
    if (requirements1.size()==0)
      throw new IllegalArgumentException("No requirements found");
    return requirements1;
  }

  /**
   * @param status
   * @return The system throws an exception if there are no requirements with
  the selected priority
   */
  public ArrayList<Requirement> getRequirementByStatus(RequirementStatus status)
  {
    ArrayList<Requirement> requirements1 = new ArrayList<>();
    for (Requirement requirement : requirements)
      if (requirement.getStatus() == status)
        requirements1.add(requirement);
    if (requirements1.size()==0)
      throw new IllegalArgumentException("No requirements found");
    return requirements1;
  }

  public boolean contains(Requirement requirement)
  {
    return requirements.contains(requirement);
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof RequirementList))
      return false;
    RequirementList other = (RequirementList) obj;
    if (idCounter == other.idCounter && size() == other.size())
    {
      for (int i = 0; i < requirements.size(); i++)
        if (!(requirements.get(i).equals(other.requirements.get(i))))
          return false;
      return true;
    }
    return false;
  }

}
