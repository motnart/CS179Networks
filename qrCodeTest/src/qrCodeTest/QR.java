package qrCodeTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

class randomMessage {
	
	public static String createMessage ()
	{
		final int MessageLength = 16;
		
		char [] randomMessage = new char [MessageLength];
		
		for (int i = 0; i < MessageLength; i++)
		{
			double newNum = Math.random() * 92 + 33;
			int castNewNum = (int)newNum;
			char intToAscii = (char) castNewNum;
			System.out.println(intToAscii);
			randomMessage [i] = intToAscii;
		}
		
		String finalMessage = new String (randomMessage);
		
		System.out.println ("Final Message: " + finalMessage);
		
		return finalMessage;
		
	}

}


public class QR {

	public static void main(String[] args) throws WriterException, IOException,
			NotFoundException {
		String qrCodeData = randomMessage.createMessage();
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
