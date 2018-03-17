package application;

/*
 * GridBagLayoutDemo.java requires no other files.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main {

	private static void initComponents(Container pane) {

		addComponentsToPane(pane);
	}

	private static void addComponentsToPane(Container pane) {


		
		
		//Timer
		JLabel tempsRestant = new JLabel();
		tempsRestant.setText("100");
		final Chrono chrono = new Chrono(100, tempsRestant);
		Panel timer = new Panel();
		timer.add(tempsRestant);
		
		//Text Area
		final JTextArea ecriture = new JTextArea("Type in as"+
		"many words as possible, before the count ends.");
		
		
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
		initComponents(frame.getContentPane());

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
