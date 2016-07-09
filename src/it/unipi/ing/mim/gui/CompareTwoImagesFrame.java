package it.unipi.ing.mim.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import it.unipi.ing.mim.facerecognition.CompareTwoImages;
import it.unipi.ing.mim.facerecognition.CompareTwoImagesFaceDetection;
import it.unipi.ing.mim.facerecognition.RecognitionParameters;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;

public class CompareTwoImagesFrame {

	private final int WIDTH = 180;
	private final int HEIGHT = 180;
	private JFrame frame;
	private JTextField txtPath1;
	private JTextField txtPath2;
	private JCheckBox chckbxFaceDetection;
	private final Action homeAction = new HomeAction();
	private final Action browseImg1Action = new BrowseImg1Action();
	private final Action browseImg2Action = new BrowseImg2Action();
	private final Action compareAction = new CompareAction();
	private JButton btnBrowse;
	private JButton btnBrowse_1;
	private JLabel lblSecondImage;
	private JLabel lblFirstImage;
	private JButton btnCompare;
	private JLayeredPane pane1;
	private JLabel lblImg2;
	private JLabel lblImg1;
	private JLabel lblResult;
	private final Action showMatchesAction = new ShowMatchesAction();
	private JButton btnShowMatches;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompareTwoImagesFrame window = new CompareTwoImagesFrame();
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
	public CompareTwoImagesFrame() {
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
		
		JButton btnHome = new JButton("Home");
		btnHome.setBounds(0, 249, 450, 29);
		frame.getContentPane().add(btnHome);
		btnHome.setAction(homeAction);
		
		JLayeredPane pane2 = new JLayeredPane();
		pane2.setBounds(0, 0, 1, 1);
		frame.getContentPane().add(pane2);
		
		pane1 = new JLayeredPane();
		pane1.setBounds(0, 0, 450, 249);
		frame.getContentPane().add(pane1);
		
		lblFirstImage = new JLabel("Image 1 :");
		lblFirstImage.setBounds(16, 43, 72, 16);
		pane1.add(lblFirstImage);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(327, 62, 117, 29);
		pane1.add(btnBrowse);
		btnBrowse.setAction(browseImg1Action);
		
		txtPath1 = new JTextField();
		txtPath1.setBounds(16, 61, 305, 28);
		pane1.add(txtPath1);
		txtPath1.setText("Insert image path");
		txtPath1.setColumns(10);
		
		lblSecondImage = new JLabel("Image 2 :");
		lblSecondImage.setBounds(16, 105, 61, 16);
		pane1.add(lblSecondImage);
		
		txtPath2 = new JTextField();
		txtPath2.setBounds(16, 122, 305, 28);
		pane1.add(txtPath2);
		txtPath2.setText("Insert image path");
		txtPath2.setColumns(10);
		
		btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.setBounds(327, 123, 117, 29);
		pane1.add(btnBrowse_1);
		btnBrowse_1.setAction(browseImg2Action);
		
		chckbxFaceDetection = new JCheckBox("Face Detection");
		chckbxFaceDetection.setBounds(18, 179, 128, 23);
		pane1.add(chckbxFaceDetection);
		
		lblImg1 = new JLabel("");
		lblImg1.setBounds(40, 6, WIDTH, HEIGHT);
		lblImg1.setVisible(false);
		pane1.add(lblImg1);
		
		lblImg2 = new JLabel("");
		lblImg2.setBounds(50 + WIDTH, 6, WIDTH, HEIGHT);
		lblImg2.setVisible(false);
		pane1.add(lblImg2);
		
		lblResult = new JLabel("New label");
		lblResult.setBounds(6, 198, 438, 16);
		lblResult.setHorizontalAlignment(JLabel.CENTER);
		lblResult.setHorizontalTextPosition(JLabel.CENTER);
		lblResult.setVisible(false);
		pane1.add(lblResult);
		
		btnShowMatches = new JButton("Show Result");
		btnShowMatches.setAction(showMatchesAction);
		btnShowMatches.setBounds(0, 220, 450, 29);
		btnShowMatches.setVisible(false);
		pane1.add(btnShowMatches);
		
		btnCompare = new JButton("Compare");
		btnCompare.setBounds(0, 220, 450, 29);
		pane1.add(btnCompare);
		btnCompare.setAction(compareAction);
	}

