/* Flashcard program
uses a JFrame with 2 sides to the window
button on left displays random flashcard question
button on right causes corresponding answer to appear
will read in a file line by line
first line is first question
second line is first answer, etc */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Flashcards
{
	private

	JFrame window;
	JPanel topPanel, bottomPanel;
	Container content;
	JButton questionButton;
	JButton answerButton;
	JButton quitButton;
	JTextArea questionTextArea;
	JTextArea answerTextArea;
	static ArrayList<String> questions = new ArrayList<String>();
	static ArrayList<String> answers = new ArrayList<String>();
	static ArrayList<String> reset_questions = new ArrayList<String>();
	static ArrayList<String> reset_answers = new ArrayList<String>();

	public Flashcards()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3,1));

		ButtonListener bListener = new ButtonListener();
		questionButton = new JButton("Next Flashcard");
		questionButton.setFont(new Font("TimesRoman", Font.BOLD, 26));
		questionButton.addActionListener(bListener);
		questionTextArea = new JTextArea();
		questionTextArea.setFont(new Font("TimesRoman", Font.BOLD, 20));
		questionTextArea.setLineWrap(true);
    questionTextArea.setWrapStyleWord(true);
    questionTextArea.setOpaque(false);
    questionTextArea.setEditable(false);
		answerTextArea = new JTextArea("");
		answerTextArea.setFont(new Font("TimesRoman", Font.BOLD, 20));
		answerTextArea.setLineWrap(true);
    answerTextArea.setWrapStyleWord(true);
    answerTextArea.setOpaque(false);
    answerTextArea.setEditable(false);
		quitButton = new JButton("EXIT");
		quitButton.setFont(new Font("TimesRoman", Font.BOLD, 26));
		quitButton.addActionListener(bListener);
		topPanel.add(quitButton);
		topPanel.add(questionTextArea);
		topPanel.add(answerTextArea);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));

		answerButton = new JButton("Answer");
		answerButton.setFont(new Font("TimesRoman", Font.BOLD, 26));
		answerButton.addActionListener(bListener);

		bottomPanel.add(questionButton);
		bottomPanel.add(answerButton);

		JFrame window = new JFrame("Flashcards");
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		content = window.getContentPane();


		content.add(topPanel, BorderLayout.NORTH);
		content.add(bottomPanel, BorderLayout.SOUTH);
		window.setSize(640,360);
		window.setVisible(true);
	}


	class ButtonListener implements ActionListener
	{
		Random gen = new Random();
		int randomIndex = -1;

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == questionButton)
			{
				randomIndex = gen.nextInt(questions.size());
				questionTextArea.setText(questions.get(randomIndex));
				answerTextArea.setText("");
			}
			if (e.getSource() == answerButton && answerTextArea.getText().equals(""))
			{
				answerTextArea.setText(answers.get(randomIndex));
				answers.remove(randomIndex);
				questions.remove(randomIndex);
				if(questions.isEmpty())
				{
					questions = reset_questions;
					answers = reset_answers;
				}
			}
			if(e.getSource() == quitButton)
			{
				System.exit(0);
			}
		}

	}

	public static void loadLists(Scanner infile)
	{
		int count=0;
		while (infile.hasNextLine())
		{
			if ( (count+1)%2 == 1)
			{
				reset_questions.add(infile.nextLine());
			}
			else
			{
				reset_answers.add(infile.nextLine());
			}
			count++;
		}
		questions = reset_questions;
		answers = reset_answers;
		infile.close();
	}


	public static void main( String[] args) throws Exception
	{
		if (args.length < 1 )
		{
			System.out.println("\nusage: C:\\> java Flashcards <input filename>\n\n"); // i.e. C:\> java Flashcards fcard.txt
			System.exit(0);
		}

		Scanner infile =  new Scanner( new File( args[0] ) );
		loadLists(infile);

		new Flashcards();
	}

}
