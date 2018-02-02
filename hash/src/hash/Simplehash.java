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


public class Simplehash {

	 /**
     * @param args
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
	static SecureRandom random = new SecureRandom();
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
    	List<String> lines = Files.readAllLines(Paths.get("input file path"));
    	List<String> lines2 = new ArrayList<String>();
    	
    	for (int i = 0; i < lines.size(); i++) {
    		
    		String input =lines.get(i);
    		//declares hash type
    		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    		
    		byte[] b= input.getBytes();  		
            byte[] result = mDigest.digest(b);
            
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < result.length; j++) {
                sb.append(Integer.toString((result[j] & 0xff) + 0x100, 16).substring(1));
            }
    		lines2.add(sb.toString());	
    	}
    	

        FileWriter writer = new FileWriter("output file path"); 
        for (int i = 0; i < lines.size(); i++) {  
            writer.write(lines2.get(i) + "\r\n");
        }
        writer.close();  
    }
}
