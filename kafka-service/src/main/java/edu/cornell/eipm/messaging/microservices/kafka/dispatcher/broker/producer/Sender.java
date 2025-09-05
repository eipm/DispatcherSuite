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
package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.producer;

import edu.cornell.eipm.messaging.microservices.executors.runtime.JSONPayloadSerializer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

/**
 * Send string payloads to selected topics.
 *
 * @author Manuele Simi
 */
public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  /**
   * Sends the payload to the selected topic.
   *
   * @param topic the target topic
   * @param values the values to send as message's payload
   */
  public void send(String topic, Map<String, String> values) {
    // Sanitize the topic parameter to prevent log injection
    String sanitizedTopic = topic.replace("\n", "").replace("\r", "");
    String json = new JSONPayloadSerializer(values).toJSON();
    LOGGER.info("sending payload='{}' to topic {}", json, sanitizedTopic);
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(sanitizedTopic, sanitizedTopic, json);
    future.whenCompleteAsync(
        (result, throwable) -> {
          if (throwable != null) {
            LOGGER.error(
                "Unable to send message to =[" + sanitizedTopic + "] due to : " + throwable.getMessage());
            future.completeExceptionally(throwable);
          } else {
            LOGGER.info(
                "Sent message to topic=["
                    + sanitizedTopic
                    + "] with offset=["
                    + result.getRecordMetadata().offset()
                    + "]");
            future.complete(result);
          }
        });
  }
}
