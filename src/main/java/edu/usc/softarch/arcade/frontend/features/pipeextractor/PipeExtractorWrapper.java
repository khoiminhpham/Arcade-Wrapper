package edu.usc.softarch.arcade.frontend.features.pipeextractor;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Map;
import edu.usc.softarch.arcade.util.ldasupport.PipeExtractor;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

/**
 * See {@code edu.usc.softarch.arcade.util.ldasupport.PipeExtractor}.
 */
public class PipeExtractorWrapper
  implements FeatureWrapper
{
  //#region CONFIGURATION
  @Override
  public String getName() { return "pipeextractor"; }

  @Override
  public String[] getArgumentIds()
  {
    //TODO fix output to be general
    return new String[] { "sourceDir", "arcOutput" };
  }
  //#endregion

  //#region EXECUTION
  @Override
  public void execute(Map<String,String> args)
    throws FileNotFoundException, IOException, IllegalArgumentException
  {
    String fs = File.separator;
    String[] parsedArgs = new String[2];
    parsedArgs[0] = args.get("sourceDir");
    parsedArgs[1] = args.get("arcOutput") + fs + "arc" + fs + "base";
    PipeExtractor.main(parsedArgs);
  }
  //#endregion

  //#region VALIDATION
  @Override
  public boolean checkArguments(Map<String,String> args)
    throws IllegalArgumentException, IOException
  {
    // Check whether source directory exists
    File sourceDirectory = new File(args.get("sourceDir"));
    if(!sourceDirectory.exists())
    {
      String errorMessage = "Source directory not found: ";
      errorMessage += args.get("sourceDir");
      throw new IllegalArgumentException(errorMessage);
    }

    // Check whether output directory exists and, if not, create it
    String fs = File.separator;
    String outputDirPath = args.get("arcOutput") + fs + "arc" + fs + "base";
    File outputDirectory = new File(outputDirPath);
    if(!outputDirectory.exists() && !outputDirectory.mkdirs())
      throw new IOException("Failed to create output directory.");

    return true;
  }
  //#endregion
}
