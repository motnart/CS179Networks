package com.lakj.comspace.simpletextclient;

import java.io.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.BufferedReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

/**
 * This is a simple Android mobile client
 * This application read any string massage typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 *
 */
public class SlimpleTextClientActivity extends Activity {

	private static long back_pressed;
	public static Socket clientsend;
	public static Socket clientrec;
	public static PrintWriter printwriter;
	public static BufferedReader buffread;
	private EditText Username = null;
	private EditText Password = null;
	private Button button;
	private String userMessage;
	private String passMessage;
	private String roomMessage;
	public String received;
	private TextView attempts;
	private EditText room = null;
	
	int counter = 3;
	
	private Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == counter)
            {
            	if(received.equals("1")){ //Credentials Incorrect
					counter--;
					attempts.setText(Integer.toString(counter));
					
					if (counter == 0)
					{
						button.setEnabled(false);
					}
					
            		Toast.makeText(getApplicationContext(), "User/Pass incorrect",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(received.equals("2")){ //Not a Door
					Toast.makeText(getApplicationContext(), "Door number does not exist",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(received.equals("3")){ //No Access for User
					Toast.makeText(getApplicationContext(), "User has no access to this door",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(received.equals("4")){ //Door Already Open
					Toast.makeText(getApplicationContext(), "Door is already open",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(received.equals("5")){ //Door Currently Unavailable
					Toast.makeText(getApplicationContext(), "Door is currently unavailable",
							Toast.LENGTH_SHORT).show();
					
				}
				else if(received.equals("6")){ //User Already Logged In
					Toast.makeText(getApplicationContext(), "User is already logged in",
							Toast.LENGTH_SHORT).show();
					
				}
				else{
					Toast.makeText(getApplicationContext(), "ERRCode Failed",
							Toast.LENGTH_SHORT).show();
					
				}
            }
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slimple_text_client);
        Username = (EditText)findViewById(R.id.editText1);
        Password = (EditText)findViewById(R.id.editText2);
        attempts = (TextView)findViewById(R.id.textView5);
        room = (EditText)findViewById(R.id.editText3);
        attempts.setText(Integer.toString(counter));
        button = (Button)findViewById(R.id.button1);

		// Button press event listener
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				userMessage = Username.getText().toString() + '\n'; // get the text in username
				passMessage = Password.getText().toString() + '\n'; // get the text in password
				roomMessage = room.getText().toString() + '\n';
				//Username.setText(""); // Reset Username field
				//Password.setText(""); // Reset Password field
				//room.setText (""); // reset the room
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				
				/*if(received != null) {
					Username.setText(received);
				}
				else {
					Username.setText("received == null");
				}*/
			}
		});
	}
	
	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				clientsend = new Socket("169.235.31.177", 46801); // connect to the server
				clientrec = new Socket("169.235.31.177", 43849);
				
				printwriter = new PrintWriter(clientsend.getOutputStream(), true);
				printwriter.write(userMessage); // write username to output stream
				printwriter.write(passMessage); // write password to output stream
				printwriter.write(roomMessage);

				printwriter.flush();
				//printwriter.close();
				
				buffread = new BufferedReader(new InputStreamReader(clientrec.getInputStream()));
				received = buffread.readLine();
				buffread.close();
				
				if(received.equals("0")){ //Success
			           //Toast.makeText(getApplicationContext(), "Redirecting...",
			           //        Toast.LENGTH_SHORT).show();
					   Intent intent = new Intent (SlimpleTextClientActivity.this, SecondActivity.class);
					   //intent.putExtra("clientsend");
			           startActivity(intent);
			    }
			    else{
			    	Message ERRMessage = hand.obtainMessage(counter);
			    	hand.sendMessage(ERRMessage);
			    }
				
				//client.close(); // closing the connection

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slimple_text_client, menu);
		return true;
	}

	@Override
	public void onBackPressed()
	{
		if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
		else Toast.makeText(getBaseContext(), "Press again to exit!", Toast.LENGTH_SHORT).show();
		back_pressed = System.currentTimeMillis();
	}

}
