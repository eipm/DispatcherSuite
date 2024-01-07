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
package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config;

import edu.cornell.eipm.messaging.microservices.executors.model.scheduler.Schedulers;
import edu.cornell.eipm.messaging.microservices.executors.model.service.Service;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Entry point for the injected configuration.
 *
 * @author Manuele Simi
 */
@Component
@Validated
@Configuration
@ConfigurationProperties("dispatcher")
public class KafkaService {

  public Service service;

  public Schedulers schedulers;

  public Service getService() {
    return service;
  }

  public Schedulers getSchedulers() {
    return schedulers;
  }

  public void setService(Service service) {
    this.service = service;
  }

  public void setSchedulers(Schedulers schedulers) {
    this.schedulers = schedulers;
  }
}
