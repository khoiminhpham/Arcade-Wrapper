package edu.usc.softarch.arcade.frontend.features.acdc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
//import edu.usc.softarch.arcade.AcdcWithSmellDetection;
import acdc.ACDC;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;
import edu.usc.softarch.arcade.frontend.features.acdc.AcdcWrapper;
import edu.usc.softarch.arcade.frontend.features.archsmelldetector.ArchSmellDetectorWrapper;
import edu.usc.softarch.arcade.frontend.features.depsbuilder.JavaSourceToDepsBuilderWrapper;

public class AcdcWorkflowWrapper
  implements FeatureWrapper
{
  private FeatureWrapper acdc = new AcdcWrapper();
  private FeatureWrapper archsmelldetector = new ArchSmellDetectorWrapper();
  private FeatureWrapper depsbuilder = new JavaSourceToDepsBuilderWrapper();

  //#region CONFIGURATION
  @Override
  public String getName() { return arcade.strings.components.acdcworkflow.id; }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.sourceDir.id,
      arcade.strings.args.outputDir.id,
      arcade.strings.args.binDir.id
    };
  }
  //#endregion

  //#region EXECUTION
  @Override
  public void execute(Map<String,String> args)
    throws Exception, IOException, IllegalArgumentException
  {
    // Setting up Variables
    String fs = File.separator;
    // TODO FIX THIS LATER (check ArchSmeelDetetor.java in Arcade to see how Duc Le retrieve version name)
    String projectName = "Struts2";

    // Dependency Builder
    depsbuilder.checkArguments(args);
    depsbuilder.execute(args);

    // Architectural Recovery
    String outputDir = args.get(arcade.strings.args.outputDir.id);
    String depsRsfFile =
      outputDir + fs + "commonRes" + fs + projectName + "_deps.rsf";
    args.put(arcade.strings.args.depsRsfFile.id, depsRsfFile);
    String clusterFile =
      outputDir + fs + "commonRes" + fs + projectName + "_cluster.rsf";
    args.put(arcade.strings.args.clusterFile.id, clusterFile);

    acdc.checkArguments(args);
    acdc.execute(args);

    // Smell Detection
    String smellsFile =
      outputDir + fs + "commonRes" + fs + projectName + "_smells.ser";
    args.put(arcade.strings.args.smellsFile.id, smellsFile);

    archsmelldetector.checkArguments(args);
    archsmelldetector.execute(args);
  }
  //#endregion

  //#region VALIDATION
  @Override
  public boolean checkArguments(Map<String,String> args)
    throws IllegalArgumentException, IOException
  {
    // Check whether source directory exists
    File sourceDirectory = new File(args.get(arcade.strings.args.sourceDir.id));
    if(!sourceDirectory.exists())
    {
      String errorMessage = "Source directory not found: ";
      errorMessage += args.get(arcade.strings.args.sourceDir.id);
      throw new IllegalArgumentException(errorMessage);
    }

    // Check whether output directory exists and, if not, create it
    String fs = File.separator;
    String outputDirPath = args.get(arcade.strings.args.outputDir.id);
    outputDirPath += fs + "commonRes";
    File outputDirectory = new File(outputDirPath);
    if(!outputDirectory.exists() && !outputDirectory.mkdirs())
      throw new IOException("Failed to create output directory.");

    // Check binDir
    String binDirPath = args.get(arcade.strings.args.sourceDir.id);
    binDirPath += fs + args.get(arcade.strings.args.binDir.id);
    File binDirectory = new File(binDirPath);
    if(!binDirectory.exists())
    {
      String errorMessage = "Classes directory not found: ";
      errorMessage += args.get(arcade.strings.args.binDir.id);
      throw new IllegalArgumentException(errorMessage);
    }

    return true;
  }
  //#endregion
}
