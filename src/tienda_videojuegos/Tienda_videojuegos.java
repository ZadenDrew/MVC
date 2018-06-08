package tienda_videojuegos;

import baseDatos.ConectarmyBase;
import grafica.Tabla_Juego;
import java.util.ArrayList;

/**
 *
 * @author andrea
 */
public class Tienda_videojuegos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tabla_Juego tb = new Tabla_Juego();
        tb.setVisible(true);
    }

    /**
     *
     */
    public static void conectar() {
        ConectarmyBase.connect();
    }

    /**
     *
     */
    public static void desconectar() {
        ConectarmyBase.disconnect();
    }

    /**
     *
     * @param codigo
     * @param nombre
     * @param consola
     * @param precio
     * @param unidades
     */
    public static void insertarJuego(String codigo, String nombre, String consola, float precio, int unidades) {

        Juegos miJuegoAinsertar = new Juegos(codigo, nombre, consola, precio, unidades);
//        miJuegoAinsertar.setCodigo(codigo);
//        miJuegoAinsertar.setNombre(nombre);
//        miJuegoAinsertar.setConsola(consola);
//        miJuegoAinsertar.setPrecio(precio);
//        miJuegoAinsertar.setUnidades(unidades);

        ConectarmyBase.insert(miJuegoAinsertar);

    }

    public static Juegos buscarJuego(String s, int op) {
        return ConectarmyBase.buscar(s, op);
    }

    /**
     *
     * @param codigoRecogido
     */
    public static void modificarJuego(String codigoRecogido) {
        ConectarmyBase.modify(codigoRecogido);
    }

    /**
     *
     * @param codigoRecogido
     */
    public static void borrarJuego(String codigoRecogido) {
        ConectarmyBase.delete(codigoRecogido);
    }
/**
 * 
 * @return 
 */
    public static ArrayList mostrar() {
        return ConectarmyBase.mostrarJuegos();
    }

}
