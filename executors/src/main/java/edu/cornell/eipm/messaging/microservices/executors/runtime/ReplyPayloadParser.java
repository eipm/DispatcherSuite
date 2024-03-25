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

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Build a map of values from the configured payload.
 *
 * @author Manuele Simi
 */
public class ReplyPayloadParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReplyPayloadParser.class);

  /**
   * Parses the payload and creates the map of values from the message payload.
   *
   * @param payload the payload
   * @param requestValues the actual values to replace in the payload
   * @return a map with an entry for each var and its actual value
   */
  public static Map<String, String> parse(String payload, final Map<String, String> requestValues) {

    Map<String, String> values = new HashMap<>();
    String[] params = payload.split("&");
    for (String param : params) {
      String[] entry = param.split("=");
      if (entry.length == 2) {
        values.put(entry[0], new TriggerValue(entry[1]).getActualValue(requestValues));
      } else {
        LOGGER.error("Invalid payload format at " + param + ". Missing = between name and value");
        throw new IllegalArgumentException(
            "Invalid payload format at " + param + ". Missing = between name and value");
      }
    }
    return values;
  }
}
