package application;

/*
 * GridBagLayoutDemo.java requires no other files.
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import speedy.Checker;

public class Main {
	
	public static AtomicInteger counter = new AtomicInteger(0);
	public static LinkedBlockingQueue<String> forChecking = new LinkedBlockingQueue<String>();
	private static LinkedList<Checker> listCheckers = new LinkedList<Checker>();
	private static boolean bol = true;
	
	private static void initComponents(Container pane, int checkers) {
		
		
		for (int i=0;i<checkers;i++) {
			Checker c = new Checker(counter,(BlockingQueue) forChecking, bol);
			listCheckers.add(c);
		}
		setUpPane(pane);
		
		
	}

	private static void setUpPane(Container pane) {


		
		//Timer
		JLabel tempsRestant = new JLabel();
		tempsRestant.setText("100");
		final Chrono chrono = new Chrono(100, tempsRestant);
		Panel timer = new Panel();
		timer.add(tempsRestant);
		
		//Text Area
		
		final JTextArea ecriture = new JTextArea("Type in as"+
		"many words as possible, before the count ends.");
		
		ecriture.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				char x = e.getKeyChar();
				if (x == ' ') {
					String toCheck = getTypedString(ecriture.getText());
					forChecking.add(toCheck);
				}
			}

			private String getTypedString(String text) {
				String typed = "";
				int l = text.length();
				for (int i=0; i<l; i++) {
					if (text.charAt(l-i-1)!= ' ') {
						typed = text.charAt(l-i-1)+typed;
					}
					else {
						if (typed != "") {
							return typed;
						}
					}
				}
				return typed;
			}

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}
		});
		ecriture.setLineWrap(true);
		ecriture.setWrapStyleWord(true);
		
		
		
		
		
		//Start Button
		JPanel buttonPanel = new JPanel();
		JButton startButton = new JButton();
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chrono.t.start();
				ecriture.setText("");
			}
		});
		buttonPanel.add(startButton);
		
		
		
		JButton start = new JButton("Start");
		JButton selecteur = new JButton("Bouton");


		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chrono.t.start();
				ecriture.setText("");
			}
		});

		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(timer)
				.addComponent(ecriture))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(start)
						.addComponent(selecteur)));
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(timer)
				.addComponent(start))
				.addComponent(ecriture)
				.addComponent(selecteur));

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		
		
		JFrame frame = new JFrame("TypeSpeedy");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		initComponents(frame.getContentPane(),8);
		

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
