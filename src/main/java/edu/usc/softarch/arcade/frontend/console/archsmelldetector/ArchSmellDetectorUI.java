package edu.usc.softarch.arcade.frontend.console.archsmelldetector;

import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.archsmelldetector.ArchSmellDetectorWrapper;

public class ArchSmellDetectorUI
  extends ConsoleUI
{
  FeatureWrapper SmellDetector = new ArchSmellDetectorWrapper();

  @Override
  public String getName()
  {
    return arcade.strings.components.SmellDetector.id;
  }

  @Override
  public String getMessage()
  {
    return arcade.strings.components.SmellDetector.message;
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "Arch Smell Detector requires the following arguments:" + cr;
    instructions += arcade.strings.args.depsRsfFile.instruction + cr;
    instructions += arcade.strings.args.clusterFile.instruction + cr;
    instructions += arcade.strings.args.smellsFile.instruction + cr;
    //instructions += arcade.strings.args.binDir.instruction + cr;

    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    // Use existing config
  /*  if(Console.arguments.containsKey(arcade.strings.args.sourceDir.id)
      && Console.arguments.containsKey(arcade.strings.args.outputDir.id)
      && Console.arguments.containsKey(arcade.strings.args.binDir.id))*/
	  
  if(Console.arguments.containsKey(arcade.strings.args.depsRsfFile.id)
		  && Console.arguments.containsKey(arcade.strings.args.clusterFile.id)
	      && Console.arguments.containsKey(arcade.strings.args.smellsFile.id))
    {
      System.out.print("All arguments found in configuration. ");
      System.out.println("Use existing arguments? (y/n)");
      String choice = Console.in.nextLine();
      if(choice.equals("y"))
        return Console.arguments;      
    }

    if(!useConfigArgument(arcade.strings.args.depsRsfFile.id))
      loadArgument(arcade.strings.args.depsRsfFile.id,
        arcade.strings.args.depsRsfFile.name);
    
    if(!useConfigArgument(arcade.strings.args.clusterFile.id))
        loadArgument(arcade.strings.args.clusterFile.id,
          arcade.strings.args.clusterFile.name);

    if(!useConfigArgument(arcade.strings.args.smellsFile.id))
      loadArgument(arcade.strings.args.smellsFile.id,
        arcade.strings.args.smellsFile.name);

    /*if(!useConfigArgument(arcade.strings.args.binDir.id))
      loadArgument(arcade.strings.args.binDir.id,
        arcade.strings.args.binDir.name);*/

    return argumentBuilder;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
	  SmellDetector.checkArguments(args);
	  SmellDetector.execute(args);
  }

  @Override
  public String[] loadRequisites()
  {
    return new String[]
      { };
  }
}
