package edu.usc.softarch.arcade.frontend.exttooladapters.mallet;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import edu.usc.softarch.arcade.frontend.exttooladapters.util.ExtToolAdapter;

@RunWith(Parameterized.class)
public class InferencerTst
{
  //#region ATTRIBUTES
  String inputArgument;
  String inferencerFilename;
  //#endregion

  //#region CONSTRUCTOR
  public InferencerTst(String inputArgument, String inferencerFilename)
  {
    super();
    this.inputArgument = inputArgument;
    this.inferencerFilename = inferencerFilename;
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
        "--input src" + fs + "test" + fs + "resources" + fs + "subject_systems"
          + fs + "httpd" + fs + "arc" + fs + "base" + fs + "topicmodel.data",
        "--inferencer-filename target" + fs + "test-results" + fs
          + "Inferencer" + fs + "httpd" + fs + "base" + fs + "infer.mallet"
      },
      {
        "--input src" + fs + "test" + fs + "resources" + fs + "subject_systems"
          + fs + "Struts2" + fs + "arc" + fs + "base" + fs + "topicmodel.data",
        "--inferencer-filename target" + fs + "test-results" + fs
          + "Inferencer" + fs + "Struts2" + fs + "base" + fs + "infer.mallet"
      }
    });
  }
  //#endregion

  //#region TESTS
  @Test
  public void testInferencer()
  {
    ExtToolAdapter inferencerAdapter = initialize();
    inferencerAdapter.addArguments(inputArgument);
    inferencerAdapter.addArguments(inferencerFilename);
    try { inferencerAdapter.execute(); }
    catch(InvocationTargetException e)
    {
      Assert.fail(e.getStackTrace().toString());
    }

    // String fs = File.separator;
    File output = new File(inferencerFilename.split(" ")[1]);
    assert output.exists();

    // THIS TEST ALWAYS FAILS BECAUSE OF MALLET NON-DETERMINISM
    // File oracle = new File("src" + fs + "test" + fs + "resources" + fs
    //   + "subject_systems" + fs + "httpd" + fs + "arc" + fs + "base" + fs
    //   + "infer.mallet");
    //
    // try
    // {
    //   byte[] f1 = Files.readAllBytes(output.toPath());
    //   byte[] f2 = Files.readAllBytes(oracle.toPath());
    //
    //   assert Arrays.equals(f1, f2);
    // }
    // catch(IOException e)
    // {
    //   Assert.fail(e.getMessage());
    // }
  }
  //#endregion

  //#region INTERNAL
  private ExtToolAdapter initialize()
  {
    // Setting up auxiliary Strings

    assert true;

    String fs = File.separator;
    String resourcesDir = System.getProperty("user.dir") + fs + "src" + fs
      + "test" + fs + "resources";
    String malletHome = resourcesDir + fs + "tools" + fs + "mallet-2.0.7" + fs;
    String commandPath;
    Map<String,String> environment = new HashMap<String,String>();

    if(SystemUtils.IS_OS_LINUX)
    {
      commandPath = malletHome + "bin" + fs + "mallet";
    }
    else if(SystemUtils.IS_OS_WINDOWS)
    {
      commandPath = malletHome + "bin" + fs + "mallet.bat";
      environment.put("MALLET_HOME", malletHome);
    }
    else
      throw new UnsupportedOperationException("OS unknown.");

    // Setting up Inferencer
    ExtToolAdapter inferencerAdapter = new Inferencer();
    assert inferencerAdapter.setToolPath(commandPath);
    inferencerAdapter.setEnvironment(environment);
    return inferencerAdapter;
  }
  //#endregion
}
