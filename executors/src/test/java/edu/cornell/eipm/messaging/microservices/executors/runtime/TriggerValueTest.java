/*
 MIT License

 Copyright (c) 2020-2026 Englander Institute for Precision Medicine

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.Test;

/**
 * Test cases for {@link TriggerValue} and the shell-safety check applied to {@link LocalCommand}.
 *
 * @author Manuele Simi
 */
public class TriggerValueTest {

  private static final String TRIGGER = "nextflow main.nf --sampleID ${sampleID} --run ${runID}";

  @Test
  public void placeholdersAreReplaced() {
    Map<String, String> values = Map.of("sampleID", "S-1_a.b", "runID", "run 42!");
    String expected = "nextflow main.nf --sampleID S-1_a.b --run run 42!";
    assertEquals(expected, new TriggerValue(TRIGGER).getActualValue(values));
    assertEquals(expected, new TriggerValue(TRIGGER).getShellSafeValue(values));
  }

  @Test
  public void shellMetacharactersAreRejected() {
    for (String bad :
        new String[] {
          "x; rm -rf /", "x && id", "x | id", "$(id)", "`id`", "x\" ; id; echo \"", "x' ; id",
          "a > /etc/passwd", "a < b", "(id)", "a\\b", "line1\nline2", "cr\rhere", "nul\u0000"
        }) {
      try {
        new TriggerValue(TRIGGER).getShellSafeValue(Map.of("sampleID", bad));
        fail("value should have been rejected: " + bad);
      } catch (IllegalArgumentException expected) {
        assertTrue(expected.getMessage().contains("sampleID"));
      }
    }
  }

  @Test
  public void valuesWithoutPlaceholderAreNotChecked() {
    assertEquals(
        "echo done", new TriggerValue("echo done").getShellSafeValue(Map.of("note", "a; id")));
  }

  @Test
  public void nonShellTriggersKeepPreviousBehavior() {
    assertEquals(
        "http://host/hook?q=a&b",
        new TriggerValue("http://host/hook?q=${q}").getActualValue(Map.of("q", "a&b")));
  }

  @Test
  public void localCommandDoesNotRunInjectedCommand() throws Exception {
    Path marker = Files.createTempFile("kd-injection", ".txt");
    Files.delete(marker);
    Action action = new Action();
    action.setTrigger("echo \"${note}\"");
    String payload = "x\"; touch " + marker + "; echo \"";
    try {
      ExecutorService.select(action).execute(Map.of("note", payload), true, MODE.BLOCKING);
      fail("the injected value should have been rejected");
    } catch (IOException expected) {
      assertTrue(expected.getMessage().startsWith("Trigger not executed"));
    }
    assertFalse("injected command must not run", Files.exists(marker));
  }

  @Test
  public void localCommandRunsSafeValues() throws Exception {
    Action action = new Action();
    action.setTrigger("test \"${note}\" = \"hello world\"");
    assertTrue(
        ExecutorService.select(action).execute(Map.of("note", "hello world"), true, MODE.BLOCKING));
  }
}
