/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuscheduling;

/**
 *
 * @author eaz99
 */
public class CPUQ {

     private Process process;  // process in this segment
     private int startTime;  // when process start its round
     private int endTime;  // when process finished its round

     private static CPUQ head;
     private CPUQ next;
     private static CPUQ tail;

     private final Process wastedProcess = new Process("X Wasted Time X");  // this an object for wasted time that not used by a process

     public CPUQ() {
          this.process = null;
          this.startTime = -1;
          this.endTime = -1;
          this.head = this;
          this.next = null;
          this.tail = this;
     }

     public CPUQ(Process process, int startTime, int endTime) {
          if (process == null) {
               process = wastedProcess;
          }
          this.process = process;
          this.startTime = startTime;
          this.endTime = endTime;

     }

     public void addCPUQ(Process process, int startTime, int endTime) {
          CPUQ temp;
          if (process == null) {
               temp = new CPUQ(null, startTime, endTime);
          } else {
               temp = new CPUQ(process, startTime, endTime);
          }
          // To Track The Addition IN The CPU Q
          //System.out.println("Print From COUQ addCPUQ\t:" + temp);
          tail.next = temp;
          tail = temp;
     }

     public boolean foundProcess(Process p, int time) {
          CPUQ temp = tail;
          if (temp != null && temp != head) {
               // check if have same object of the process
               // if the end time is less or equal so this process is processing
               // or it is only now finished and another next process have higher priority
               // to be in Ready Q
               
               // To Track The values if there is an Error
//               System.out.println("\n\n");
//               System.out.println("Combare process\t tail process = " + tail.getProcess().getName() + "\t p = " + p.getName());
//               System.out.println("Combare Time\t tail.getEndTime = " + tail.getEndTime() + "\t current time = " + time);
//               System.out.println("\n\n");

               if (tail.getProcess() == p && tail.getEndTime() > time) {
                    return true;
               }
          }
          return false;
     }

     public static boolean CPUBusy(int time) {
          if (tail.getEndTime() > time) {
               return true;
          } else {
               return false;
          }
     }

     /*
     to calculate waiting time
     1 take the total burst
     2 take minus the arrivel time minus the burst time
      */
 /*
     to calculate the turnarround time
     1 take when the process end its last round
     2 minus the arrivel time     
      */
     public Process getProcess() {
          return process;
     }

     public void setProcess(Process process) {
          this.process = process;
     }

     public int getStartTime() {
          return startTime;
     }

     public void setStartTime(int startTime) {
          this.startTime = startTime;
     }

     public int getEndTime() {
          return endTime;
     }

     public void setEndTime(int endTime) {
          this.endTime = endTime;
     }

     public CPUQ getHaid() {
          return head;
     }

     public void setHaid(CPUQ haid) {
          this.head = haid;
     }

     public CPUQ getNext() {
          return next;
     }

     public void setNext(CPUQ next) {
          this.next = next;
     }

     public CPUQ getTail() {
          return tail;
     }

     public void setTail(CPUQ tail) {
          this.tail = tail;
     }

     public static String printTimeline() {
          StringBuilder timeline = new StringBuilder("0"); // Start from time 0

          CPUQ current = head.next; // Start from the head's next, assuming head is a placeholder
          while (current != null && current != head) {
               // Append the process name and its time interval
               timeline.append(" --[").append(current.process.getName()).append("]--> ")
                       .append(current.endTime);
               current = current.next;
          }

          return timeline.toString();
     }

     @Override
     public String toString() {
          return "CPUQ{" + "process=" + process + ", startTime=" + startTime + ", endTime=" + endTime + ", next=" + next + '}';
     }

}
