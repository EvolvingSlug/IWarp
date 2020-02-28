import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IWarp {

	// *-*-*-*-*-*-*-*-*-*-*-*-a
	// Ignore this part
	// *-*-*-*-*-*-*-*-*-*-*-*-
	private static JLabel imageLabel = null;
	private static JFrame frame = null;
	private static Color[][] workingImage = null;
	private static ArrayList<Integer> mouseClicks = new ArrayList<Integer>();
	private static int normFactor = 50;
	private static JButton recordButton = null;
	private static boolean recording = false;
	private static String recordingFileName = null;

	private enum Effect { LIQUIFY, TWIST_LEFT, TWIST_RIGHT, PINCH, BULGE; }
	private static Effect currentEffect = Effect.LIQUIFY;
	// *-*-*-*-*-*-*-*-*-*-*-*-
	// Ignore this part
	// *-*-*-*-*-*-*-*-*-*-*-*-


	public static void main(String[] args) throws Exception
	{
		displayApp();
	}


	/**
	 * TODO: Implement and document this method
	 */
	public static void liquifyEffect(Color[][] imagePixels, int rowP0, int colP0, int rowP1, int colP1, double normFactor){
		int destRow = 0;
		int destCol=0;
		Color[][] destination = new Color[imagePixels.length][imagePixels[0].length];


		//Code here
		//for loop to keep clickling

		//Difference between rowP0 and rowP1
		double deltaRow = rowP0 - rowP1;
		//Difference between colP0 and colP1
		double deltaCol = colP0 - colP1;
		//This gets the euclidean distance between(destRow,destCol)and (rowP1,colP1)
		double distance = Math.sqrt((rowP1 - destRow) * (rowP1 - destRow) + (colP1 - destCol) * (colP1 - destCol));
		//Weight to
		double weight = Math.pow(2, -distance / normFactor);
		double srcRow = destRow - (deltaCol * weight);
		double srcCol = destCol - (deltaCol * weight);

		//Rewrite a better if srcCol and srcRow
		if (srcRow < 0) {
			srcRow = 0;
		} else if (imagePixels.length > srcRow) {
			srcRow = imagePixels.length;
		} else if (srcCol < 0) {
			srcCol = 0;
		} else if (imagePixels.length >= srcCol) {
			srcRow = imagePixels.length;

		}


		destination[destRow][destCol]= imagePixels[(int)srcRow][(int)srcCol];
		updateImage(destination);
	}


	/**
	 * TODO: Implement and document this method
	 */
	public static void twistLeftEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor)
	{
		double row=0, col=0, destRow = 0,destCol=0;
		Color[][] destination = new Color[imagePixels.length][imagePixels[0].length];
		//Code here
		//Step 1
		double maxAngleDelta= Math.PI /2;
		//Step 3
		double distance = Math.sqrt((rowP0 - destRow) * (rowP0 - destRow) + (colP0 - destCol) * (colP0 - destCol));
		//Step 4
		double weight = Math.pow(2, -distance / normFactor);
		//Step 5
		double angleDelta = maxAngleDelta * weight;
		//Step 6
		double newAngle = Math.atan2(row - rowP0, col - colP0);
		//Step 7
		double srcRow = rowP0+ Math.sin(angleDelta) *distance;
		//Step 8
		double srcCol =colP0+ Math.sin(angleDelta) *distance;


		destination[(int)destRow][(int)destCol]= imagePixels[(int)srcRow][(int)srcCol];
		updateImage(destination);
	}

	/**
	 * TODO: Implement and document this method
	 */
	public static void twistRightEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor)
	{
		double row=0, col=0, destRow = 0,destCol=0;
		Color[][] destination = new Color[imagePixels.length][imagePixels[0].length];
		//Code here
		//Step 1 check on the negative pi
		double maxAngleDelta= -Math.PI /2;
		//Step 3
		double distance = Math.sqrt((rowP0 - destRow) * (rowP0 - destRow) + (colP0 - destCol) * (colP0 - destCol));
		//Step 4
		double weight = Math.pow(2, -distance / normFactor);
		//Step 5
		double angleDelta = maxAngleDelta * weight;
		//Step 6
		double newAngle = Math.atan2(row - rowP0, col - colP0);
		//Step 7
		double srcRow = rowP0+ Math.sin(angleDelta) *distance;
		//Step 8
		double srcCol =colP0+ Math.sin(angleDelta) *distance;


		destination[(int)destRow][(int)destCol]= imagePixels[(int)srcRow][(int)srcCol];
		updateImage(destination);
	}

	/**
	 * TODO: Implement and document this method
	 */
	public static void twistEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor, double maxAngleDelta)
	{
		Color[][] destination = new Color[imagePixels.length][imagePixels[0].length];

		//Code Here

		updateImage(destination);
	}

	/**
	 * TODO: Implement and document this method
	 */
	public static void pinchEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor)
	{
		double row=0, col=0, destRow = 0,destCol=0;
		Color[][] DestinationImage = new Color[imagePixels.length][imagePixels[0].length];
		//Code here
		//Step 1
		double maxDistDelta = -0.5;
		//Step 3
		double distance = Math.sqrt((rowP0 - destRow) * ((double)rowP0 - destRow) + ((double)colP0 - destCol) * ((double)colP0 - destCol));
		//Step 4
		double weight = Math.pow(2,(-distance/normFactor));
		//Step 5
		double angle = Math.atan2(destRow - (double)rowP0, destCol - (double)colP0);
		//Step 6
		double deltaDistance = maxDistDelta * distance;
		//Step 7
		double weightedDistance = distance - (weight * deltaDistance);
		//Step 8
		int srcRow = (int) (rowP0 + Math.sin(angle) * weightedDistance);
		//Step 9
		int srcCol = (int) (colP0 + Math.cos(angle) * weightedDistance);
		
		DestinationImage[(int)destRow][(int)destCol] = imagePixels[(int)srcRow][(int)srcCol];
		updateImage(DestinationImage);
	}

	/**
	 * TODO: Implement and document this method
	 */
	public static void bulgeEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor)
	{
		double row=0, col=0, destRow = 0,destCol=0;
		Color[][] DestinationImage = new Color[imagePixels.length][imagePixels[0].length];
		//Code here
		//Step 1
		double maxDistDelta = 0.5;
		//Step 3
		double distance = Math.sqrt((rowP0 - destRow) * (rowP0 - destRow) + (colP0 - destCol) * (colP0 - destCol));
		//Step 4
		double weight = Math.pow(2,(-distance/normFactor));
		//Step 5
		double angle = Math.atan2(destRow - rowP0, destCol - colP0);
		//Step 6
		double deltaDistance = maxDistDelta * distance;
		//Step 7
		double weightedDistance = distance - (weight * deltaDistance);
		//Step 8
		int srcRow = (int) (rowP0 + Math.sin(angle) * weightedDistance);
		//Step 9
		int srcCol = (int) (colP0 + Math.cos(angle) * weightedDistance);
		
		DestinationImage[(int)destRow][(int)destCol] = imagePixels[srcRow][srcCol];
		updateImage(DestinationImage);

	}

	/**
	 * TODO: Implement and document this method
	 */
	public static void zoomEffect(Color[][] imagePixels, int rowP0, int colP0, double normFactor, double maxDistDelta)
	{
		Color[][] destination = new Color[imagePixels.length][imagePixels[0].length];

		//Code here

		updateImage(destination);
	}

	/**
	 * Executes the operations stored in the given text file
	 * @param filePath Path to the file that contains the instructions
	 *
	 * DO NOT MODIFY
	 *
	 */
	private static void executeFileInstructions(String filePath) {

		File file = new File(filePath);
		Scanner sc;
		try {
			sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tokens = line.split(" ");

				if (tokens.length < 4)
					continue;

				int rowP0 = Integer.parseInt(tokens[1]);
				int colP0 = Integer.parseInt(tokens[2]);
				switch(tokens[0]) {
					case "LIQUIFY":
						int rowP1 = Integer.parseInt(tokens[3]);
						int colP1 = Integer.parseInt(tokens[4]);
						liquifyEffect(workingImage, rowP0, colP0, rowP1, colP1, normFactor);
						break;
					case "TWIST_LEFT":
						twistLeftEffect(workingImage, rowP0, colP0, normFactor);
						break;
					case "TWIST_RIGHT":
						twistRightEffect(workingImage, rowP0, colP0, normFactor);
						break;
					case "PINCH":
						pinchEffect(workingImage, rowP0, colP0, normFactor);
						break;
					case "BULGE":
						bulgeEffect(workingImage, rowP0, colP0, normFactor);
						break;
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
		}




	}

	/**
	 * Appends the given effect to the given text file
	 *
	 * @param filePath		Path to the text file
	 * @param effectName	One of the following: LIQUIFY, TWIST_LEFT, TWIST_RIGHT, PINCH, BULGE
	 * @param effectParams	Effect parameters
	 *
	 * DO NOT MODIFY
	 */
	private static void appendOperationToFile(String filePath, String effectName, int[] effectParams) {
		// Add your code here

		try {
			File file = new File(filePath);
			FileWriter fr = new FileWriter(file, true);
			fr.write(effectName + " ");

			for (int i = 0; i < effectParams.length; i++)
				fr.write(effectParams[i] + " ");

			fr.write("\n");
			fr.close();


		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Save the image that is being displayed.
	 * Hint: the global variable workingImage holds the pixel values of the image that needs to be saved
	 */
	protected static void saveImageClicked() {
		JOptionPane.showMessageDialog(frame, "Extra Credit: Implement this feature");
		// Add your code here
	}


	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	//
	// You can ignore the rest of this file. You don't have to understand what these methods do.
	// However, if you are curious, feel free to read them! (:
	//
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Updates the image being displayed
	 * @param imagePixels New image to be displayed
	 */
	public static void updateImage(Color[][] imagePixels) {
		workingImage = imagePixels;
		mouseClicks.clear();
		frame.remove(imageLabel);
		BufferedImage image = createImageFromPixelArray(imagePixels);
		ImageIcon icon = new ImageIcon();
		icon.setImage(image);

		imageLabel = new JLabel();
		imageLabel.setIcon(icon);

		imageLabel.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) { }
			public void mouseReleased(MouseEvent me) { }
			public void mouseEntered(MouseEvent me) { }
			public void mouseExited(MouseEvent me) { }
			public void mouseClicked(MouseEvent me) {
				if(me.getButton() == MouseEvent.BUTTON1) {
					imageClicked(me.getY(), me.getX());
				}
			}
		});

		frame.add(imageLabel);
		frame.invalidate();
		frame.revalidate();
		frame.repaint();

	}

	/**
	 * Computes Euclidean distance of the two given points
	 * @param rowP0
	 * @param colP0
	 * @param rowP1
	 * @param colP1
	 * @return
	 */
	public static double computeDistance(int rowP0, int colP0, int rowP1, int colP1)
	{
		return Math.sqrt(Math.pow(rowP1 - rowP0, 2) + Math.pow(colP1 - colP0, 2));
	}

	/**
	 * Executes when the Record/Stop button is clicked.
	 */
	public static void recordClicked() {

		if (! recording)
			recordingFileName = JOptionPane.showInputDialog(frame, "Type the name of the file");

		recording = ! recording;

		recordButton.setText(recording ? "Stop" : "Record");
	}

	/**
	 * This method saves a given image to disk
	 *
	 * @param imagePixels Image to be saved
	 * @param fileName	  Name of the file
	 */
	public static void saveImage(Color[][] imagePixels, String fileName)
	{
		try {
			// Convert to BufferedImage
			BufferedImage bi = createImageFromPixelArray(imagePixels);

			//Save file
			File outputfile = new File(fileName);
			ImageIO.write(bi, "jpg", outputfile);
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
	}

	/**
	 * Executes a given effect when the image is clicked
	 *
	 * @param row row where the image was clicked
	 * @param col col where the image was clicked
	 */
	private static void imageClicked(int row, int col) {

		int[] params = {row, col};
		switch(currentEffect) {
			case LIQUIFY:
				if (mouseClicks.size() < 2) {
					mouseClicks.add(row);
					mouseClicks.add(col);
				}
				else {

					int[] liquifyParams = {mouseClicks.get(0), mouseClicks.get(1), row, col};
					liquifyEffect(workingImage, mouseClicks.get(0), mouseClicks.get(1), row, col, normFactor);

					if (recording)
						appendOperationToFile(recordingFileName, "LIQUIFY", liquifyParams);

					mouseClicks.clear();

				}
				break;
			case TWIST_LEFT:
				twistLeftEffect(workingImage, row, col, normFactor);

				if (recording)
					appendOperationToFile(recordingFileName, "TWIST_LEFT", params);

				break;
			case TWIST_RIGHT:
				twistRightEffect(workingImage, row, col, normFactor);

				if (recording)
					appendOperationToFile(recordingFileName, "TWIST_RIGHT", params);

				break;
			case PINCH:
				pinchEffect(workingImage, row, col, normFactor);

				if (recording)
					appendOperationToFile(recordingFileName, "PINCH", params);

				break;
			case BULGE:
				bulgeEffect(workingImage, row, col, normFactor);

				if (recording)
					appendOperationToFile(recordingFileName, "BULGE", params);

				break;
		}

	}

	/**
	 * Display a GUI component to select a text file where
	 * the set of effect instructions is stored.
	 */
	private static void executeFromFileButtonClicked() {
		FileFilter textFilter = new FileNameExtensionFilter(
				"Text Files", "txt");
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(textFilter);
		int returnVal = fc.showOpenDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			executeFileInstructions(file.getAbsolutePath());
		}
	}

	/**
	 * Displays the GUI
	 *
	 */
	public static void displayApp()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Color[][] whiteImage = {{Color.WHITE}};

		ImageIcon icon = new ImageIcon(resizeImage(createImageFromPixelArray(whiteImage)));
		imageLabel = new JLabel();
		imageLabel.setIcon(icon);

		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(screenSize.width,screenSize.height);

		JButton selectImageButton = new JButton("Select Image");
		JButton readFileButton = new JButton("Execute Instructions (from file)");
		recordButton = new JButton("Record");
		JButton saveImageButton = new JButton("Save Image");


		ButtonGroup group = new ButtonGroup();
		JRadioButton liquifyButton = new JRadioButton("Liquify");
		JRadioButton twistLeftButton = new JRadioButton("Twist Left");
		JRadioButton twistRightButton = new JRadioButton("Twist Right");
		JRadioButton pinchButton = new JRadioButton("Pinch");
		JRadioButton bulgeButton = new JRadioButton("Bulge");
		group.add(liquifyButton);
		group.add(twistLeftButton);
		group.add(twistRightButton);
		group.add(pinchButton);
		group.add(bulgeButton);

		liquifyButton.setSelected(true);
		frame.add(selectImageButton);
		frame.add(readFileButton);
		frame.add(recordButton);
		frame.add(saveImageButton);
		frame.add(liquifyButton);
		frame.add(twistLeftButton);
		frame.add(twistRightButton);
		frame.add(pinchButton);
		frame.add(bulgeButton);
		frame.add(imageLabel);


		selectImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readImageClicked();
			}
		});

		readFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeFromFileButtonClicked();
			}
		});

		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recordClicked();
			}
		});
		saveImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveImageClicked();

			}
		});

		liquifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				effectChanged(Effect.LIQUIFY);
			}
		});

		twistLeftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				effectChanged(Effect.TWIST_LEFT);
			}
		});

		twistRightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				effectChanged(Effect.TWIST_RIGHT);
			}
		});

		pinchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				effectChanged(Effect.PINCH);
			}
		});

		bulgeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				effectChanged(Effect.BULGE);
			}
		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}




	/**
	 * Changes the current effect when a radio button is clicked
	 * @param effect Selected effect
	 */
	protected static void effectChanged(Effect effect) {

		currentEffect = effect;
		mouseClicks.clear();
	}



	/**
	 * Opens a file chooser to select an image
	 */
	protected static void readImageClicked() {

		FileFilter imageFilter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes());

		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(imageFilter);
		int returnVal = fc.showOpenDialog(frame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			updateImage(readImage(file.getAbsolutePath()));
		}

	}


	/**
	 * This method reads an image given its path
	 *
	 * @param filePath	The path of the image to be read
	 * @return			A 2D array of pixels representing the image
	 * @throws Exception Exception is thrown when the file does not exist
	 */
	public static Color[][] readImage(String filePath){
		File imageFile = new File(filePath);
		BufferedImage image;
		try {
			image = ImageIO.read(imageFile);
			image = resizeImage(image);

			Color[][] colors = new Color[image.getHeight()][image.getWidth()];

			for (int row = 0; row < image.getHeight(); row++) {
				for (int col = 0; col < image.getWidth(); col++) {
					colors[row][col] = new Color(image.getRGB(col, row));
				}
			}

			return colors;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Method that resized the input image to 854x480
	 * @param Image Image to be resized
	 * @return Resized image
	 */
	public static BufferedImage resizeImage(BufferedImage image) {
		int width = 854;
		int height = 480;
		Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		image = new BufferedImage(width, height, Image.SCALE_REPLICATE);
		image.getGraphics().drawImage(tmp, 0, 0 , null);

		return image;
	}

	/**
	 * This method receives a 2D array of pixel colors and returns
	 * its equivalent BufferedImage representation
	 *
	 * @param imagePixels Pixel values of the image to be converted
	 *
	 * @return BufferedImage representation of the input
	 */
	public static BufferedImage createImageFromPixelArray(Color[][] imagePixels) {
		BufferedImage image = new BufferedImage(imagePixels[0].length, imagePixels.length, BufferedImage.TYPE_INT_RGB);

		for (int row = 0; row < image.getHeight(); row++) {
			for (int col = 0; col < image.getWidth(); col++) {
				image.setRGB(col, row, imagePixels[row][col].getRGB());
			}
		}

		return image;
	}

}
