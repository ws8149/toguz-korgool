import javax.swing.*;

public class FrameConfigurator {

    public void configureFrame(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(false); //stops resizing
        frame.setVisible(true);
    }



}
