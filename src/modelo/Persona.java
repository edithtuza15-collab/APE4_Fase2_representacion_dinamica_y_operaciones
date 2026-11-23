package modelo;

/** Datos de una persona registrada en la red. */
public class Persona {
    public String usuario;   // identificador normalizado (minusculas)
    public String nombre;

    public Persona(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }
}
