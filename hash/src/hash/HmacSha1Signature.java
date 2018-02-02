package hash;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;




public class HmacSha1Signature {
	
	//creates random salt
	protected static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public static String calculateRFC2104HMAC(String data, String key)
		throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}

	public static void main(String[] args) throws Exception {		
		List<String> lines = Files.readAllLines(Paths.get("input file path"));
    	List<String> lines2 = new ArrayList<String>(); 	
    	for (int i = 0; i < lines.size(); i++) {   		
    	String input =lines.get(i);
    	String salt = getSaltString();	
		String hmac = calculateRFC2104HMAC(salt, input);
		lines2.add(hmac+":"+salt); //modifies result to match Hashcat file compatibility
		//System.out.println(salt+"\n");
		
    	}		
		
		FileWriter writer = new FileWriter("output file path"); 
        for (int i = 0; i < lines.size(); i++) {
           
            writer.write(lines2.get(i) + "\r\n");
        }
        writer.close();
	}
}