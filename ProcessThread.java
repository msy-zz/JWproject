import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ProcessThread extends Thread{
Socket minetSocket;
final static String VERSION = "CS1.0";
ArrayList<User> onlineUserList;

public ProcessThread(Socket socket, ArrayList<User> onlineList){
minetSocket = socket;
//还没考虑onlineList的多线程操作
onlineUserList = onlineList;
//the thread start
//start();
run();
}

@Override
public void run(){
//用于测试arraylist
/*
InetAddress i;
try {
i = InetAddress.getByName("www.baidu.com");
User user = new User(i, 1999, "daisy");
onlineUserList.add(user);
} catch (UnknownHostException e2) {
// TODO Auto-generated catch block
e2.printStackTrace();
}*/
//用于接受用户请求报文
String data = null;
BufferedReader inFromClient = null;
DataOutputStream outToClient = null;
PrintWriter printWriter = null;
try {
printWriter = new PrintWriter(minetSocket.getOutputStream(), true);
} catch (IOException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
try {
inFromClient = new BufferedReader(new InputStreamReader(
minetSocket.getInputStream()));
//while((data = inFromClient.readLine()) != null)
//printWriter.println(data);

//读取头部行信息,读取报文第一行
data = inFromClient.readLine();
String[] head = data.split(" ");
if(head[0].equals("MINET")){
data = "handshake" + '\n';
//实现握手命令
}
else if(head[0].equals(VERSION)){
//登录报文
if(head[1].equals("LOGIN")){
data = "login success" + '\n';
login(head[2]);
}
//获取在线用户列表报文
else if(head[1].equals("GETLIST")){
data = "login success" + '\n';
getList();
}
//离开报文
else if(head[1].equals("LEAVE")){
leave();
}
//用户群发信息
else if(head[1].equals("MESSAGE")){
sendMessage();
}
//用户发送心跳信息
else if(head[1].equals("BEAT")){
beat();
}
}
printWriter.println(data);
//作为响应报文返回给用户
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} finally {
try {
inFromClient.close();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
printWriter.close();
close();
}
}

public void close(){
try {
minetSocket.close();
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

//自己定义参数和返回值，处理相应报文，封装在outData中
private void login(String username){
  //error表示登录类型，0为用户名不存在可用，1为用户名存在且ip和端口都符合
	//可正常登录，2为用户名已存在但ip和端口不符，无法登录
	int error = 0;           
	if(onlineUserList.isEmpty()){}               //用户列表为空则用户名不存在

	/*若用户列表不为空，遍历数组寻找是否有相同的用户名
	   */
	else{
		for(int i = 0;i < onlineUserList.size();i++)
		{   
			/*如果有相同用户名，判断用户的ip和端口是否和记录中该用户名的IP和端口相同
			  *相同则可登录，error为1，不同则用户名重复，无法登录，error为2
			*/
			if(onlineUserList.get(i).getName == username){
				if (onlineUserList.get(i).getIPAddress() == minetSocket.getInetAddress() && onlineUserList.get(i).getPort() == minetSocket.getPort())
					error = 1;
				else
					error = 2;
				//找到用户名以后设置i的大小使之跳出循环
				i = onlineUserList.size();
			}
		}
	}

	/*用户名不存在则添加该用户到onlineUserList中并显示登录成功
	   */
	if(error == 0)
	{
		InetAddress ip = minetSocket.getInetAddress();
		int port = minetSocket.getPort();
		User user = new User(ip,port,username);
		onlineUserList.add(user);
		//返回报文
	}
	if(error == 1)
		//返回报文
	if(error == 2)
		//返回报文
}
//怎么返回报文还没想好

private void getList(){
}

private void leave(){
}

private void sendMessage(){
}

private void beat(){
}
}
