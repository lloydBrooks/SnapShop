/*
 * TCSS 305 - Spring 2017
 */

package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.Filter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Creates and manages a GUI for SnapShop Program.
 * 
 * @author Lloyd Brooks
 * @version 4/28/17
 */
public class SnapShopGUI extends JPanel {

    /**
     * Auto generated.
     */
    private static final long serialVersionUID = -5220402245365656081L;

    //Class constant:
    /**
     * The amount of space between buttons in this GUI.
     */
    private static final int BORDER = 5;
    
    //Instance Fields
    /**
     * T/F : Has image?
     */
    private boolean myImageStatus;
    
    /**
     * The image to be manipulated and displayed by this program.
     */
    private PixelImage myImage;
    
    /**
     * A list of Buttons.
     */
    private final List<JButton> myButtons;
    
    
    // javax.swing components
    /**
     * Main window for the program.
     */
    private JFrame myWindow;
    
    /**
     * holds all buttons in the west side of the JFrame. Uses a border layout.
     */
    private JPanel myButtonPanel;
    
    /**
     * Holds the Image in the center of the JFrame. Visible if there is an image. 
     * Uses a flow layout set to left justified.
     */
    private JPanel myImagePanel;
    
    /**
     * Holds the Filter buttons in a grid layout with one column.
     */
    private JPanel myFilterButtonPanel;
    
    /**
     * Holds the file management buttons in a grid layout with one column.
     */
    private JPanel myFileButtonPanel;
    
    /**
     * Holds the image in a JLabel.
     */
    private final JLabel myImageLabel;
    
    /**
     * Used for open, and Save As functionality.
     */
    private final JFileChooser myFileManager;
    
    //Constructor
    /**
     * A basic constructor for the SnapShopGUI class.
     */
    public SnapShopGUI() {
        super();
        
        myImageStatus = false;
        myImageLabel = new JLabel();
        myFileManager = new JFileChooser();
        final File relativeDir = new File(".");
        myFileManager.setCurrentDirectory(relativeDir);
        myButtons = new ArrayList<JButton>();
        
        setLayout(new BorderLayout(BORDER, BORDER));
    }

    /**
     * set up the GUI.
     */
    public void start() {
      
        //set up myButtonPanel
        myButtonPanel = new JPanel(new BorderLayout(BORDER, BORDER));
        
        //set up myImagePanel
        myImagePanel = new JPanel();
        
        //set up myFilterButtonPanel
        final List<Filter> filters = setFilters();
        myFilterButtonPanel = new JPanel(new GridLayout(filters.size(), 1, BORDER, BORDER));
        final List<JButton> filterButtons = createFilterButtons(filters);
        addButtons(filterButtons, myFilterButtonPanel);
       
        //set up myFileButtonPanel
        final int buttonNum = 3;
        myFileButtonPanel = new JPanel(new GridLayout(buttonNum, 1, BORDER, BORDER));
        final List<JButton> fileButtons = createFileButtons();
        addButtons(fileButtons, myFileButtonPanel);
        
        //fill myButtons arrayList.
        myButtons.addAll(filterButtons);
        myButtons.addAll(fileButtons);
        final int hateMagicNums = 3;
        myButtons.remove(myButtons.size() - hateMagicNums);
        setActive();
        assemble();
    }
    
    /**
     * Assembles the composite layout for this GUI and places it into a JFrame.
     */
    private void assemble() {
        
        myButtonPanel.add(myFilterButtonPanel, BorderLayout.NORTH);
        myButtonPanel.add(myFileButtonPanel, BorderLayout.SOUTH);
        
        myImagePanel.add(myImageLabel, FlowLayout.LEFT);
        
        this.add(myButtonPanel, BorderLayout.WEST);
        this.add(myImagePanel, BorderLayout.CENTER);
        
        myWindow = new JFrame("TCSS 305 SnapShop");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setContentPane(this);

        //smallest the window can be, so that buttons are never hidden.
        setSmallest(); 
        
        //make window visible to user.
        myWindow.setVisible(true);
        
    }

    /**
     * resets the smallest size of the window.
     */
    private void setSmallest() {
        myWindow.setMinimumSize(new Dimension(1, 1));
        myWindow.pack();
        final Dimension min = new Dimension(myWindow.getSize()); 
        myWindow.setMinimumSize(min);
    }

