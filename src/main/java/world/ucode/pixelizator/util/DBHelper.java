package world.ucode.pixelizator.util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DBHelper {

    public static final String DB_NAME = "songs";

    public void createDbIfNotExists() {
        if (!dbExists())
            crateDb();
    }

    private void crateDb() {
        Connection conn = null;
        try {
            URL url = this.getClass().getClassLoader().getResource("data/init.sql");
            var filePath = Paths.get(url.toURI()).toString();

            conn = getConnection();
            ScriptRunner sr = new ScriptRunner(conn);
            Reader reader = new BufferedReader(new FileReader(filePath));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException | URISyntaxException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private boolean dbExists() {

        var exists = false;

        Connection conn = null;
        try {
            // db parameters
            conn = getConnection();

            var sql = "SHOW DATABASES";
            Statement statement = conn.createStatement();
            var rs = statement.executeQuery(sql);

            while (rs.next()) {
                var dbName = rs.getString(1);
                if (dbName.equals(DB_NAME)) {
                    exists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return exists;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String fileContent(URL url) throws URISyntaxException {
        var filePath = url.toURI();
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
