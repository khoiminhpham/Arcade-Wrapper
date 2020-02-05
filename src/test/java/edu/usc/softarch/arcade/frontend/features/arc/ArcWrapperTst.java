package edu.usc.softarch.arcade.frontend.features.arc;

import edu.usc.softarch.arcade.frontend.console.Console;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.util.Collection;
import java.util.Arrays;
import edu.usc.softarch.arcade.frontend.features.wrappers.FeatureWrapper;

@RunWith(Parameterized.class)
public class ArcWrapperTst
{
  //#region ATTRIBUTES
  String sourceDirectory;
  String outputDirectory;
  String classesDirectory;
  boolean language;
  //#endregion

  //#region CONSTRUCTOR
  public ArcWrapperTst(
    String sourceDirectory, String outputDirectory, String classesDirectory,
    boolean language)
  {
    super();
    this.sourceDirectory = sourceDirectory;
    this.outputDirectory = outputDirectory;
    this.classesDirectory = classesDirectory;
  }
  //#endregion

  //#region PARAMETERS
  @Parameterized.Parameters
  public static Collection<Object[]> input()
  {
    String fs = File.separator;
    return Arrays.asList(new Object[][]
    {
      {
        "src" + fs + "test" + fs + "resources" + fs + "subject_systems" + fs
          + "Struts2" + fs + "src",
        "target" + fs + "test-results" + fs + "Arc" + fs + "Struts2",
        "lib_struts", false
      },
      {
        "src" + fs + "test" + fs + "resources" + fs + "subject_systems" + fs
          + "httpd" + fs + "src",
        "target" + fs + "test-results" + fs + "Arc" + fs + "httpd",
        "", true
      }
    });
  }
  //#endregion

  //#region TESTS
  @Test
  public void testArcWrapper()
  {
    FeatureWrapper arcWrapper = new ArcWrapper();
    Object[] args;
    if(this.language)
      args = new Object[] { sourceDirectory, outputDirectory, classesDirectory,
        "c" };
    else
      args = new Object[] { sourceDirectory, outputDirectory,
        classesDirectory };

    try { arcWrapper.execute(args); }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    // String fs = File.separator;
    // File outputDir = new File(outputDirectory);
    // File[] outputFiles = outputDir.listFiles();
    // for(File output : outputFiles)
    // {
    //   if(!output.isDirectory())
    //   {
    //     File oracle = new File("src" + fs + "test" + fs + "resources" + fs
    //       + "subject_systems" + fs + "httpd" + fs + "arc" + fs
    //       + output.getName());
    //
    //     try
    //     {
    //       byte[] f1 = Files.readAllBytes(output.toPath());
    //       byte[] f2 = Files.readAllBytes(oracle.toPath());
    //
    //       assert Arrays.equals(f1, f2);
    //     }
    //     catch(IOException e)
    //     {
    //       Assert.fail(e.getMessage());
    //     }
    //   }
    // }
  }

  @Test
  public void testArcRun()
  {
    Console.main(new String[] { this.sourceDirectory, this.outputDirectory,
      this.classesDirectory } );
  }
  //#endregion
}
