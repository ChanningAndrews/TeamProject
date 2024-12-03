# ASCEND
 Authors: Channing Andrews, Connor Myers, Markie, Owen Kiilsgard, Fabrice Faustin

 Inspired by classic games such as Bomberman and Tanks, ROCKETMAN offers a competitive top-down shooter experience for 2-8 players within a destructible grid based environment. It features fast paced gameplay, a fully fledged lobby system, statistics collection, and a bundled map editor as well.

 ## Installation & Setup


### Client Setup
To setup the ASCEND client, follow the steps below.
```
1. Download the latest version of the ASCEND source.
2. Extract the .ZIP into your desired location for the game
3. Run the 'RunGameGUI.bat' from the 'bat_files' directory.
```

Note: Make sure there is a corresponding GameServer running properly before running the RunGameGUI.bat file to avoid errors.

### Server + DB Setup
The server setup is similar to the client setup but requires you to instantiate a local copy of the database prior to starting the server. The server setup portion utilizes XAMPP to manage the MySQL server.
```
1. Download the latest version of the ASCEND source.
2. Extract the .ZIP into your desired location for the game.
3. Launch XAMPP and start your MySQL server.
4. database part
7. Run RunGameServer.bat from the 'bat_files' directory.
```
### Connecting to Remote Server/Computer

If you want your client session to connect to a server hosted in a different machine, follow these steps:
```
1. Locate the clientConfig.txt file in the resource folder located in the extracted project folder
2. Enter the IP address of the server/machine you wish to connect to.
3. Save the changes to the file
4. Run GameGUI.bat as intended ( refer to Client Setup section )
```


### Editing the Project in IntelliJ Idea

In case the batch files do not work for you, you can import the project into IntelliJ and run it through the IDE.

To edit the project follow these steps: -- should be changed----
```
1. Download and unzip the file in your workspace directory
2. Using import existing projects to import the project into your IDE
3. Add ocsf.jar and mysql-connector to the buildpath of the project
4. Setting up the Server/Client is the same except skip you run the ServerUI or ClientUI classes from within your IDE instead of through the batch files
5. thats it
```

## All that is left is for you to try out our game!!! Thanks for Playing!

```
