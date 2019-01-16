import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import javax.swing.JButton;
import javax.swing.JFrame;
import com.athaydes.automaton.Swinger;
import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;

public class GameBoardAppTest {
    Swinger swinger;

    @Before
    public void setup() {
        new GameBoardApp();
        swinger = Swinger.forSwingWindow();
        
        swinger.pause(200);
    }
    @Ignore
    @Test
    public void normalUseOf() {
        swinger.pause(200);
        // JFrame mainFrame = (JFrame) swinger.getAt("name:mainFrame");
        swingerResetFocus();

        for(JButton button : swinger.getAll( JButton.class )) {
            assertThat(button, hasText("9"));
        }
    }

    private void swingerResetFocus() {
        swinger = Swinger.getUserWith(javax.swing.FocusManager.getCurrentManager().getActiveWindow());
    }

}