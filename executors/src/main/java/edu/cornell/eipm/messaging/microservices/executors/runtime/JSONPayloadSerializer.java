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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;

/**
 * Serializer for map of values
 *
 * @author Manuele Simi
 */
public class JSONPayloadSerializer {

  private final Map<String, String> data;

  /**
   * Creates the payload
   *
   * @param data the serialized payload
   */
  public JSONPayloadSerializer(Map<String, String> data) {
    this.data = data;
  }

  /**
   * Serializes the payload as JSON document.
   *
   * @return the serialized payload
   */
  public String toJSON() {
    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    return prettyGson.toJson(data);
  }
}
