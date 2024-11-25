/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuscheduling;

import java.util.ArrayList;

/**
 *
 * @author eaz99
 */
public class Process {

     private String name; // for process name
     private int arrivelTime;  // when process arrived
     private int burstTime;  // required quantom for the process
     private int completionTime = 0;  // when the process finished its work
     private static String processesInfo = "";
     private boolean IsFirstTime = true;  // true mean yes it is first time for taking a round for the process

     private int waitingTime;  // sum of time process waited without processing
     private int turnaroundTime;  // the time when the process finished minus its arrived time
     private int responseTime;  // calculate when the process first time respponse First Start Time - Arrival Time

     private ArrayList<Integer> rounds = new ArrayList<>();  // create segments of rounds with required quantoms for the process
     //private int numberOfRounds;

     private Process next;
     private Process prev;
     private static Process tail;
     //private static Process head;

     public Process(String name) {
          this.name = name;
     }

     public Process(String name, int arrivelTime, int burstTime, int quantom) {
          this.name = name;
          this.arrivelTime = arrivelTime;
          this.burstTime = burstTime;
          setRounds(quantom);

          if (this.tail == null) {
               this.tail = this;
               this.next = this;
               this.prev = null;
          }
     }

     public void setRounds(int quantom) {
          int remainBurst = this.burstTime;

          while (remainBurst != 0) {
               if (remainBurst > quantom) {
                    this.rounds.add(quantom);
                    remainBurst = remainBurst - quantom;
               } else {
                    this.rounds.add(remainBurst);
                    //this.numberOfRounds = rounds.size();
                    break;
               }
          }
     }

     public int getRound() {
          if (rounds.isEmpty()) {
               return -1;
          }
          int roundNumber = rounds.get(0);
          rounds.remove(0);
          return roundNumber;
     }

     public void addProcess(String name, int arrivelTime, int burstTime, int quantom) {
          Process temp = new Process(name, arrivelTime, burstTime, quantom);
          tail.next = temp;
          temp.prev = tail;
          tail = temp;
     }

     // to delete a specific process
     public Process rempveProcess(Process headP, Process toRemove) {
          // delete a first process
          if (headP == toRemove) {
               headP = headP.next;
               //headP.prev = null;
          } // delete last process
          else if (toRemove == tail) {
               tail.prev.next = null;
          } else {
               toRemove.prev.next = toRemove.next;
               toRemove.next.prev = toRemove.prev;
          }
          return headP;
     }

     public void calculateTurnaroundTime() {
          turnaroundTime = completionTime - arrivelTime;
          calculateWaitingTime();
     }

     public void calculateWaitingTime() {
          waitingTime = turnaroundTime - burstTime;
     }

     public void calculateResponseTime(int time) {
          if (IsFirstTime) {
               responseTime = time - arrivelTime;
               IsFirstTime = false;
          }
     }

     public void appendProcessInfo(int completionTime) {
          this.completionTime = completionTime;
          appendProcessInfo();
     }

     // Method to add a specific process's information to processesInfo
     public void appendProcessInfo() {
          calculateTurnaroundTime();
          processesInfo += String.format("%-12s %-12d %-12d %-15d %-17d %-15d\n",
                  name, arrivelTime, burstTime, waitingTime, turnaroundTime, responseTime);
          CPUScheduling.AVGTurenedaroundTime = CPUScheduling.AVGTurenedaroundTime + turnaroundTime;
          CPUScheduling.AVGWaitingTime = CPUScheduling.AVGWaitingTime + waitingTime;
     }

     // Getter for processesInfo
     public static String getProcessesInfo() {
          // Header row
          StringBuilder header = new StringBuilder();
          header.append(String.format("%-12s %-12s %-12s %-15s %-17s %-15s\n",
                  "Name", "ArrivalTime", "BurstTime", "WaitingTime", "TurnaroundTime", "ResponseTime"));
          header.append("==========================================================================================\n");

          return header.toString() + processesInfo;
     }

     public static String toStringLinkedList(Process head) {
          StringBuilder sb = new StringBuilder();

          // Header row
          sb.append(String.format("%-12s %-12s %-12s %-15s %-17s %-15s %-20s\n",
                  "Name", "ArrivalTime", "BurstTime", "WaitingTime",
                  "TurnaroundTime", "ResponseTime", "Rounds"));

          // Separator line
          for (int i = 0; i < 90; i++) {
               sb.append("=");
          }
          sb.append("\n");

          // Traverse and format each process
          Process current = head;
          while (current != null) {
               sb.append(String.format("%-12s %-12d %-12d %-15d %-17d %-15d %-20s\n",
                       current.name, current.arrivelTime, current.burstTime,
                       current.waitingTime, current.turnaroundTime,
                       current.responseTime, current.rounds.toString()));
               current = current.next;
          }

          return sb.toString();
     }

     public ArrayList<Integer> getRounds() {
          return rounds;
     }

     public void setRounds(ArrayList<Integer> rounds) {
          this.rounds = rounds;
     }

     public Process getNext() {
          return next;
     }

     public void setNext(Process next) {
          this.next = next;
     }

//     public int getNumberOfRounds() {
//          return numberOfRounds;
//     }
//
//     public void setNumberOfRounds(int numberOfRounds) {
//          this.numberOfRounds = numberOfRounds;
//     }
     public Process getPrev() {
          return prev;
     }

     public void setPrev(Process prev) {
          this.prev = prev;
     }

     public static Process getTail() {
          return tail;
     }

     public static void setTail(Process tail) {
          Process.tail = tail;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public int getArrivelTime() {
          return arrivelTime;
     }

     public void setArrivelTime(int arrivelTime) {
          this.arrivelTime = arrivelTime;
     }

     public int getBurstTime() {
          return burstTime;
     }

     public void setBurstTime(int burstTime) {
          this.burstTime = burstTime;
     }

     public int getWaitingTime() {
          return waitingTime;
     }

     public void setWaitingTime(int waitingTime) {
          this.waitingTime = waitingTime;
     }

     public int getTurnaroundTime() {
          return turnaroundTime;
     }

     public void setTurnaroundTime(int turnaroundTime) {
          this.turnaroundTime = turnaroundTime;
     }

     public int getResponseTime() {
          return responseTime;
     }

     public void setResponseTime(int responseTime) {
          this.responseTime = responseTime;
     }

}
