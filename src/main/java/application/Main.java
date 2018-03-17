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
	static boolean notFinished = true;
	
	private static void initComponents(Container pane, int checkers) {
		
		
		for (int i=0;i<checkers;i++) {
			Checker c = new Checker(counter,forChecking, notFinished);
			listCheckers.add(c);
			c.start();
			}
		setUpPane(pane);
		
		
	}

	private static void setUpPane(Container pane) {


		
		//Timer
		JLabel tempsRestant = new JLabel();
		tempsRestant.setText("30");
		final Chrono chrono = new Chrono(30, tempsRestant);
		Panel timer = new Panel();
		timer.add(tempsRestant);
		
		//Counter
		final JLabel count = new JLabel("0");
		
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
				count.setText(counter.toString());
				
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
		
		
		JPanel countPanel = new JPanel();
		countPanel.add(count);


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
						.addComponent(countPanel)));
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(timer)
				.addComponent(start))
				.addComponent(ecriture)
				.addComponent(countPanel));

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 * @throws InterruptedException 
	 */
	private static void createAndShowGUI() throws InterruptedException {
		// Create and set up the window.
		
		
		JFrame frame = new JFrame("TypeSpeedy");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		initComponents(frame.getContentPane(),8);
		

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		
//		while (notFinished) {
//			Thread.sleep(100);
//		}
//		frame.setVisible(false);
//		createAndShowFinish();
	}
	
	private static void createAndShowFinish() throws InterruptedException {
		System.out.println("coucou");
	}
	
	
	public static void main(String args[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}
}
