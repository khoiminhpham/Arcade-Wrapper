package edu.usc.softarch.arcade.frontend.console.smellanalysis;

import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.smellanalysis.SmellEvolutionAnalyzerWrapper;

public class SmellEvolutionAnalyzerConsoleUI
  extends ConsoleUI
{
  FeatureWrapper SmellAnalysis = new SmellEvolutionAnalyzerWrapper();

  @Override
  public String getName()
  {
    return arcade.strings.components.SmellAnalysis.id;
  }

  @Override
  public String getMessage()
  {
    return arcade.strings.components.SmellAnalysis.message;
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "ACDC requires the following arguments:" + cr;
    instructions += arcade.strings.args.sourceDir.instruction + cr;
   
    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    // Use existing config
    if(Console.arguments.containsKey(arcade.strings.args.sourceDir.id))	     
    {
      System.out.print("All arguments found in configuration. ");
      System.out.println("Use existing arguments? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        return Console.arguments;
    }        

    if(!useConfigArgument(arcade.strings.args.sourceDir.id))
      loadArgument(arcade.strings.args.sourceDir.id,
        arcade.strings.args.sourceDir.name);    

    return argumentBuilder;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
	  SmellAnalysis.checkArguments(args);
	  SmellAnalysis.execute(args);
  }

  @Override
  public String[] loadRequisites()
  {
    return new String[]
      { };
  }
}