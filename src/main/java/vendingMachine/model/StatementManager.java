package vendingMachine.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementManager {
    private Connection connection;

    public StatementManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Prepare Statement
     * @param sqlText sql command to prepare statement for
     * @return a PreparedStatement of the sql command or null in the case of an exception
     */
    public PreparedStatement prepareStatement(String sqlText){
        try{
            PreparedStatement ps = connection.prepareStatement(sqlText);
            ps.setQueryTimeout(30);

            return ps;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return a statement that can be used to execute any sql query
     */
    public Statement createStatement() {
        try {
            Statement statement = this.connection.createStatement();

            /*---------------------------------------------------
            set query timeout to 30 seconds in case of any
            queries that take too long to execute
            ---------------------------------------------------*/
            statement.setQueryTimeout(30);

            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
