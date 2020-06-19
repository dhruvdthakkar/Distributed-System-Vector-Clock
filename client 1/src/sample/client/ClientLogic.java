
/*
 * Name: Dhruv Dhananjaybhai Thakkar
 * Id: 1001815888
 *
 * */
package sample.client;

import java.io.*;
import java.net.Socket;

// class for establish connection to the server and communicate to server
public class ClientLogic {
    // All are class level variable
    static BufferedReader receiveRead = null;
    // message for random data
    static String message;
    // port address
    String address;
    int port;
    // writer
    static PrintWriter pwrite = null;
    // Buffer reader for sending data
    BufferedReader keyRead = null;
    // socket
    Socket s;

    // set the address and port from server connection
    ClientLogic(String address, int port) {
        this.address = address;
        this.port = port;
    }

    // method for receiving any message from server
    public static String receiveMessage() throws IOException {
        // read the data
        message = receiveRead.readLine();
        // If server sends close message
        /*if (message.equals("close")) {
            return "close";
        } else {
            // else return message
            return message;
        }*/
        return message;
    }

    // method for receiving any message from server
    public static String clientNotHere() throws IOException {
        // read the data
        message = receiveRead.readLine();
        // return message
        return message;
    }

    // Establish first time connection to the server
    public void start() {
        try {
            // create temporary socket
            s = new Socket(address, port);

            // Get output stream
            OutputStream ostream = s.getOutputStream();

            // Get PrintWriter Object
            pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
            InputStream istream = s.getInputStream();
            receiveRead = new BufferedReader(new InputStreamReader(istream));

            // Get new port number from server for new connection for message communication
            int newPort = Integer.parseInt(receiveRead.readLine());

            // set new connection
            s = new Socket(address, newPort);

            // Get output stream
            ostream = s.getOutputStream();

            // Get PrintWriter Object
            pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
            istream = s.getInputStream();
            receiveRead = new BufferedReader(new InputStreamReader(istream));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // write data to server for check username
    public void checkIn(String userName) {
        pwrite.println(userName);
        pwrite.flush();
    }

    // write data to server for close connection
    public static void sendCloseSignal() {
        pwrite.println("::closeIt::");
        pwrite.flush();
    }

    // write data to server for send message
    public static void sendMessage(String s) {
        pwrite.println(s);
        pwrite.flush();
    }

    // Receive response from client
    public String receiveResponse() throws IOException {
        // read number of client from server
        String clientNumberInString = receiveRead.readLine();
        int clientNumber;
        // If close, terminate
        if (clientNumberInString.equals("close")) {
            return "close";
        } else {
            clientNumber = Integer.parseInt(clientNumberInString);
        }

        // Read data from server and put into clients Hashmap for reference
        String dataFromClient = "";
        for (int i = 0; i < clientNumber; i++) {
            try {
                // read data
                dataFromClient = receiveRead.readLine();
                // put into Hash map
                Main.selectClientForMessage.put(dataFromClient, false);
                System.out.println("Data received from server! " + s.getPort() + dataFromClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // return data from client
        return dataFromClient;
    }
}
