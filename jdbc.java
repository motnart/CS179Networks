//STEP 1. Import required packages
import java.sql.*;
import java.io.*;
import java.util.Date;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class jdbc {
   // JDBC driver name and database URL

private Connection _connection = null;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
static final String DB_URL = "jdbc:mysql://localhost/master";

static Socket[] DoorWrite = new Socket[20];

public jdbc () throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         System.out.println ("Connection URL: " + DB_URL + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(DB_URL, "root", "siu3u-h8be38");
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.exit(-1);
      }//end catch
   }//end EmbeddedSQL




public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

public void executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       // iterates through the result set and count nuber of results.

   }



public void printQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
    

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
        
      }//end while
      stmt.close ();
   }



 public boolean authenticate (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      if(rs.next())return true;
      else return false;
   }

public String getaccess (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      rs.next();
      String access = rs.getString (2); 
   
      stmt.close ();
      return access;
   }

   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup
   
   public static void main(String[] args) throws Exception{


ServerSocket readSocket = new ServerSocket(46801);
ServerSocket writeSocket = new ServerSocket(43849);
System.out.println("Listening...");

while(true){

new ServerThread(readSocket.accept(),writeSocket.accept()).start();

}
}
}

class ServerThread extends Thread{


public static String createMessage () throws Exception
{
final int MessageLength = 16;

char [] randomMessage = new char [MessageLength];

for (int i = 0; i < MessageLength; i++)
{
double newNum = Math.random() * 92 + 33;
int castNewNum = (int)newNum;
char intToAscii = (char) castNewNum;
//System.out.println(intToAscii);
randomMessage [i] = intToAscii;
}

String finalMessage = new String (randomMessage);

return finalMessage;

}


	private Socket readSock;
	private Socket writeSock;

 public ServerThread(Socket read, Socket write) {
//Socket readSock = readSocket.accept();
	super("ServerThread");

        this.readSock = read;
	this.writeSock=write;
//Socket writeSock = writeSocket.accept();
	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	

        
System.out.println(readSock.getRemoteSocketAddress());
System.out.println("Client Connected!");
//System.out.println( sdf.format(cal.getTime()) );
System.out.println(writeSock.getRemoteSocketAddress());
System.out.println("Client Connected!");
System.out.println( sdf.format(cal.getTime()) );
    }

