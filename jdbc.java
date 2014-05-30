//STEP 1. Import required packages
import java.sql.*;
import java.io.*;
import java.util.Date;
import java.net.*;



public class jdbc {
   // JDBC driver name and database URL

public Connection _connection = null;
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
static final String DB_URL = "jdbc:mysql://localhost/master";

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
   
   public static void main(String[] args) throws Exception{

ServerSocket readSocket = new ServerSocket(46801);
ServerSocket writeSocket = new ServerSocket(43849);
System.out.println("Listening...");
while(true){
new ServerThread(readSocket.accept(),writeSocket.accept()).start();
//Socket readSock = readSocket.accept();
//System.out.println(readSock.getRemoteSocketAddress());
//System.out.println("Client Connected!");
//Socket writeSock = writeSocket.accept();
//System.out.println(writeSock.getRemoteSocketAddress());
//System.out.println("Client Connected!");
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
	private Socket readSock;
	private Socket writeSock;

 public ServerThread(Socket read, Socket write) {
        super("ServerThread");
        this.readSock = read;
	this.writeSock=write;
    }

   jdbc conn = null;
 String userID=null;
String password=null;
boolean success=false;
String access=null;
String doorchoice=null;

public void run(){

try(
BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
BufferedReader inFromClient = new BufferedReader(new InputStreamReader(readSock.getInputStream()));
PrintWriter outToClient = new PrintWriter(writeSock.getOutputStream(),true);
){

//outToClient.write("hello\n");
//outToClient.flush();
//System.out.println("Sent hello");
//System.out.print("User ID: ");
    userID = inFromClient.readLine();
System.out.println(userID);

    //System.out.print("Password: ");
password = inFromClient.readLine();
System.out.println(password);
}
catch(IOException ioe)
{
System.out.println("An unexpected error occured.");
}

if(userID.substring(0,4).equals("door"))
{
System.out.println("door");
String random = createMessage();
System.out.println(random);
outToClient.write(random+'\n');
outToClient.flush();
while(true){}
}
else

{
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection

      conn = new mysql();

      //STEP 4: Execute a query
      //System.out.println("Creating database...");
      //stmt = conn.createStatement();
      
      String sql = "SELECT UserID,access FROM Users WHERE UserID='"+userID+"' AND password='"+password+"';";
      success = conn.authenticate(sql);
      if(!success){
      outToClient.write("0\n");
outToClient.flush();
//outToClient.close();
}
      else
{
      outToClient.write("1\n");
outToClient.flush();
//outToClient.close();

access=conn.getaccess(sql);
doorchoice=inFromClient.readLine();
System.out.println(doorchoice);
sql = "SELECT * FROM (SELECT DoorID FROM GenDoors WHERE access="+access+" UNION ALL SELECT DoorID FROM SpecDoors WHERE UserID='"+userID+"') as t1 WHERE DoorID="+doorchoice+";";
//String rows="SELECT COUNT(*) FROM (SELECT DoorID FROM GenDoors WHERE access="+access+" UNION ALL SELECT DoorID FROM SpecDoors WHERE UserID='"+userID+"') as t1;";
success = conn.authenticate(sql);
      if(!success){
      outToClient.write("0\n");
outToClient.flush();
//outToClient.close();
}
      else
{
      outToClient.write("1\n");
outToClient.flush();
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
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }
}//end try
//end main
}//end JDBCExample
}


