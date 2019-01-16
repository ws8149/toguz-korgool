import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.*;
import org.json.simple.*;
import javax.swing.UIManager.*;



/**
 class that builds the GUI for the board
 */

public class GameBoardApp extends JFrame {
    private BoardPanel mainPanel;
    private ArrayList<JButton> buttons;
    private GameBoard gameBoardLogic;
    private  JLabel opponentKazanLabel = new JLabel();
    private  JLabel yourKazanLabel = new JLabel();

    private JButton saveButton = new JButton("save");

    /**
     * constructor for a normal game
     */
    public GameBoardApp() {
        gameBoardLogic = new GameBoard();

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        buttons = new ArrayList<JButton>();

        for (int i = 0; i < 18; i++) {

            JButton temp = new JButton("9");
            temp.setName("button" + i);
            temp.putClientProperty("buttonID", i);
            temp.setPreferredSize(new Dimension(60, 20));

            temp.addActionListener(e -> {
                int buttonID = (int) ((JButton) e.getSource()).getClientProperty("buttonID");
                buttonClicked(buttonID);
            });

            buttons.add(temp);
        }

        saveButton.addActionListener(e -> {
            saveButtonClicked();
        });

        setupUI();
    }


    /**
     * constructor for making a custom game
     * @param korgoolCounts an array for the number of korgools per hole
     * @param whitePlayerKazanKorgools the number of korgools in the player's kazan
     * @param blackPlayerKazanKorgools the number of korgools in the opponent's kazan
     * @param whitePlayerTuzIndex the tuz index of the player
     * @param blackPlayerTuzIndex the tuz index of the opponent
     */
    public GameBoardApp(int[] korgoolCounts,
                        int whitePlayerKazanKorgools,
                        int blackPlayerKazanKorgools,
                        int whitePlayerTuzIndex,
                        int blackPlayerTuzIndex) {

        gameBoardLogic = new GameBoard(
                korgoolCounts,
                whitePlayerKazanKorgools,
                blackPlayerKazanKorgools,
                whitePlayerTuzIndex,
                blackPlayerTuzIndex);
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        setupUI();

        if (whitePlayerTuzIndex >= 0) {
            buttons.get(whitePlayerTuzIndex).setBackground(Color.green);
        }

        if (blackPlayerTuzIndex >= 0 ) {
            buttons.get(blackPlayerTuzIndex).setBackground(Color.green);
        }


    }


