package Testing;

import ClientSide.HostOrJoinGameControl;
import ClientSide.HostOrJoinGamePanel;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import ClientSide.GameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class HostOrJoinGamePanelTest {

    private HostOrJoinGamePanel panel;
    private HostOrJoinGameControl mockControl;
    private JPanel mockContainer;
    private GameClient mockClient;

    @BeforeEach
    void setUp() throws IOException {
        // Set up the container and mock client
        mockContainer = new JPanel(new CardLayout());
        String host = "localhost"; // Test-specific host
        int port = 12345;          // Test-specific port

        // Initialize the GameClient with mock host and port
        mockClient = new GameClient(host, port);

        // Initialize the HostOrJoinGameControl using the mock container and client
        mockControl = new HostOrJoinGameControl(mockContainer, mockClient);

        // Initialize the HostOrJoinGamePanel
        panel = new HostOrJoinGamePanel(mockControl);
    }

    @Test
    void testPanelInitialization() {
        assertNotNull(panel, "Panel should be initialized");
        assertEquals(1080, panel.getPreferredSize().width, "Panel width should be set correctly");
        assertEquals(624, panel.getPreferredSize().height, "Panel height should be set correctly");
    }

    @Test
    void testButtonsInitialization() {
        JButton[] buttons = {panel.host, panel.join, panel.logout};
        for (JButton button : buttons) {
            assertNotNull(button, "Button should be initialized");
            assertFalse(button.isContentAreaFilled(), "Button content area should not be filled");
            assertFalse(button.isBorderPainted(), "Button border should not be painted");
            assertNotNull(button.getIcon(), "Button should have an icon");
        }
    }

    @Test
    void testErrorLabelInitialization() {
        JLabel errorLabel = panel.errorLabel;
        assertNotNull(errorLabel, "Error label should be initialized");
        assertEquals("", errorLabel.getText(), "Error label text should be empty initially");
        assertEquals(SwingConstants.CENTER, errorLabel.getHorizontalAlignment(), "Error label should be center-aligned");
        assertEquals(Color.RED, errorLabel.getForeground(), "Error label color should be red");
    }

    @Test
    void testSetError() {
        String errorMessage = "Test error message";
        panel.setError(errorMessage);
        assertEquals(errorMessage, panel.errorLabel.getText(), "Error label should display the correct error message");
    }

    @Test
    void testActionCommands() {
        assertEquals("Host", panel.host.getActionCommand(), "Host button action command should be set");
        assertEquals("Join", panel.join.getActionCommand(), "Join button action command should be set");
        assertEquals("Logout", panel.logout.getActionCommand(), "Logout button action command should be set");
    }
}
