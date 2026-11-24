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

/**
 * Neutralizes user-controlled values before they are written to the logs, preventing forged log
 * entries (CWE-117).
 *
 * @author Manuele Simi
 */
public final class LogSanitizer {

  private LogSanitizer() {}

  /**
   * Replaces carriage returns and line feeds so a value cannot inject additional log lines.
   *
   * @param value the value to log (may be {@code null})
   * @return the sanitized string representation of the value, or {@code null}
   */
  public static String sanitize(Object value) {
    if (value == null) return null;
    return String.valueOf(value).replaceAll("[\r\n]", "_");
  }
}
