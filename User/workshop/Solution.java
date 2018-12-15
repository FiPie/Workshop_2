package pl.coderslab.models;

import java.sql.*;
import java.util.ArrayList;

public class Solution {

    private int id;
    private String created;
    private String updated;
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

    public void loadAllByUserId(int id){

    }

    public void loadAllByExerciseId(int id){

    }



    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description) VALUES (?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement
                    = conn.prepareStatement( sql, generatedColumns );
            preparedStatement.setString( 1, this.created );
            preparedStatement.setString( 2, this.updated );
            preparedStatement.setString( 3, this.description );
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt( 1 );
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setString( 1, this.created );
            preparedStatement.setString( 2, this.updated );
            preparedStatement.setString( 3, this.description );
            preparedStatement.setInt( 4, this.id );
            preparedStatement.executeUpdate();
        }
    }

    static public pl.coderslab.models.Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        preparedStatement.setInt( 1, id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            pl.coderslab.models.Solution loadedSolution = new pl.coderslab.models.Solution();
            loadedSolution.id = resultSet.getInt( "id" );
            loadedSolution.created = resultSet.getString( "created" );
            loadedSolution.updated = resultSet.getString( "updated" );
            loadedSolution.description = resultSet.getString( "description" );
            return loadedSolution;
        }
        return null;
    }


    static public pl.coderslab.models.Solution[] loadAllSolution(Connection conn) throws SQLException {
        ArrayList<pl.coderslab.models.Solution> solution = new ArrayList<pl.coderslab.models.Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement( sql );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            pl.coderslab.models.Solution loadedSolution = new pl.coderslab.models.Solution();
            loadedSolution.id = resultSet.getInt( "id" );
            loadedSolution.created = resultSet.getString( "created" );
            loadedSolution.updated = resultSet.getString( "updated" );
            loadedSolution.description = resultSet.getString( "description" );
            solution.add( loadedSolution );
        }
        pl.coderslab.models.Solution[] uArray = new pl.coderslab.models.Solution[solution.size()];
        uArray = solution.toArray( uArray );
        return uArray;
    }

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement( sql );
            preparedStatement.setInt( 1, this.id );
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }
}
