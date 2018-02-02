package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class bcrypttest {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    	List<String> lines = Files.readAllLines(Paths.get("input file path"));
    	List<String> lines2 = new ArrayList<String>();
    	
    	for (int i = 0; i < lines.size(); i++) {
    		
    		String input =lines.get(i);
    		String pw_hash = BCrypt.hashpw(input, BCrypt.gensalt());
    		System.out.println(pw_hash);
    		lines2.add(pw_hash);	
    	}
    	
    	
    	FileWriter writer = new FileWriter("output file path"); 
        for (int i = 0; i < lines.size(); i++) {  
            writer.write(lines2.get(i) + "\r\n");
        }
        writer.close();  
    		
	}
	
}
