package command;

import model.Model;

/**
 * This interface acts as a helper interface for our Controller. We are following Command design
 * pattern to overcome the problem of having our Controller polluted with several methods.
 */
public interface Command {
  /**
   * Imposes all classes implementing this interface to have this method execute, which follow
   * similar pattern of code, get input, ask model to process, tell view to display.
   */
  Model execute();
}
