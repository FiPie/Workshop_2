package pl.coderslab.models;

import java.sql.*;
import java.util.ArrayList;

public class UserGroup {

    private int id;
    private String name;


    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/UniCod?useSSL=false&characterEncoding=utf8";
        String user = "root";
        String password = "codeslab";


        try (Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/UniCod?useSSL=false&characterEncoding=utf8",
                "root",
                "coderslab" )) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO user_group(name) VALUES (?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement
                    = conn.prepareStatement( sql, generatedColumns );
            preparedStatement.setString( 1, this.name );

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt( 1 );
            }
        } else {
            String sql = "UPDATE user_group SET name=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setString( 1, this.name );
            preparedStatement.setInt( 2, this.id );
            preparedStatement.executeUpdate();
        }

    }

    static public pl.coderslab.models.UserGroup loadGroupById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        preparedStatement.setInt( 1, id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            pl.coderslab.models.Group loadedUserGroup = new pl.coderslab.models.UserGroup();
            loadedUserGroup.id = resultSet.getInt( "id" );
            loadedUserGroup.name = resultSet.getString( "name" );
            return loadedUserGroup;
        }
        return null;
    }


    static public pl.coderslab.models.UserGroup[] loadAllUserGroup(Connection conn) throws SQLException {
        ArrayList<pl.coderslab.models.User> userGroup = new ArrayList<pl.coderslab.models.UserGroup>();
        String sql = "SELECT * FROM user_group";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            pl.coderslab.models.UserGroup loadedUserGroup = new pl.coderslab.models.User();
            loadedUserGroup.id = resultSet.getInt( "id" );
            loadedUserGroup.name = resultSet.getString( "name" );
            userGroup.add( loadedUserGroup );
        }
        pl.coderslab.models.UserGroup[] uArray = new pl.coderslab.models.UserGroup[userGroup.size()];
        uArray = userGroup.toArray( uArray );
        return uArray;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM user_group WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setInt( 1, this.id );
            preparedStatement.executeUpdate();
            this.id = 0;
        }

    }

}
