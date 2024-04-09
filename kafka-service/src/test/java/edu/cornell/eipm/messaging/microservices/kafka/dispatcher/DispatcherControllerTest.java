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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DispatcherApp.class)
@AutoConfigureMockMvc
@EmbeddedKafka(
    partitions = 1,
    topics = {SpringKafkaApplicationTest.TEST_TOPIC})
public class DispatcherControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void noParamAboutShouldReturnDefaultMessage() throws Exception {
    this.mockMvc
        .perform(get("/about"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").value("Kafka-Dispatcher Service 1.3.2-SNAPSHOT"));
  }

  @Test
  public void configuration() throws Exception {
    this.mockMvc.perform(get("/configuration")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void publishData() throws Exception {
    this.mockMvc
        .perform(
            post("/publish-data/" + SpringKafkaApplicationTest.TEST_TOPIC)
                .content("{\"runID\":\"xyz\",\"sampleID\":\"1234\"}")
                .contentType(MediaType.TEXT_PLAIN_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
