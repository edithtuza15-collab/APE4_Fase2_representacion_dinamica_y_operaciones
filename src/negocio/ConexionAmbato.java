package negocio;

import modelo.*;

/**
 * APE 4 - Fase 2: red social dinamica "Conexion Ambato".
 * Multilista: lista enlazada de vertices (personas) y, por cada vertice,
 * una lista enlazada de aristas (AMISTAD en ambos sentidos, SEGUIR dirigida).
 * Sin HashMap, HashSet, ArrayList ni LinkedList: solo nodos propios.
 */
public class ConexionAmbato {

    private Vertice cabeza;

    // ---------- auxiliares ----------

    /** Busca un vertice por su identificador normalizado. */
    private Vertice buscar(String usuario) {
        if (usuario == null) return null;
        String clave = usuario.trim().toLowerCase();
        for (Vertice v = cabeza; v != null; v = v.sig)
            if (v.persona.usuario.equals(clave)) return v;
        return null;
    }

    /** Inserta una arista al comienzo de la lista de origen. */
    private void insertar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista a = new Arista();
        a.destino = destino;
        a.tipo = tipo;
        a.sig = origen.aristas;
        origen.aristas = a;
    }

    /** Indica si ya existe una arista de ese tipo de origen a destino. */
    private boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.aristas; a != null; a = a.sig)
            if (a.destino == destino && a.tipo == tipo) return true;
        return false;
    }

    /** Retira de origen la primera arista de ese tipo hacia destino. */
    private boolean quitar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista anterior = null;
        for (Arista a = origen.aristas; a != null; anterior = a, a = a.sig) {
            if (a.destino == destino && a.tipo == tipo) {
                if (anterior == null) origen.aristas = a.sig;
                else anterior.sig = a.sig;
                return true;
            }
        }
        return false;
    }

    /** Retira de origen toda arista (de cualquier tipo) que apunte a objetivo. */
    private void quitarHacia(Vertice origen, Vertice objetivo) {
        Arista anterior = null;
        Arista a = origen.aristas;
        while (a != null) {
            if (a.destino == objetivo) {
                if (anterior == null) origen.aristas = a.sig;
                else anterior.sig = a.sig;
            } else {
                anterior = a;
            }
            a = a.sig;
        }
    }

    private static String codigo(Vertice v) {
        return v.persona.usuario.toUpperCase();
    }

    // ---------- personas ----------

    public boolean registrarPersona(String usuario, String nombre) {
        if (usuario == null || nombre == null) return false;
        String clave = usuario.trim().toLowerCase();
        if (clave.isEmpty() || nombre.trim().isEmpty()) return false;
        if (clave.indexOf(' ') >= 0 || clave.indexOf(',') >= 0) return false;
        if (buscar(clave) != null) return false;
        Vertice v = new Vertice();
        v.persona = new Persona(clave, nombre.trim());
        v.sig = cabeza;             // inserta al inicio de la lista de vertices
        cabeza = v;
        return true;
    }

    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return false;
        for (Vertice w = cabeza; w != null; w = w.sig)
            quitarHacia(w, objetivo);               // limpia aristas entrantes
        Vertice anterior = null, actual = cabeza;
        while (actual != objetivo) {
            anterior = actual;
            actual = actual.sig;
        }
        if (anterior == null) cabeza = objetivo.sig;
        else anterior.sig = objetivo.sig;
        return true;
    }

    // ---------- relaciones ----------

    public boolean agregarAmistad(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb ||
                existe(va, vb, TipoRelacion.AMISTAD)) return false;
        insertar(va, vb, TipoRelacion.AMISTAD);
        insertar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    public boolean seguir(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb ||
                existe(va, vb, TipoRelacion.SEGUIR)) return false;
        insertar(va, vb, TipoRelacion.SEGUIR);
        return true;
    }

    public boolean eliminarAmistad(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb ||
                !existe(va, vb, TipoRelacion.AMISTAD)) return false;
        quitar(va, vb, TipoRelacion.AMISTAD);
        quitar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    public boolean dejarDeSeguir(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb) return false;
        return quitar(va, vb, TipoRelacion.SEGUIR);
    }

    // ---------- consultas ----------

    private String unir(StringBuilder sb) {
        return sb.length() == 0 ? "(ninguno)" : sb.toString();
    }

    public String listarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (a.tipo == TipoRelacion.AMISTAD) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(a.destino.persona.nombre);
            }
        return unir(sb);
    }

    public String listarSeguidos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (a.tipo == TipoRelacion.SEGUIR) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(a.destino.persona.nombre);
            }
        return unir(sb);
    }

    /** Recorre toda la red: busca las aristas SEGUIR que llegan al usuario. */
    public String listarSeguidores(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Vertice w = cabeza; w != null; w = w.sig)
            if (existe(w, objetivo, TipoRelacion.SEGUIR)) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(w.persona.nombre);
            }
        return unir(sb);
    }

    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return -1;
        int n = 0;
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (a.tipo == TipoRelacion.AMISTAD) n++;
        return n;
    }

    public int contarPersonas() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.sig) n++;
        return n;
    }

    /** Cuenta las amistades (cada una se almacena como dos aristas). */
    public int contarAmistades() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.sig)
            for (Arista a = v.aristas; a != null; a = a.sig)
                if (a.tipo == TipoRelacion.AMISTAD) n++;
        return n / 2;
    }

    public void mostrarRed() {
        System.out.println("Red \"Conexion Ambato\": " + contarPersonas()
                + " personas, " + contarAmistades() + " amistades");
        for (Vertice v = cabeza; v != null; v = v.sig) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(codigo(v)).append("] ").append(v.persona.nombre).append(" ->");
            if (v.aristas == null) sb.append(" (sin relaciones)");
            for (Arista a = v.aristas; a != null; a = a.sig)
                sb.append(" ").append(a.tipo).append(":").append(codigo(a.destino));
            System.out.println(sb);
        }
    }
}
