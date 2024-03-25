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

/**
 * An action associated to the {@link Topic} An action is something to execute when a message is
 * received from the topic. An action can have a reply, which is a message to send after the action
 * is executed.
 *
 * @author Manuele Simi
 */
public class Action {

  private String trigger;
  private Reply reply;

  /** if the action must be executed locally (no ssh wrapper) */
  private boolean local;

  /**
   * Gets the trigger for this action.
   *
   * @return the string with the trigger to launch
   */
  public String getTrigger() {
    return trigger;
  }

  /**
   * Sets the trigger for this action.
   *
   * @param trigger the trigger to execute
   */
  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  /**
   * Gets the reply configured for this action.
   *
   * @return the reply
   */
  public Reply getReply() {
    return reply;
  }

  /**
   * Sets the reply.
   *
   * @param reply the reply.
   */
  public void setReply(Reply reply) {
    this.reply = reply;
  }

  /**
   * Checks if the action has executed locally.
   *
   * @return true if local, false otherwise.
   */
  public boolean isLocal() {
    return local;
  }

  /**
   * Sets the action as local.
   *
   * @param local true if the action is locally executed, false otherwise.
   */
  public void setLocal(boolean local) {
    this.local = local;
  }
}
