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

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Handle trigger values by evaluating the actual values of the placeholders (if any).
 *
 * @author Manuele Simi
 */
public class TriggerValue {

  /**
   * Characters that allow a substituted value to escape its position in a shell command (quote
   * breaking, command separators, substitutions, redirections, line breaks).
   */
  private static final Pattern SHELL_UNSAFE = Pattern.compile("[`$;&|<>()\"'\\\\\\r\\n\\x00]");

  private final String formalValue;

  /**
   * Creates a new value to associate to a trigger.
   *
   * @param value the value
   */
  public TriggerValue(String value) {
    this.formalValue = value;
  }

  /**
   * Looks for malicious parameters
   *
   * @param value the value to check
   * @return the same value if validated
   * @throws IllegalArgumentException if the value is not valid
   */
  private String validate(String value) {
    if (value.toLowerCase().contains("bash"))
      throw new IllegalArgumentException("Looks like the parameter is trying to execute a script");
    return value;
  }

  /**
   * Gets the actual formalValue of the parameter, given the input list of values
   *
   * @param values list of values for the placeholders (if any)
   * @return the actual formalValue
   */
  public String getActualValue(final Map<String, String> values) {
    final String[] actualValue = new String[] {formalValue};
    values.forEach((k, v) -> actualValue[0] = actualValue[0].replace("${" + k + "}", validate(v)));
    return actualValue[0];
  }

  /**
   * Gets the actual value of a trigger that is executed by a shell. In addition to {@link
   * #getActualValue(Map)}, every value that replaces a placeholder of this trigger is rejected if
   * it contains shell metacharacters, so that message payloads cannot inject commands.
   *
   * @param values list of values for the placeholders (if any)
   * @return the actual value, safe to pass to a shell
   * @throws IllegalArgumentException if a substituted value contains shell metacharacters
   */
  public String getShellSafeValue(final Map<String, String> values) {
    for (Map.Entry<String, String> entry : values.entrySet()) {
      if (entry.getValue() != null
          && formalValue.contains("${" + entry.getKey() + "}")
          && SHELL_UNSAFE.matcher(entry.getValue()).find())
        throw new IllegalArgumentException(
            "The value of placeholder '"
                + LogSanitizer.sanitize(entry.getKey())
                + "' contains shell metacharacters and was rejected");
    }
    return getActualValue(values);
  }
}
