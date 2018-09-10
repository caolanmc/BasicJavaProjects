import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
public class CS4092Project2Group10
{
	public static ArrayList<ArrayList<String>> topics;
	public static ArrayList<ArrayList<String>> Questions;
	public static ArrayList<ArrayList<String>> usernamesPasswords;
	public static int tempScore;
	public static int validUserIndex;
	public static void main(String [] args) throws IOException
	{
			boolean readFile; // Set a boolean to see if the files exist
			
			readFile = readFilesIntoArrayLists(); // Calling the method
			if (logIn()) // If log in is successful
			{
				if(!readFile) // if files don't exist
					JOptionPane.showMessageDialog(null,"One or more files do not exist."); 
				else
				{	
					if (QuestionAndAnswers()) // if all questions on a topic answered
					{
						reportOnTest(); // report on how user did
						writeToFile(); // rewrite file with new score 
					}
				}
			}
	}
	
	/*
		This method asks the user for a username and password. The user will be continuously
		asked for a username until a valid username is inputted. Then, the user has three tries to input 
		the corresponding password. If they fail to do so, they will be exited from the application.
	*/
	
	public static boolean logIn() throws IOException
	{
		String username = " ";
		String password = "";
		String temp = "";
		int triesPass = 3;
		validUserIndex = 0;
		boolean validUser = false;
		boolean validPass = false;
		boolean logInComplete = false;
		while (!validUser && username != null)
		{
			username = JOptionPane.showInputDialog(null,"Enter a username.");
			if (usernamesPasswords.get(0).indexOf(username) != -1)
			{
				validUser = true;
				validUserIndex = usernamesPasswords.get(0).indexOf(username);
			}
			else if (username != null)
			{
				JOptionPane.showMessageDialog(null,"Username not found on database. Please try again.");
			}
		}
		if (validUser)
		{
			int j = triesPass;
			for (int i= 0; i < triesPass && !validPass; i++)
			{
				password = JOptionPane.showInputDialog(null,"Enter a password.");
				temp = usernamesPasswords.get(1).get(validUserIndex);
				if (temp != null)
				{
					if (password.equals(temp))
					{
						validPass = true;
						logInComplete = true;
					}
					else
					{
						j--;
						JOptionPane.showMessageDialog(null,"Incorrect password. Please try again. You have " + j + " remaining.");
					}
				}
				
			}
			if (j == 0)
			{
				JOptionPane.showMessageDialog(null,"You have exceeded your number of tries. You will be exited from the application.");
				return false;
			}
			
		}
		if (logInComplete == true)
			return true;
		else 
			return false;
	}
	
	/*
		This method reads all the files and makes several arraylists for each file.
		The method will return true if all the files exist. Otherwise, it will return false.
	*/
	
	public static boolean readFilesIntoArrayLists() throws IOException
	{
		String filename1 = "Topics.txt";
		String filename2 = "Questions.txt";
		String filename3 = "UsernamesAndPasswords.txt";
		
		String fileElements[];
		File inputFile1 = new File(filename1);
		File inputFile2 = new File(filename2);
		File inputFile3 = new File(filename3);
		
		topics = new ArrayList<ArrayList<String>>();
		topics.add(new ArrayList<String>());
		topics.add(new ArrayList<String>());
		
		Questions = new ArrayList<ArrayList<String>>();
		
		usernamesPasswords = new ArrayList<ArrayList<String>>();
		usernamesPasswords.add(new ArrayList<String>());
		usernamesPasswords.add(new ArrayList<String>());
		usernamesPasswords.add(new ArrayList<String>());
		
		if(inputFile1.exists() && inputFile2.exists() && inputFile3.exists())
		{
			Scanner in;
			in = new Scanner(inputFile1);
			while(in.hasNext())
			{
				fileElements = (in.nextLine()).split(",");
			    topics.get(0).add(fileElements[0]);
				topics.get(1).add(fileElements[1]);
	        }
		    in.close();
			in = new Scanner(inputFile1);
			while(in.hasNext())
			{
				in.nextLine();
				Questions.add(new ArrayList<String>()); // This adds as many arraylists as there are lines in Topics.txt 
			}
			in.close();
			
			in = new Scanner(inputFile2);
			String aLine,aChar,topicNumber,question;
			
			while(in.hasNext())
			{
				aLine =in.nextLine();
				aChar = aLine.substring(0,1);
				for(int i=0;i<topics.get(0).size();i++)
				{
					topicNumber =topics.get(0).get(i);
					if(topicNumber.equals(aChar))
					{
						int tempNumber = Integer.parseInt(topicNumber);
						Questions.get(tempNumber-1).add(aLine);
					}			    
				}
			}
			
			in = new Scanner(inputFile3);
			while(in.hasNext())
			{
				fileElements = (in.nextLine()).split(",");
			    usernamesPasswords.get(0).add(fileElements[0]);
				usernamesPasswords.get(1).add(fileElements[1]);
				usernamesPasswords.get(2).add(fileElements[2]);
	        }
		    in.close();
			return true;
		}
		else
			return false;
	}
	
