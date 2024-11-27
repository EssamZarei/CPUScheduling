/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuscheduling;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author eaz99
 */
public class CPUScheduling {

     /**
      * @param args the command line arguments
      */
     static Scanner in = new Scanner(System.in);
     static int time = 0;
     static int doneProcess = 0;

     static Process headProcess = null;

     static CPUQ cpuQ = new CPUQ();
     static ReadyQ readyQ = new ReadyQ();
     static Process fakeHeadProcess = null;
     static Process temp4ArriveTime = null;

     public static void main(String[] args) {
          // TODO code application logic here

          System.out.println("Hi, Welcome to RR CPU Scheduling");

          // Getting The Quantom of the CPU
          System.out.println("Please enter the Quantom for the CPU");
          int quantom = validateInt(false); // false mean can not be zero

          // getting the number of processes
          System.out.println("Please Enter The Number of Processes");
          int processesNum = validateInt(false); // false mean can not be zero
          
          System.out.println("---\t---\t---\t---\t---\n");
               
          // Getting information of the processes
          headProcess = headProcess(quantom, processesNum);

          System.out.println(Process.toStringLinkedList(headProcess));
          // 2 3 p1 0 3 p2 2 4 p3 4 2
          // 0 --[p1]--> 2 --[p2]--> 4 --[p3]--> 6 --[p1]--> 7 --[p2]--> 9

          // 3 4   p1 1 5   p2 2 4   p3 4 6   p4 20 4
          // 0 --[X Wasted Time X]--> 1 --[p1]--> 4 --[p2]--> 7 --[p3]--> 10 --[p1]--> 12 --[p2]--> 13 --[p3]--> 16 --[X Wasted Time X]--> 17 --[X Wasted Time X]--> 18 --[X Wasted Time X]--> 19 --[X Wasted Time X]--> 20 --[p4]--> 23 --[p4]--> 24
          // 3 4   p1 1 5   p2 0 4   p3 20 8   p4 3 6
          // 0 --[p2]--> 3 --[p4]--> 6 --[p1]--> 9 --[p2]--> 10 --[p4]--> 13 --[p1]--> 15 --[X Wasted Time X]--> 16 --[X Wasted Time X]--> 17 --[X Wasted Time X]--> 18 --[X Wasted Time X]--> 19 --[X Wasted Time X]--> 20 --[p3]--> 23 --[p3]--> 26 --[p3]--> 28
          // 2 5  p1 0 5    p2 1 3   p3 2 2   p4 3 2  p5 4 3
          // Start Round Robin Scheduilng
          // 3 5  p1 0 8    p2 5 2   p3 1 7   p4 6 3  p5 8 5
          // 2 5  p1 0 5    p2 1 3   p3 2 1   p4 3 2  p5 4 3
          
          RR(headProcess, processesNum, quantom);

          System.out.println("\n\n\n");
          //queue.printTimeline();
          //printAVG(processesNum);

     }

     public static void RR(Process headProcess, int processesNum, int quantom) {

          // 2 5  p1 0 5    p2 1 3   p3 2 1   p4 3 2  p5 4 3
          fakeHeadProcess = headProcess;

          //System.out.println(Process.toStringLinkedList(headProcess));
          while (doneProcess != processesNum) {
               // To Track The Current Time
               //System.out.println("Time   ===   " + time);

               // create a temp to not loose the correct pointer
               temp4ArriveTime = fakeHeadProcess;

               // Ready Q Running
               ReadyQRunning(processesNum);

               // CPU Running
               CPURunning(quantom, processesNum);

               // To Track The CPU Q After Each Time
               //cpuQ.printTimeline();
               time++;
               //System.out.println("\n\n\n");

          }
          System.out.println(cpuQ.printTimeline() + "\n");
          System.out.println(Process.getProcessesInfo());

     }

     public static void ReadyQRunning(int processesNum) {

          boolean inCPUQ = false;
          boolean inReadyQ = false;
          // to get any arrived process and put in in the ready Q
          // if we have original 5 process and done 2 so there is remainning 3 processes to check
          for (int i = 0; i < (processesNum - doneProcess); i++) {

               // To Track The Current process That IN Check
               //System.out.println("Check Process : " + temp4ArriveTime.getName());
               // here mean found a process arrived 
               /*
               // we need 2 method
               // first too check that in the ready Q only a one round for each single process
               // to check also this process currently not in the CPUQ processing
                */
               // boolean if the process that in check in the CPU Q running and processing
               inCPUQ = cpuQ.foundProcess(temp4ArriveTime, time);
               // boolean if the process already have a round prepared in the Ready Q
               inReadyQ = readyQ.foundProcess(temp4ArriveTime);

               // // To Track The State of the IN CPU  &  IN Ready Q  variables
               //System.out.println("In CPU Q ? " + inCPUQ + "\t\tIn Ready Q ? " + inReadyQ);
               if (inCPUQ || inReadyQ) {
                    // do not let this process to have a round
                    // because it is running in the CPU Q
                    // or it is already in the Ready Q
                    // so let other next process get a chance
                    // To Track The Already IN CPU Q Running AND Processing  OR  IN The Ready Q Already
                    //System.out.println("The Process Already In The    CPU      OR     Ready Q    :  " + temp4ArriveTime.getName());
               } else {
                    // here mean a process can be in the Ready Q
                    // here to check Arrive Time for each process
                    if (time >= temp4ArriveTime.getArrivelTime()) {
                         // take the duration of the process for next round

                         // To Track The use of the get Round method
                         //System.out.println("***           getRound Used for Process           ***\t" + temp4ArriveTime.getName());
                         int duration = temp4ArriveTime.getRound();
                         // if the duration of this process = -1 mean it finished need to be deleted
                         if (duration != -1) {
                              readyQ.addReadyQ(temp4ArriveTime, duration);
                         } else {
                              // this method will take the completion time
                              // then use other methods to calsulater the Arrival & Waiting Time
                              // then append the information of the process to the static String
                              // before deleting the process
                              temp4ArriveTime.appendProcessInfo(time);
                              // delete a process that finished and do not have emainning rounds
                              fakeHeadProcess = fakeHeadProcess.rempveProcess(fakeHeadProcess, temp4ArriveTime);
                              doneProcess++;
                         }

                    } else {

                    }

               }
               // To Track The Number OOF Done Proccesses
               //System.out.println("Done Processes : " + doneProcess);
               // get next process to check
               temp4ArriveTime = temp4ArriveTime.getNext();
          }
     }

