package app;

import negocio.ConexionAmbato;

import java.io.PrintStream;

/** Secuencia de comprobacion de la Tabla 3 (Fase 2) sobre Conexion Ambato. */
public class Main {

    static void linea(String texto) { System.out.println(texto); }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        ConexionAmbato red = new ConexionAmbato();

        linea("[ANEXO 1] registrarPersona y mostrarRed");
        String[][] datos = {{"A01", "Sofía"}, {"A02", "Mateo"}, {"A03", "Valeria"},
                            {"A04", "Andrés"}, {"A05", "Camila"}, {"A06", "Diego"}};
        for (String[] d : datos)
            linea("registrarPersona(" + d[0] + ", " + d[1] + ") -> " + red.registrarPersona(d[0], d[1]));
        red.mostrarRed();

        linea("");
        linea("[ANEXO 2] amistades y seguimientos");
        linea("agregarAmistad(A01, A02) -> " + red.agregarAmistad("A01", "A02"));
        linea("agregarAmistad(A02, A03) -> " + red.agregarAmistad("A02", "A03"));
        linea("agregarAmistad(A03, A04) -> " + red.agregarAmistad("A03", "A04"));
        linea("agregarAmistad(A04, A01) -> " + red.agregarAmistad("A04", "A01"));
        linea("agregarAmistad(A04, A05) -> " + red.agregarAmistad("A04", "A05"));
        linea("seguir(A02, A01) -> " + red.seguir("A02", "A01"));
        linea("seguir(A06, A01) -> " + red.seguir("A06", "A01"));
        red.mostrarRed();

        linea("");
        linea("[ANEXO 4] listarAmigos, listarSeguidos, listarSeguidores y contarAmigos");
        linea("listarAmigos(A04) -> " + red.listarAmigos("A04"));
        linea("listarSeguidos(A06) -> " + red.listarSeguidos("A06"));
        linea("listarSeguidores(A01) -> " + red.listarSeguidores("A01"));
        for (String[] d : datos)
            linea("contarAmigos(" + d[0] + ") -> " + red.contarAmigos(d[0]));
        linea("listarAmigos(A99) -> " + red.listarAmigos("A99"));
        linea("contarAmigos(A99) -> " + red.contarAmigos("A99"));

        linea("");
        linea("[ANEXO 3] eliminarAmistad y eliminarPersona");
        linea("eliminarAmistad(A04, A05) -> " + red.eliminarAmistad("A04", "A05"));
        linea("contarAmigos(A04) -> " + red.contarAmigos("A04")
                + " ; contarAmigos(A05) -> " + red.contarAmigos("A05"));
        linea("eliminarPersona(A01) -> " + red.eliminarPersona("A01"));
        red.mostrarRed();
        linea("listarSeguidores(A01) -> " + red.listarSeguidores("A01"));

        linea("");
        linea("[VALIDACIONES] pasos 7 y 8");
        linea("registrarPersona(A02, Otro nombre) -> " + red.registrarPersona("A02", "Otro nombre"));
        linea("agregarAmistad(A03, A03) -> " + red.agregarAmistad("A03", "A03"));
    }
}
