package tienda_videojuegos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrea
 */
public class Juegos {

    private String nombre, consola, codigo;
    private float precio;
    private int unidades;

    public Juegos() {
    }

    public Juegos(String codigo, String nombre, String consola, float precio, int unidades) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.consola = consola;
        this.precio = precio;
        this.unidades = unidades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return ("Juegos ->\ncodigo= " + codigo + ", \nnombre= " + nombre + ", \nconsola= " + consola + ", \nprecio= " + precio + ", \nunidades= " + unidades);
    }

}
