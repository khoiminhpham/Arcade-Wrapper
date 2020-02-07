package edu.usc.softarch.arcade.frontend.features.smellanalysis;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import edu.usc.softarch.arcade.antipattern.detection.SmellEvolutionAnalyzer;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

public class SmellEvolutionAnalyzerWrapper
  implements FeatureWrapper
{
  //#region CONFIGURATION
  @Override
  public String getName() { return arcade.strings.components.SmellAnalysis.id; }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.sourceDir.id,
    };
  }
  //#endregion

  //#region EXECUTION
  @Override
  public void execute(Map<String,String> args)
    throws Exception, IOException, IllegalArgumentException
  {
    String fs = File.separator;    
    String[] parsedArgs = new String[1];
    parsedArgs[0] = args.get(arcade.strings.args.sourceDir.id);    
    
    SmellEvolutionAnalyzer.main(parsedArgs);
  }
  //#endregion

  //#region VALIDATION
  @Override
  public boolean checkArguments(Map<String,String> args)
    throws IllegalArgumentException, IOException
  {
    // Check whether source directory exists
    File sourceDir = new File(args.get(arcade.strings.args.sourceDir.id));
    if(!sourceDir.exists())
    {
      String errorMessage = "sourceDir not found: ";
      errorMessage += args.get(arcade.strings.args.sourceDir.id);
      throw new IllegalArgumentException(errorMessage);
    }
        
    return true;
  }
  //#endregion
}