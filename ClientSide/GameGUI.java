package ClientSide;

import CoreGame.GameController;
import CoreGame.GameMap;
import CoreGame.GamePanel;
import CoreGame.SinglePlayerTesting;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;

public class GameGUI extends JFrame{
    // Constructor
    public GameGUI() throws Exception {
        // set up the game client
        GameClient client = new GameClient("localhost", 12345);

        try {
            client.openConnection();
        }catch(IOException e) {
            e.printStackTrace();
        }

        // set title and default close operation
        this.setTitle("Ascend");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the card layout container.
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        // create the controllers
        InitialControl ic = new InitialControl(container, client);
        LoginControl lc = new LoginControl(container, client);
        CreateAccountControl cac = new CreateAccountControl(container,client);
        HostOrJoinGameControl hj = new HostOrJoinGameControl(container,client);
        HostControl hc = new HostControl(container, client);
        JoinControl jc = new JoinControl(container, client);
        GameController gc = new GameController(container, client);

        // set the controllers that will be communicating with the client in the client
        client.setLoginControl(lc);
        client.setCreateAccountControl(cac);
        client.setGameController(gc);

        // open connection to server
        //the client should automatically pass the map it will have created after a successful connection to the server to the gameController

        // link the different panels/views of the app/GUI to their controllers
        JPanel view1 = new InitialPanel(ic);
        JPanel view2 = new LoginPanel(lc);
        JPanel view3 = new CreateAccountPanel(cac);
        JPanel view4 = new HostOrJoinGamePanel(hj);
        JPanel view5 = new HostPanel(hc);
        JPanel view6 = new JoinPanel(jc);
        JPanel view7 = new GamePanel(gc);

        while(true) {
            if(client.isConnectionSetUpOver()) {
                //System.out.println("set up is over");
                client.createMap();
                break;
            }
            //System.out.println("In loop");
        }

        // add the views to the card layout container.
        container.add(view1, "1");
        container.add(view2, "2");
        container.add(view3, "3");
        container.add(view4, "4");
        container.add(view5, "5");
        container.add(view6, "6");
        container.add(view7,"7");

        // show the initial view in the card layout.
        cardLayout.show(container, "1");

        // add the card layout container to the JFrame.
        this.add(container);

        // show the JFrame.
        this.setSize(1080, 624);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new GameGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
