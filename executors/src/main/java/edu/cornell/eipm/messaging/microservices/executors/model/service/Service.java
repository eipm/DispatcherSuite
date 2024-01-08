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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Instance configuration for the service.
 *
 * @author Manuele Simi
 */
public class Service {

  private List<Topic> topics = new ArrayList<>();

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  public Optional<List<Topic>> getTopics() {
    return Optional.ofNullable(topics);
  }

  /**
   * Gets all registered topics.
   *
   * @return the names
   */
  public Set<String> getTopicNames() {
    return topics.stream().map(Topic::getName).collect(Collectors.toSet());
  }

  /**
   * Gets the actions of interest for the given topic.
   *
   * @param name the topic name
   * @return the list of messages
   */
  public List<Action> getActions(String name) {
    Optional<Topic> action = topics.stream().filter(t -> name.equals(t.getName())).findAny();
    return action.orElse(new Topic()).getActions();
  }

  @Override
  public String toString() {
    return "Service{" + "topics=" + topics + '}';
  }
}
