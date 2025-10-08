/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import modelos.Materia;
import persistencia.MateriaData;
import java.util.List;

public class MateriaController {
    private MateriaData materiaBD;

    public MateriaController() {
        this.materiaBD = new MateriaData();
    }

    // Guardar nueva materia con validación
    public String guardarMateria(String nombreMateria, int anio) {
        // Validaciones
        if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
            return "El nombre de la materia no puede estar vacío";
        }
        if (anio < 1 || anio > 5) {
            return "El año debe estar entre 1 y 5";
        }

        // Crear y guardar materia
        Materia materia = new Materia(nombreMateria, anio, true);
        materiaBD.insertarMateria(materia);

        return "Materia guardada correctamente";
    }

    // Obtener todas las materias
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaBD.listarMaterias();
    }

    // Obtener solo materias activas
    public List<Materia> obtenerMateriasActivas() {
        return materiaBD.listarMateriasActivas();
    }

    // Obtener materias por año
    public List<Materia> obtenerMateriasPorAnio(int anio) {
        if (anio < 1 || anio > 5) {
            return null;
        }
        return materiaBD.listarMateriasPorAnio(anio);
    }

    // Buscar materia por ID
    public Materia buscarMateriaPorId(int idMateria) {
        return materiaBD.buscarMateriaPorId(idMateria);
    }

    // Actualizar materia con validación
    public String actualizarMateria(Materia materia) {
        // Validaciones
        if (materia == null) {
            return "La materia no puede ser nula";
        }
        if (materia.getNombreMateria() == null || materia.getNombreMateria().trim().isEmpty()) {
            return "El nombre de la materia no puede estar vacío";
        }
        if (materia.getAnio() < 1 || materia.getAnio() > 5) {
            return "El año debe estar entre 1 y 5";
        }

        materiaBD.actualizarMateria(materia);
        return "Materia actualizada correctamente";
    }

    // Dar de baja materia (cambiar estado a inactivo)
    public String darDeBajaMateria(int idMateria) {
        Materia materia = materiaBD.buscarMateriaPorId(idMateria);
        if (materia == null) {
            return "No se encontró la materia con ID: " + idMateria;
        }

        materiaBD.cambiarEstadoMateria(idMateria, false);
        return "Materia dada de baja correctamente";
    }

    // Dar de alta materia (reactivar)
    public String darDeAltaMateria(int idMateria) {
        Materia materia = materiaBD.buscarMateriaPorId(idMateria);
        if (materia == null) {
            return "No se encontró la materia con ID: " + idMateria;
        }

        materiaBD.cambiarEstadoMateria(idMateria, true);
        return "Materia dada de alta correctamente";
    }

    // Eliminar materia físicamente (usar con precaución)
    public String eliminarMateria(int idMateria) {
        Materia materia = materiaBD.buscarMateriaPorId(idMateria);
        if (materia == null) {
            return "No se encontró la materia con ID: " + idMateria;
        }

        materiaBD.eliminarMateria(idMateria);
        return "Materia eliminada correctamente";
    }
}

