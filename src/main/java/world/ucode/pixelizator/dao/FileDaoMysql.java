package world.ucode.pixelizator.dao;

import org.springframework.stereotype.Service;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.dao.error.NotFoundException;
import world.ucode.pixelizator.model.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileDaoMysql implements FileDao {

    @Override
    public void createFile(File file) throws FileDaoException {
        Connection connection = null;
        try {
            connection = getConnection();
            var sql = "INSERT INTO file (file_id, name, size, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)";
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(file.getId()));
            preparedStatement.setString(2, file.getName());
            preparedStatement.setLong(3, file.getSize());
            preparedStatement.setLong(4, file.getTimestamp());
            var rs = preparedStatement.executeUpdate();

            if (rs != 1) {
                throw new FileDaoException("File creation error, rs must equal 1. Actual result: " + rs);
            }
        } catch (SQLException e) {
            throw new FileDaoException("Can't add file to data base", e);
        }

        closeConnection(connection);
    }

    @Override
    public File fileById(UUID id) throws FileDaoException {
        File result;
        Connection connection;
        try {
            connection = getConnection();
            var sql = "SELECT BIN_TO_UUID(file_id) as file_id, name, size, created_at FROM file WHERE file_id = UUID_TO_BIN(?)";
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            var rs = preparedStatement.executeQuery();

            if (rs.next()) {
                result = fileFromResultSet(rs);
            } else {
                throw new NotFoundException("File with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new FileDaoException("An error happened while a file searching", e);
        }

        closeConnection(connection);
        return result;
    }

    @Override
    public List<File> findAll() throws FileDaoException {
        var fileList = new ArrayList<File>();
        Connection connection;
        try {
            connection = getConnection();
            var sql = "SELECT BIN_TO_UUID(file_id) as file_id, name, size, created_at FROM file ORDER BY name";
            var statement = connection.createStatement();
            var rs = statement.executeQuery(sql);

            while (rs.next()) {
                var file = fileFromResultSet(rs);
                fileList.add(file);
            }
        } catch (SQLException e) {
            throw new FileDaoException("An error happened while a files searching", e);
        }

        closeConnection(connection);
        return fileList;
    }

    @Override
    public void update(File file) throws FileDaoException {
        Connection connection;
        try {
            connection = getConnection();
            var sql = "UPDATE file SET name = ?, size = ?, created_at = ? WHERE file_id = UUID_TO_BIN(?)";
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setLong(2, file.getSize());
            preparedStatement.setString(3, String.valueOf(file.getId()));
            preparedStatement.setLong(4, file.getTimestamp());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new FileDaoException("An error happened while a file update", e);
        }

        closeConnection(connection);
    }

    @Override
    public void deleteFile(UUID id) throws FileDaoException {
        Connection connection;
        try {
            connection = getConnection();
            var sql = "DELETE FROM file WHERE file_id = UUID_TO_BIN(?)";
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new FileDaoException("Can't delete a file", e);
        }

        closeConnection(connection);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pixelizator";
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

    private File fileFromResultSet(ResultSet rs) {
        File file = null;
        try {
            var uuid = rs.getString("file_id");
            var name = rs.getString("name");
            var size = rs.getLong("size");
            var timestamp = rs.getLong("created_at");
            file = new File(UUID.fromString(uuid), name, size, timestamp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return file;
    }
}
