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
 * A reply associated to an Action. When a reply is configured, the configured payload is sent to
 * the topic after the action has been executed.
 *
 * @author Manuele Simi
 */
public class Reply {

  private String topic;
  private String payload;

  public String getTopic() {
    return topic;
  }

  /**
   * Sets the topic where to publish the reply.
   *
   * @param topic the topic name
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * Gets the payload of the reply.
   *
   * @return the payload
   */
  public String getPayload() {
    return payload;
  }

  /**
   * Sets the payload to send with this reply.
   *
   * @param payload the payload
   */
  public void setPayload(String payload) {
    this.payload = payload;
  }
}
