import java.io.*;
import java.util.*;

public class RiddleLoader {

	public static void main(String[] args) throws IOException {	
	
		LinkedList<RiddleAnswer> linky = new LinkedList<RiddleAnswer>();
		
		int fileLength = 0;
		String riddle = null;
		String answer = null;
		boolean Friddle = false;
		boolean Fanswer = false;

		try {
			String inputString = null;
			FileReader fr = new FileReader("Riddles.txt");
			BufferedReader br = new BufferedReader(fr);
			String rTag = "<Riddle>";
			String aTag = "<Answer>";
			//int rTagLength = rTag.length();

			while ((inputString = br.readLine()) != null) {

				if (inputString.startsWith(rTag)) {
					riddle = inputString.substring(8);
					
					Friddle = true;
					continue;
				} if (inputString.startsWith(aTag)) {
					answer = inputString.substring(8);
					Fanswer = true;
				}
				
				if(Friddle && Fanswer) {
				RiddleAnswer finRiddle = new RiddleAnswer(riddle, answer);

				linky.add(finRiddle);
				Friddle = false;
				Fanswer = false;
				}
				
			}
			br.close();
			System.out.println(linky.size());

		} catch (IOException ex) {
			System.out.println("Input Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		try {
			
		FileWriter fw = new FileWriter("RiddlesCopy.txt");
		Writer output = new BufferedWriter(fw);
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < linky.size(); i++){
			
				RiddleAnswer ra = linky.get(i);
				 riddle = ra.GetRiddle();
				answer = ra.GetAnswer();
			
				sb.append("<Riddle>"+ riddle + "\n");
				sb.append("<Answer>"+ answer + "\n");
				
		
			
		}
		output.write(sb.toString());
		output.flush();
		output.close();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	
		UserInterface ui = new UserInterface(linky);
		try {
		for(int i = 23; i<=linky.size(); i--) {
			RiddleAnswer rra =linky.get(i);
			riddle = rra.GetRiddle();
			answer = rra.GetAnswer();
			System.out.println("<Riddle> "+riddle);
			System.out.println("<Answer> "+answer);
		}
		}catch(Exception x) {
		}
			}
	
}
