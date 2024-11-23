package ClientSide;

public class LobbyControl {

    // Fields
    private GameClient gameClient;
    private LobbyPanel lobbyPanel;
    private LobbyData lobbyData;
    private boolean isPlayerReady;

    // Constructor
    public LobbyControl(GameClient gameClient, LobbyPanel lobbyPanel) {
        this.gameClient = gameClient;
        this.lobbyPanel = lobbyPanel;
        this.isPlayerReady = false;
    }

    // Update the lobby with new data
    public void updateLobby(LobbyData lobbyData) {
        this.lobbyData = lobbyData;
        lobbyPanel.updateLobbyDisplay(lobbyData);  // Assuming LobbyPanel has an updateLobbyDisplay method
        System.out.println("Lobby updated with new data: " + lobbyData);
    }

    // Create a new lobby
    public void createLobby() {
        System.out.println("Creating new lobby...");
        //gameClient.createGame();
    }

    // Join an existing lobby
    public void joinLobby(String lobbyCode) {
        System.out.println("Joining lobby with code: " + lobbyCode);
        //gameClient.joinGame(lobbyCode);
    }

    // Handle the start of the game when all players are ready
    public void handleStartGame() {
        if (lobbyData != null && allPlayersReady()) {
            System.out.println("All players are ready. Starting the game...");
            //gameClient.startGame();
        } else {
            System.out.println("Not all players are ready.");
        }
    }

    // Set the player's ready status and notify the server
    public void handlePlayerReadyStatus(String username, boolean isReady) {
        isPlayerReady = isReady;
        lobbyPanel.updateReadyStatus(username, isReady);  // Update specific player's ready status in the UI
        //gameClient.sendPlayerReady();

        System.out.println(username + (isReady ? " is ready." : " is not ready."));
    }

    // Check if all players in the lobby are ready
    private boolean allPlayersReady() {
        return lobbyData != null && lobbyData.getPlayers().stream().allMatch(LobbyData.Player::isReady);
    }

    // Notify lobby of a player joining
    public void playerJoined(String username) {
        System.out.println(username + " has joined the lobby.");
        lobbyPanel.addPlayer(username, isPlayerReady);  // Assuming LobbyPanel has an addPlayer method
    }

    // Notify lobby of a player leaving
    public void playerLeft(String username) {
        System.out.println(username + " has left the lobby.");
        lobbyPanel.removePlayer(username);  // Assuming LobbyPanel has a removePlayer method
    }
}
