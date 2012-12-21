import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class TCPServer {
public static void main(String argv[]) throws Exception{
//create a thread pool of size 20 for efficiency
ExecutorService threadPool = Executors.newFixedThreadPool(20);
ServerSocket miroSocket = null;
Socket minetSocket = null;
int serverPort = 6789;
//store online users, think about it
ArrayList<User> onlineUserList = new ArrayList<User>();

try{
miroSocket = new ServerSocket(serverPort);
while(true){
minetSocket = miroSocket.accept();

//start a new thread to process each user's request
Thread thread = new ProcessThread(minetSocket, onlineUserList);
threadPool.execute(thread);
}
} catch (Exception exception){
exception.printStackTrace();
} finally {
try{
miroSocket.close();
threadPool.shutdown();
} catch (Exception exception){}
}
}
}
