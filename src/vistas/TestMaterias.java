package vistas;

import modelos.Materia;
import persistencia.MateriaData;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author  Nahuel Guerra
 */
public class TestMaterias {

    public static void main(String[] args) {
        MateriaData materiaData = new MateriaData();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  TEST DE MATERIAS - GRUPO 21");
        System.out.println("===========================================\n");

        // Ingresar materias de prueba
        ingresarMateriasDePrueba(materiaData);

        // Mostrar todas las materias
        System.out.println("\n--- LISTADO DE TODAS LAS MATERIAS ---");
        mostrarMaterias(materiaData.listarMaterias());

        // Menú de opciones
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===========================================");
            System.out.println("MENU DE OPCIONES:");
            System.out.println("1. Mostrar todas las materias");
            System.out.println("2. Mostrar solo materias activas");
            System.out.println("3. Buscar materia por ID");
            System.out.println("4. Buscar materias por año");
            System.out.println("5. Actualizar materia");
            System.out.println("6. Dar de baja materia (baja lógica)");
            System.out.println("7. Dar de alta materia (alta lógica)");
            System.out.println("8. Eliminar materia (borrado físico)");
            System.out.println("9. Ingresar nueva materia");
            System.out.println("0. Salir");
            System.out.println("===========================================");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- TODAS LAS MATERIAS ---");
                    mostrarMaterias(materiaData.listarMaterias());
                    break;

                case 2:
                    System.out.println("\n--- MATERIAS ACTIVAS ---");
                    mostrarMaterias(materiaData.listarMateriasActivas());
                    break;

                case 3:
                    System.out.print("Ingrese ID de la materia: ");
                    int idBuscar = scanner.nextInt();
                    scanner.nextLine();
                    Materia materiaBuscada = materiaData.buscarMateriaPorId(idBuscar);
                    if (materiaBuscada != null) {
                        System.out.println("\nMateria encontrada:");
                        mostrarMateria(materiaBuscada);
                    } else {
                        System.out.println("No se encontró materia con ID: " + idBuscar);
                    }
                    break;

                case 4:
                    System.out.print("Ingrese año (1-5): ");
                    int anioBuscar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("\n--- MATERIAS DE " + anioBuscar + "° AÑO ---");
                    mostrarMaterias(materiaData.listarMateriasPorAnio(anioBuscar));
                    break;

                case 5:
                    System.out.print("Ingrese ID de la materia a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    Materia materiaActualizar = materiaData.buscarMateriaPorId(idActualizar);
                    if (materiaActualizar != null) {
                        System.out.print("Nuevo nombre (actual: " + materiaActualizar.getNombreMateria() + "): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo año (actual: " + materiaActualizar.getAnio() + "): ");
                        int nuevoAnio = scanner.nextInt();
                        scanner.nextLine();

                        materiaActualizar.setNombreMateria(nuevoNombre);
                        materiaActualizar.setAnio(nuevoAnio);

                        materiaData.actualizarMateria(materiaActualizar);
                    } else {
                        System.out.println("No se encontró materia con ID: " + idActualizar);
                    }
                    break;

                case 6:
                    System.out.print("Ingrese ID de la materia a dar de baja: ");
                    int idBaja = scanner.nextInt();
                    scanner.nextLine();
                    materiaData.cambiarEstadoMateria(idBaja, false);
                    break;

                case 7:
                    System.out.print("Ingrese ID de la materia a dar de alta: ");
                    int idAlta = scanner.nextInt();
                    scanner.nextLine();
                    materiaData.cambiarEstadoMateria(idAlta, true);
                    break;

                case 8:
                    System.out.print("Ingrese ID de la materia a eliminar (¡CUIDADO! Borrado físico): ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("¿Está seguro? (S/N): ");
                    String confirmacion = scanner.nextLine();
                    if (confirmacion.equalsIgnoreCase("S")) {
                        materiaData.eliminarMateria(idEliminar);
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;

                case 9:
                    System.out.print("Nombre de la materia: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Año (1-5): ");
                    int anio = scanner.nextInt();
                    scanner.nextLine();

                    Materia nuevaMateria = new Materia(nombre, anio, true);
                    materiaData.insertarMateria(nuevaMateria);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        scanner.close();
    }

    // Método para ingresar materias de prueba
    private static void ingresarMateriasDePrueba(MateriaData materiaData) {
        // Verificar si ya existen materias en la base de datos
        List<Materia> materiasExistentes = materiaData.listarMaterias();

        if (!materiasExistentes.isEmpty()) {
            System.out.println("Ya existen materias en la base de datos. Saltando carga de datos de prueba.\n");
            return;
        }

        System.out.println("Ingresando materias de prueba...\n");

        // Materias de 1er año
        Materia materia1 = new Materia("Matemática I", 1, true);
        materiaData.insertarMateria(materia1);
        System.out.println("Materia 1: Matemática I insertada con ID " + materia1.getIdMateria());

        Materia materia2 = new Materia("Laboratorio I", 1, true);
        materiaData.insertarMateria(materia2);
        System.out.println("Materia 2: Laboratorio I insertada con ID " + materia2.getIdMateria());

        // Materias de 2do año
        Materia materia3 = new Materia("Inglés Técnico I", 2, true);
        materiaData.insertarMateria(materia3);
        System.out.println("Materia 3: Inglés Técnico I insertada con ID " + materia3.getIdMateria());

        Materia materia4 = new Materia("Estadística", 2, true);
        materiaData.insertarMateria(materia4);
        System.out.println("Materia 4: Estadística insertada con ID " + materia4.getIdMateria());

        // Materias de 3er año
        Materia materia5 = new Materia("Acceso a Datos", 3, true);
        materiaData.insertarMateria(materia5);
        System.out.println("Materia 5: Acceso a Datos insertada con ID " + materia5.getIdMateria());
    }

    // Método para mostrar lista de materias
    private static void mostrarMaterias(List<Materia> materias) {
        if (materias.isEmpty()) {
            System.out.println("No hay materias para mostrar.");
            return;
        }

        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-5s %-30s %-10s %-10s%n",
                "ID", "Nombre", "Año", "Estado");
        System.out.println("-----------------------------------------------------------");

        for (Materia materia : materias) {
            System.out.printf("%-5d %-30s %-10d %-10s%n",
                    materia.getIdMateria(),
                    materia.getNombreMateria(),
                    materia.getAnio(),
                    materia.isEstado() ? "Activa" : "Inactiva");
        }
        System.out.println("-----------------------------------------------------------");
    }

    // Método para mostrar una sola materia
    private static void mostrarMateria(Materia materia) {
        System.out.println("ID: " + materia.getIdMateria());
        System.out.println("Nombre: " + materia.getNombreMateria());
        System.out.println("Año: " + materia.getAnio());
        System.out.println("Estado: " + (materia.isEstado() ? "Activa" : "Inactiva"));
    }
}
