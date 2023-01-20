package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // remove this
            removeExistDB(conn);

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            for (int i = 18; i <= 20; i++) {
                Client c = new Client("test" + (i - 17), i);
                dao.add(c);
                int id = c.getId();
                System.out.println("ID of last added client: " + id);
            }

            System.out.println();
            System.out.println("Query without parameters:");
            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

//            list.get(0).setAge(55);
//            dao.update(list.get(0));

            System.out.println();
            System.out.println("Query with \"age\" parameter:");
//            List<Client> list2 = dao.getAll(Client.class, "name", "age");
            List<Client> list2 = dao.getAll(Client.class, "age");
            for (Client cli : list2)
                System.out.println(cli);
        }
    }
    private static void removeExistDB(Connection conn){
        try {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS Clients");
                //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
