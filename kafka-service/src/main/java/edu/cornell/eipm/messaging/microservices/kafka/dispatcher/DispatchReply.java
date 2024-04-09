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
package edu.cornell.eipm.messaging.microservices.kafka.dispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holder for triggers and replies used for simulation.
 *
 * @author Manuele Simi
 */
public class DispatchReply {

  private List<String> triggers = new ArrayList<>();
  private List<String> replies = new ArrayList<>();

  public void addTrigger(String trigger) {
    this.triggers.add(trigger);
  }

  public void addReply(String topic, String replace) {
    this.replies.add("Publish to " + topic + " with payload " + replace);
  }

  public List<String> getTriggers() {
    return triggers;
  }

  /**
   * Sets the triggers for the reply.
   *
   * @param triggers the list of triggers
   */
  public void setTriggers(final List<String> triggers) {
    this.triggers = triggers;
  }

  /**
   * Gets the list of replies.
   *
   * @return the list of replies
   */
  public List<String> getReplies() {
    return Collections.unmodifiableList(replies);
  }

  /**
   * Sets the replies.
   *
   * @param replies the replies
   */
  public void setReplies(final List<String> replies) {
    this.replies = replies;
  }
}
