import java.io.*;
import java.net.*;

public class TCPClient {
public static void main(String argv[]) throws Exception{
String sentence = null;
String modifiedSentence = null;

BufferedReader inFromUser = new BufferedReader(
new InputStreamReader(System.in));
//²âÊÔÊ±¸ÄÎª×Ô¼ºµÄipµØÖ·
Socket clientSocket = new Socket("172.18.66.166",6789);

BufferedReader inFromServer = new BufferedReader(
new InputStreamReader(clientSocket.getInputStream()));

sentence = inFromUser.readLine() + "\r\n";

OutputStream socketOut = clientSocket.getOutputStream();
        socketOut.write(sentence.getBytes());
        
//outToServer.writeBytes(sentence + '\n');
modifiedSentence = inFromServer.readLine();
System.out.println("FROM SERVER: " + modifiedSentence);

clientSocket.close();
}
}
