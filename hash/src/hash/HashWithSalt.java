package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
public class HashWithSalt {
 
    /**
     * @param args
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
	//creates random salt
	static SecureRandom random = new SecureRandom();
	
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
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
    	List<String> lines = Files.readAllLines(Paths.get("input file path"));
    	List<String> lines2 = new ArrayList<String>();
    	
    	for (int i = 0; i < lines.size(); i++) {
    		String input =lines.get(i);
    		//declares hash type
    		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    		String slt = getSaltString();    		
    		byte[] salt = slt.getBytes() ;
    		//random.nextBytes(salt);
    		//the following lines add the salt in the beginning of the plaintext 
    		byte[] a = salt;
    		byte[] b= input.getBytes(); 		
    		byte[] tempb = new byte[a.length + b.length]; 		
    		System.arraycopy(a, 0, tempb, 0, a.length); 
    		System.arraycopy(b, 0, tempb, a.length, b.length); 
            byte[] result = mDigest.digest(tempb);
            
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < result.length; j++) {
                sb.append(Integer.toString((result[j] & 0xff) + 0x100, 16).substring(1));
            }
           
    		lines2.add(sb.toString()+":"+slt);  //modifies result to match Hashcat file compatibility
    		//System.out.println("\n"+slt);
    		
    	}
    	

        FileWriter writer = new FileWriter("output file path"); 
        for (int i = 0; i < lines.size(); i++) {     
            writer.write(lines2.get(i) + "\r\n");
        }
        writer.close();
  	
    }
  
}