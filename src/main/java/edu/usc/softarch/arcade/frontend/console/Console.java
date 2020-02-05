package edu.usc.softarch.arcade.frontend.console;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ServiceLoader;
import java.util.Scanner;

/**
 * Driver for {@link edu.usc.softarch.arcade.frontend.console}.
 *
 * @author Marcelo Schmitt Laser
 */
public class Console
{
  public static Scanner in = new Scanner(System.in);

  public static Map<String,String> arguments;

  public static void main(String[] args)
  {
    arguments = loadConfiguration();
    ServiceLoader<ConsoleUI> componentLoader = ServiceLoader.load(ConsoleUI.class);
    Map<String, ConsoleUI> components = new HashMap<String, ConsoleUI>();

    for(ConsoleUI comp : componentLoader)
    {
      components.put(comp.getName(), comp);
    }

    // Hand over to Wizard
    if(args.length == 0)
      Wizard.execute(components);
    // -------------------
    else
    {
      try
      {
//        run(args, components);
      }
      catch(Exception e)
      {
        System.out.println(e.getMessage() + System.lineSeparator());

        if(e.getClass().equals(IllegalArgumentException.class))
        {
          StackTraceElement thrower = e.getStackTrace()[0];
          if(e.getCause() != null)
            thrower = e.getCause().getStackTrace()[0];
          String throwerName = thrower.getClassName() + "#"
            + thrower.getMethodName();

          if(throwerName.equals("edu.usc.softarch.arcade.frontend.console#run"))
          {
            System.out.println(installInstructions());
          }
        }
      }
    }
  }

  /**
   * Tries to execute feature component directly.
   * <p>
   * If the requested service does not exist, displays instructions for
   * installing new services to {@code arcade_console}. If the arguments are
   * incorrect, displays an error message and instructions for usage of the
   * feature component. If the service exists and the arguments are correct,
   * initiates execution of the feature component.
   */
  private static void run(Map<String,String> args,
    Map<String, ConsoleUI> components)
    throws Exception
  {
    ConsoleUI comp = components.get(args.get("feature"));
    if(comp == null)
    {
      IllegalArgumentException e = new IllegalArgumentException
        ("Component " + args.get("feature") + " not found.");
      throw e;
    }

    comp.execute(args);
  }

  private static String installInstructions()
  {
    //TODO install instructions for new services
    throw new UnsupportedOperationException();
  }

  private static Map<String,String> loadConfiguration()
  {
    BufferedReader reader;
    try
    {
      reader = new BufferedReader(new FileReader("config.arcade"));
      String line = reader.readLine();
      Map<String,String> result = new HashMap<String,String>();
      while(line != null)
      {
        String[] lineArg = line.split("=");
        if(lineArg.length == 2)
        {
          lineArg[1] = lineArg[1].replace("\\", File.separator);
          result.put(lineArg[0], lineArg[1]);
        }
        line = reader.readLine();
      }
      reader.close();
      return result;
    }
    catch(IOException e)
    {
      return new HashMap<String,String>();
    }
  }
}
