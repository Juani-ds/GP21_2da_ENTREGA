package controladores;

import modelos.Alumno;
import persistencia.AlumnoData;
import java.time.LocalDate;
import java.util.List;

public class AlumnoController {
    private AlumnoData alumnoBD;

    public AlumnoController() {
        this.alumnoBD = new AlumnoData();
    }

    // Guardar nuevo alumno con validación
    public String guardarAlumno(String nombre, String apellido, int dni, LocalDate fechaNacimiento) {
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            return "El apellido no puede estar vacío";
        }
        if (dni <= 0) {
            return "El DNI debe ser un número positivo";
        }
        if (fechaNacimiento == null) {
            return "La fecha de nacimiento no puede estar vacía";
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            return "La fecha de nacimiento no puede ser futura";
        }

        // Verificar si ya existe un alumno con ese DNI
        Alumno alumnoExistente = alumnoBD.buscarAlumnoPorDni(dni);
        if (alumnoExistente != null) {
            return "Ya existe un alumno con el DNI: " + dni;
        }

        // Crear y guardar alumno
        Alumno alumno = new Alumno(nombre, apellido, dni, fechaNacimiento, true);
        alumnoBD.insertarAlumno(alumno);

        return "Alumno guardado correctamente";
    }

    // Obtener todos los alumnos
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoBD.listarAlumnos();
    }

    // Obtener solo alumnos activos
    public List<Alumno> obtenerAlumnosActivos() {
        return alumnoBD.listarAlumnosActivos();
    }

    // Buscar alumno por ID
    public Alumno buscarAlumnoPorId(int idAlumno) {
        return alumnoBD.buscarAlumnoPorId(idAlumno);
    }

    // Buscar alumno por DNI
    public Alumno buscarAlumnoPorDni(int dni) {
        return alumnoBD.buscarAlumnoPorDni(dni);
    }

    // Actualizar alumno con validación
    public String actualizarAlumno(Alumno alumno) {
        // Validaciones
        if (alumno == null) {
            return "El alumno no puede ser nulo";
        }
        if (alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }
        if (alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            return "El apellido no puede estar vacío";
        }
        if (alumno.getDni() <= 0) {
            return "El DNI debe ser un número positivo";
        }
        if (alumno.getFechaNacimiento() == null) {
            return "La fecha de nacimiento no puede estar vacía";
        }

        alumnoBD.actualizarAlumno(alumno);
        return "Alumno actualizado correctamente";
    }

    // Dar de baja alumno (cambiar estado a inactivo)
    public String darDeBajaAlumno(int idAlumno) {
        Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            return "No se encontró el alumno con ID: " + idAlumno;
        }

        alumnoBD.cambiarEstadoAlumno(idAlumno, false);
        return "Alumno dado de baja correctamente";
    }

    // Dar de alta alumno (reactivar)
    public String darDeAltaAlumno(int idAlumno) {
        Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            return "No se encontró el alumno con ID: " + idAlumno;
        }

        alumnoBD.cambiarEstadoAlumno(idAlumno, true);
        return "Alumno dado de alta correctamente";
    }

    public String eliminarAlumno(int idAlumno) {
        Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            return "No se encontró el alumno con ID: " + idAlumno;
        }

        alumnoBD.eliminarAlumno(idAlumno);
        return "Alumno eliminado correctamente";
    }
}
