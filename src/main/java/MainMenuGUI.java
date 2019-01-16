import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;



public class MainMenuGUI extends JFrame{
    private JPanel mainPanel;

    public static void main(String[] args) {
        new MainMenuGUI();
    }

    public MainMenuGUI() {
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
        setupUI();
        new FrameConfigurator().configureFrame(this,"Main Menu");
        setContentPane(mainPanel);
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));


        //Set up game title
        JLabel gameTitle = new JLabel("Toguz Korgool", SwingConstants.CENTER);
        gameTitle.setPreferredSize(new Dimension(250,250));
        gameTitle.setFont(new Font("Serif", Font.PLAIN, 48));
        mainPanel.add(gameTitle, BorderLayout.NORTH );

        //set up game mode buttons
        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setName("singlePlayerButton");
        JButton practiceModeButton = new JButton("Practice Mode");
        JButton loadGameButton = new JButton("Load Game");
        singlePlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        practiceModeButton.setName("practiceModeButton");
        practiceModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add action listeners to buttons
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameBoardApp();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int playerKazan=0;
                int opponentKazan=0;
                int playerTuz=0;
                int opponentTuz=0;
                int holesKorgools[] = new int[18];
                try {
                    FileReader reader = new FileReader("gameInfo.json");
                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
                        JSONArray jsonEntries = (JSONArray) jsonObject.get("number of korgools per hole");
                        for (int i = 0; i < jsonEntries.size(); i++)
                        {
                             Long jsonEntry = (Long) jsonEntries.get(i);
                            holesKorgools[i] =  Integer.parseInt(jsonEntry.toString());

                        }

                        playerKazan = ((Long)jsonObject.get("player's kazan korgools")).intValue();
                        opponentKazan = ((Long)jsonObject.get("opponent's kazan korgools")).intValue();
                        playerTuz = ((Long)jsonObject.get("player's tuz index")).intValue();
                        opponentTuz = ((Long)jsonObject.get("opponent's tuz index")).intValue();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                new GameBoardApp(holesKorgools, playerKazan, opponentKazan, playerTuz, opponentTuz);
            }
        });

        //add action listeners to buttons
        practiceModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BoardCustomizationGUI();
            }
        });

        //Set up panel containing the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.Y_AXIS));
        buttonsPanel.add(singlePlayerButton);
        buttonsPanel.add(Box.createVerticalStrut(25));
        buttonsPanel.add(practiceModeButton);
        buttonsPanel.add(Box.createVerticalStrut(25));
        buttonsPanel.add(loadGameButton);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);



    }
}
	
