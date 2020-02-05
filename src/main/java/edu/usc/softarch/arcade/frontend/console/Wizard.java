package edu.usc.softarch.arcade.frontend.console;

import java.util.Map;

/**
 * This class manages the wizard operations for the ARCADE console interface.
 *
 * @author Marcelo Schmitt Laser
 */
public class Wizard
{
  /**
   * Driver method for the wizard.
   *
   * @param components A map of front-end service providers for the console UI.
   */
  public static void execute(Map<String, ConsoleUI> components)
  {
    ConsoleUI comp = mainMenu(components);

    if(comp.loadRequisites().length > 0)
      System.out.println("This component has pre-requisites. Please select " +
        "whether you would like to run them.");

    for(String requisite : comp.loadRequisites())
    {
      System.out.println("Do you wish to run "
        + components.get(requisite).getMessage() + "? (y/n)");
      String option = Console.in.nextLine();
      switch(option)
      {
        case "y":
          Map<String,String> args
            = components.get(requisite).loadArgumentsWizard();
          try
          {
            components.get(requisite).execute(args);
          }
          catch(Exception e)
          {
            e.printStackTrace();
          }
          break;

        case "n":
          break;
      }
    }

    Map<String,String> args = comp.loadArgumentsWizard();
    try
    {
      comp.execute(args);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Wizard menu for selecting the desired feature component.
   *
   * @return An instance of the selected component's wrapper.
   */
  private static ConsoleUI mainMenu(Map<String, ConsoleUI> components)
  {
    while(true)
    {
      System.out.println("Main menu:");

      for(ConsoleUI comp : components.values())
      {
        System.out.println(comp.getName() + " - " + comp.getMessage());
      }

      System.out.println("exit - Exit the program.");

      String option = Console.in.nextLine();
      if(option.equals("exit"))
        System.exit(0);
      if(components.containsKey(option))
        return components.get(option);
      else
        System.out.println("Invalid option.");
    }
  }
}
