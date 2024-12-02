package ServerSide;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Database
{
    private Connection conn;
    private static final String ENCRYPTION_KEY = "superSecretKey";
    //Add any other data fields you like – at least a Connection object is mandatory
    public Database()
    {
        //Add your code here
        try {
            //create a properties object
            Properties props = new Properties();

            //open file inpu stream
            FileInputStream fis = new FileInputStream("db.properties");

            props.load(fis);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String pass = props.getProperty("password");
            // set the connection
            conn = DriverManager.getConnection(url,user,pass);
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public ArrayList<String> query(String query)
    {
        //Add your code here
        //variable declirations
        ArrayList<String>list = new ArrayList<String>();
        int count = 0;
        int numColumns = 0;
        try {
            //create db statment
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(query);

            //get result set meta data
            ResultSetMetaData rmd = rs.getMetaData();

            numColumns = rmd.getColumnCount();

            while(rs.next()) {
                String record = "" ;

                for (int i = 0; i < numColumns; i++) {
                    record += rs.getString(i+1);
                    record += ",";
                }
                list.add(record);
            }

            //check for empty list
            if(list.size() == 0) {
                return null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return list;

    }

    public void executeDML(String dml) throws SQLException
    {
        //Add your code here

        Statement statement = conn.createStatement();
        statement.execute(dml);
    }

    public boolean verifyLogin(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND AES_DECRYPT(password, ?) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, ENCRYPTION_KEY);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAccount(String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, AES_ENCRYPT(?, ?))";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return false; // Username already exists
            }
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, ENCRYPTION_KEY);
            insertStmt.executeUpdate();
            return true; // Account created successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Account creation failed
        }
    }




}
