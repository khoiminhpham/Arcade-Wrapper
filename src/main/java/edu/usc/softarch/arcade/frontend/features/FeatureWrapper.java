package edu.usc.softarch.arcade.frontend.features;

import java.util.Map;

/**
 * The FeatureWrapper interface specifies the necessary operations for
 * configuring and executing an ARCADE feature.
 *
 * @author Marcelo Schmitt Laser
 */
public interface FeatureWrapper
{
  //#region CONFIGURATION
  /**
   * Returns the feature component's name. This may not match the class or
   * package name, as it is used as an identifier within Arcade.
   *
   * @return Name of the component.
   */
  public String getName();

  /**
   * Returns the feature component's necessary arguments. These may be used
   * as identifiers within Arcade to detect whether two components share an
   * argument.
   *
   * @return Names of the required arguments.
   */
  public String[] getArgumentIds();
  //#endregion

  //#region EXECUTION
  /**
   * Executes the feature component's main functionality. If it contains more
   * than one primary functionality, it is advised that the component be
   * refactored. Does not necessarily validate arguments automatically.
   *
   * @param args All arguments necessary for execution of the feature.
   * @throws Exception Any exception appropriate to the wrapped feature.
   */
  public void execute(Map<String,String> args)
    throws Exception;
  //#endregion

  //#region VALIDATION
  /**
   * Checks whether the arguments available are appropriate for usage with
   * this feature component. Arguments must be in the correct order.
   *
   * @param args Arguments to check.
   * @return True if arguments are valid.
   * @throws Exception Any exception appropriate to the wrapped feature.
   */
  public boolean checkArguments(Map<String,String> args)
    throws Exception;
  //#endregion
}
