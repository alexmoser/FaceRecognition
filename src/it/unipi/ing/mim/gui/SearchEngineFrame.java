package it.unipi.ing.mim.gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Desktop;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bytedeco.javacpp.opencv_core.Mat;

import it.unipi.ing.mim.facedetection.DetectionParameters;
import it.unipi.ing.mim.facedetection.FaceDetection;
import it.unipi.ing.mim.facedetection.Utility;
import it.unipi.ing.mim.facerecognition.RecognitionParameters;
import it.unipi.ing.mim.facerecognition.SearchEngine;
import it.unipi.ing.mim.facerecognition.SearchEngineFaceDetection;

import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchEngineFrame {

	private final int WIDTH = 180;
	private final int HEIGHT = 180;
	private JFrame frame;
	private final Action homeAction = new HomeAction();
	private final Action browseAction = new BrowseAction();
	private final Action searchAction = new SearchAction();
	private JTextField txtPath;
	private JSpinner spinner;
	private JCheckBox chckbxFaceDetection;
	private JLabel lblImage;
	private JButton btnBrowse;
	private JLabel lblNumberOfMost;
	private JButton btnSearch;
	private JLabel lblImg;
	private JLabel lblSelect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchEngineFrame window = new SearchEngineFrame();
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
	public SearchEngineFrame() {
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
		btnHome.setAction(homeAction);
		frame.getContentPane().add(btnHome);
		
		lblImage = new JLabel("Image :");
		lblImage.setBounds(23, 50, 61, 16);
		frame.getContentPane().add(lblImage);
		
		txtPath = new JTextField();
		txtPath.setText("Insert image path");
		txtPath.setBounds(23, 69, 298, 28);
		frame.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1);
		spinner = new JSpinner(sm);
		spinner.setBounds(254, 109, 53, 28);
		frame.getContentPane().add(spinner);
		
		lblNumberOfMost = new JLabel("Number of most similar images :");
		lblNumberOfMost.setBounds(23, 115, 219, 16);
		frame.getContentPane().add(lblNumberOfMost);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.setAction(browseAction);
		btnBrowse.setBounds(327, 70, 117, 29);
		frame.getContentPane().add(btnBrowse);
		
		chckbxFaceDetection = new JCheckBox("Face Detection");
		chckbxFaceDetection.setBounds(23, 159, 128, 23);
		frame.getContentPane().add(chckbxFaceDetection);
		
		lblImg = new JLabel("");
		lblImg.setBounds(23, 17, WIDTH, HEIGHT);
		lblImg.setVisible(false);
		frame.getContentPane().add(lblImg);
		
		lblSelect = new JLabel("Select one face");
		lblSelect.setBounds(6, 230, 438, 16);
		lblSelect.setHorizontalAlignment(JLabel.CENTER);
		lblSelect.setHorizontalTextPosition(JLabel.CENTER);
		lblSelect.setVisible(false);
		frame.getContentPane().add(lblSelect);
		
		btnSearch = new JButton("Search");
		btnSearch.setAction(searchAction);
		btnSearch.setBounds(0, 225, 450, 29);
		frame.getContentPane().add(btnSearch);
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
	private class BrowseAction extends AbstractAction {
		public BrowseAction() {
			putValue(NAME, "Browse");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG files", "jpg");
				fileChooser.setFileFilter(filter);
				fileChooser.setCurrentDirectory(new File("data/lfw_funneled"));
				int returnVal = fileChooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getPath());
				}
		}
	}
	private class SearchAction extends AbstractAction {
		public SearchAction() {
			putValue(NAME, "Search");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			try {
				lblImg.setIcon(loadAndResizeImg(txtPath.getText(), WIDTH, HEIGHT));
				changeVisibility(false);
				if(chckbxFaceDetection.isSelected()){
					FaceDetection faceDetector = new FaceDetection(DetectionParameters.HAAR_CASCADE_FRONTALFACE);
					final Mat[] imgMat = faceDetector.getFaces(txtPath.getText(), DetectionParameters.PADDING);
					int x_offset = 40 + WIDTH,
						y_offset = 17;
					for(int i = 0; i < imgMat.length; i++){
						if(!RecognitionParameters.TMP_SEARCH_ENGINE_FOLDER.exists())
							RecognitionParameters.TMP_SEARCH_ENGINE_FOLDER.mkdir();
						final String facePath = RecognitionParameters.TMP_SEARCH_ENGINE_FOLDER + "/" + "face_" + i + ".jpg";
						Utility.face2File(imgMat[i], new File(facePath));
						JLabel lblFace = new JLabel("");
						lblFace.setName("lblFace_" + i);
						lblFace.setBounds(x_offset, y_offset, 50, 50);
						lblFace.setIcon(loadAndResizeImg(facePath, 50, 50));
						lblFace.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								String name = lblFace.getName();
								String [] tmp = name.split("_");
								int index = Integer.parseInt(tmp[1]);
								try{
									SearchEngineFaceDetection.searchEngine(facePath, (int)spinner.getValue(), imgMat[index]);
									Desktop.getDesktop().browse(RecognitionParameters.SEARCH_ENGINE_HTML_FD.toURI());
								}
								catch(ClassNotFoundException e1){
									e1.printStackTrace();
									return;
								}
								catch(IOException e1) {
									System.err.println("Html results file not found!");
									return;
								}
							}
						});
						frame.getContentPane().add(lblFace);
						
						if(((i+1) % 3) == 0){
							x_offset = 40 + WIDTH;
							y_offset += 60;
						}
						else
							x_offset += 60;
					}
				}
				else{
					try{
						lblImg.setHorizontalAlignment(SwingConstants.CENTER);
						SearchEngine.searchEngine(txtPath.getText(), (int)spinner.getValue());
						Desktop.getDesktop().browse(RecognitionParameters.SEARCH_ENGINE_HTML.toURI());
					}
					catch(ClassNotFoundException e1){
						e1.printStackTrace();
						return;
					}
					catch(IOException e1) {
						System.err.println("Html results file not found!");
						return;
					}
				}
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void changeVisibility(boolean visible) {
		lblImage.setVisible(visible);
		txtPath.setVisible(visible);
		btnBrowse.setVisible(visible);
		btnSearch.setVisible(visible);
		chckbxFaceDetection.setVisible(visible);
		lblNumberOfMost.setVisible(visible);
		spinner.setVisible(visible);
		lblImg.setVisible(!visible);
		if(chckbxFaceDetection.isSelected())
			lblSelect.setVisible(!visible);
	}
	
	private ImageIcon loadAndResizeImg(String path, int width, int height) {
		BufferedImage img = null;
		
		try {
		    img = ImageIO.read(new File(path));
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		return new ImageIcon(dimg);
	}
}
