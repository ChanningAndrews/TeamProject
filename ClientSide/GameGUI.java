package ClientSide;

import CoreGame.GameController;
import CoreGame.GameMap;
import CoreGame.GamePanel;
import CoreGame.SinglePlayerTesting;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameGUI extends JFrame{
    // Constructor
    public GameGUI() throws Exception {
        // 1) Set the title and default close operation.
        this.setTitle("Ascend");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2) create game client
        GameClient client = new GameClient("localhost", 12345);


        // 3) Create the card layout container.
        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        // 4) Next, create the Controllers
        //InitialController initialController = new InitialController(container);
        //LoginController loginController = new LoginControl(container, client);
        //CreateAccountController createAccountController = new CreateAccountController(container, client);
        //MenuController menuController = new MenuController(container, client);
        //JoinController joinController = new JoinController(container, client);
        //HostController hostController = new HostController(container, client);
        //LobbyController lobbyController = new LobbyController(container);
        GameController gameController = new GameController(container, client);
        //ResultsController resultsController = new ResultsController(container, client);


        // 5) set the controllers that will be communicating with the client in the client
//        client.setCreateAccountControl(createAccountController);
//        client.setLoginControl(loginController);
//        client.setMenuControl(menuController);
//        client.setJoinControl(joinController);
//        client.setHostControl(hostController);
//        client.setLobbyControl(lobbyController);
        client.setGameController(gameController);
        //client.setResultsControl(resultsController);

        // 6) open connection to server
        //the client should automatically pass the map it will have created after a successful connection to the server to the gameController
        try {
            client.openConnection();
        }catch(IOException e) {
            e.printStackTrace();
        }

        // 7) link the different panels/views of the app/GUI to their controllers
//        JPanel view1 = new InitialPanel(initialController);
//        JPanel view2 = new LoginPanel(loginController);
//        JPanel view3 = new CreateAccountPanel(cac);
//        JPanel view4 = new JoinPanel(joinController);
//        JPanel view5 = new HostPanel(hostController);
//        JPanel view6 = new LobbyPanel(lobbyController);
        JPanel view7 = new GamePanel(gameController);
        //JPanel view8 = new ResultsPanel(resultsController);
        while(true) {
            if(client.isConnectionSetUpOver()) {
                //System.out.println("set up is over");
                client.createMap();
                break;
            }
            System.out.println("In loop");
        }

        System.out.println("out of the loop");
        System.out.println("In loop");
        // 8) Add the views to the card layout container.
//        container.add(view1, "1");
//        container.add(view2, "2");
//        container.add(view3, "3");
//        container.add(view4, "4");
//        container.add(view5, "5");
//        container.add(view6, "6");
        System.out.println("Adding the gamePanel to the card layout manager");
        container.add(view7, "7");
//        container.add(view8, "8");


        // 9) Show the initial view in the card layout.
        System.out.println("showing the gamePanel");
        cardLayout.show(container, "7");

        // 10) Add the card layout container to the JFrame.
        this.add(container, BorderLayout.CENTER);

        // 11) Show the JFrame.
        this.setSize(1080, 660);
        this.setVisible(true);
        //this.setFocusable(true);
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