     public static void CPURunning(int quantom, int processesNum) {

          ReadyQ tempReadyQ;
          int duration;

          // mean the CPU has a process in current time
          // if -1 mean it is first node for contolling    OR   last process in Q is done
          // so the CPU ready to get from the Q
          if (cpuQ.getTail().getEndTime() == -1 || cpuQ.getTail().getEndTime() <= time) {
               // this if (readyQ.getNext() == null && CPUQ.CPUBusy(time) && doneProcess != processesNum) 
               // mean that we have waste rounds of the CPU and in the same time there is a process need it but not arrived yet
               // readyQ.getNext() == null  we have to check no one in the Ready Q want the CPU
               // CPUQ.CPUBusy(time)  we have to know if the CPU have a Process round in current time
               // doneProcess != processesNum  we do not want to add waste time when already all processes finished
               if (readyQ.getNext() == null && CPUQ.CPUBusy(time) && doneProcess != processesNum) {
                    cpuQ.addCPUQ(null, time, (time + quantom - 1));
                    // To Track The Wasted Time
                    //System.out.println("#############################   Time = " + time + "\t\t Quantom = " + quantom + "\t\t sum = " + (time+quantom));
               } // here we will add the process to the CPU Q
               else {

                    // remove a round from Ready Q
                    // and take it as a temp
                    tempReadyQ = readyQ.getReadyQ();
                    // if not null continue
                    if (tempReadyQ != null) {
                         // take the duration
                         duration = tempReadyQ.getDuration();
                         Process tempP = tempReadyQ.getProcess();
                         tempP.calculateResponseTime(time);
                         // add a round to the CPU
                         cpuQ.addCPUQ(tempP, time, (time + duration));
                    }
               }

          } // CPU has a process
          // do not take from the Ready Q
          else {

          }

     }

    public static int validateInt(boolean canZero) {
        
         int greater =  canZero ? -1 : 0 ;
         
        int number;
        while (true) {
            System.out.print("Enter a must be greater than " + greater + " : ");
            try {
                number = in.nextInt();
                if (number > greater) { // Ensure the number is positive
                    return number;
                } else {
                    System.out.println("Error: The number must be greater than " + greater + ". Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid integer.");
                in.next(); // Clear invalid input
            }
        }
    }

    // Method to validate a non-empty string
    public static String validateString() {
        String input;
        while (true) {
            System.out.print("Enter a non-empty string: ");
            input = in.next(); // Trim whitespace
            if (!input.isEmpty()) { // Ensure string is not empty
                return input;
            } else {
                System.out.println("Error: The string cannot be empty. Please try again.");
            }
        }
    }
    
     public static Process headProcess(int quantom, int processesNum) {

          String name; // for process name
          int arrivelTime;  // when process arrived
          int burstTime;  // required quantom for the process

          Process headProcess = null;

          // creating objects for the processes
          for (int i = 0; i < processesNum; i++) {
               System.out.println("Please Enter Information for Process " + (i + 1));
               System.out.println("Please Enter The Name");
               name = validateString();

               System.out.println("Please Enter The Arrivel Time");
               arrivelTime = validateInt(true); // true mean can be zero

               System.out.println("Please Enter the Burst Time");
               burstTime = validateInt(false); // false mean can not be zero

               // if null mean first process
               if (headProcess == null) {
                    headProcess = new Process(name, arrivelTime, burstTime, quantom);
               } // else mean add it as a tail using addProcess method
               else {
                    headProcess.addProcess(name, arrivelTime, burstTime, quantom);
               }
               System.out.println("---\t---\t---\t---\t---\n");
          }

          return headProcess;
     }
     
     public static double AVGTurenedaroundTime = 0;
     public static double AVGWaitingTime = 0;
     
     public static void printAVG(int processNum){
          System.out.println("\n\nAVG Turnaround time: " + (AVGTurenedaroundTime/processNum));
          System.out.println("AVG Waiting Time: " + (AVGWaitingTime/processNum));
     }
}
