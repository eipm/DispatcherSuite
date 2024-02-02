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
package edu.cornell.eipm.messaging.microservices.executors.model.service;

import java.util.Collections;
import java.util.List;

/**
 * A topic with registered actions to trigger.
 *
 * @author Manuele Simi
 */
public class Topic {

  private String name;
  private List<Action> actions = Collections.emptyList();

  public String getName() {
    return name;
  }

  /**
   * Sets the name of this topic.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets all the actions associated to this topic.
   *
   * @return the list of actions
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Sets the actions for this topic.
   *
   * @param actions the list of actions
   */
  public void setActions(final List<Action> actions) {
    this.actions = actions;
  }

  @Override
  public String toString() {
    return "Topic{" + "name='" + name + '\'' + ", actions=" + actions + '}';
  }
}
