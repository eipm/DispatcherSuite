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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Response for /about
 *
 * @author Manuele Simi
 */
public class About {

  private Properties properties;
  private final String component;
  private final String version;

  public About() {
    this.read("kd-info.txt");
    this.component = this.getComponent();
    this.version = this.getVersion();
  }

  public String getComponent() {
    return getProperty("kd.name");
  }

  public String getVersion() {
    return getProperty("kd.version");
  }

  private void read(String propertyFileName) {
    InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName);
    this.properties = new Properties();
    try {
      this.properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getProperty(String propertyName) {
    return this.properties.getProperty(propertyName);
  }
}
