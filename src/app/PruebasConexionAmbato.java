package app;

import negocio.ConexionAmbato;

import java.io.PrintStream;

/** Pruebas automatizadas (sin librerias externas) de ConexionAmbato. */
public class PruebasConexionAmbato {

    static int total = 0, ok = 0;

    static void verificar(String nombre, boolean condicion) {
        total++;
        if (condicion) ok++;
        System.out.println((condicion ? "PASA  " : "FALLA ") + nombre);
    }

    static boolean igual(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    static ConexionAmbato redBase() {
        ConexionAmbato r = new ConexionAmbato();
        r.registrarPersona("A01", "Sofía");
        r.registrarPersona("A02", "Mateo");
        r.registrarPersona("A03", "Valeria");
        r.registrarPersona("A04", "Andrés");
        r.registrarPersona("A05", "Camila");
        r.registrarPersona("A06", "Diego");
        r.agregarAmistad("A01", "A02");
        r.agregarAmistad("A02", "A03");
        r.agregarAmistad("A03", "A04");
        r.agregarAmistad("A04", "A01");
        r.agregarAmistad("A04", "A05");
        return r;
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        ConexionAmbato r = redBase();

        verificar("estado inicial: 6 personas", r.contarPersonas() == 6);
        verificar("estado inicial: 5 amistades", r.contarAmistades() == 5);
        verificar("grados 2,2,2,3,1,0",
                r.contarAmigos("A01") == 2 && r.contarAmigos("A02") == 2 && r.contarAmigos("A03") == 2
                && r.contarAmigos("A04") == 3 && r.contarAmigos("A05") == 1 && r.contarAmigos("A06") == 0);

        verificar("registrarPersona rechaza usuario repetido", !r.registrarPersona("A02", "Otro"));
        verificar("registrarPersona rechaza usuario repetido en minuscula", !r.registrarPersona("a02", "Otro"));
        verificar("registrarPersona rechaza usuario vacio", !r.registrarPersona("  ", "Ana"));
        verificar("registrarPersona rechaza usuario nulo", !r.registrarPersona(null, "Ana"));
        verificar("registrarPersona rechaza nombre vacio", !r.registrarPersona("A07", ""));
        verificar("registrarPersona rechaza espacios en el usuario", !r.registrarPersona("A 07", "Ana"));
        verificar("registrarPersona rechaza comas en el usuario", !r.registrarPersona("A,07", "Ana"));
        verificar("buscar normaliza mayusculas (a01 = A01)", r.contarAmigos("a01") == 2);

        verificar("agregarAmistad rechaza lazo", !r.agregarAmistad("A03", "A03"));
        verificar("agregarAmistad rechaza duplicado", !r.agregarAmistad("A01", "A02"));
        verificar("agregarAmistad rechaza duplicado invertido", !r.agregarAmistad("A02", "A01"));
        verificar("agregarAmistad rechaza usuario inexistente", !r.agregarAmistad("A01", "A99"));
        verificar("la amistad es reciproca", igual(r.listarAmigos("A05"), "Andrés"));

        verificar("seguir(A02,A01) devuelve true", r.seguir("A02", "A01"));
        verificar("seguir rechaza duplicado", !r.seguir("A02", "A01"));
        verificar("seguir rechaza lazo", !r.seguir("A02", "A02"));
        verificar("seguir permite sentido contrario (dirigido)", r.seguir("A01", "A02"));
        verificar("seguir(A06,A01): Diego sin amigos puede seguir", r.seguir("A06", "A01"));
        verificar("listarSeguidos(A06) = Sofía", igual(r.listarSeguidos("A06"), "Sofía"));
        verificar("listarSeguidores(A01) incluye a Mateo y Diego",
                r.listarSeguidores("A01").contains("Mateo") && r.listarSeguidores("A01").contains("Diego"));
        verificar("seguir no cambia contarAmigos", r.contarAmigos("A01") == 2);
        verificar("dejarDeSeguir(A06,A01) devuelve true", r.dejarDeSeguir("A06", "A01"));
        verificar("dejarDeSeguir sin relacion devuelve false", !r.dejarDeSeguir("A06", "A01"));
        verificar("dejarDeSeguir conserva las amistades", r.contarAmigos("A01") == 2);

        verificar("listarAmigos inexistente devuelve null", r.listarAmigos("A99") == null);
        verificar("listarSeguidores inexistente devuelve null", r.listarSeguidores("A99") == null);
        verificar("contarAmigos inexistente devuelve -1", r.contarAmigos("A99") == -1);
        verificar("listarAmigos(A06) sin amigos", igual(r.listarAmigos("A06"), "(ninguno)"));

        verificar("eliminarAmistad(A04,A05) devuelve true", r.eliminarAmistad("A04", "A05"));
        verificar("eliminarAmistad quita ambos sentidos",
                r.contarAmigos("A04") == 2 && r.contarAmigos("A05") == 0);
        verificar("eliminarAmistad repetida devuelve false", !r.eliminarAmistad("A04", "A05"));

        verificar("eliminarPersona(A01) devuelve true", r.eliminarPersona("A01"));
        verificar("eliminarPersona deja 5 personas", r.contarPersonas() == 5);
        verificar("eliminarPersona limpia aristas entrantes (Mateo)", r.contarAmigos("A02") == 1);
        verificar("eliminarPersona limpia aristas entrantes (Andrés)", r.contarAmigos("A04") == 1);
        verificar("eliminarPersona quita los SEGUIR que llegaban", igual(r.listarSeguidos("A02"), "(ninguno)"));
        verificar("la persona eliminada ya no existe", r.contarAmigos("A01") == -1);
        verificar("eliminarPersona inexistente devuelve false", !r.eliminarPersona("A01"));
        verificar("eliminar la cabeza de la lista (A06)", r.eliminarPersona("A06") && r.contarPersonas() == 4);

        System.out.println();
        System.out.println("Resultado: " + ok + " de " + total + " pruebas aprobadas"
                + (ok == total ? " - TODAS PASAN" : " - HAY FALLOS"));
        if (ok != total) System.exit(1);
    }
}
