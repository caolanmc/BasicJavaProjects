import javax.swing.JOptionPane;
// This method will take in user input, and using a series of loops, count and display each consonant in a window.

public class consonantCount
{
	public static void main (String [] args)
	{                                                                                 
		String result = "";																
		int i, count;
		String input= JOptionPane.showInputDialog(null, "Please enter sentence: ");
		result += "Input: " +input + "\n";												
		input=input.replaceAll("[aeiouAEIOU ]","");									
		if ((input != null) && input.matches("[^A-Za-z]"))							
		{
			JOptionPane.showMessageDialog(null, "No input entered. Please enter a word or sentence."); // Error message for user if they don't enter a valid sentece.
		}
		else
		{		
			char j;
			for (j=(char) 65; j <= 90; j++)			   	//This for loop runs goes through the entire alphabet, moving from letter a to z in increments of 1.
			{
				count =0;
				for (i= 0; i < input.length(); i++)     //This loop goes in steps of 1 through the user input.
				{
					if(j==input.charAt(i) || (j+32) == input.charAt(i)) //Then this loop then takes the current character at j and compres to i, if they are equal, it adds to the count.
					{
						count++;
					}
				}
				if(count!=0 ) //This if loop prevents any letters that have count:0 from being displayed.
				{						
					result += j + "=" + count + "\n";
				}
			}		
		}
	JOptionPane.showMessageDialog(null,result);
	}
}