	/*
		Checks if arraylists contain questions for a certain topic.
		Displays topics that have questions avaliable.
		Displays a random question from the chosen topic.
		If user answers correctly, you move to the next question.
		If not, an explaination is shown and then you move to the next question.
		The method returns true if all questions in that topic were answered.
		Otherwise, it returns false.
	*/
	
	public static boolean QuestionAndAnswers() 
	{
		tempScore = 0;
		int questionCounter = 0;
		String options[] = new String [topics.get(1).size()];
		for(int i = 0, j = 0;i < Questions.size();i++)
		{
			if(!(Questions.get(i).isEmpty()))
			{
				options[j] = topics.get(1).get(i);
				j++;
			}
		}
		String selectedTopic = (String) JOptionPane.showInputDialog(null,"Choose one","Input",1,null,options,options[0]);
		String temp;
		int number = 0;
		for (int i = 0;i < topics.get(1).size(); i++)
		{
			temp = topics.get(1).get(i);
			if (selectedTopic.matches(temp))
				number = Integer.parseInt(topics.get(0).get(i));
		}
		if(selectedTopic != null)
		{
			String selectedAnswer = " ";
			
			int [] questionTopicsGenerated = new int [Questions.get(number - 1).size()];
			int [] randomQuestionNumbers = new int [Questions.get(number - 1).size()];
			
			randomQuestionNumbers = generateRandomQuestions(questionTopicsGenerated, number);
			
			while (selectedAnswer != null && questionCounter < 10) 
			{
				String questionElements [];
				
				questionElements = Questions.get(number - 1).get(randomQuestionNumbers[questionCounter]).split(",");
				
				String questionFormat = questionElements[2] + "\n1. " + questionElements[3] + "\n2. " + questionElements[4] + "\n3. " + questionElements[5] + "\n4. " + questionElements[6];
				selectedAnswer = JOptionPane.showInputDialog(null,questionFormat,"Choose an answer.",3);
				
				while (!(validateInput(selectedAnswer)))
				{
					selectedAnswer = JOptionPane.showInputDialog(null,questionFormat,"Choose an answer.",3);	
				}
				if(selectedAnswer.matches(questionElements[7]))
				{
					tempScore++;
				}	
				else
				{
					JOptionPane.showMessageDialog(null,"Explaination: " + questionElements[8],"Incorrect",1);
				}	
				questionCounter++;
			}		
		}
		if (questionCounter >= 10)
			return true;
		else
			return false;
	}
	
	/*
		Generates a unique array of random numbers in the range of 0-9.
	*/
	
	public static int [] generateRandomQuestions(int[] questionsAsked, int number)
	{
		int index = 0, duplicateIndex;
		while (index < questionsAsked.length)
		{
			int randomQuestion = (int) (Math.random() * (Questions.get(number - 1).size()));
			questionsAsked[index] = randomQuestion;
			duplicateIndex = 0;
			while (questionsAsked[duplicateIndex] != randomQuestion)
				duplicateIndex++;
			if (index == duplicateIndex)
				index++;
		} 
		return questionsAsked;
	}
	
	/*
		Validates input of user when answering a question.
		Ensures input is a number in the range 1-4.
	*/
	
	public static boolean validateInput(String userInput)
	{
		boolean valid = false;
		String validInput = "1|2|3|4";
		userInput = userInput.replaceAll("\\s+", "");
		
		if(userInput.matches(validInput))
			valid = true;
		else
			JOptionPane.showMessageDialog(null, "Invalid input, enter the answer number only.");
		
		return valid;
	}
	
	/*
		Reports on what score the user got and which user was being used.
	*/
	
	public static void reportOnTest()
	{
		String user = "";
		String totalScore = "";
		int score;
		user = usernamesPasswords.get(0).get(validUserIndex);
		score = tempScore;
		score = score + Integer.parseInt(usernamesPasswords.get(2).get(validUserIndex));
		totalScore = score + "";
		JOptionPane.showMessageDialog(null,"Congratulations " + user + " you completed the Multiple Choice questions with a score of " + tempScore +
											" and a total score to date of " + totalScore + ".");
		usernamesPasswords.get(2).set(validUserIndex, totalScore);
	}
	
	/*
		Rewrites file with updated score.
	*/
	
	public static void writeToFile() throws IOException
	{
		PrintWriter printer = new PrintWriter("UsernamesAndPasswords.txt");
		for (int i = 0; i < usernamesPasswords.get(0).size(); i++)
		{
			for (int j = 0; j < usernamesPasswords.size(); j++)
			{
				if (j != usernamesPasswords.size() - 1)
					printer.print(usernamesPasswords.get(j).get(i) + ",");
				else
					printer.print(usernamesPasswords.get(j).get(i));
			}
			if (i != usernamesPasswords.get(0).size() - 1)
				printer.println();
		}
		printer.close();
	}
}
	