import javax.swing.JOptionPane;
/* This method will take in user input, do multiple checks, E.g If it doesn't contain vowels, if it contains all vowels, all vowels in alphabetical order, all vowels
   in reverse order, and counts the vowels.                        																									*/
public class vowelWork
{
	public static void main (String [] args)
	{
		String result = "";
		String order= "aeiou";
		String reverseOrder= "uoiea"; 
		int i, count;
		String input= JOptionPane.showInputDialog(null, "Please enter sentence: ");
		result += "Input: " +input + "\n";
		input=input.replaceAll("[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ ]","");
		if ((input != null) && input.matches("[^A-Za-z0-9]"))
		{
			JOptionPane.showMessageDialog(null, "Error!");
			
		}
		else
		{		
			if (input.matches(".*[aeiou].*"))			
			{
				result += "Contains vowel(s)" + "\n";
				
				if (input.contains("a")&&input.contains("e")&&input.contains("i")&&input.contains("o")&&input.contains("u")) 
				{
					result += "Contains all vowels." + "\n";
				}
				if(input.matches("aeiou"))
				{ 
					result += "Contains all vowels alphabetically" + "\n";
				}
				if(input.matches("uoiea"))
				{ 
					result += "Contains all vowels in reverse alphabetical order." + "\n";
				}
			char j;
				for (j=(char) 65; j <= 90; j++)										//Exact same as my consonant count, but this time checking for the vowels.
				{
					count =0;
					for (i= 0; i < input.length(); i++)
					{
						if(j==input.charAt(i) || (j+32) == input.charAt(i))
						{
							count++;
						}
					}
					if(count!=0 )
					{						
						result += "" + j + "=" + count + "\n";
					}
				}		
			}
			else
			{
				result += "This string contains no vowels... " +"\n";
			}
		}
	JOptionPane.showMessageDialog(null,result);
	}
}