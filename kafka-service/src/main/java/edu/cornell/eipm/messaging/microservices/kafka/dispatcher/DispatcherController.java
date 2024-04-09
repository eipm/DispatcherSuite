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

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import edu.cornell.eipm.messaging.microservices.executors.runtime.JSONPayloadDeserializer;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.consumer.Receiver;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.producer.Sender;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the DispatcherApp service.
 *
 * @author Manuele Simi
 */
@RestController
public class DispatcherController {

  private final Logger logger = LoggerFactory.getLogger(DispatcherController.class);

  @Autowired private KafkaService kafkaService;

  @Autowired private Receiver receiver;

  @Autowired private Sender sender;

  @RequestMapping("/")
  public String home() {
    return "Hello from the Kafka-Dispatcher Service";
  }

  @RequestMapping("/configuration")
  public Set<String> config() throws IOException {
    return kafkaService.getService().getTopicNames();
  }

  @RequestMapping("/configuration/topics")
  public Set<String> configTopics() throws IOException {
    return kafkaService.getService().getTopicNames();
  }

  @RequestMapping("/configuration/actions")
  public List<Action> configMessages(@RequestParam(required = true, value = "topic") String topic) {
    if (topic.isEmpty()) return Collections.emptyList();
    return kafkaService.getService().getActions(topic);
  }

  /**
   * Maps the GET request for simulating what a message will trigger and the potential replies.
   *
   * @param topic the topic name where the message would be published
   * @param payload the payload of the message
   * @return a reply including the triggers that would be executed and their responses.
   */
  @RequestMapping("/dispatch")
  public DispatchReply dispatch(
      @RequestParam(required = true, value = "topic") String topic,
      @RequestParam(required = false, value = "payload") String payload) {

    List<Action> actions = kafkaService.getService().getActions(topic);
    DispatchReply dispatchReply = new DispatchReply();
    actions.forEach(
        action -> dispatchReply.addTrigger(action.getTrigger().replace("${payload}", payload)));
    actions.forEach(
        action ->
            dispatchReply.addReply(
                action.getReply().getTopic(),
                action.getReply().getPayload().replace("${payload}", payload)));

    return dispatchReply;
  }

  /**
   * Maps the GET web request to publish a payload to a topic.
   *
   * @param topic the topic name
   * @param allRequestParams a list of key-value pairs to publish
   * @return
   */
  @RequestMapping("/publish/{topic}")
  public String publish(
      @PathVariable(value = "topic") String topic,
      @RequestParam Map<String, String> allRequestParams) {
    if (topic.isEmpty()) return "Topic cannot be empty";
    logger.info("Sending new message to topic: " + topic);
    logger.info("Parameters are " + allRequestParams.entrySet());

    sender.send(topic, allRequestParams);
    return "Sent payload " + allRequestParams.entrySet() + " to Topic " + topic;
  }

  /**
   * Maps the POST web request to publish a payload to a topic.
   *
   * @param topic the topic name
   * @param unusedParams a map currently not used
   * @param body the payload in a JSON format
   * @return a message with the results of the publication
   * @throws IOException if the payload is not valid
   */
  @PostMapping(path = "/publish-data/{topic}", consumes = MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String dpublish(
      @PathVariable(value = "topic") String topic,
      @RequestParam Map<String, String> unusedParams,
      @RequestBody String body)
      throws IOException {
    if (topic.isEmpty()) return "Topic cannot be empty";
    logger.info("Sending new message to topic: " + topic);
    logger.info("Body is " + body);
    Map<String, String> allRequestParams = new JSONPayloadDeserializer(body).fromJSON();
    sender.send(topic, allRequestParams);
    return "Sent payload " + allRequestParams.entrySet() + " to Topic " + topic;
  }

  /**
   * Maps the web request for information about this service .
   *
   * @return a class with information holding some properties about this service
   */
  @RequestMapping("/about")
  public About about() {
    return new About();
  }
}
