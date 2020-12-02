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

  public Requirement getRequirement(int index)
  {
    return requirements.get(index);
    //TODO index not valid exception
  }

  public Requirement getRequirementById(int id)
  {
    for (Requirement requirement : requirements)
      if (requirement.getId() == id)
        return requirement;
    return null; //TODO EXCEPTION
  }

  public ArrayList<Requirement> getRequirementByPriority(Priority priority)
  {
    ArrayList<Requirement> requirements1 = new ArrayList<>();
    for (Requirement requirement : requirements)
      if (requirement.getPriority() == priority)
        requirements1.add(requirement);
    return requirements1; //TODO exception for no priority found and invalid one
  }

  public ArrayList<Requirement> getRequirementByStatus(RequirementStatus status)
  {
    ArrayList<Requirement> requirements1 = new ArrayList<>();
    for (Requirement requirement : requirements)
      if (requirement.getStatus() == status)
        requirements1.add(requirement);
    return requirements1; //TODO exception for no status found and invalid one
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
