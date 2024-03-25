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
package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

/**
 * Configured schedulers with their jobs
 *
 * @author Manuele Simi
 */
public class Schedulers {

  private Fixed fixed;
  private Delay delay;
  private Cron cron;

  /**
   * Gets the list of jobs to start with a delay.
   *
   * @return the list
   */
  public Delay getDelay() {
    return delay;
  }

  /**
   * Sets the list of jobs to start with a delay.
   *
   * @param delay the list
   */
  public void setDelay(Delay delay) {
    this.delay = delay;
  }

  /**
   * Gets the list of cron-like jobs.
   * @return
   */
  public Cron getCron() {
    return cron;
  }

  /**
   * Sets the list of cron-like jobs.
   *
   * @param cron the list
   */
  public void setCron(Cron cron) {
    this.cron = cron;
  }

  /**
   * Gets the list of fixed jobs.
   *
   * @return the list
   */
  public Fixed getFixed() {
    return fixed;
  }

  /**
   * Sets the list of fixed jobs.
   *
   * @param fixed the list
   */
  public void setFixed(Fixed fixed) {
    this.fixed = fixed;
  }
}
