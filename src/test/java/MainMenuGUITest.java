import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.awt.Window;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.athaydes.automaton.Swinger;
import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;


public class MainMenuGUITest {
    Swinger swinger;
    MainMenuGUI mainMenu;

    @Before
    public void setup() {
        mainMenu = new MainMenuGUI();
        // swinger = Swinger.forSwingWindow();
        swinger = Swinger.getUserWith( mainMenu );

        swinger.pause(1000);
    }

    @After
    public void end() {
        mainMenu.dispose();
    }
    
    @Test
    public void normalUseOfSinglePlayer() {
        // JFrame mainPanel = (JFrame) swinger.getAt("name:mainPanel");

        assertThat(swinger.getAt("singlePlayerButton"), hasText("Single Player"));
        
        swinger.clickOn("singlePlayerButton").pause(1000);

        setSwingerByWindowName("mainFrame");
        for(int i=0; i<18; i++) {
            JButton button = (JButton) swinger.getAt("button" + i);
            assertThat(button, hasText("9"));
        }

        

        swinger.clickOn("button8")
                .pause(1000);

        swingerResetFocus();
        assertThat( swinger.getAt( "button8" ), hasText( "1" ) );
    }
    
    @Test 
    public void normalUseOfPracticeMode() {
        
        assertThat(swinger.getAt("practiceModeButton"), hasText("Practice Mode"));
        
        swinger.clickOn("practiceModeButton").pause(1000);

        
        // setSwingerByWindowName("customBoard").clickOn("text:Complete!").pause(1000);

        setSwingerByWindowName("customFrame");
        // swingerResetFocus();
        for(int i=0; i<18; i++) {
            JButton button = (JButton) swinger.getAt("button" + i);
            assertThat(button, hasText("9"));
        }

        swinger.clickOn("button8").type("3").pause(1000);

        swinger.clickOn( "text:OK" ).pause(1000);

        assertThat(swinger.getAt("button8"), hasText("3"));

        swinger.clickOn("text:Complete!").pause(1000);
        swinger.clickOn("text:OK").pause(1000);

        swinger.clickOn("button7").type("12").pause(1000);
        swinger.clickOn( "text:OK" ).pause(1000);
        swinger.clickOn( "text:Yes" ).pause(1000);
        
        
        assertThat(swinger.getAt("button7"), hasText("12"));

        swinger.clickOn("yourKazan").type("2").pause(1000);
        swinger.clickOn( "text:Cancel" ).pause(1000);
        swinger.clickOn("text:OK").pause(1000);

        swinger.clickOn("yourKazan").type("2").pause(1000);
        swinger.clickOn( "text:OK" ).pause(1000);

        swinger.clickOn("opponentKazan").type("1").pause(1000);
        swinger.clickOn( "text:OK" ).pause(1000);

        swinger.clickOn( "text:Complete!" ).pause(3000);


        JFrame frame = (JFrame) swingerResetFocus().getAt("mainFrame");
        assertEquals(frame.getTitle(), "GameBoardApp");

        // swingerResetFocus();
        // assertThat(swinger.getAt("mainFrame"), hasText("GameBoard"));
        // TODO: add 5 to other hole and press complete
    }
    
    @Test 
    public void noEditInPracticeMode() {
        swinger.clickOn("practiceModeButton").pause(1000);

        setSwingerByWindowName("customFrame").clickOn("text:Complete!").pause(1000);

        for(int i=0; i<18; i++) {
            JButton button = (JButton) swinger.getAt("button" + i);
            assertThat(button, hasText("9"));
        }
    }
    
    @Test 
    public void cancelEditInPracticeMode() {
        swinger.clickOn("practiceModeButton").pause(1000);

        // setSwingerByWindowName("customBoard").clickOn("button7").type("2").pause(1000);
        swingerResetFocus();
        swinger.clickOn("button7").type("2").pause(1000);

        // cancel input after typing
        swinger.clickOn( "text:Cancel" ).pause(1000);
        // answer prompt about having to input a number
        swinger.clickOn("text:OK").pause(1000);
        // set it to a tuz
        swinger.clickOn("text:Yes").pause(1000);


        for(int i=0; i<18; i++) {
            JButton button = (JButton) swinger.getAt("button" + i);
            assertThat(button, hasText("9"));
        }
    }
    
    @Test
    public void normalSaveGame() {
        swinger.clickOn("text:Practice Mode").pause(1000);

        // System.out.println(setSwingerByWindowName("custom"));
        setSwingerByWindowName("customFrame");
        swinger.clickOn("name:button3").type("3").pause(1000);
        
        swinger.clickOn("text:OK").pause(1000);
        swinger.clickOn("text:No").pause(1000);
        swinger.clickOn("name:opponentKazan").type("6").pause(1000);
        swinger.clickOn("text:OK").pause(1000);
        swinger.clickOn("text:Complete!").pause(1000);

        setSwingerByWindowName("mainFrame").clickOn("text:save").pause(1000);

        // start new window to test load
        MainMenuGUI newMenu = new MainMenuGUI();
        swinger.getUserWith( newMenu ).clickOn("text:Load Game").pause(1000);
        
        assertThat(swinger.getAt("name:button3"), hasText("3"));
        assertThat(swinger.getAt("name:opponentKazan"), hasText("6"));
    }

    private Swinger swingerResetFocus() {
        swinger = Swinger.getUserWith(javax.swing.FocusManager.getCurrentManager().getActiveWindow());
        return swinger;
    }

    private Swinger setSwingerByWindowName(String name) {
        // Window window = ;
        swinger = Swinger.getUserWith(findWindow(name));
        return swinger;
    }

    private Window findWindow(String name) {
        Window[] windows = java.awt.Window.getWindows();

        for(Window w : windows) {
            if(w.getName().equals(name) && w.isActive()){
                    return w;
            }
        }
        return null;
    }


    // private Swinger setSwingerByValue(String value) {
    //     swinger = Swinger.getUserWith(swinger.getAt())
    // }
}