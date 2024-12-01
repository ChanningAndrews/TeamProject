//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerConnectionTest {
    private GameServer server;
    private static final int TEST_PORT = 12345;

    ServerConnectionTest() {
    }

    @BeforeEach
    void setUp() {
        this.server = new GameServer(12345);
        (new Thread(() -> {
            try {
                this.server.listen();
            } catch (IOException var2) {
                IOException e = var2;
                e.printStackTrace();
            }

        })).start();
    }

    @AfterEach
    void tearDown() {
        try {
            this.server.close();
        } catch (IOException var2) {
            IOException e = var2;
            e.printStackTrace();
        }

    }

    @Test
    void testServerStarts() {
        Assertions.assertTrue(this.server.isListening(), "Server should be listening.");
    }

    @Test
    void testClientConnection() {
        try {
            Socket client = new Socket("localhost", 12345);

            try {
                Assertions.assertTrue(client.isConnected(), "Client should connect to the server.");
            } catch (Throwable var5) {
                try {
                    client.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            client.close();
        } catch (IOException var6) {
            IOException e = var6;
            Assertions.fail("Client failed to connect to the server: " + e.getMessage());
        }

    }

    @Test
    void testMessageExchange() {
        try {
            Socket client = new Socket("localhost", 12345);

            try {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    try {
                        String testMessage = "Hello Server!";
                        out.println(testMessage);
                        String response = in.readLine();
                        Assertions.assertNotNull(response, "Response from server should not be null.");
                        Assertions.assertEquals("Acknowledged: " + testMessage, response, "Server should respond with acknowledgment.");
                    } catch (Throwable var9) {
                        try {
                            in.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }

                        throw var9;
                    }

                    in.close();
                } catch (Throwable var10) {
                    try {
                        out.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }

                    throw var10;
                }

                out.close();
            } catch (Throwable var11) {
                try {
                    client.close();
                } catch (Throwable var6) {
                    var11.addSuppressed(var6);
                }

                throw var11;
            }

            client.close();
        } catch (IOException var12) {
            IOException e = var12;
            Assertions.fail("Message exchange failed: " + e.getMessage());
        }

    }

    @Test
    void testClientDisconnection() {
        try {
            Socket client = new Socket("localhost", 12345);

            try {
                client.close();
                Assertions.assertTrue(client.isClosed(), "Client connection should be closed.");
            } catch (Throwable var5) {
                try {
                    client.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            client.close();
        } catch (IOException var6) {
            IOException e = var6;
            Assertions.fail("Client disconnection failed: " + e.getMessage());
        }

    }
}
