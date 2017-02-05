package ua.nure.vorozhka.SummaryTask4.db.connector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stanislav on 12.01.2017.
 */
public final class DAOUtils {

    private DAOUtils(){}

    public static void fillPreparedStatement(
            PreparedStatement pstmt, int... rows)
            throws SQLException {

        int counter = 1;
        for (int row : rows) {
            pstmt.setInt(counter++, row);
        }
    }

    public static void fillPreparedStatement(
            PreparedStatement pstmt, String... rows)
            throws SQLException {

        int counter = 1;
        for (String row : rows) {
            pstmt.setString(counter++, row);
        }
    }

    public static void fillPreparedStatementPostfix(
            PreparedStatement pstmt, int id, String... rows)
            throws SQLException {

        int counter = 1;
        for (String row : rows) {
            pstmt.setString(counter++, row);
        }
        pstmt.setInt(counter++, id);
    }
}
