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

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Test cases for {@link SSHWrapper}
 *
 * @author Manuele Simi
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class SSHWrapperTest {
  private static final String trigger = "echo \" 'message' \">> /my/output/file.txt";

  @Test
  public void getCommand() {
    try {
      SSHWrapper wrapper = new SSHWrapper();
      String command = wrapper.getCommand("mymac.med.cornel.edu", "mas2182", "1234", trigger);
      System.out.println(command);
      assertEquals(getExpectedResults(), command);
    } catch (IOException e) {
      e.printStackTrace();
      assertNull(e);
    }
  }

  private static String getExpectedResults() {
    return "#!/usr/bin/env sh\n"
        + "\n"
        + "LOOKUP_USER=`getent passwd 1234 | cut -d: -f1`\n"
        + "echo \"LOOKUP_USER=${LOOKUP_USER}\"\n"
        + "CURRENT_ID=`id -u`\n"
        + "if [[ \"kduser\" == \"${LOOKUP_USER}\" ]]; then\n"
        + "    echo \"the kduser was already created with the same HOST_USER_ID\"\n"
        + "    su kduser -c 'ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v"
        + " mas2182@mymac.med.cornel.edu \"echo \\\" \\'message\\' \\\">>"
        + " /my/output/file.txt\"'\n"
        + "elif [[ \"1234\" == \"${CURRENT_ID}\" ]]; then\n"
        + "    echo \"the current user owns the ID HOST_USER_ID\"\n"
        + "    ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v"
        + " mas2182@mymac.med.cornel.edu \"echo \\\" \\'message\\' \\\">>"
        + " /my/output/file.txt\"\n"
        + "elif [[ -z \"${LOOKUP_USER}\" ]]; then\n"
        + "    echo \"the ID 1234 is free\"\n"
        + "    id -u kduser || adduser -u 1234 kduser -D -h /home/kduser\n"
        + "    su kduser -c 'ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v"
        + " mas2182@mymac.med.cornel.edu \"echo \\\" \\'message\\' \\\">>"
        + " /my/output/file.txt\"'\n"
        + "else\n"
        + "    >&2 echo \"ID 1234 is reserved and cannot be used inside this Docker"
        + " container, please choose another user\"\n"
        + "    exit 1\n"
        + "fi";
  }
}
