package ClientSide;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HostOrJoinGameControl implements ActionListener {

    private JPanel container;
    private GameClient client;

    public HostOrJoinGameControl(JPanel container, GameClient client) {
        this.container = container;
        this.client = client;
    }

    public void actionPerformed(ActionEvent ae) {

        // get the name of the button clicked
        String command = ae.getActionCommand();

        if (command.equals("Logout")) {
            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "1");
        }
        else if (command.equals("Host")) {
            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "5");
        }
        else if (command.equals("Join")) {
            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "6");
        }

    }

}