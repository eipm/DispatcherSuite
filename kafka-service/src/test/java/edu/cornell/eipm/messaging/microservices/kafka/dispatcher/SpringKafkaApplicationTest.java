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

import static org.assertj.core.api.Assertions.assertThat;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.consumer.Receiver;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.broker.producer.Sender;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    topics = {SpringKafkaApplicationTest.TEST_TOPIC})
public class SpringKafkaApplicationTest {

  static final String TEST_TOPIC = "oncorseq_sequencing_in_progress";
  static Map<String, String> params = new HashMap<>();

  @Autowired private Sender sender;

  @Autowired private Receiver receiver;

  @BeforeClass
  public static void setupParameters() {
    params.put("sampleID", "sample123");
    params.put("runID", "run123");
    params.put("sayHello", "Hello from Spring Kafka Send/Receive!");
  }

  @Test
  public void testSendReceive() throws Exception {
    receiver.resetLatch();
    sender.send(TEST_TOPIC, params);
    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
  }

  @Test
  public void testSend() {
    sender.send(TEST_TOPIC, params);
  }

  @Test
  public void testReceive() throws Exception {
    receiver.resetLatch();
    // receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    // assertThat(receiver.getLatch().getCount()).isEqualTo(0);
  }
}
