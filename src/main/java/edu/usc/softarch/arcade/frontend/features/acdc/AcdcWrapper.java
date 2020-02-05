package edu.usc.softarch.arcade.frontend.features.acdc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
//import edu.usc.softarch.arcade.AcdcWithSmellDetection;
import acdc.ACDC;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

public class AcdcWrapper
  implements FeatureWrapper
{
  //#region CONFIGURATION
  @Override
  public String getName() { return arcade.strings.components.acdc.id; }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.depsRsfFile.id,
      arcade.strings.args.clusterFile.id,
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
    //String[] parsedArgs = new String[3];
    String[] parsedArgs = new String[2];
    parsedArgs[0] = args.get(arcade.strings.args.depsRsfFile.id);
    parsedArgs[1] = args.get(arcade.strings.args.clusterFile.id);// + fs + "acdc";
    //parsedArgs[2] = args.get(arcade.strings.args.binDir.id);
    //AcdcWithSmellDetection.main(parsedArgs);
    ACDC.main(parsedArgs);
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
    
    File clusterFile = new File(args.get(arcade.strings.args.clusterFile.id));
    if(!clusterFile.exists())
    {
      String errorMessage = "clusterFile directory does not exist: ";
      errorMessage += args.get(arcade.strings.args.clusterFile.id);
      throw new IllegalArgumentException(errorMessage);
    }

    // Check whether output directory exists and, if not, create it
   /* String fs = File.separator;
    String outputDirPath = args.get(arcade.strings.args.outputDir.id);
    outputDirPath += fs + "acdc";
    File outputDirectory = new File(outputDirPath);
    if(!outputDirectory.exists() && !outputDirectory.mkdirs())
      throw new IOException("Failed to create output directory.");*/

    // Check binDir
    /*File[] sourceDirs = sourceDirectory.listFiles();
    for(File sDir : sourceDirs)
      // If no files inside the source directory are named binDir
      if(sDir.list((d, s) ->
        {
          return s.equals(args.get(arcade.strings.args.binDir.id));
        }).length != 1)
      {
        String errorMessage = "One or more source directories does not ";
        errorMessage +=  "contain the specified binary directory.";
        throw new IllegalArgumentException(errorMessage);
      }*/

    return true;
  }
  //#endregion
}
