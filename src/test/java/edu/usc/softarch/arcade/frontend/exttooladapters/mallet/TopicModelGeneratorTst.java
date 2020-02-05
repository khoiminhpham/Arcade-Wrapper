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
public class TopicModelGeneratorTst
{
  //#region ATTRIBUTES
  String inputArgument;
  String outputArgument;
  //#endregion

  //#region CONSTRUCTOR
  public TopicModelGeneratorTst(String inputArgument, String outputArgument)
  {
    super();
    this.inputArgument = inputArgument;
    this.outputArgument = outputArgument;
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
          + fs + "httpd" + fs + "src" + fs,
        "--output target" + fs + "test-results" + fs + "Arc"
          + fs + "httpd" + fs + "base" + fs + "topicmodel.data"
      },
      {
        "--input src" + fs + "test" + fs + "resources" + fs + "subject_systems"
          + fs + "Struts2" + fs + "src" + fs,
        "--output target" + fs + "test-results" + fs + "Arc"
          + fs + "Struts2" + fs + "base" + fs + "topicmodel.data"
      }
    });
  }
  //#endregion

  //#region TESTS
  @Test
  public void testTMG()
  {
    ExtToolAdapter tmgAdapter = initialize();
    tmgAdapter.addArguments(inputArgument);
    tmgAdapter.addArguments(outputArgument);
    try { tmgAdapter.execute(); }
    catch(InvocationTargetException e)
    {
      Assert.fail(e.getStackTrace().toString());
    }

    // String fs = File.separator;
    File output = new File(outputArgument.split(" ")[1]);
    assert output.exists();

    // THIS TEST ALWAYS FAILS BECAUSE OF MALLET NON-DETERMINISM
    // File oracle = new File("src" + fs + "test" + fs + "resources" + fs
    //   + "subject_systems" + fs + "httpd" + fs + "arc" + fs + "base" + fs
    //   + "topicmodel.data");
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

    // Setting up TopicModelGenerator
    ExtToolAdapter tmgAdapter = new TopicModelGenerator();
    assert tmgAdapter.setToolPath(commandPath);
    tmgAdapter.setEnvironment(environment);
    return tmgAdapter;
  }
  //#endregion
}
