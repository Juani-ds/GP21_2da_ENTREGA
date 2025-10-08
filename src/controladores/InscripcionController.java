package controladores;

import modelos.Inscripcion;
import modelos.Alumno;
import modelos.Materia;
import persistencia.InscripcionData;
import persistencia.AlumnoData;
import persistencia.MateriaData;
import java.util.List;
import java.util.ArrayList;

public class InscripcionController {
    private InscripcionData inscripcionBD;
    private AlumnoData alumnoBD;
    private MateriaData materiaBD;

    public InscripcionController() {
        this.inscripcionBD = new InscripcionData();
        this.alumnoBD = new AlumnoData();
        this.materiaBD = new MateriaData();
    }

    // Inscribir alumno a materia con validaciones
    public String inscribirAlumno(int idAlumno, int idMateria) {
        // Validar que el alumno existe y está activo
        Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            return "No se encontró el alumno con ID: " + idAlumno;
        }
        if (!alumno.isEstado()) {
            return "El alumno no está activo";
        }

        // Validar que la materia existe y está activa
        Materia materia = materiaBD.buscarMateriaPorId(idMateria);
        if (materia == null) {
            return "No se encontró la materia con ID: " + idMateria;
        }
        if (!materia.isEstado()) {
            return "La materia no está activa";
        }

        // Validar que no exista ya la inscripción
        if (inscripcionBD.existeInscripcion(idAlumno, idMateria)) {
            return "El alumno ya está inscrito en esta materia";
        }

        // Crear y guardar inscripción
        Inscripcion inscripcion = new Inscripcion(null, alumno, materia);
        inscripcionBD.insertarInscripcion(inscripcion);

        return "Alumno inscrito correctamente";
    }

    // Cargar o actualizar nota
    public String cargarNota(int idInscripcion, int nota) {
        // Validar nota
        if (nota < 0 || nota > 10) {
            return "La nota debe estar entre 0 y 10";
        }

        // Verificar que existe la inscripción
        Inscripcion inscripcion = inscripcionBD.buscarInscripcionPorId(idInscripcion);
        if (inscripcion == null) {
            return "No se encontró la inscripción con ID: " + idInscripcion;
        }

        inscripcionBD.actualizarNota(idInscripcion, nota);
        return "Nota cargada correctamente";
    }

    // Obtener todas las inscripciones
    public List<Inscripcion> obtenerTodasLasInscripciones() {
        return inscripcionBD.listarInscripciones();
    }

    // Obtener inscripciones de un alumno
    public List<Inscripcion> obtenerInscripcionesPorAlumno(int idAlumno) {
        Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            return null;
        }
        return inscripcionBD.listarInscripcionesPorAlumno(idAlumno);
    }

    // Obtener inscripciones de una materia
    public List<Inscripcion> obtenerInscripcionesPorMateria(int idMateria) {
        Materia materia = materiaBD.buscarMateriaPorId(idMateria);
        if (materia == null) {
            return null;
        }
        return inscripcionBD.listarInscripcionesPorMateria(idMateria);
    }

    // Buscar inscripción por ID
    public Inscripcion buscarInscripcionPorId(int idInscripcion) {
        return inscripcionBD.buscarInscripcionPorId(idInscripcion);
    }

    // Anular inscripción (eliminar)
    public String anularInscripcion(int idInscripcion) {
        Inscripcion inscripcion = inscripcionBD.buscarInscripcionPorId(idInscripcion);
        if (inscripcion == null) {
            return "No se encontró la inscripción con ID: " + idInscripcion;
        }

        inscripcionBD.eliminarInscripcion(idInscripcion);
        return "Inscripción anulada correctamente";
    }

    // Verificar si un alumno está inscrito en una materia
    public boolean estaInscrito(int idAlumno, int idMateria) {
        return inscripcionBD.existeInscripcion(idAlumno, idMateria);
    }

    // Obtener alumnos aprobados de una materia (nota >= 6)
    public List<Inscripcion> obtenerAlumnosAprobados(int idMateria) {
        List<Inscripcion> todasLasInscripciones = inscripcionBD.listarInscripcionesPorMateria(idMateria);
        List<Inscripcion> aprobados = new ArrayList<>();
        
        for (Inscripcion i : todasLasInscripciones) {
            if (i.getNota() != null && i.getNota() >= 6) {
                aprobados.add(i);
            }
        }
        
        return aprobados;
    }

    // Obtener alumnos desaprobados de una materia (nota < 6)
    public List<Inscripcion> obtenerAlumnosDesaprobados(int idMateria) {
        List<Inscripcion> todasLasInscripciones = inscripcionBD.listarInscripcionesPorMateria(idMateria);
        List<Inscripcion> desaprobados = new ArrayList<>();
        
        for (Inscripcion i : todasLasInscripciones) {
            if (i.getNota() != null && i.getNota() < 6) {
                desaprobados.add(i);
            }
        }
        
        return desaprobados;
    }

    // Obtener inscripciones sin nota
    public List<Inscripcion> obtenerInscripcionesSinNota(int idMateria) {
        List<Inscripcion> todasLasInscripciones = inscripcionBD.listarInscripcionesPorMateria(idMateria);
        List<Inscripcion> sinNota = new ArrayList<>();
        
        for (Inscripcion i : todasLasInscripciones) {
            if (i.getNota() == null) {
                sinNota.add(i);
            }
        }
        
        return sinNota;
    }
}
