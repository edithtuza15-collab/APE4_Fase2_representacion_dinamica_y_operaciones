
package app;

import negocio.ConexionAmbato;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ConexionAmbato red = new ConexionAmbato();

        int opcion;

        do {
            System.out.println("\n===== CONEXION AMBATO =====");
            System.out.println("1. Registrar persona");
            System.out.println("2. Eliminar persona");
            System.out.println("3. Agregar amistad");
            System.out.println("4. Seguir usuario");
            System.out.println("5. Eliminar amistad");
            System.out.println("6. Dejar de seguir");
            System.out.println("7. Mostrar red");
            System.out.println("8. Listar amigos");
            System.out.println("9. Listar seguidos");
            System.out.println("10. Listar seguidores");
            System.out.println("11. Contar amigos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                String usuario, otro, nombre;

                switch (opcion) {

                    case 1:
                        System.out.print("Ingrese usuario: ");
                        usuario = sc.nextLine();

                        System.out.print("Ingrese nombre: ");
                        nombre = sc.nextLine();

                        System.out.println(red.registrarPersona(usuario, nombre)
                                ? "Persona registrada correctamente."
                                : "No se pudo registrar la persona.");
                        break;

                    case 2:
                        System.out.print("Usuario a eliminar: ");
                        usuario = sc.nextLine();

                        System.out.println(red.eliminarPersona(usuario)
                                ? "Persona eliminada."
                                : "Usuario no encontrado.");
                        break;

                    case 3:
                        System.out.print("Primer usuario: ");
                        usuario = sc.nextLine();

                        System.out.print("Segundo usuario: ");
                        otro = sc.nextLine();

                        System.out.println(red.agregarAmistad(usuario, otro)
                                ? "Amistad agregada."
                                : "No se pudo agregar la amistad.");
                        break;

                    case 4:
                        System.out.print("Usuario que sigue: ");
                        usuario = sc.nextLine();

                        System.out.print("Usuario seguido: ");
                        otro = sc.nextLine();

                        System.out.println(red.seguir(usuario, otro)
                                ? "Seguimiento agregado."
                                : "No se pudo agregar el seguimiento.");
                        break;

                    case 5:
                        System.out.print("Primer usuario: ");
                        usuario = sc.nextLine();

                        System.out.print("Segundo usuario: ");
                        otro = sc.nextLine();

                        System.out.println(red.eliminarAmistad(usuario, otro)
                                ? "Amistad eliminada."
                                : "No se pudo eliminar la amistad.");
                        break;

                    case 6:
                        System.out.print("Usuario que deja de seguir: ");
                        usuario = sc.nextLine();

                        System.out.print("Usuario a dejar de seguir: ");
                        otro = sc.nextLine();

                        System.out.println(red.dejarDeSeguir(usuario, otro)
                                ? "Seguimiento eliminado."
                                : "No se pudo eliminar el seguimiento.");
                        break;

                    case 7:
                        red.mostrarRed();
                        break;

                    case 8:
                        System.out.print("Ingrese usuario: ");
                        usuario = sc.nextLine();

                        String amigos = red.listarAmigos(usuario);
                        System.out.println(amigos == null
                                ? "Usuario no encontrado."
                                : "Amigos: " + amigos);
                        break;

                    case 9:
                        System.out.print("Ingrese usuario: ");
                        usuario = sc.nextLine();

                        String seguidos = red.listarSeguidos(usuario);
                        System.out.println(seguidos == null
                                ? "Usuario no encontrado."
                                : "Seguidos: " + seguidos);
                        break;

                    case 10:
                        System.out.print("Ingrese usuario: ");
                        usuario = sc.nextLine();

                        String seguidores = red.listarSeguidores(usuario);
                        System.out.println(seguidores == null
                                ? "Usuario no encontrado."
                                : "Seguidores: " + seguidores);
                        break;

                    case 11:
                        System.out.print("Ingrese usuario: ");
                        usuario = sc.nextLine();

                        int cantidad = red.contarAmigos(usuario);

                        System.out.println(cantidad == -1
                                ? "Usuario no encontrado."
                                : "Cantidad de amigos: " + cantidad);
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                opcion = -1;
                System.out.println("Ingrese un numero valido.");
            }

        } while (opcion != 0);

        sc.close();
    }
}
