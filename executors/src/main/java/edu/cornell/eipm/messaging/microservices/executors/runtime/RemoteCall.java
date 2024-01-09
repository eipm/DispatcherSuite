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
package edu.cornell.eipm.messaging.microservices.executors.runtime;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * A call to a remote service.
 *
 * @author Manuele Simi
 */
public class RemoteCall extends BaseExecutor {

  private final Logger logger = LoggerFactory.getLogger(RemoteCall.class);

  RestTemplate restTemplate = new RestTemplate();

  public RemoteCall(Action action) {
    this.setAction(action);
  }

  @Override
  protected boolean run(String url, boolean local, MODE mode) {
    logger.debug("Remote call to: {}", url);
    // Fire the request.
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    logger.debug("Remote Call Dispatched");
    logger.trace(response.getBody());
    return response.getStatusCode() == HttpStatus.OK;
  }
}
