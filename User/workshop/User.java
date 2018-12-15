package pl.coderslab.models;


import java.sql.*;
import java.util.ArrayList;

public class User {


    private int id;
    private String username;
    private String password;
    private String email;




    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.setPassword( password );
    }


    public void setPassword(String password) {
        this.password = BCrypt.hashpw( password, BCrypt.gensalt() );
    }




    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/UniCod?useSSL=false&characterEncoding=utf8";
        String user = "root";
        String password = "codeslab";


        try (Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/UniCod?useSSL=false&characterEncoding=utf8",
                "root",
                "coderslab" )) {
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadAllByGroupId(){

    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement
                    = conn.prepareStatement( sql, generatedColumns );
            preparedStatement.setString( 1, this.username );
            preparedStatement.setString( 2, this.email );
            preparedStatement.setString( 3, this.password );
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt( 1 );
            }
        } else {
            String sql = "UPDATE users SET username=?, email=?, password=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setString( 1, this.username );
            preparedStatement.setString( 2, this.email );
            preparedStatement.setString( 3, this.password );
            preparedStatement.setInt( 4, this.id );
            preparedStatement.executeUpdate();
        }
    }

    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        preparedStatement.setInt( 1, id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt( "id" );
            loadedUser.username = resultSet.getString( "username" );
            loadedUser.password = resultSet.getString( "password" );
            loadedUser.email = resultSet.getString( "email" );
            return loadedUser;
        }
        return null;
    }


    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt( "id" );
            loadedUser.username = resultSet.getString( "username" );
            loadedUser.password = resultSet.getString( "password" );
            loadedUser.email = resultSet.getString( "email" );
            users.add( loadedUser );
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray( uArray );
        return uArray;
    }



    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setInt( 1, this.id );
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


//    // Hash a password for the first time
//    String hashed = BCrypt.hashpw( password, BCrypt.gensalt() );
//
//    // gensalt's log_rounds parameter determines the complexity
//// the work factor is 2**log_rounds, and the default is 10
//    String hashed = BCrypt.hashpw( password, BCrypt.gensalt( 12 ) );
//
//// Check that an unencrypted password matches one that has
//// previously been hashed
//if(BCrypt.checkpw(candidate,hashed))
//            System.out.println("It matches");
//else
//        System.out.println("It does not match");


}

//    CREATE TABLE `UniCod`.`users` (
//        `id` BIGINT(20) NOT NULL,
//        PRIMARY KEY (`id`));