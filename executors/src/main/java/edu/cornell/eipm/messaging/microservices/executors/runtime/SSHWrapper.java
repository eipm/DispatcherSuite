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

import java.io.*;
import java.util.stream.Collectors;

/**
 * The SSH wrapper for running command within a docker container.
 *
 * @author Manuele Simi
 */
class SSHWrapper {

  private static final String wrapperTemplate = "ssh_command_wrapper.sh";
  private static String wrapperContent = "";

  SSHWrapper() throws IOException {
    this.loadTemplate();
  }

  /**
   * Loads the ssh wrapper from the classpath
   *
   * @throws IOException if the template is not found
   */
  private void loadTemplate() throws IOException {
    if (wrapperContent.isEmpty()) {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      try (InputStream inputStream = classLoader.getResourceAsStream(wrapperTemplate);
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        wrapperContent = reader.lines().collect(Collectors.joining(System.lineSeparator()));
      }
    }
  }

  /**
   * Gets the wrapped command.
   *
   * @param hostname
   * @param user
   * @param userID
   * @param command
   * @return the command wrapped in the script to execute
   */
  String getCommand(String hostname, String user, String userID, String command) {
    String content = wrapperContent;
    content =
        content
            .replaceAll("<HOST_HOSTNAME>", hostname)
            .replaceAll("<HOST_USER>", user)
            .replaceAll("<HOST_USER_ID>", userID);
    command = command.replaceAll("'", "\\\\'");
    command = command.replaceAll("\"", "\\\\\"");
    content = content.replace("<SSH_COMMAND>", command);
    return content;
  }
}
