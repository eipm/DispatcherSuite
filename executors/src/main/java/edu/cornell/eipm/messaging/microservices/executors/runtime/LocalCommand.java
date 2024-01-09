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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A command to execute on the local machine.
 *
 * @author Manuele Simi
 */
public class LocalCommand extends BaseExecutor {

  private final Logger logger = LoggerFactory.getLogger(LocalCommand.class);

  LocalCommand(Action action) {
    this.setAction(action);
  }

  @Override
  protected boolean run(String command, boolean local, MODE mode) throws IOException {
    logger.debug("Local execution for: {}", command);
    String wrappedCommand;
    String ssh_command;
    String hostname = System.getenv("HOST_HOSTNAME");
    String hostuser = System.getenv("HOST_USER");
    String hostuserid = System.getenv("HOST_USER_ID");
    Path tmpFile =
        Files.createTempFile(
            "kd",
            ".sh",
            PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------")));
    if (!(local)
        && hostname != null
        && !hostname.isEmpty()
        && hostuser != null
        && !hostuser.isEmpty()) {
      // we are running inside a docker container
      if (hostuserid.isEmpty()) {
        logger.error("The Docker container must be started with option '-e HOST_USER_ID=<id>'");
        return false;
      }
      wrappedCommand = new SSHWrapper().getCommand(hostname, hostuser, hostuserid, command);
    } else {
      // we go with a local execution
      wrappedCommand = "#!/usr/bin/env sh \n";
      wrappedCommand += command + "\n";
    }
    logger.trace("Command wrapped as: {}", wrappedCommand);
    byte[] strToBytes = wrappedCommand.getBytes();
    Files.write(tmpFile, strToBytes);
    ssh_command = tmpFile.toAbsolutePath().toString();
    logger.trace("Local command wrapped as: {}", ssh_command);
    Process process = Runtime.getRuntime().exec(ssh_command);
    logger.debug("Local Command Dispatched");
    if (mode == MODE.BLOCKING) {
      InputStream stdIn = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(stdIn);
      BufferedReader br = new BufferedReader(isr);

      String line = null;

      // catch the std output
      logger.debug("<OUTPUT>");
      while ((line = br.readLine()) != null) logger.debug(line);
      logger.debug("</OUTPUT>");
      BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      // catch the std error
      logger.debug("<ERROR>");
      while ((line = stdError.readLine()) != null) logger.debug(line);
      logger.debug("</ERROR>");

      // check if the process is done
      int exitVal = 0;
      try {
        exitVal = process.waitFor();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      logger.trace("Process exit value: " + exitVal);
    } else {
      try {
        // wait few seconds before to check if the process is alive
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        logger.error("Unable to wait ", e);
      }
    }
    logger.trace("Process is alive? " + process.isAlive());
    // calling exitValue if alive results in a IllegalThreadStateException.
    if (Objects.nonNull(tmpFile)) tmpFile.toFile().delete();
    return (process.isAlive() || process.exitValue() == 0);
  }
}
