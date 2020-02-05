package edu.usc.softarch.arcade.frontend.console.mallet;

import org.apache.commons.lang3.SystemUtils;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.tooladapters.ToolAdapter;
import edu.usc.softarch.arcade.frontend.tooladapters.mallet.TopicModelGenerator;

public class TopicModelGeneratorConsoleUI
  extends ConsoleUI
{
  ToolAdapter tpm = new TopicModelGenerator(false);

  @Override
  public String getName()
  {
    return "topicmodelgenerator";
  }

  @Override
  public String getMessage()
  {
    return "Mallet: Topic Model Generator";
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "Mallet's topic model generation requires the ";
    instructions += "following arguments:" + cr;
    instructions += "Source Directory: This is a directory containing one or ";
    instructions += "more subdirectories with different versions of the ";
    instructions += "subject system." + cr;
    instructions += "Output Directory: This is the directory where the output ";
    instructions += "file goes." + cr;
    instructions += "Tool path: path to mallet's executable (mallet for Linux ";
    instructions += "or mallet.bat for Windows)";

    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    //TODO this is atrocious and unreadable, fix it
    if(SystemUtils.IS_OS_WINDOWS)
    {
      System.out.print("Arcade has detected that you are executing from a ");
      System.out.println("Windows OS machine.");
      Map<String,String> environment = new HashMap<String,String>();

      if(Console.arguments.containsKey("malletHome"))
      {
        System.out.print("Argument malletHome found in configuration. ");
        System.out.println("Use existing configuration? (y/n)");
        String choice = Console.in.nextLine();
        if(choice.equals("y"))
          environment.put("MALLET_HOME", Console.arguments.get("malletHome"));
      }

      if(!environment.containsKey("MALLET_HOME"))
      {
        System.out.print("Please enter the path to Mallet's home directory in");
        System.out.println("order to set a temporary environment variable:");
        String malletHome = Console.in.nextLine();
        environment.put("MALLET_HOME", malletHome);
        Console.arguments.put("malletHome", malletHome);
      }

      tpm.setEnvironment(environment);
    }

    // Use existing config
    if(Console.arguments.containsKey("sourceDir")
      && Console.arguments.containsKey("arcOutput")
      && Console.arguments.containsKey("malletPath"))
    {
      System.out.print("All arguments found in configuration. ");
      System.out.println("Use existing arguments? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
      {
        tpm.setToolPath(Console.arguments.get("malletPath"));
        return Console.arguments;
      }
    }

    Map<String,String> result = new HashMap<String,String>();

    if(Console.arguments.containsKey("sourceDir"))
    {
      System.out.print("Argument sourceDir found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        result.put("sourceDir", Console.arguments.get("sourceDir"));
    }

    if(!result.containsKey("sourceDir"))
    {
      System.out.println("Please enter source directory: ");
      String sourceDir = Console.in.nextLine();
      result.put("sourceDir", sourceDir);
      Console.arguments.put("sourceDir", sourceDir);
    }

    if(Console.arguments.containsKey("arcOutput"))
    {
      System.out.print("Argument arcOutput found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        result.put("arcOutput", Console.arguments.get("arcOutput"));
    }

    if(!result.containsKey("arcOutput"))
    {
      System.out.println("Please enter output directory: ");
      String arcOutput = Console.in.nextLine();
      result.put("arcOutput", arcOutput);
      Console.arguments.put("arcOutput", arcOutput);
      String fs = File.separator;
      String topicModel = arcOutput + "arc" + fs + "base" + fs
        + "topicmodel.data";
      Console.arguments.put("topicModel", topicModel);
    }

    boolean pathSet = false;

    if(Console.arguments.containsKey("malletPath"))
    {
      System.out.print("Argument malletPath found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
      {
        tpm.setToolPath(Console.arguments.get("malletPath"));
        pathSet = true;
      }
    }

    if(!pathSet)
    {
      System.out.println("Please enter path to mallet's executable: ");
      String malletPath = Console.in.nextLine();
      tpm.setToolPath(Console.arguments.get("malletPath"));
      Console.arguments.put("malletPath", malletPath);
    }

    return result;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
    tpm.addArguments(args);
    tpm.execute();
  }

  @Override
  public String[] loadRequisites()
  {
    return new String[]{ "pipeextractor" };
  }
}
