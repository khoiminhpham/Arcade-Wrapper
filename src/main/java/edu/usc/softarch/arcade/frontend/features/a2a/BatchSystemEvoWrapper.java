package edu.usc.softarch.arcade.frontend.features.a2a;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import edu.usc.softarch.arcade.metrics.BatchSystemEvo;
import edu.usc.softarch.arcade.frontend.features.FeatureWrapper;

public class BatchSystemEvoWrapper
  implements FeatureWrapper
{
  @Override
  public String getName()
  {
    return arcade.strings.components.a2a.id;
  }

  @Override
  public String[] getArgumentIds()
  {
    return new String[]
    {
      arcade.strings.args.sourceDir.id,      
    };
  }

  @Override
  public void execute(Map<String,String> args)
    throws Exception, IOException, IllegalArgumentException
  {
    String fs = File.separator;
    String[] parsedArgs = new String[3];
    parsedArgs[0] = args.get(arcade.strings.args.sourceDir.id); 
    parsedArgs[1] = args.get(arcade.strings.args.param1.id); 
    parsedArgs[2] = args.get(arcade.strings.args.param2.id); 
    
    BatchSystemEvo.main(parsedArgs);
  }

  @Override
  public boolean checkArguments(Map<String,String> args)
    throws IllegalArgumentException, IOException
  {
    // Check whether source directory exists
    /*File sourceDirectory = new File(args.get(arcade.strings.args.sourceDir.id));
    if(!sourceDirectory.exists())
    {
      String errorMessage = "Source directory not found: ";
      errorMessage += args.get(arcade.strings.args.sourceDir.id);
      throw new IllegalArgumentException(errorMessage);
    }    */

    return true;
  }
}