    /**
     * Action to be taken when the save button is clicked.
     * writes to a file the values which should be passed to the custom
     * constructor to recreate a game by using a JSON
     */
    private void saveButtonClicked(){


        JSONObject sampleObject = new JSONObject();

        JSONArray korgoolCounts = new JSONArray();
        for(int i =0; i < buttons.size(); i++)
        {
            korgoolCounts.add(gameBoardLogic.getHole(i).getNumberOfKorgools());
        }

        sampleObject.put("number of korgools per hole", korgoolCounts);
        sampleObject.put("player's kazan korgools", gameBoardLogic.getWhitePlayer().getKazan().getNumberOfKorgools());
        sampleObject.put("opponent's kazan korgools", gameBoardLogic.getBlackPlayer().getKazan().getNumberOfKorgools());
        sampleObject.put("player's tuz index", gameBoardLogic.getWhitePlayer().getTuzIndex());
        sampleObject.put("opponent's tuz index", gameBoardLogic.getBlackPlayer().getTuzIndex());

        try {

            Files.write(Paths.get("gameInfo.json"), sampleObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * action taken when a hole is clicked
     * updates the GUI and the actual gameBoard with the required values
     * @param buttonID the ID of the button to be clicked
     */
    private void buttonClicked(int buttonID) {
        int korgoolsCollected = gameBoardLogic.getWhitePlayer().getKazan().getNumberOfKorgools();
        yourKazanLabel.setText(String.valueOf(korgoolsCollected));
        if (buttonID >= 9) {
            System.out.println("That button belongs to your opponent.");
            return;
        }
        if (gameBoardLogic.getHole(buttonID).getNumberOfKorgools() == 0){
            System.out.println("Zero korgools in this hole");
            return;
        }
        gameBoardLogic.getCurrentTurn().clickHole(buttonID);
        updatePlayerUI();
        updateOpponentUI();
        checkBoardState();

    }



    /**
     * Builds the GUI
     */
    private void setupUI() {
        setupButtons(gameBoardLogic.getHoles());
        setName("mainFrame");
        mainPanel = new BoardPanel(buttons);

        //setup kazan labels
        int korgoolsCollected = gameBoardLogic.getWhitePlayer().getKazan().getNumberOfKorgools();
        yourKazanLabel.setText(String.valueOf(korgoolsCollected));
        mainPanel.yourKazan.add(yourKazanLabel, mainPanel.gbc);

        korgoolsCollected = gameBoardLogic.getBlackPlayer().getKazan().getNumberOfKorgools();
        opponentKazanLabel.setText(String.valueOf(korgoolsCollected));
        opponentKazanLabel.setName("opponentKazan");
        mainPanel.opponentKazan.add(opponentKazanLabel, mainPanel.gbc);

        //setup save button
        saveButton.setBackground( Color.BLACK);
        saveButton.setForeground(Color.white);
        mainPanel.yourPanel.add(saveButton);

        new FrameConfigurator().configureFrame(this,"GameBoardApp");
        setContentPane(mainPanel);
    }

    private void setupButtons(Hole[] holes) {
        buttons = new ArrayList<JButton>();

        for (int i = 0; i < 18; i++) {

            JButton temp = new JButton("" + holes[i].getNumberOfKorgools());
            temp.setName("button" + i);
            temp.putClientProperty("buttonID", i);
            temp.setPreferredSize(new Dimension(60, 20));

            temp.addActionListener(e -> {
                int buttonID = (int) ((JButton) e.getSource()).getClientProperty("buttonID");
                buttonClicked(buttonID);
            });

            buttons.add(temp);
        }
        saveButton.addActionListener(e -> {
            saveButtonClicked();
        });
    }




   /**
    * updates the player's part of the GUI with the number of korgools collected,
    * the tuz index and the number of korgools per hole.
    */
    private void updatePlayerUI() {
        for (int k = 0; k < 9; k++) {
            int holeIndex = (int) buttons.get(k).getClientProperty("buttonID");
            int numberKor = gameBoardLogic.getHole(holeIndex).getNumberOfKorgools();


            buttons.get(k).setText(String.valueOf(numberKor));
        }


        if(gameBoardLogic.getWhitePlayer().getTuzIndex() != -1)
        {
            int tuzIndex = gameBoardLogic.getWhitePlayer().getTuzIndex();
            buttons.get(tuzIndex).setBackground(Color.green);

        }
        int korgoolsCollected = gameBoardLogic.getWhitePlayer().getKazan().getNumberOfKorgools();
        yourKazanLabel.setText(String.valueOf(korgoolsCollected));

    }


    /**
     * updates the oppponent's part of the GUI with the number of korgools collected,
     * the tuz index and the number of korgools per hole.
     */
    private void updateOpponentUI () {

        for (int j = 9; j < 18; j++) {
            int holeIndex = (int) buttons.get(j).getClientProperty("buttonID");
            int numberKor = gameBoardLogic.getHole(holeIndex).getNumberOfKorgools();

            buttons.get(j).setText(String.valueOf(numberKor));
        }


        if(gameBoardLogic.getBlackPlayer().getTuzIndex() != -1)
        {
            int tuzIndex = gameBoardLogic.getBlackPlayer().getTuzIndex();
            buttons.get(tuzIndex).setBackground(Color.green);

        }
        int korgoolsCollected = gameBoardLogic.getBlackPlayer().getKazan().getNumberOfKorgools();
        opponentKazanLabel.setText(String.valueOf(korgoolsCollected));

    }


    private void checkBoardState() {
        String currentState = gameBoardLogic.endGame().toString();
        if (currentState.equals("DRAW")) {
            JOptionPane.showMessageDialog(this,"DRAW");
            endGame();
        } else if (currentState.equals("WHITEWINS")) {
            JOptionPane.showMessageDialog(this,"WHITE PLAYER WINS");
            endGame();
        } else if (currentState.equals("BLACKWINS")) {
            JOptionPane.showMessageDialog(this,"BLACK PLAYER WINS");
            endGame();
        }
    }


    /**
     * Ends the game and asks if the user would like to play again
     */
    private void endGame() {

        int dialogResult = JOptionPane.showConfirmDialog(this,"Play again?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.dispose();
            new GameBoardApp();
        } else {
            this.dispose();
        }
    }

}