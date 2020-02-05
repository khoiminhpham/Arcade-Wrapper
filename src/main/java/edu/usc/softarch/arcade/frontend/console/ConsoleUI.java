package edu.usc.softarch.arcade.frontend.console;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents the front-end service provider of a component. This may be
 * either an Arcade feature or external tool.
 *
 * @author Marcelo Schmitt Laser
 */
public abstract class ConsoleUI
{
  protected Map<String,String> argumentBuilder = new HashMap<String,String>();

  /**
   * Returns the adapted component's name. The result may not match the class
   * or package name. This name is used as an identifier within Arcade.
   *
   * @return Name of the feature component.
   */
  public abstract String getName();

  /**
   * Returns a message to be displayed as a menu option.
   *
   * @return Menu option message.
   */
  public abstract String getMessage();

  /**
   * Returns usage instructions for the adapted functional component.
   *
   * @return Use instructions.
   */
  public abstract String getInstructions();

  /**
   * Initiates a wizard for loading the arguments required by the adapted
   * functional component.
   *
   * @return A map with all the necessary arguments.
   */
  public abstract Map<String,String> loadArgumentsWizard();

  /**
   * Executes the adapted component's primary functionality.
   *
   * @param args A map with all the necessary arguments.
   * @throws Exception Any exception appropriate to the adapted component.
   */
  public abstract void execute(Map<String,String> args)
    throws Exception;

  /**
   * Returns the names of all requisite components. These are those from which
   * the output serves as input to the selected one.
   *
   * @return Pre-requisite component names.
   */
  public abstract String[] loadRequisites();

  protected boolean useConfigArgument(String id)
  {
    if(Console.arguments.containsKey(id))
    {
      System.out.print("Argument " + id + " found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
      {
        argumentBuilder.put(id, Console.arguments.get(id));
        return true;
      }
    }
    return false;
  }

  protected void loadArgument(String id, String name)
  {
    System.out.println("Please enter " + name + ": ");
    String newArg = Console.in.nextLine();
    argumentBuilder.put(id, newArg);
    Console.arguments.put(id, newArg);
  }
}