    /**
     * Creates the file management buttons and returns them as a List of JButtons.
     * 
     * @return A list of JButtons.
     */
    private List<JButton> createFileButtons() {
        final List<JButton> result = new ArrayList<JButton>();
        
        //set up the open button.
        final JButton open = new JButton("Open");
        open.addActionListener((theEvent) -> {
            setUpOpen();
        });
        
        //set up the saveAs button.
        final JButton saveAs = new JButton("Save As");
        saveAs.addActionListener((theEvent) -> {
            setUpSave();
        });
        
        //set up the close button.
        final JButton close = new JButton("Close Image");
        close.addActionListener((theEvent) -> {
            myImageStatus = false;
            setActive();
            setSmallest();
        });
        
        result.add(open);
        result.add(saveAs);
        result.add(close);
        
        return result;
    }

    /**
     * Sets up the save behavior.
     */
    private void setUpSave() {
        final int result = myFileManager.showSaveDialog(new JOptionPane());
        if (result == JFileChooser.APPROVE_OPTION) {
            final File newFile = myFileManager.getSelectedFile();
            try {
                myImage.save(newFile);
            } catch (final IOException e) {
                final JLabel errorMessage = new JLabel("There was a problem saving!");
                JOptionPane.showMessageDialog(null, errorMessage, "Error! "
                                              , JOptionPane.ERROR_MESSAGE);
            }           
        }
    }

    /**
     * Sets up the behavior of the Open button.
     */
    private void setUpOpen() {
        final int result = myFileManager.showOpenDialog(new JOptionPane());
        if (result == JFileChooser.APPROVE_OPTION) {
            final File f = myFileManager.getSelectedFile();
        
            try {
                myImage = PixelImage.load(f);
                myImageStatus = true;
                myImageLabel.setIcon(new ImageIcon(myImage));
                setActive();
                setSmallest();
            } catch (final IOException e) {
                final JLabel errorMessage = new JLabel("The selected file did "
                                + "not contain an image!");
                JOptionPane.showMessageDialog(null, errorMessage
                                              , "Error!"
                                              , JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }


    /**
     * Enables/disables the buttons other than Open based on myImageStatus. 
     * and sets the visibility of the Image based on myImageStatus.
     */
    private void setActive() {
        
        for (final JButton b : myButtons) {
            
            b.setEnabled(myImageStatus);
            
        }
        myImagePanel.setVisible(myImageStatus);
    }

    /**
     * Adds all buttons from the given list to the given panel.
     * 
     * @param theButtons A list of JButtons.
     * @param thePanel A JPanel to place the buttons in.
     */
    private void addButtons(final List<JButton> theButtons, final JPanel thePanel) {
        
        for (final JButton b : theButtons) {
            thePanel.add(b);
        } 
    }

    /**
     * Fills a List of JButtons for the filter buttons.
     * 
     * @param theFilters A list of Filter objects used to generate buttons.
     * @return A list of JButtons.
     */
    private List<JButton> createFilterButtons(final List<Filter> theFilters) {
        final List<JButton> result = new ArrayList<JButton>();
        
        //create and add all buttons to result.
        for (final Filter f : theFilters) {
            final String buttonName = f.getDescription();
            final JButton button = new JButton(buttonName);
            //add action listeners.
            button.addActionListener((theEvent) -> {
                f.filter(myImage);
                myImageLabel.setIcon(new ImageIcon(myImage));
            });
            result.add(button);
        }
        
        return result;
    }

    /**
     * Fills a list of Filters. If you want to add a filter to the program do so here.
     * 
     * @return an ArrayList of Filters.
     */
    private List<Filter> setFilters() {
        final ArrayList<Filter> result = new ArrayList<Filter>();
        
        result.add(new EdgeDetectFilter());
        result.add(new EdgeHighlightFilter());
        result.add(new FlipHorizontalFilter());
        result.add(new FlipVerticalFilter());
        result.add(new GrayscaleFilter());
        result.add(new SharpenFilter());
        result.add(new SoftenFilter());
        
        return result;
    }
    
    /**
     * Main method mostly for testing purposes.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SnapShopGUI().start();
            }
        });
    }
}
