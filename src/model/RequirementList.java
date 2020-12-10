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
   * requirement id
   * Everytime a requirement is added, the requirements will be sorted by priority
   *
   * @param requirement the requirement that will be added to the list
   */
  public void add(Requirement requirement)
  {
    Requirement other;
    requirement.setId(idCounter);
    requirements.add(requirement);
    idCounter++;
    for (int i = 0; i < requirements.size() - 1; i++)
      for (int j = i + 1; j < requirements.size(); j++)
        if (requirements.get(i).getPriority() != Priority.CRITICAL && requirements.get(j).getPriority() == Priority.CRITICAL)
        {
          other = requirements.get(i);
          requirements.set(i, requirements.get(j));
          requirements.set(j, other);
        }
    for (int i = 0; i < requirements.size() - 1; i++)
      for (int j = i + 1; j < requirements.size(); j++)
        if (requirements.get(i).getPriority() == Priority.LOW && requirements.get(j).getPriority() == Priority.HIGH)
        {
          other = requirements.get(i);
          requirements.set(i, requirements.get(j));
          requirements.set(j, other);
        }
  }

  public void remove(Requirement requirement)
  {
    requirements.remove(requirement);
  }

  /**
   * The system checks the index before searching in the array
   *
   * @param index the index of the requirement
   * @return the requirement in the selected position
   */
  public Requirement getRequirement(int index)
  {
    if (index < 0 || index >= size())
      throw new IllegalArgumentException("index out of bounds");
    return requirements.get(index);
  }

  /**
   * @param id the id of the searched requirement
   * @return The system throws an exception if there is not a requirement with
   * the mentioned id
   */
  public Requirement getRequirementById(int id)
  {
    for (Requirement requirement : requirements)
      if (requirement.getId() == id)
        return requirement;
    throw new IllegalArgumentException("No requirement found");
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