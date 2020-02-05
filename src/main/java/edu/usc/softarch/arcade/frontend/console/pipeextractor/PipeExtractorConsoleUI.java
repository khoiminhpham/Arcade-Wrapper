package edu.usc.softarch.arcade.frontend.console.pipeextractor;

import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.pipeextractor.PipeExtractorWrapper;

public class PipeExtractorConsoleUI
  extends ConsoleUI
{
  FeatureWrapper pipeextractor = new PipeExtractorWrapper();

  @Override
  public String getName()
  {
    return "pipeextractor";
  }

  @Override
  public String getMessage()
  {
    return "Pipe Extractor";
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "PipeExtractor requires the following arguments:";
    instructions += cr + "Source Directory: This is a directory containing one";
    instructions += " or more subdirectories with different versions of the ";
    instructions += "subject system." + cr;
    instructions += "Output Directory: This is the directory where the output ";
    instructions += "file goes.";

    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    // Use existing config
    if(Console.arguments.containsKey("sourceDir")
      && Console.arguments.containsKey("arcOutput"))
    {
      System.out.print("All arguments found in configuration. ");
      System.out.println("Use existing arguments? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        return Console.arguments;
    }

    Map<String,String> result = new HashMap<String,String>();

    //TODO refactor all of this into an "argumentWizard" or something
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

    return result;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
    pipeextractor.checkArguments(args);
    pipeextractor.execute(args);
  }

  @Override
  public String[] loadRequisites()
  {
    return new String[]{ };
  }
}
