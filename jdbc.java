//STEP 1. Import required packages
import java.sql.*;
import java.io.*;
import java.util.Date;
import java.net.*;



public class jdbc {
   // JDBC driver name and database URL

private Connection _connection = null;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
static final String DB_URL = "jdbc:mysql://localhost/master";
static BufferedReader inFromDoor=null;
static PrintWriter outToDoor=null;

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

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
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

  /*public int getrowcount (String query) throws SQLException {
int count=0;
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      while (rs.next()){
  count = rs.getInt(1);
  }
return count;
   }*/

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
   
   public static void main(String[] args) throws Exception{

ServerSocket readSocket = new ServerSocket(46801);
ServerSocket writeSocket = new ServerSocket(43849);
System.out.println("Listening...");
Socket doorRead = readSocket.accept();
Socket doorWrite = writeSocket.accept();
System.out.println(doorRead.getRemoteSocketAddress());
System.out.println("Door Connected!");
System.out.println(doorWrite.getRemoteSocketAddress());
System.out.println("Door Connected!");
inFromDoor = new BufferedReader(new InputStreamReader(doorRead.getInputStream()));
outToDoor = new PrintWriter(doorWrite.getOutputStream(),true);
while(true){

new ServerThread(readSocket.accept(),writeSocket.accept()).start();

}
}
}

class ServerThread extends Thread{

 //  Database credentials
   //static final String USER = "username";
   //static final String PASS = "password";
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

//Socket writeSock = writeSocket.accept();

        super("ServerThread");
        this.readSock = read;
	this.writeSock=write;
System.out.println(readSock.getRemoteSocketAddress());
System.out.println("Client Connected!");
System.out.println(writeSock.getRemoteSocketAddress());
System.out.println("Client Connected!");
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
//try{
//readSock.setSoTimeout(10000);
//}catch(Exception e) {
//System.err.println (e.getMessage ());
//readSock.close();
//writeSock.close();
  //    }


//outToClient.write("hello\n");
//outToClient.flush();
//System.out.println("Sent hello");
//System.out.print("User ID: ");
    userID = inFromClient.readLine();
System.out.println(userID);

    //System.out.print("Password: ");
password = inFromClient.readLine();
System.out.println(password);


//if(userID.substring(0,4).equals("door"))
//{
//System.out.println("door");
//try{
//String random = createMessage();
//System.out.println(random);
//outToClient.write(random+'\n');
//outToClient.flush();
//}catch(Exception e) {
//System.err.println (e.getMessage ());
//}
//while(true){}
//}
//else

//{
  // try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection

      conn = new jdbc();

      //STEP 4: Execute a query
      //System.out.println("Creating database...");
      //stmt = conn.createStatement();
      
      String sql = "SELECT UserID,access FROM Users WHERE UserID='"+userID+"' AND password='"+password+"';";
      String test1;
      String test2;
      String test3;
      if(!conn.authenticate(sql)){
      outToClient.write("0\n");
outToClient.flush();
readSock.close();
writeSock.close();
//outToClient.close();
}
      else
{
      outToClient.write("1\n");
outToClient.flush();
//outToClient.close();
//outToClient.write("123\n");
//outToClient.flush();
access=conn.getaccess(sql);
doorchoice=inFromClient.readLine();
System.out.println(doorchoice);
test1 = "SELECT * FROM Doors WHERE DoorID="+doorchoice+";";
test2 = "SELECT * FROM (SELECT DoorID FROM GenDoors WHERE access="+access+" UNION ALL SELECT DoorID FROM SpecDoors WHERE UserID='"+userID+"') as t1 WHERE DoorID="+doorchoice+";";
test3 = "SELECT * FROM Doors WHERE DoorID="+doorchoice+" AND status = 0;";
if(!conn.authenticate(test1)){
      outToClient.write("1\n");
outToClient.flush();
System.out.println("Not a door");
//outToClient.close();
}
else if(!conn.authenticate(test2)){
      outToClient.write("2\n");
outToClient.flush();
System.out.println("You do not have access to this door!");
//outToClient.close();
}
else if(!conn.authenticate(test3)){
      outToClient.write("3\n");
outToClient.flush();
System.out.println("Door is open");
//outToClient.close();
}
else
{
      outToClient.write("0\n");
outToClient.flush();
System.out.println("Valid");
String random = createMessage();
System.out.println(random);
conn.outToDoor.write(random+'\n');
conn.outToDoor.flush();
String QRscan = inFromClient.readLine();
if(QRscan.equals(random)){
conn.outToDoor.write("0\n");
conn.outToDoor.flush();
}
else{
conn.outToDoor.write("1\n");
conn.outToDoor.flush();
}


}
//outToClient.close();
//conn.printQuery(sql);
//int count=conn.getrowcount(rows);
//System.out.println(count);
//sql = "UPDATE Users SET loggedin=1 WHERE UserID='" + userID + "';"; 
//conn.executeUpdate(sql);

}


      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(conn != null) {
               System.out.print("Disconnecting from database...");
               //conn.cleanup ();
               System.out.println("Done\n\nBye !");
		readSock.close();
		writeSock.close();
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }
//}end try
//}
//catch(IOException ioe)
//{
//System.out.println("An unexpected error occured.");
//}//end main
}//end JDBCExample
}


