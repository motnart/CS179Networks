import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

class QR {

	public static void QR (String v) throws WriterException, IOException
	{
		String qrCodeData = v;
		String filePath = "QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(qrCodeData, filePath, charset, hintMap, 500, 500);
		System.out.println("QR Code image created");
		
		
	}

	public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}
}


public class DoorClient {
	
	public static PrintWriter printwriter;
	public static BufferedReader buffread;
	static boolean connected = false;

	public static void main(String[] args) throws UnknownHostException, IOException, WriterException {
		// TODO Auto-generated method stub
		
		Socket clientsend = new Socket("169.235.31.177", 46801); // connect to server send
		Socket clientrec = new Socket("169.235.31.177", 43849); // connect to server rec
		
		System.out.println ("Connected to server.");
		connected = true;
		Scanner in = new Scanner (System.in); // takes user input
		
		System.out.println ("Username:");
		String username = in.next();
		username = username + '\n';
		System.out.println (username);
		
		printwriter = new PrintWriter(clientsend.getOutputStream(), true);
		printwriter.write(username); // write username to output stream
		printwriter.write(username); 

		printwriter.flush();
		printwriter.close();
		
		while (connected)
		{
			// taking door input
			buffread = new BufferedReader(new InputStreamReader(clientrec.getInputStream()));
			String received = buffread.readLine();
			buffread.close();
			
			// printing the randomly generated message
			System.out.println (received);
			
			//generate QR code
			QR.QR(received);
			
			System.out.println ("GO OPEN QR CODE");
			
			
		}

	}

}
