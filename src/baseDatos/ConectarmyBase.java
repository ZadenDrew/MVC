package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
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
    public static String url = "jdbc:sqlite:/home/oracle/NetBeansProjects/tienda_videojuegos/" + db;
//    public String user = "AndreaBase";
//    public String pass = "012345C";
    public static Connection link;
    public static PreparedStatement stmt;
    public static ArrayList<Juegos> listaJuegos = new ArrayList();

    /**
     * Constructor por defecto de ConectarmyBase
     */
    public ConectarmyBase() {

    }

    /**
     * Método para conectarse a la base de datos
     */
    public static void connect() {

        try {

            Class.forName("org.sqlite.JDBC");

            link = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\andrea\\Documents\\NetBeansProjects\\tienda_videojuegos\\mibase.db");

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
     * Método para desconectar de la base de datos
     */
    public static void disconnect() {
        try {

            if (stmt != null) {
                stmt.closeOnCompletion();

            }
            if (link != null) {

                link.close();
            }

            System.out.println("Conexión cerrada.");

        } catch (SQLException ex) {
            System.out.println("Error:" + ex);
        }
    }

    /**
     * Método que inserta en la base de datos de Juegos los valores obtenidos
     * mediante el parámetro Juegos que se le pasa
     *
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ese codigo ya esta repetido");
        }
        disconnect();
        ConectarmyBase.mostrarJuegos();
    }

    /**
     * Método que recoge todos los datos de la base de datos de Juegos los mete
     * en un ArrayList y los devuelve.
     *
     * @return ArrayList de Juegos
     */
    public static ArrayList mostrarJuegos() {
        listaJuegos = new ArrayList();
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
            System.out.println(ex.getMessage());
        }
        disconnect();
        return listaJuegos;

    }

    /**
     * Método que modifica una fila seleccionada Requiriendo los nuevos valores
     * mediante unos JOptionPane.
     *
     * @param codigo
     */
    public static void modify(String codigo) {
        connect();
        try {

            String nombre = JOptionPane.showInputDialog("dame el nombre:");
            String consola = JOptionPane.showInputDialog("dame el nombre de la consola:");
            String precio = JOptionPane.showInputDialog("dame el precio:");
            String unidades = JOptionPane.showInputDialog("dame las unidades:");
            stmt = link.prepareStatement("UPDATE Juegos set nombre = '" + nombre + "' , consola = '" + consola + "' ,precio = '" + precio + "',unidades = '" + unidades + "' where codigo='" + codigo + "';");
            stmt.executeUpdate();

            System.out.println("La fila ha sido modificada con éxito.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        disconnect();
    }

    /**
     * Método que se conecta a la base de datos de Juegos y hace una consulta en
     * la tabla por código y elimina de la base de datos la fila
     * correspondiente.
     *
     * @param codigo
     */
    public static void delete(String codigo) {
        connect();

        try {

            Statement st = link.createStatement();

            String delete = "DELETE from JUEGOS where codigo='" + codigo + "';";
            st.executeUpdate(delete);

            System.out.println("Filas borradas con éxito.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
        disconnect();
    }

    /**
     * Método que se conecta a la base de datos de Juegos y hace una consulta
     * empleando Query con los valores requeridos. Con los valores encontrados
     * instanciamos un objeto de la clase Juegos que retornaremos.
     *
     * @param s
     * @param op
     * @return Juegos
     */
    public static Juegos buscar(String s, int op) {
        connect();
        ResultSet result;
        String busca = null;
        Juegos usu = null;

        try {
            Statement st = link.createStatement();
            if (op == 1) {
                busca = "SELECT * from JUEGOS where codigo='" + s + "';";
            }

            if (op == 2) {
                busca = "SELECT * from JUEGOS where nombre='" + s + "';";
            }

            result = st.executeQuery(busca);
            while (result.next()) {
                usu = new Juegos(result.getString("codigo"), result.getString("nombre"),
                        result.getString("consola"), result.getFloat("precio"), result.getInt("unidades"));

                System.out.println("Filas encontrada con éxito.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Seleccione un juego en la tabla a mostrar .");
        }

        disconnect();
        return usu;
    }

}