	private class HomeAction extends AbstractAction {
		public HomeAction() {
			putValue(NAME, "Home");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			MenuFrame.main(null);
		}
	}
	private class BrowseImg1Action extends AbstractAction {
		public BrowseImg1Action() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG files", "jpg");
				fileChooser.setFileFilter(filter);
				if(chckbxFaceDetection.isSelected())
					fileChooser.setCurrentDirectory(new File("data/lfw"));
				else
					fileChooser.setCurrentDirectory(new File("data/lfw_funneled"));
				int returnVal = fileChooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtPath1.setText(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getPath());
				}
		}
	}

	private class BrowseImg2Action extends AbstractAction {
		public BrowseImg2Action() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG files", "jpg");
				fileChooser.setFileFilter(filter);
				if(chckbxFaceDetection.isSelected())
					fileChooser.setCurrentDirectory(new File("data/lfw"));
				else
					fileChooser.setCurrentDirectory(new File("data/lfw_funneled"));
				int returnVal = fileChooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtPath2.setText(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("You chose to open this file: " + fileChooser.getSelectedFile().getName());
				}
		}
	}
	
	private class CompareAction extends AbstractAction {
		public CompareAction() {
			putValue(NAME, "Compare");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			boolean res = false;
			int faces = 0;
			try{
				changeVisibility(false);
				if(chckbxFaceDetection.isSelected()) {
					faces = CompareTwoImagesFaceDetection.compare(txtPath1.getText(), txtPath2.getText());
					if(faces > 0)
						lblResult.setText("There are " + faces + " people that appear in both images");
					else
						lblResult.setText("There are not people that appear in both images");
				}
				else { 
					res = CompareTwoImages.compare(txtPath1.getText(), txtPath2.getText());
					if(res)
						lblResult.setText("The person in the two images is the same!");
					else
						lblResult.setText("The person in the two images is NOT the same!");
				}
			}
			catch (Exception e1) {
				System.err.println("Images not valid!");
				e1.printStackTrace();
				changeVisibility(true);
				return;
			}

			lblImg1.setIcon(loadAndResizeImg(txtPath1.getText()));
			lblImg2.setIcon(loadAndResizeImg(txtPath2.getText()));
		}
	}
	
	private class ShowMatchesAction extends AbstractAction {
		public ShowMatchesAction() {
			putValue(NAME, "Show Matches");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			// open .html results file
			try {
				Desktop.getDesktop().browse(RecognitionParameters.COMPARE_HTML_FD.toURI());
			}
			catch(IOException e1) {
				System.err.println("Html results file not found!");
				return;
			}
		}
	}
	
	private void changeVisibility(boolean visible) {
		lblFirstImage.setVisible(visible);
		lblSecondImage.setVisible(visible);
		txtPath1.setVisible(visible);
		txtPath2.setVisible(visible);
		btnBrowse.setVisible(visible);
		btnBrowse_1.setVisible(visible);
		btnCompare.setVisible(visible);
		chckbxFaceDetection.setVisible(visible);
		lblImg1.setVisible(!visible);
		lblImg2.setVisible(!visible);
		lblResult.setVisible(!visible);
		if(chckbxFaceDetection.isSelected())
			btnShowMatches.setVisible(!visible);
		
	}
	
	private ImageIcon loadAndResizeImg(String path) {
		BufferedImage img = null;
		
		try {
		    img = ImageIO.read(new File(path));
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		
		return new ImageIcon(dimg);
	}
}
