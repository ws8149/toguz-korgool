import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * GUI that allows the user to customize the board
 * It then creates a new game using the customized board data
 */


public class BoardCustomizationGUI extends JFrame {
    private BoardPanel mainPanel;
    private ArrayList<JButton> buttons;
    private JButton opponentKazanButton;
    private JButton yourKazanButton;
    private JLabel korgoolsCounterLabel;
    private int playerTuzIndex;
    private int opponentTuzIndex;
    private int korgoolsCounter = 0;


    /**
     * Check if input is a positive number
     * @param userInput, The input to check on
     * @return A boolean indicating it is a positive number or not     *
     */
    private boolean inputIsValid(String userInput) {

        try {
            //do not allow negative numbers
            return Integer.parseInt(userInput) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }


    }


    /**
     * Adds korgools previously placed in the holes back into the counter
     * After that, it takes from the counter depending on what new korgools have
     * been placed into the hole
     * @param prevKorgools, The previous korgools in a hole
     * @param newKorgools, The new korgools that will be placed in a hole
     */
    private void updateKorgoolsCounter(int prevKorgools, int newKorgools) {
        //place previous korgools back into the counter
        korgoolsCounter += prevKorgools;
        //remove korgools that have been placed from counter
        korgoolsCounter -= newKorgools;
        korgoolsCounterLabel.setText("Korgools left: " + korgoolsCounter);

    }


    /**
     * Checks if a button has not been selected as tuz
     * @param buttonID, id indicating which button has been pressed
     * @return True if the button has not been selected as tuz, else false
     */
    private boolean hasNotSelectedTuz(int buttonID ) {

        if (buttonID >= 0 && buttonID <= 7) {
            return playerTuzIndex == -1;
        } else if (buttonID >= 9 && buttonID <= 16) {
            return opponentTuzIndex == -1;
        } else {
            return false;
        }


    }



    /**
     * Enables a button to take in user input
     * The button will open up pane asking the user for
     * how many korgools to input, after that it asks if the
     * the user wants this hole to be a tuz
     * @param button, the button that will be modified
     */
    private void setUserInputActionListener(JButton button) {

        button.addActionListener(e -> {
            String userInput = JOptionPane.showInputDialog(this, "How many korgools would you like to set this to?");

            if (inputIsValid(userInput)) {
                JButton currentButton = (JButton) e.getSource();
                int prevKorgools = Integer.parseInt(currentButton.getText());
                currentButton.setText(userInput);
                updateKorgoolsCounter(prevKorgools, Integer.parseInt(userInput));
            } else {
                JOptionPane.showMessageDialog(this,"Please input a number");
            }

            //if the button selected is not the kazan (as selecting the kazan would return null)
            if (((JButton) e.getSource()).getClientProperty("buttonID") != null) {
                int buttonID = (int) ((JButton) e.getSource()).getClientProperty("buttonID");

                if (hasNotSelectedTuz(buttonID)) {
                    //Check if user wants to set button to a tuz
                    int dialogResult = JOptionPane.showConfirmDialog(this,"Would you like to set this button to a Tuz?");

                    if (dialogResult == JOptionPane.YES_OPTION) {

                        //do not allow index 8 and 17 to be a tuz
                        if (buttonID >= 0 && buttonID <= 7) {
                            playerTuzIndex = buttonID;
                            button.setBackground(Color.green);
                        } else if (buttonID >= 9 && buttonID <= 16) {
                            opponentTuzIndex = buttonID;
                            button.setBackground(Color.green);
                        }
                    }
                }
            }
        });



    }


    /**
     * Sets up GameBoardApp using customized board data
     */
    private void completeCustomization() {
        int[] korgoolCounts = new int[18];
        for (int i = 0; i < 18; i++) {
            korgoolCounts[i] = Integer.parseInt( this.buttons.get(i).getText() );

        }

        int blackPlayerKazanKorgools =  Integer.parseInt( yourKazanButton.getText() );
        int whitePlayerKazanKorgools = Integer.parseInt( opponentKazanButton.getText() );

        if (korgoolsCounter == 0) {
            System.out.println("player tuz index: " + this.playerTuzIndex);
            System.out.println("opponent uz index: " + this.opponentTuzIndex);
            new GameBoardApp(korgoolCounts, blackPlayerKazanKorgools, whitePlayerKazanKorgools,
                             // swapping these params
                             this.opponentTuzIndex, this.playerTuzIndex);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please use up all the korgools before starting the game");
        }

    }

    /**
     * Class Constructor
     */
    public BoardCustomizationGUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        buttons = new ArrayList<JButton>();
        playerTuzIndex = -1;
        opponentTuzIndex = -1;
        setupUI();

    }

    /**
     * Sets up GUI elements onto the board
     */
    private void setupUI() {
        setupButtons();
        mainPanel = new BoardPanel(buttons);
        setName("customFrame");
        //set up counter label
        korgoolsCounterLabel = new JLabel("Korgools Left: " + korgoolsCounter);
        mainPanel.opponentPanel.add(korgoolsCounterLabel);

        // set up complete button
        JButton completeButton = new JButton("Complete!");
        completeButton.addActionListener(e -> completeCustomization());
        mainPanel.yourPanel.add(completeButton);

        // setup kazan buttons
        yourKazanButton = new JButton();
        yourKazanButton.setName("yourKazan");
        yourKazanButton.setText("0");
        setUserInputActionListener(yourKazanButton);
        mainPanel.yourKazan.add(yourKazanButton, mainPanel.gbc);

        opponentKazanButton = new JButton();
        opponentKazanButton.setHorizontalAlignment(0);
        opponentKazanButton.setHorizontalTextPosition(0);
        opponentKazanButton.setName("opponentKazan");
        opponentKazanButton.setText("0");
        setUserInputActionListener(opponentKazanButton);
        mainPanel.opponentKazan.add(opponentKazanButton, mainPanel.gbc);

        new FrameConfigurator().configureFrame(this,"Board Customization");
        setContentPane(mainPanel);
    }

    private void setupButtons() {
        buttons = new ArrayList<JButton>();
        for (int i = 0; i < 18; i++) {
            JButton temp = new JButton("9");
            temp.putClientProperty( "buttonID", i);
            temp.setName("button" + i);
            temp.setPreferredSize(new Dimension(60, 20));

            setUserInputActionListener(temp);

            buttons.add(temp);
        }
    }

}
