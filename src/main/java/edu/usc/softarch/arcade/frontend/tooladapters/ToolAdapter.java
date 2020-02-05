package edu.usc.softarch.arcade.frontend.tooladapters;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Service interface for executing external tools.
 *
 * @author Marcelo Schmitt Laser
 */
public abstract class ToolAdapter
{
  //#region ATTRIBUTES
  /**
   * Path to the tool's executable;
   */
  private String path = "";

  /**
   * Arguments for executing the tool. Keys used are the Arcade identifiers
   * for the arguments.
   */
  private Map<String,String> arguments = new HashMap<String,String>();

  /**
   * Map of environment variables.
   */
  private Map<String,String> environment = new HashMap<String,String>();

  /**
   * Represents whether or not the external tool is a .jar file. If true, the
   * commands "java -jar" will be prepended to the execution command
   * automatically.
   */
  protected final boolean isJar;
  //#endregion

  //#region CONSTRUCTOR
  /**
   * Base constructor. Initializes {@link #path} to empty String.
   *
   * @param isJar {@link #isJar}
   */
  protected ToolAdapter(boolean isJar)
  {
    this.isJar = isJar;
  }

  /**
   * Constructor with set path. If path does not exist, will throw exception
   * and terminate.
   *
   * @param path Path to the tool's executable.
   * @param isJar {@link #isJar}
   * @throws FileNotFoundException If path does not exist. Does NOT check
   *                               whether path is an executable.
   */
  protected ToolAdapter(String path, boolean isJar)
    throws FileNotFoundException
  {
    if(!setToolPath(path))
    {
      String error = "No executable found for " + getName() + " at " + path;
      throw new FileNotFoundException(error);
    }
    this.isJar = isJar;
  }
  //#endregion

  //#region ABSTRACT METHODS
  /**
   * Returns the name of the tool. This name may include extra information,
   * such as its version. This name is used as an identifier within Arcade.
   *
   * @return Name of the tool.
   */
  public abstract String getName();

  /**
   * Returns the tool's necessary arguments. These may be used as identifiers
   * within Arcade to detect whether two components share an argument.
   *
   * @return Names of the required arguments.
   */
  public abstract String[] getArgumentIds();

  /**
   * Verifies whether the arguments are sufficient for execution.
   *
   * @return True if arguments are valid and sufficient.
   * @throws java.lang.IllegalArgumentException If an argument is incorrect
   *                                            or missing.
   * @throws java.io.IOException If it fails to create necessary directories.
   * @see #execute()
   */
  protected abstract boolean validateArguments()
    throws IllegalArgumentException, IOException;

  /**
   * Handles exceptions thrown by the tool's process.
   *
   * @param p Tool's process.
   * @throws InvocationTargetException If any exceptions are thrown by the
   *                                   process.
   */
  protected abstract void exceptionHandling(Process p)
    throws InvocationTargetException;

  /**
   * Builds the command for executing the tool.
   *
   * @return List of Strings that make up the command.
   */
  protected List<String> buildCommand()
  {
    List<String> command = new ArrayList<String>();
    if(isJar)
    {
      command.add("java");
      command.add("-jar");
    }

    command.add(getToolPath());
    command.addAll(buildCommandArguments());
    return command;
  }

  /**
   * Adds the arguments to the execution command.
   *
   * @return List of Strings with arguments to be appended to base command.
   * @see #buildCommand()
   */
  protected abstract List<String> buildCommandArguments();

  /**
   * Performs the tool-specific execution tasks. Has a default implementation.
   *
   * @throws java.lang.reflect.InvocationTargetException If any errors occur
   *                                                     during the tool's
   *                                                     execution.
   * @see #execute()
   */
  protected void executeInternal()
    throws InvocationTargetException
  {
    List<String> command = buildCommand();

    try
    {
      ProcessBuilder pb = new ProcessBuilder(command);
      System.out.println(pb.command());
      pb.environment().putAll(environment);
      pb.inheritIO();
      Process p = pb.start();
      p.waitFor();
      exceptionHandling(p);
    }
    catch(SecurityException e)
    {
      e.printStackTrace();
      //TODO throw exception from lack of permission
    }
    catch(IOException e)
    {
      e.printStackTrace();
      //TODO throw exception due to I/O error
    }
    catch(InterruptedException e)
    {
      e.printStackTrace();
      //TODO throw exception due to some crazy threading error
    }
    catch(NullPointerException | IllegalArgumentException e)
    {
      e.printStackTrace();
      //TODO throw exception due to herpaderp
    }
  }
  //#endregion

  //#region INTERFACE
  /**
   * Sets path to the tool's executable file. Does not check whether the file
   * referenced by the path is correct, only whether it exists.
   *
   * @param path Path to the tool's executable.
   * @return True if file exists, false otherwise.
   */
  public boolean setToolPath(String path)
  {
    File testFile = new File(path);
    boolean exists;
    if(exists = testFile.exists())
      this.path = path;
    return exists;
  }

  protected String getToolPath()
  {
    return this.path;
  }

  /**
   * Adds one or more arguments for the execution of the tool. Does not perform
   * any checks. Replaces existing arguments if new ones are provided for the
   * same Arcade identifier.
   *
   * @param args Arguments to be added. Keys should be their Arcade identifier.
   */
  public void addArguments(Map<String,String> args)
  {
    this.arguments.putAll(args);
  }

  /**
   * Adds one argument for the execution of the external tool. Does not perform
   * any checks.
   *
   * @param argId Arcade id of the argument being added.
   * @param argValue Argument to be added.
   */
  public void addArguments(String argId, String argValue)
  {
    this.arguments.put(argId, argValue);
  }

  protected Map<String,String> getArguments()
  {
    return new HashMap<String,String>(this.arguments);
  }

  /**
   * This method may be used to set environment variables with which to
   * execute the tool, when necessary. Only variables in addition to the
   * standard OS environment need to be included.
   *
   * @param env Map of environment variables of the form &lt;varName,value&gt;.
   */
  public void setEnvironment(Map<String,String> env)
  {
    this.environment = env;
  }

  protected Map<String,String> getEnvironment()
  {
    return new HashMap<String,String>(this.environment);
  }

  /**
   * Executes the tool. If an exception is encountered, it should be handled by
   * an appropriate
   * {@link edu.usc.softarch.arcade.frontend.features.ExceptionHandler}.
   *
   * @throws java.lang.reflect.InvocationTargetException If any errors occur
   *                                                     during the tool's
   *                                                     execution.
   * @throws java.lang.IllegalArgumentException If the provided arguments
   *                                            are invalid or insufficient.
   * @throws java.io.IOException If it fails to create necessary directories.
   */
  public void execute()
    throws InvocationTargetException, IllegalArgumentException, IOException
  {
    // This method serves to structure the execution process. The actual tasks
    // are implemented in the tool-specific methods.
    try { validateArguments(); }
    catch(IllegalArgumentException e) { throw e; }
    catch(IOException e) { throw e; }

    try { executeInternal(); }
    catch(InvocationTargetException e) { throw e; }
  }
  //#endregion
}
