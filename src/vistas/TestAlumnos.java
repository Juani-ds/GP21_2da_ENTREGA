package vistas;

import controladores.AlumnoController;
import modelos.Alumno;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TestAlumnos {

    public static void main(String[] args) {
        AlumnoController alumnoController = new AlumnoController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("  TEST DE ALUMNOS - GRUPO 21");
        System.out.println("===========================================\n");

        // Ingresar alumnos del grupo
        ingresarAlumnosDelGrupo(alumnoController);

        // Mostrar todos los alumnos
        System.out.println("\n--- LISTADO DE TODOS LOS ALUMNOS ---");
        mostrarAlumnos(alumnoController.obtenerTodosLosAlumnos());

        // Menú de opciones
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===========================================");
            System.out.println("MENU DE OPCIONES:");
            System.out.println("1. Mostrar todos los alumnos");
            System.out.println("2. Mostrar solo alumnos activos");
            System.out.println("3. Buscar alumno por ID");
            System.out.println("4. Buscar alumno por DNI");
            System.out.println("5. Actualizar alumno");
            System.out.println("6. Dar de baja alumno (baja lógica)");
            System.out.println("7. Dar de alta alumno (alta lógica)");
            System.out.println("8. Eliminar alumno (borrado físico)");
            System.out.println("9. Ingresar nuevo alumno");
            System.out.println("0. Salir");
            System.out.println("===========================================");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- TODOS LOS ALUMNOS ---");
                    mostrarAlumnos(alumnoController.obtenerTodosLosAlumnos());
                    break;

                case 2:
                    System.out.println("\n--- ALUMNOS ACTIVOS ---");
                    mostrarAlumnos(alumnoController.obtenerAlumnosActivos());
                    break;

                case 3:
                    System.out.print("Ingrese ID del alumno: ");
                    int idBuscar = scanner.nextInt();
                    scanner.nextLine();
                    Alumno alumnoBuscado = alumnoController.buscarAlumnoPorId(idBuscar);
                    if (alumnoBuscado != null) {
                        System.out.println("\nAlumno encontrado:");
                        mostrarAlumno(alumnoBuscado);
                    } else {
                        System.out.println("No se encontró alumno con ID: " + idBuscar);
                    }
                    break;

                case 4:
                    System.out.print("Ingrese DNI del alumno: ");
                    int dniBuscar = scanner.nextInt();
                    scanner.nextLine();
                    Alumno alumnoporDni = alumnoController.buscarAlumnoPorDni(dniBuscar);
                    if (alumnoporDni != null) {
                        System.out.println("\nAlumno encontrado:");
                        mostrarAlumno(alumnoporDni);
                    } else {
                        System.out.println("No se encontró alumno con DNI: " + dniBuscar);
                    }
                    break;

                case 5:
                    System.out.print("Ingrese ID del alumno a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    Alumno alumnoActualizar = alumnoController.buscarAlumnoPorId(idActualizar);
                    if (alumnoActualizar != null) {
                        System.out.print("Nuevo nombre (actual: " + alumnoActualizar.getNombre() + "): ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo apellido (actual: " + alumnoActualizar.getApellido() + "): ");
                        String nuevoApellido = scanner.nextLine();
                        System.out.print("Nuevo DNI (actual: " + alumnoActualizar.getDni() + "): ");
                        int nuevoDni = scanner.nextInt();
                        scanner.nextLine();

                        alumnoActualizar.setNombre(nuevoNombre);
                        alumnoActualizar.setApellido(nuevoApellido);
                        alumnoActualizar.setDni(nuevoDni);

                        String resultado = alumnoController.actualizarAlumno(alumnoActualizar);
                        System.out.println(resultado);
                    } else {
                        System.out.println("No se encontró alumno con ID: " + idActualizar);
                    }
                    break;

                case 6:
                    System.out.print("Ingrese ID del alumno a dar de baja: ");
                    int idBaja = scanner.nextInt();
                    scanner.nextLine();
                    String resultadoBaja = alumnoController.darDeBajaAlumno(idBaja);
                    System.out.println(resultadoBaja);
                    break;

                case 7:
                    System.out.print("Ingrese ID del alumno a dar de alta: ");
                    int idAlta = scanner.nextInt();
                    scanner.nextLine();
                    String resultadoAlta = alumnoController.darDeAltaAlumno(idAlta);
                    System.out.println(resultadoAlta);
                    break;

                case 8:
                    System.out.print("Ingrese ID del alumno a eliminar (¡CUIDADO! Borrado físico): ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("¿Está seguro? (S/N): ");
                    String confirmacion = scanner.nextLine();
                    if (confirmacion.equalsIgnoreCase("S")) {
                        String resultadoEliminar = alumnoController.eliminarAlumno(idEliminar);
                        System.out.println(resultadoEliminar);
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;

                case 9:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("DNI: ");
                    int dni = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                    String fechaStr = scanner.nextLine();
                    LocalDate fecha = LocalDate.parse(fechaStr);

                    String resultadoGuardar = alumnoController.guardarAlumno(nombre, apellido, dni, fecha);
                    System.out.println(resultadoGuardar);
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

    // Método para ingresar alumnos del grupo
    private static void ingresarAlumnosDelGrupo(AlumnoController alumnoController) {
        System.out.println("Ingresando alumnos del Grupo 21...\n");

        // Integrante 1
        String resultado1 = alumnoController.guardarAlumno(
                "Juan Ignacio",
                "Género",
                12345678,
                LocalDate.of(2000, 5, 15)
        );
        System.out.println("Integrante 1: " + resultado1);

        // Integrante 2
        String resultado2 = alumnoController.guardarAlumno(
                "Alaina Sabrina",
                "Reyes Fernandez",
                23456789,
                LocalDate.of(2001, 8, 20)
        );
        System.out.println("Integrante 2: " + resultado2);

        // Integrante 3
        String resultado3 = alumnoController.guardarAlumno(
                "Tiziana",
                "Pastrana",
                34567890,
                LocalDate.of(2003, 7, 19)
        );
        System.out.println("Integrante 3: " + resultado3);

        // Integrante 4
        String resultado4 = alumnoController.guardarAlumno(
                "Nahuel Emiliano",
                "Guerra",
                40722349,
                LocalDate.of(1997, 10, 21)
        );
        System.out.println("Integrante 4: " + resultado4);
    }

    // Método para mostrar lista de alumnos
    private static void mostrarAlumnos(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos para mostrar.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-15s %-15s %-12s %-15s %-10s%n",
                "ID", "Nombre", "Apellido", "DNI", "Fecha Nac.", "Estado");
        System.out.println("---------------------------------------------------------------");

        for (Alumno alumno : alumnos) {
            System.out.printf("%-5d %-15s %-15s %-12d %-15s %-10s%n",
                    alumno.getIdAlumno(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.getDni(),
                    alumno.getFechaNacimiento(),
                    alumno.isEstado() ? "Activo" : "Inactivo");
        }
        System.out.println("---------------------------------------------------------------");
    }

    // Método para mostrar un solo alumno
    private static void mostrarAlumno(Alumno alumno) {
        System.out.println("ID: " + alumno.getIdAlumno());
        System.out.println("Nombre: " + alumno.getNombre());
        System.out.println("Apellido: " + alumno.getApellido());
        System.out.println("DNI: " + alumno.getDni());
        System.out.println("Fecha de Nacimiento: " + alumno.getFechaNacimiento());
        System.out.println("Estado: " + (alumno.isEstado() ? "Activo" : "Inactivo"));
    }
}
