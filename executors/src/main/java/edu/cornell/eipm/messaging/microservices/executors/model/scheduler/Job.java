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
package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import java.util.ArrayList;
import java.util.List;

/**
 * The job configuration.
 *
 * @author Manuele Simi
 */
public class Job {

  String when;

  /**
   * Gets when to execute the job
   *
   * @return the config for when
   */
  public String getWhen() {
    return when;
  }

  /**
   * Sets when the job will be executed
   *
   * @param when the value of when, it can be crontab-style (eg.g *\/20 * * * * ?) or in
   *     milliseconds
   */
  public void setWhen(String when) {
    this.when = when;
  }

  private final List<Action> actions = new ArrayList<>();

  /**
   * List of actions associated to the job
   *
   * @return the list of actions
   */
  public List<Action> getActions() {
    return actions;
  }
}
