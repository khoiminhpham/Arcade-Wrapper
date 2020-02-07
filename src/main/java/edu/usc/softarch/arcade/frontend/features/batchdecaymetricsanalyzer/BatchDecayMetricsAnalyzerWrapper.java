package edu.usc.softarch.arcade.frontend.features.batchdecaymetricsanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
//import edu.usc.softarch.arcade.AcdcWithSmellDetection;
import edu.usc.softarch.arcade.decay.BatchDecayMetricsAnalyzer;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

public class BatchDecayMetricsAnalyzerWrapper
  implements FeatureWrapper
{
  //#region CONFIGURATION
  @Override
  public String getName() { return arcade.strings.components.DecayMetrics.id; }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.sourceDir.id,
      arcade.strings.args.sourceDir2.id,
      //arcade.strings.args.binDir.id
    };
  }
  //#endregion

  //#region EXECUTION
  @Override
  public void execute(Map<String,String> args)
    throws Exception, IOException, IllegalArgumentException
  {
    String fs = File.separator;    
    String[] parsedArgs = new String[2];
    parsedArgs[0] = args.get(arcade.strings.args.sourceDir.id);
    parsedArgs[1] = args.get(arcade.strings.args.sourceDir2.id);
    
    BatchDecayMetricsAnalyzer.main(parsedArgs);
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
    
    File sourceDir2 = new File(args.get(arcade.strings.args.sourceDir2.id));
    if(!sourceDir2.exists())
    {
      String errorMessage = "sourceDir2 directory does not exist: ";
      errorMessage += args.get(arcade.strings.args.sourceDir2.id);
      throw new IllegalArgumentException(errorMessage);
    }  

    return true;
  }
  //#endregion
}