package edu.usc.softarch.arcade.frontend.console.arc;

import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.arc.ArcWrapper;

public class ArcConsoleUI
  extends ConsoleUI
{
  FeatureWrapper arc = new ArcWrapper();

  @Override
  public String getName()
  {
    return "arc";
  }

  @Override
  public String getMessage()
  {
    return "ARC: Architectural Recovery based on Concerns";
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "ARC requires the following arguments:" + cr;
    instructions += "Source Directory: This is a directory containing one or ";
    instructions += "more subdirectories with different versions of the ";
    instructions += "subject system." + cr;
    instructions += "Output Directory: This is the directory where all output ";
    instructions += "files go. It must contain a subdirectory called base ";
    instructions += "that contains the files infer.mallet and output.pipe. ";
    instructions += "If running ARC from the console wizard, these will be ";
    instructions += "generated and organized for you." + cr;
    instructions += "Classes Directory: This is the directory in each ";
    instructions += "subject system that contains the compiled classes of ";
    instructions += "that system version." + cr;
    instructions += "Language: Programming language of the subject system. ";
    instructions += "Currently supported languages are java and c.";

    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    // Use existing config
    if(Console.arguments.containsKey("sourceDir")
      && Console.arguments.containsKey("arcOutput")
      && Console.arguments.containsKey("binDir")
      && Console.arguments.containsKey("language"))
    {
      System.out.print("All arguments found in configuration. ");
      System.out.println("Use existing arguments? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        return Console.arguments;
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
    }

    if(Console.arguments.containsKey("binDir"))
    {
      System.out.print("Argument binDir found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        result.put("binDir", Console.arguments.get("binDir"));
    }

    if(!result.containsKey("binDir"))
    {
      System.out.println("Please enter classes directory: ");
      String binDir = Console.in.nextLine();
      result.put("binDir", binDir);
      Console.arguments.put("binDir", binDir);
    }

    if(Console.arguments.containsKey("language"))
    {
      System.out.print("Argument language found in configuration. ");
      System.out.println("Use existing configuration? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        result.put("language", Console.arguments.get("language"));
    }

    if(!result.containsKey("language"))
    {
      System.out.println("Please enter subject system language: ");
      String language = Console.in.nextLine();
      result.put("language", language);
      Console.arguments.put("language", language);
    }

    return result;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
    arc.checkArguments(args);
    arc.execute(args);
  }

  @Override
  public String[] loadRequisites()
  {
    return new String[]
      { "pipeextractor", "topicmodelgenerator", "inferencer" };
  }
}
