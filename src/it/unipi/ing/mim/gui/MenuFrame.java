package it.unipi.ing.mim.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

/**
 * This is the main of the entire application. It provides a graphic interface
 * that allows the user to choose between the two different functions.
 * */
public class MenuFrame {

	private JFrame frame;
	private final Action compareTwoImagesAction = new CompareTwoImagesAction();
	private final Action searchEngineAction = new SearchEngineAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame window = new MenuFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCompareTwoImages = new JButton("Compare Two Images");
		btnCompareTwoImages.setBounds(6, 90, 438, 29);
		btnCompareTwoImages.setAction(compareTwoImagesAction);
		frame.getContentPane().add(btnCompareTwoImages);
		
		JButton btnSearchEngine = new JButton("New button");
		btnSearchEngine.setBounds(6, 145, 438, 29);
		btnSearchEngine.setAction(searchEngineAction);
		frame.getContentPane().add(btnSearchEngine);
	}
	
	private class CompareTwoImagesAction extends AbstractAction {
		/**
		 * default serialVersionUID assigned
		 */
		private static final long serialVersionUID = 1L;
		public CompareTwoImagesAction() {
			putValue(NAME, "Compare Two Images");
			putValue(SHORT_DESCRIPTION, "Click here to compare two images");
		}
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			CompareTwoImagesFrame.main(null);
		}
	}
	
	private class SearchEngineAction extends AbstractAction {
		/**
		 * default serialVersionUID assigned
		 */
		private static final long serialVersionUID = 1L;
		public SearchEngineAction() {
			putValue(NAME, "Search Engine");
			putValue(SHORT_DESCRIPTION, "Click here to search similar images in the database");
		}
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			SearchEngineFrame.main(null);
		}
	}
}
