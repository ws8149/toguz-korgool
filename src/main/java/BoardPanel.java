import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardPanel extends JPanel {

    public JPanel opponentPanel;
    public JPanel yourPanel;
    public JPanel yourKazan;
    public JPanel opponentKazan;
    public GridBagConstraints gbc;

    public BoardPanel(ArrayList<JButton> buttons) {
        setLayout(new BorderLayout(0, 0));

        //make panels
        opponentPanel = new JPanel();
        opponentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        opponentPanel.setBackground(Color.gray);
        add(opponentPanel, BorderLayout.NORTH);
        yourPanel = new JPanel();
        yourPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        yourPanel.setBackground(Color.white);
        add(yourPanel, BorderLayout.SOUTH);

        //make labels for panels
        final JLabel opponentLabel = new JLabel();
        opponentLabel.setBackground(Color.gray);
        opponentLabel.setText("Opponent");
        opponentPanel.add(opponentLabel);

        final JLabel yourLabel = new JLabel();
        yourLabel.setText("You");
        yourPanel.add(yourLabel);

        //make buttons for panels
        for(int i = 17 ; i > 8 ; i--)
        {
            opponentPanel.add(buttons.get(i));
        }

        for(int i = 0 ; i < 9 ; i ++)
        {
            yourPanel.add(buttons.get(i));
        }

        //split board
        final JSplitPane splitPane1 = new JSplitPane();
        splitPane1.setDividerLocation(400);
        splitPane1.setEnabled(false);
        add(splitPane1, BorderLayout.CENTER);


        //make kazans
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        opponentKazan = new JPanel();
        opponentKazan.setLayout(new GridBagLayout());
        opponentKazan.setBackground(Color.gray);
        splitPane1.setRightComponent(opponentKazan);
        yourKazan = new JPanel();
        yourKazan.setLayout(new GridBagLayout());
        yourKazan.setBackground(Color.white);
        splitPane1.setLeftComponent(yourKazan);
    }

}