   jdbc conn = null;
 String userID=null;
String password=null;
String access=null;
String doorchoice=null;

public void run() {


try{

BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
BufferedReader inFromClient = new BufferedReader(new InputStreamReader(readSock.getInputStream()));
PrintWriter outToClient = new PrintWriter(writeSock.getOutputStream(),true);
//){


Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection

      conn = new jdbc();

    userID = inFromClient.readLine();
System.out.println(userID);

    //System.out.print("Password: ");
password = inFromClient.readLine();
System.out.println(password);

if(userID.substring(0,4).equals("door"))
{

System.out.println("This is a door");
int actualdoor=Integer.parseInt(userID.substring(4));
//inFromDoor = new BufferedReader(new InputStreamReader(doorRead.getInputStream()));
int arrayloc;
if(actualdoor>=200)arrayloc=actualdoor-190;
else arrayloc=actualdoor-100;
conn.DoorWrite[arrayloc]=writeSock;

         try{
            if(conn != null) {
               System.out.print("Disconnecting from database...");
		conn.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            System.err.println (e.getMessage ());
         }//end try
      
}

else{


doorchoice = inFromClient.readLine();
System.out.println(doorchoice);


PrintWriter outToDoor = null;
int actualdoor=Integer.parseInt(doorchoice);
//inFromDoor = new BufferedReader(new InputStreamReader(doorRead.getInputStream()));
int arrayloc;
if(actualdoor>=200)arrayloc=actualdoor-190;
else arrayloc=actualdoor-100;
      

      //STEP 4: Execute a query
      //System.out.println("Creating database...");
      //stmt = conn.createStatement();
      
      String test1 = "SELECT UserID,access FROM Users WHERE UserID='"+userID+"' AND password='"+password+"';";
	if(!conn.authenticate(test1)){
      	outToClient.write("1\n");
	outToClient.flush();
	readSock.close();
	writeSock.close();
	System.out.println("Invalid credentials");
//outToClient.close();
	}

else{
      String test2 = "SELECT * FROM Doors WHERE DoorID="+doorchoice+";";
access=conn.getaccess(test1);
      String test3 = "SELECT * FROM (SELECT DoorID FROM GenDoors WHERE access="+access+" UNION ALL SELECT DoorID FROM SpecDoors WHERE UserID='"+userID+"') as t1 WHERE DoorID="+doorchoice+";";
      String test4 = "SELECT * FROM Doors WHERE DoorID="+doorchoice+" AND status = 0;";
      String test5 = String.format("SELECT * FROM Users WHERE userID='%s' AND password='%s' AND loggedin=0",userID,password);

try{
readSock.setSoTimeout(20000);
if(!conn.authenticate(test5))
{
outToClient.write("6\n");
outToClient.flush();
readSock.close();
writeSock.close();
System.out.println("Already logged in");
}

else if(!conn.authenticate(test2))
{
      outToClient.write("2\n");
outToClient.flush();
readSock.close();
writeSock.close();
System.out.println("Not a door");

}

else if(!conn.authenticate(test3))
{
      outToClient.write("3\n");
outToClient.flush();
readSock.close();
writeSock.close();
System.out.println("You do not have access to this door!");

}

else if(!conn.authenticate(test4)){
      outToClient.write("4\n");
outToClient.flush();
readSock.close();
writeSock.close();
System.out.println("Door is open");
//outToClient.close();
}

else if(conn.DoorWrite[arrayloc]==null || conn.DoorWrite[arrayloc].isClosed())
{
outToClient.write("5\n");
outToClient.flush();
readSock.close();
writeSock.close();
System.out.println("Door is unavailable");
}

else
{
      outToClient.write("0\n");
outToClient.flush();
System.out.println("Valid");
String query=String.format("UPDATE Users SET loggedin=1 WHERE userID='%s'",userID);
conn.executeUpdate (query);
String random = createMessage();
System.out.println(random);

Socket TempDoorWrite = conn.DoorWrite[arrayloc];
outToDoor = new PrintWriter(TempDoorWrite.getOutputStream(),true);
outToDoor.write(random+'\n');
outToDoor.flush();
System.out.println("Waiting for scan...");
String QRscan = inFromClient.readLine();
System.out.println(QRscan);
readSock.close();
writeSock.close();
if(QRscan.equals(random)){
outToDoor.write("0\n");
outToDoor.flush();
query=String.format("UPDATE Doors SET status=1 WHERE DoorID=%s",doorchoice);
conn.executeUpdate (query);
System.out.println(doorchoice + " Unlocked!");
Thread.sleep(10000);
query=String.format("UPDATE Doors SET status=0 WHERE DoorID=%s",doorchoice);
conn.executeUpdate (query);
System.out.println(doorchoice + " Locked!");
}
else{
System.out.println("Wrong code!");
outToDoor.write("1\n");
outToDoor.flush();
}


}
}
catch(SocketTimeoutException e){
System.out.println("Client disconnected");
String query=String.format("UPDATE Users SET loggedin=0 WHERE userID='%s'",userID);
conn.executeUpdate (query);
outToDoor.write("1\n");
outToDoor.flush();
readSock.close();
writeSock.close();
}
}

         try{
	String query=String.format("UPDATE Users SET loggedin=0 WHERE userID='%s'",userID);
conn.executeUpdate (query);
            if(conn != null) {
               System.out.print("Disconnecting from database...");
               conn.cleanup ();
               System.out.println("Done\n\nBye !");
		readSock.close();
		writeSock.close();
            }//end if
         }catch (Exception e) {
            System.err.println (e.getMessage ());
         }//end try
}


      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }

}//end JDBCExample
}


