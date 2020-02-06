package edu.usc.softarch.arcade.frontend.console.a2a;

import java.util.Map;
import java.util.HashMap;
import edu.usc.softarch.arcade.frontend.console.ConsoleUI;
import edu.usc.softarch.arcade.frontend.console.Console;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.a2a.BatchSystemEvoWrapper;

public class BatchSystemEvoConsoleUI
  extends ConsoleUI
{
	BatchSystemEvoWrapper a2a
    = new BatchSystemEvoWrapper();

  @Override
  public String getName()
  {
    return arcade.strings.components.a2a.id;
  }

  @Override
  public String getMessage()
  {
    return arcade.strings.components.a2a.message;
  }

  @Override
  public String getInstructions()
  {
    String cr = System.lineSeparator();
    String instructions = "Java Fact Extractor requires the following arguments:" + cr;
    instructions += arcade.strings.args.sourceDir.instruction + cr;    

    return instructions;
  }

  @Override
  public Map<String,String> loadArgumentsWizard()
  {
    // Use existing config
	if(Console.arguments.containsKey(arcade.strings.args.sourceDir.id)
    	&& Console.arguments.containsKey(arcade.strings.args.distopt.id))      
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
  	if(!useConfigArgument(arcade.strings.args.distopt.id))
  	    loadArgument(arcade.strings.args.distopt.id,
  	      arcade.strings.args.distopt.name);

    return argumentBuilder;
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception
  {
	  a2a.checkArguments(args);
	  a2a.execute(args);
  }  

  @Override
  public String[] loadRequisites()
  {
    return new String[]
      { };
  }
}