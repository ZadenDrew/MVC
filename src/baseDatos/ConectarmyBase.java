package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.swing.JOptionPane;
import tienda_videojuegos.Juegos;

/**
 *
 * @author andrea
 */
public class ConectarmyBase {

    public static DataSource dataSource;
    public static String db = "mibase.db";
    public static String url = "jdbc:sqlite:/home/local/DANIELCASTELAO/acabezaslopez/NetBeansProjects/tienda_videojuegos/" + db;
//    public String user = "AndreaBase";
//    public String pass = "012345C";
    public static Connection link;
    public static PreparedStatement stmt;
/**
 * Constructor por defecto
 */
    public ConectarmyBase() {

    }
/**
 * Método que conecta nuestro programa a la base de datos.
 */
    public static void connect() {

        try {

            Class.forName("org.sqlite.JDBC");

            link = DriverManager.getConnection("jdbc:sqlite:/home/local/DANIELCASTELAO/acabezaslopez/NetBeansProjects/tienda_videojuegos/mibase.db");
            link.setAutoCommit(false);
            if (link != null) {
                System.out.println("Conectado.");
            }
        } catch (SQLException exsq) {
            System.out.println("Error: " + exsq);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error : " + ex);
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);

        }

    }
/**
 * Método que desconecta el programa de la base de datos.
 */
    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (link != null) {
                link.close();
            }
            System.out.println("Conexión cerrada.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
/**
 * Método que inserta en la tabla Juegos de la base de datos los valores de un objeto Juegos.
 * @param j 
 */
    public static void insert(Juegos j) {
        connect();

        try {

            PreparedStatement st = link.prepareStatement("insert into Juegos values (?,?,?,?,?)");
            st.setString(1, j.getCodigo());
            st.setString(2, j.getNombre());
            st.setString(3, j.getConsola());
            st.setFloat(4, j.getPrecio());
            st.setInt(5, j.getUnidades());
            st.execute();
            link.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ConectarmyBase.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        disconnect();
        ConectarmyBase.mostrarJuegos();
    }
/**
 * Método que crea un arrayList con 
 * @return 
 */
    public static ArrayList mostrarJuegos() {
        ArrayList<Juegos> listaJuegos = new ArrayList();
        connect();
        ResultSet result;
        try {
            PreparedStatement st = link.prepareStatement("SELECT * FROM Juegos");
            result = st.executeQuery();
            while (result.next()) {
                Juegos usu = new Juegos(result.getString("codigo"), result.getString("nombre"),
                        result.getString("consola"), result.getFloat("precio"), result.getInt("unidades"));
                listaJuegos.add(usu);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        disconnect();
        return listaJuegos;
    }

    public static void modify(String codigo) {
        connect();
        try {

            String nombre = JOptionPane.showInputDialog("dame el nombre:");
            String consola = JOptionPane.showInputDialog("dame el nombre de la consola:");
            String precio = JOptionPane.showInputDialog("dame el precio:");
            String unidades = JOptionPane.showInputDialog("dame las unidades:");
            stmt = link.prepareStatement("UPDATE Juegos set nombre = '" + nombre + "' , consola = '" + consola + "' ,precio = '" + precio + "',unidades = '" + unidades + "' where codigo='" + codigo + "';");

            stmt.executeUpdate();
            link.commit();

            System.out.println("La fila ha sido modificada con éxito.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        disconnect();
    }

    public static void delete(String codigo) {
        connect();

        try {

            Statement st = link.createStatement();

            String delete = "DELETE from JUEGOS where codigo='" + codigo + "';";
            st.executeUpdate(delete);
            link.commit();

            System.out.println("Filas borradas con éxito.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        disconnect();
    }
}
