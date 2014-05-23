import java.io.*;
import java.net.*;

class TCPClient
{
 public static void main(String argv[]) throws Exception
 {
  String sentence;
  String fromserver;
  String userID;
  String password;
int success;

BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  Socket clientSocket = new Socket("169.235.31.177", 46801);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    System.out.print("User ID: ");
    userID = input.readLine();
    System.out.print("Password: ");
    password = input.readLine();

//fromserver = inFromServer.readLine();
//System.out.println("aaaaa");
  //System.out.println(fromserver);
  //sentence = inFromUser.readLine();
  outToServer.writeBytes(userID+'\n');

  outToServer.writeBytes(password+'\n');
  success = inFromServer.read();
if(success==0)
{
  System.out.println("Credientials Do Not Match!");
  System.exit(0);
}
else
System.out.println("Successfully Authenticated!");
{
while(true)
{
}
}
  //sentence = inFromUser.readLine();
  //outToServer.writeBytes(sentence);
//fromserver = inFromServer.readLine();
  //System.out.println(fromserver);
 }
}
