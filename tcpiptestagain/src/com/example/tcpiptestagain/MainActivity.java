package com.example.tcpiptestagain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Socket socketwrite;
	private Socket socketread;
	private static long back_pressed;
	private PrintWriter printwriter;
	private BufferedReader buffread;
	private EditText Username = null;
	private EditText Password = null;
	private Button button;
	private String userMessage;
	private String passMessage;
	public String received;
	private TextView attempts;
	int counter = 3;

	private static final int SERVERPORTWRITE = 46801;
	private static final int SERVERPORTREAD = 43849;
	private static final String SERVER_IP = "169.235.31.177";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Username = (EditText)findViewById(R.id.editText1);
        Password = (EditText)findViewById(R.id.editText2);
        attempts = (TextView)findViewById(R.id.textView5);
        attempts.setText(Integer.toString(counter));
        button = (Button)findViewById(R.id.button1);

		new Thread(new ClientThread()).start();

		button.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View view) {
				try {
					userMessage = Username.getText().toString() + '\n'; // get the text in username
					passMessage = Password.getText().toString() + '\n'; // get the text in password
					printwriter = new PrintWriter(socketwrite.getOutputStream(), true);
					printwriter.write(userMessage);
					printwriter.write(passMessage);
					
					Username.setText(""); // Reset Username field
					Password.setText(""); // Reset Password field
					
					printwriter.flush();
					printwriter.close();
					
					buffread = new BufferedReader(new InputStreamReader(
							socketread.getInputStream()));
					
					received = buffread.readLine();
					buffread.close();
					
					if(received != null) {
						Username.setText(received);
					}
					else {
						Username.setText("received == null");
					}
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class ClientThread implements Runnable {

		@Override
		public void run() {

			try {
				//InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

				socketwrite = new Socket(SERVER_IP, SERVERPORTWRITE);
				
				socketread = new Socket(SERVER_IP, SERVERPORTREAD);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}