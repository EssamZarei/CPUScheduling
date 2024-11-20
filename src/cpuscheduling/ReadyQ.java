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
public class ReadyQ {
     
     private Process process;
     private int duration;
     
     private ReadyQ next;
     private static ReadyQ head;
     private static ReadyQ tail;
          
     public ReadyQ(){
          this.head = this;
          this.tail = this;
          this.next = null;
          this.duration = 0;
     }

     public ReadyQ(Process process, int duration) {
          this.process = process;
          this.duration = duration;
     }
     
     
     public void addReadyQ(Process tempProcess, int duration){
          ReadyQ tempReadyQ = new ReadyQ(tempProcess, duration);
          if (head.next == null){
               head.next = tempReadyQ;
               head.tail = tempReadyQ;
          }else{
               head.tail.next = tempReadyQ;
               head.tail = tempReadyQ;
          }
     }
     
     public ReadyQ getReadyQ(){
          if(head.next == null){
               return  null;
          }else{
               ReadyQ tempReadyQ = head.next;
               head.next = head.next.next;
               return tempReadyQ;
          }
     }
     
     // a method to check a specific process not already have a place in the ReadyQ
     public boolean foundProcess(Process p){
          ReadyQ temp = head.next;
          while(temp != null){
               if(p == temp.getProcess()){
                    return true;
               }
               else{
                    temp = temp.getNext();
               }
          }
          return false;
     }
     

     public Process getProcess() {
          return process;
     }

     public void setProcess(Process process) {
          this.process = process;
     }

     public ReadyQ getNext() {
          return next;
     }

     public void setNext(ReadyQ next) {
          this.next = next;
     }

     public static ReadyQ getHead() {
          return head;
     }

     public static void setHead(ReadyQ head) {
          ReadyQ.head = head;
     }

     public static ReadyQ getTail() {
          return tail;
     }

     public static void setTail(ReadyQ tail) {
          ReadyQ.tail = tail;
     }

     public int getDuration() {
          return duration;
     }

     public void setDuration(int duration) {
          this.duration = duration;
     }
     
     
     
}
