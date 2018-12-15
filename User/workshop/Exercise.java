package pl.coderslab.models;

import java.sql.*;
import java.util.ArrayList;

public class Exercise {


    private int id;
    private String title;
    private String description;

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




    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO exercise(title, description) VALUES (?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement
                    = conn.prepareStatement( sql, generatedColumns );
            preparedStatement.setString( 1, this.title );
            preparedStatement.setString( 2, this.description );
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt( 1 );
            }
        } else {
            String sql = "UPDATE exercise SET title=?, description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setString( 1, this.title );
            preparedStatement.setString( 2, this.description );
            preparedStatement.setInt( 4, this.id );
            preparedStatement.executeUpdate();
        }
    }

    static public pl.coderslab.models.Exercise loadExerciseById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM exercise where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        preparedStatement.setInt( 1, id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            pl.coderslab.models.Exercise loadedExercise = new pl.coderslab.models.Exercise();
            loadedExercise.id = resultSet.getInt( "id" );
            loadedExercise.title = resultSet.getString( "title" );
            loadedExercise.description = resultSet.getString( "description" );
            return loadedExercise;
        }
        return null;
    }


    static public pl.coderslab.models.Exercise[] loadAllExercise(Connection conn) throws SQLException {
        ArrayList<pl.coderslab.models.Exercise> exercise = new ArrayList<pl.coderslab.models.Exercise>();
        String sql = "SELECT * FROM exercise";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            pl.coderslab.models.Exercise loadedExercise = new pl.coderslab.models.Exercise();
            loadedExercise.id = resultSet.getInt( "id" );
            loadedExercise.title = resultSet.getString( "title" );
            loadedExercise.description = resultSet.getString( "description" );
            exercise.add( loadedExercise );
        }
        pl.coderslab.models.Exercise[] uArray = new pl.coderslab.models.Exercise[exercise.size()];
        uArray = exercise.toArray( uArray );
        return uArray;
    }

    public void delete(Connection conn) throws SQLException {

        if (this.id != 0) {
            String sql = "DELETE FROM exercise WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setInt( 1, this.id );
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }
}
