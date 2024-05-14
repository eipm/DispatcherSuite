/*
 MIT License

 Copyright (c) 2020-2024 Englander Institute for Precision Medicine

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package edu.cornell.eipm.messaging.microservices.executors.runtime;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import java.io.IOException;
import java.util.Map;

/**
 * Base logic common to all {@link Executor}s
 *
 * @author Manuele Simi
 */
public abstract class BaseExecutor implements Executor {

  /** The action to execute */
  protected Action action;

  @Override
  public void setAction(Action action) {
    this.action = action;
  }

  @Override
  public boolean execute(Map<String, String> values, boolean local, MODE mode) throws IOException {
    String actualTrigger = new TriggerValue(action.getTrigger()).getActualValue(values);
    return this.run(actualTrigger, local, mode);
  }

  /**
   * Runs the trigger with its actual values
   *
   * @param actualTrigger the trigger to execute
   * @param local true if it's a local execution, false otherwise
   * @param mode the mode in which the trigger is executed
   * @return true if the action completed successfully or if it has been started * successfully
   *     (depending on the MODE)
   * @throws IOException if the trigger fails
   */
  protected abstract boolean run(String actualTrigger, boolean local, MODE mode) throws IOException;
}
