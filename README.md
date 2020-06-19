# Distributed-System-Vector-Clock
Created client server application to demonstrate vector clock concept to synchronize the servers in Distributed System.

• Implemented a message service consisting of a server process and three clients. Each client process will connect to the server over a socket. The server would be able to handle all three clients concurrently

• Clients will maintain vector clocks, and the messages exchanged via the message service will be constrained exclusively to those vector clocks

• Each client will maintain a vector clock. Every two to ten seconds, each client will randomly choose one other client (e.g., a unicast) and send that client its vector clock

• When a client sends a message, it will print the intended recipient of the message, as well as its updated vector clock, to its respective GUI. When a client receives a message, it will update and print its vector clock. This is how it maintains synchronization by using vector clocks

• Technology and concepts: Java, JavaFX, Multithreading, Synchronization in the distributed system, Vector clock.

Steps: 

❖	Open on IntelliJ IDE or other IDE

❖	Set up JDK 11

❖	Run server: Go to the server folder, find server.java file and run it.

❖	It will open server UI

❖	Click on connect to start the server

❖	Client: Open 3 of the client folder on 3 different IntelliJ windows one after one and Select the username and produce the output as describe in requirements. 

❖	Find Main.java file in each client’s windows and run it. 

❖	Select the new username 

❖	Fetch the Vector clock’s updated data from button.

I used some or learned code from the following websites. 

https://www.geeksforgeeks.org/socket-programming-in-java/

https://way2java.com/networking/chat-program-two-way-communication/

https://www.callicoder.com/javafx-registration-form-gui-tutorial/

https://www.geeksforgeeks.org/multi-threaded-chat-application-set-1/

https://www.javatpoint.com/javafx-checkbox

https://www.javatpoint.com/how-to-create-a-file-in-java
