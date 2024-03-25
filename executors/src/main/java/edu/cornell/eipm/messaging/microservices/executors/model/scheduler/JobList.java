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
 * List of configured jobs.
 *
 * @author Manuele Simi
 */
abstract class JobList {

  private Job job1;
  private Job job2;
  private Job job3;
  private Job job4;
  private Job job5;
  private Job job6;
  private Job job7;
  private Job job8;
  private Job job9;
  private Job job10;
  private Job job11;
  private Job job12;
  private Job job13;
  private Job job14;
  private Job job15;

  /**
   * Sets the job in the 1st position.
   *
   * @param job the job
   */
  public void setJob1(Job job) {
    this.job1 = job;
  }

  /**
   * Sets the job in the 2nd position.
   *
   * @param job the job
   */
  public void setJob2(Job job) {
    this.job2 = job;
  }

  /**
   * Sets the job in the 3rd position.
   *
   * @param job the job
   */
  public void setJob3(Job job) {
    this.job3 = job;
  }

  /**
   * Sets the job in the 4th position.
   *
   * @param job the job
   */
  public void setJob4(Job job) {
    this.job4 = job;
  }

  /**
   * Sets the job in the 5th position.
   *
   * @param job the job
   */
  public void setJob5(Job job) {
    this.job5 = job;
  }

  /**
   * Sets the job in the 6th position.
   *
   * @param job the job
   */
  public void setJob6(Job job) {
    this.job6 = job;
  }

  /**
   * Sets the job in the 7th position.
   *
   * @param job the job
   */
  public void setJob7(Job job) {
    this.job7 = job;
  }

  /**
   * Sets the job in the 8th position.
   *
   * @param job the job
   */
  public void setJob8(Job job) {
    this.job8 = job;
  }

  /**
   * Sets the job in the 9th position.
   *
   * @param job the job
   */
  public void setJob9(Job job) {
    this.job9 = job;
  }

  /**
   * Sets the job in the 10th position.
   *
   * @param job the job
   */
  public void setJob10(Job job) {
    this.job10 = job;
  }

  /**
   * Sets the job in the 11th position.
   *
   * @param job the job
   */
  public void setJob11(Job job) {
    this.job11 = job;
  }

  /**
   * Sets the job in the 12th position.
   *
   * @param job the job
   */
  public void setJob12(Job job) {
    this.job12 = job;
  }

  /**
   * Sets the job in the 13th position.
   *
   * @param job the job
   */
  public void setJob13(Job job) {
    this.job13 = job;
  }

  /**
   * Sets the job in the 14th position.
   *
   * @param job the job
   */
  public void setJob14(Job job) {
    this.job14 = job;
  }

  /**
   * Sets the job in the 15th position.
   *
   * @param job the job
   */
  public void setJob15(Job job) {
    this.job15 = job;
  }

  /**
   * Gets the N-th job in the list
   *
   * @param index the position of the job
   * @return the job
   */
  public Job getJob(int index) {
    Job selected = null;
    switch (index) {
      case 1:
        selected = job1;
        break;
      case 2:
        selected = job2;
        break;
      case 3:
        selected = job3;
        break;
      case 4:
        selected = job4;
        break;
      case 5:
        selected = job5;
        break;
      case 6:
        selected = job6;
        break;
      case 7:
        selected = job7;
        break;
      case 8:
        selected = job8;
        break;
      case 9:
        selected = job9;
        break;
      case 10:
        selected = job10;
        break;
      case 11:
        selected = job11;
        break;
      case 12:
        selected = job12;
        break;
      case 13:
        selected = job13;
        break;
      case 14:
        selected = job14;
        break;
      case 15:
        selected = job15;
        break;
    }
    return selected;
  }
}
