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
package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.consumer;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Reply;
import edu.cornell.eipm.messaging.microservices.executors.runtime.ExecutorService;
import edu.cornell.eipm.messaging.microservices.executors.runtime.JSONPayloadDeserializer;
import edu.cornell.eipm.messaging.microservices.executors.runtime.MODE;
import edu.cornell.eipm.messaging.microservices.executors.runtime.ReplyPayloadParser;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.producer.Sender;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Receive string payloads from selected topics.
 *
 * @author Manuele Simi
 */
public class Receiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  @Value("${kafka.consumer.topics}")
  private String[] topics;

  @Value("${kafka.consumer.group-id}")
  private String groupId;

  @Value("${dispatcher.mode}")
  private MODE mode;

  @Autowired private Sender sender;

  @Autowired private KafkaService kafkaService;

  public CountDownLatch getLatch() {
    return latch;
  }

  public CountDownLatch resetLatch() {
    return latch = new CountDownLatch(1);
  }

  @KafkaListener(
      id = "#{'${kafka.consumer.group-id}'}",
      clientIdPrefix = "receiver",
      topics = "#{'${kafka.consumer.topics}'.split(',')}")
  public void receive(List<ConsumerRecord<?, String>> messages) {

    LOGGER.info("-----start of batch receive-----");
    for (int i = 0; i < messages.size(); i++) {
      ConsumerRecord<?, String> message = messages.get(i);
      LOGGER.info("Received messages on topic [{}]: [{}] ", message.topic(), message.value());
      kafkaService
          .getService()
          .getActions(message.topic())
          .forEach(
              action -> {
                try {
                  Map<String, String> values =
                      new JSONPayloadDeserializer(message.value()).fromJSON();
                  boolean needReply =
                      ExecutorService.select(action)
                          .execute(
                              new JSONPayloadDeserializer(message.value()).fromJSON(),
                              action.isLocal(),
                              mode);
                  if (needReply) {
                    // send back the reply, if configured
                    Reply actionReply = action.getReply();
                    if (Objects.nonNull(actionReply) && Objects.nonNull(actionReply.getTopic()))
                      sender.send(
                          actionReply.getTopic(),
                          ReplyPayloadParser.parse(actionReply.getPayload(), values));
                  }
                } catch (IOException e) {
                  LOGGER.error("Failed to dispatch action '{}'", action.getTrigger(), e);
                }
              });
    }
    LOGGER.info("-----end of batch receive-----");
    latch.countDown();
  }
}
