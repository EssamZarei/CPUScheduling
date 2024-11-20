

CPU Scheduling: Round Robin

Overview
This project implements the **Round Robin (RR)** CPU Scheduling algorithm. The RR algorithm is a preemptive scheduling approach that assigns a fixed time slice (quantum) to each process, ensuring fair CPU time allocation. It simulates how processes are scheduled, calculates performance metrics (waiting time, turnaround time, response time), and handles wasted CPU time.

---

Features
- Implements the Round Robin scheduling mechanism with customizable quantum time.
- Supports dynamic addition of processes with attributes like:
  - **Process Name**
  - **Arrival Time**
  - **Burst Time**
- Tracks and displays:
  - Waiting Time
  - Turnaround Time
  - Response Time
- Visualizes the CPU scheduling timeline and processes' execution order.
- Handles **wasted CPU time** when no process is ready for execution.

---

Modules and Classes
   1. Process
   - Represents a process in the system.
   - Attributes:
     - Name
     - Arrival Time
     - Burst Time
     - Waiting Time
     - Turnaround Time
     - Response Time
   - Methods:
     - Calculates process metrics.
     - Tracks process rounds and completion.
     - Stores and displays detailed process execution information.

   2. CPUQ
   - Simulates the CPU execution queue.
   - Attributes:
     - Tracks the processes currently being executed.
     - Handles wasted CPU time.
   - Methods:
     - Adds processes to the CPU queue.
     - Checks if the CPU is busy at any given time.
     - Prints the CPU execution timeline.

   3. ReadyQ
   - Manages the ready queue of processes waiting for execution.
   - Methods:
     - Adds processes to the ready queue.
     - Removes and retrieves processes for execution.
     - Prevents duplicate entries in the ready queue.

  4. CPUScheduling
   - Main program logic and entry point.
   - Handles user input for processes and quantum.
   - Implements the Round Robin algorithm.
   - Methods:
     - RR: Core Round Robin scheduling implementation.
     - validateInt and validateString: Utility methods for robust input validation.
     - headProcess: Initializes the list of processes based on user input.

---

  How It Works
1.   User Input:
   - Enter the CPU quantum time.
   - Enter the number of processes and their details (name, arrival time, burst time).

2.   Simulation:
   - The program schedules processes in a round-robin fashion using the quantum time.
   - Calculates and displays metrics like waiting time, turnaround time, and response time.

3.   Output:
   - Detailed process information in tabular format.
   - CPU timeline showing process execution order and wasted time.

---

  Example Execution
- Input:
  - Quantum: 3
  - Number of processes: 4
  - Process details:  
    
    P1: Arrival Time = 0, Burst Time = 6  
    P2: Arrival Time = 1, Burst Time = 4  
    P3: Arrival Time = 2, Burst Time = 5  
    P4: Arrival Time = 3, Burst Time = 7  
    

- Output:
  -   CPU Timeline:
    
    0 --[P1]--> 3 --[P2]--> 6 --[P3]--> 9 --[P4]--> 12 --[P1]--> 15 --[P4]--> 16
    
  - **Process Metrics:**
    
    Name         ArrivalTime   BurstTime   WaitingTime   TurnaroundTime   ResponseTime
    P1           0             6           9             15              0
    P2           1             4           8             12              2
    P3           2             5           9             14              7
    P4           3             7           10            17              12
    

---

  How to Run
1. Compile all the Java files:
   
   javac *.java
   
2. Run the main program:
   
   java cpuscheduling.CPUScheduling
   
3. Follow the prompts to enter the quantum and processes.

---

  Customization
- Quantum Time: Modify directly during runtime.
- Number of Processes: Add more or fewer processes during the input phase.
- Process Details: Customize arrival and burst times as needed.

---

Limitations
- Does not handle processes with zero burst time (needs positive burst values).
- Assumes all processes are input at the start (no dynamic addition after execution begins).

---
