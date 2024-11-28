package ClientSide;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class JoinControl implements ActionListener {

    private JPanel container;
    private GameClient client;

    public JoinControl(JPanel container, GameClient client) {
        this.container = container;
        this.client = client;
    }

    public void actionPerformed(ActionEvent ae) {

        // get the name of the button clicked
        String command = ae.getActionCommand();

        if (command.equals("Submit")) {
            System.out.println("ip submitted!");
            JoinData data = new JoinData(JoinPanel.getIp());

            /*

            // Check the validity of the information locally first.
            if (data.getIp().equals(""))
            {
                displayError("You must enter Host IP.");
                return;
            }

            // Submit the login information to the server.
            try
            {
                client.sendToServer(data);
            }
            catch (IOException e)
            {
                displayError("Error connecting to the server.");
            }

             */

        }
        else if (command.equals("Cancel")) {
            CardLayout cardLayout = (CardLayout)container.getLayout();
            cardLayout.show(container, "4");
        }

    }

}
