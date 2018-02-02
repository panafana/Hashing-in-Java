package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class pbkdf2 {

	
	 /**
     * @param args
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */
	 static SecureRandom random = new SecureRandom();
	 
	 static String byteArrayToString(byte[] in) {
		    char out[] = new char[in.length * 2];
		    for (int i = 0; i < in.length; i++) {
		        out[i * 2] = "0123456789ABCDEF".charAt((in[i] >> 4) & 15);
		        out[i * 2 + 1] = "0123456789ABCDEF".charAt(in[i] & 15);
		    }
		    return new String(out);
		}
	 
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    	List<String> lines = Files.readAllLines(Paths.get("input file path"));
    	List<String> lines2 = new ArrayList<String>();
    	int iter =1000;
    	
for (int i = 0; i < lines.size(); i++) {
    		
    		String input =lines.get(i);
    		byte[] salt = new byte[16];
    		random.nextBytes(salt);
    		KeySpec spec = new PBEKeySpec(input.toCharArray(), salt, iter, 160);
    		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    		byte[] hash = f.generateSecret(spec).getEncoded();
    		Base64.Encoder enc = Base64.getEncoder();
    		String temps = new String(enc.encodeToString(salt));
    		String temph = new String(enc.encodeToString(hash));
    		
    		String temp = "sha1:"+iter+":"+temps+":"+temph;
    		System.out.printf(temp+"\n");
    		lines2.add(temp);
    		
    	}
	
	  
	  FileWriter writer = new FileWriter("output file path"); 
      for (int i = 0; i < lines.size(); i++) {       
          writer.write(lines2.get(i) + "\r\n");
      }
      writer.close();
      
	
	}
}
