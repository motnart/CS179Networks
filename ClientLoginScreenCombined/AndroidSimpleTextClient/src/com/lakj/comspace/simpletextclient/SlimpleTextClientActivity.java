package com.lakj.comspace.simpletextclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a simple Android mobile client
 * This application read any string massage typed on the text field and 
 * send it to the server when the Send button is pressed
 * Author by Lak J Comspace
 *
 */
public class SlimpleTextClientActivity extends Activity {

	private static long back_pressed;
	private Socket client;
	private PrintWriter printwriter;
	private EditText Username = null;
	private EditText Password = null;
	private Button button;
	private String userMessage;
	private String passMessage;
	private TextView attempts;
	int counter = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slimple_text_client);
        Username = (EditText)findViewById(R.id.editText1);
        //Username.setText("Username");
        Password = (EditText)findViewById(R.id.editText2);
        //Password.setText("Password");
        attempts = (TextView)findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));
        button = (Button)findViewById(R.id.button1);

		// Button press event listener
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				userMessage = Username.getText().toString() + '\n'; // get the text in username
				passMessage = Password.getText().toString() + '\n'; // get the text in password
				Username.setText(""); // Reset Username field
				Password.setText(""); // Reset Password field
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				
				if(Username.getText().toString().equals("admin") &&
			               Password.getText().toString().equals("admin")){
			           Toast.makeText(getApplicationContext(), "Redirecting...",
			                   Toast.LENGTH_SHORT).show();
			    }
			    else{
			     Toast.makeText(getApplicationContext(), "Wrong Credentials",
			                Toast.LENGTH_SHORT).show();
			        attempts.setBackgroundColor(Color.RED);
			        counter--;
			        attempts.setText(Integer.toString(counter));
			        if(counter==0){
			            button.setEnabled(false);
			        }
			    }
			}
		});
	}
	
	private class SendMessage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {

				client = new Socket("169.235.31.177", 46801); // connect to the server
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(userMessage); // write username to output stream
				printwriter.write(passMessage); // write password to output stream

				printwriter.flush();
				printwriter.close();
				client.close(); // closing the connection

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
