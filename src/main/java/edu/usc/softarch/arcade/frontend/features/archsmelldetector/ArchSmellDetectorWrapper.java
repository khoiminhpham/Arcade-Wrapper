package edu.usc.softarch.arcade.frontend.features.archsmelldetector;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import edu.usc.softarch.arcade.antipattern.detection.ArchSmellDetector;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

public class ArchSmellDetectorWrapper
  implements FeatureWrapper
{
  //#region CONFIGURATION
  @Override
  public String getName() { return arcade.strings.components.SmellDetector.id; }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.depsRsfFile.id,
      arcade.strings.args.clusterFile.id,
      arcade.strings.args.smellsFile.id      
    };
  }
  //#endregion

  //#region EXECUTION
  @Override
  public void execute(Map<String,String> args)
    throws Exception, IOException, IllegalArgumentException
  {
    String fs = File.separator;
    String[] parsedArgs = new String[3];    
    parsedArgs[0] = args.get(arcade.strings.args.depsRsfFile.id);
    parsedArgs[1] = args.get(arcade.strings.args.clusterFile.id);// + fs + "acdc";
    parsedArgs[2] = args.get(arcade.strings.args.smellsFile.id);
    //AcdcWithSmellDetection.main(parsedArgs);
    ArchSmellDetector.main(parsedArgs);
  }
  //#endregion

  //#region VALIDATION
  @Override
  public boolean checkArguments(Map<String,String> args)
    throws IllegalArgumentException, IOException
  {
    // Check whether source directory exists
    File depsRsfFile = new File(args.get(arcade.strings.args.depsRsfFile.id));
    if(!depsRsfFile.exists())
    {
      String errorMessage = "depsRsfFile not found: ";
      errorMessage += args.get(arcade.strings.args.depsRsfFile.id);
      throw new IllegalArgumentException(errorMessage);
    }

	// Check whether source directory exists
    File clusterFile = new File(args.get(arcade.strings.args.clusterFile.id));
    if(!clusterFile.exists())
    {
      String errorMessage = "clusterFile not found: ";
      errorMessage += args.get(arcade.strings.args.clusterFile.id);
      throw new IllegalArgumentException(errorMessage);
    }

    File smellsFile = new File(args.get(arcade.strings.args.smellsFile.id));
    if(!smellsFile.exists())
    {
      String errorMessage = "smellsFile path does not exist: ";
      errorMessage += args.get(arcade.strings.args.smellsFile.id);
      throw new IllegalArgumentException(errorMessage);
    }
    
    // Check whether output directory exists and, if not, create it
   /* String fs = File.separator;
    String outputDirPath = args.get(arcade.strings.args.outputDir.id);
    outputDirPath += fs + "acdc";
    File outputDirectory = new File(outputDirPath);
    if(!outputDirectory.exists() && !outputDirectory.mkdirs())
      throw new IOException("Failed to create output directory.");*/   

    return true;
  }
  //#endregion
}
