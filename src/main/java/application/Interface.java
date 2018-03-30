package application;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Interface extends Thread {
	
	volatile static boolean started;
	static Lock lock;
	static LinkedBlockingQueue<String> forChecking;
	static AtomicInteger counter;
	static Chrono chrono;
	static JLabel tempsRestant;
	static JFrame frame;
	
	public Interface(boolean started, Lock lock, LinkedBlockingQueue<String> forChecking,AtomicInteger counter, JLabel tempsRestant, Chrono chrono) {
		this.started = started;
		this.lock = lock;
		this.forChecking = forChecking;
		this.counter = counter;
		this.chrono = chrono;
		this.tempsRestant = tempsRestant;
	}
	
private static void setUpPane(Container pane) {


		
		//Timer
			
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
		//JButton startButton = new JButton("Start");
		JButton startB = new JButton("Start");
		startB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				synchronized(lock) {
				started = true;
				lock.notifyAll();
				}
				ecriture.setText("");
			}
		});
		buttonPanel.add(startB);
		
		
		
		
		JButton selecteur = new JButton("Bouton");
		
		
		JPanel countPanel = new JPanel();
		countPanel.add(count);


		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addComponent(timer)
				.addComponent(ecriture))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(startB)
						.addComponent(countPanel)));
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(timer)
				.addComponent(startB))
				.addComponent(ecriture)
				.addComponent(countPanel));
		

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 * @throws InterruptedException 
	 */

	public static void createAndShowGUI() throws InterruptedException {
		// Create and set up the window.
		
		
		frame = new JFrame("TypeSpeedy");
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		setUpPane(frame.getContentPane());
		//initComponents(frame.getContentPane(),8);
		

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void finish(ConcurrentHashMap<String, Boolean> alreadyUsed, GameModes mode, BlockingQueue<String> goodWords) {
		//MAJ du fichier contenant les mots interdits
		if (mode==GameModes.ADVANCED) {
			File seen = new File("AlreadyUsed.txt");
			BufferedWriter bwriter = null;
			try {
				bwriter = new BufferedWriter(new FileWriter(seen));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				for (String word : alreadyUsed.keySet()) {
					bwriter.write(word+"\n");
				}
				bwriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		frame.setVisible(false);
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(400, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = frame.getContentPane();
		
		//scoreet mots utilisés
		
		JLabel mots =new JLabel();
		String words = "Mots utilisés avec succès :\n";
		for (String word : goodWords) {
			words += word+" ";
		}
		mots.setText(words);
		mots.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(mots);

		JLabel score = new JLabel("Votre score est de : "+counter.toString()+" point(s).");
		score.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(score);
		
		//layout
		
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				           .addComponent(mots)
				           .addComponent(score))
				);
				layout.setVerticalGroup(
				   layout.createSequentialGroup()
				      .addComponent(mots)
				      .addComponent(score)
				);
		
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public void run() {
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
