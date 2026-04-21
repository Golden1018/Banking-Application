import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/CS413";
        String user = "root";
        String password = "";

        System.out.println("Testing connection to: " + url);
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("SUCCESS - Connected to MySQL!");

            // Check admin table
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM admin");
            System.out.println("Admin table rows:");
            while (rs.next()) {
                System.out.println("  userid=" + rs.getString("userid") + "  pwd=" + rs.getString("pwd"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("FAILED - " + e.getMessage());
        }
    }
}
