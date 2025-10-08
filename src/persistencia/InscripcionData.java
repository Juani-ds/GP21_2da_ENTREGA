
package persistencia;
import conector.Conexion;
import modelos.Inscripcion;
import modelos.Alumno;
import modelos.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author tizia
 */
public class InscripcionData {
    private AlumnoData alumnoBD = new AlumnoData();
    private MateriaData materiaBD = new MateriaData();

    // INSERTAR
    public void insertarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion(id_alumno, id_materia, nota) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inscripcion.getAlumno().getIdAlumno());
            ps.setInt(2, inscripcion.getMateria().getIdMateria());

            if (inscripcion.getNota() != null) {
                ps.setInt(3, inscripcion.getNota());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inscripcion.setIdInscripto(rs.getInt(1));
            }

            System.out.println("Inscripción insertada correctamente.");

        } catch (SQLException ex) {
            System.out.println("Error insertando inscripción: " + ex.getMessage());
        }
    }

    // LISTAR TODAS
    public List<Inscripcion> listarInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.id_inscripto, i.nota, i.id_alumno, i.id_materia " +
                     "FROM inscripcion i";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = alumnoBD.buscarAlumnoPorId(rs.getInt("id_alumno"));
                Materia materia = materiaBD.buscarMateriaPorId(rs.getInt("id_materia"));

                Inscripcion inscripcion = new Inscripcion(
                    rs.getInt("id_inscripto"),
                    (Integer) rs.getObject("nota"),
                    alumno,
                    materia
                );
                inscripciones.add(inscripcion);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando inscripciones: " + ex.getMessage());
        }

        return inscripciones;
    }

    // LISTAR INSCRIPCIONES POR ALUMNO
    public List<Inscripcion> listarInscripcionesPorAlumno(int idAlumno) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.id_inscripto, i.nota, i.id_materia " +
                     "FROM inscripcion i " +
                     "WHERE i.id_alumno = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            Alumno alumno = alumnoBD.buscarAlumnoPorId(idAlumno);

            while (rs.next()) {
                Materia materia = materiaBD.buscarMateriaPorId(rs.getInt("id_materia"));

                Inscripcion inscripcion = new Inscripcion(
                    rs.getInt("id_inscripto"),
                    (Integer) rs.getObject("nota"),
                    alumno,
                    materia
                );
                inscripciones.add(inscripcion);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando inscripciones del alumno: " + ex.getMessage());
        }

        return inscripciones;
    }

    // LISTAR ALUMNOS POR MATERIA
    public List<Inscripcion> listarInscripcionesPorMateria(int idMateria) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT i.id_inscripto, i.nota, i.id_alumno " +
                     "FROM inscripcion i " +
                     "WHERE i.id_materia = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();

            Materia materia = materiaBD.buscarMateriaPorId(idMateria);

            while (rs.next()) {
                Alumno alumno = alumnoBD.buscarAlumnoPorId(rs.getInt("id_alumno"));

                Inscripcion inscripcion = new Inscripcion(
                    rs.getInt("id_inscripto"),
                    (Integer) rs.getObject("nota"),
                    alumno,
                    materia
                );
                inscripciones.add(inscripcion);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando inscripciones de la materia: " + ex.getMessage());
        }

        return inscripciones;
    }

    // BUSCAR POR ID
    public Inscripcion buscarInscripcionPorId(int idInscripcion) {
        String sql = "SELECT i.id_inscripto, i.nota, i.id_alumno, i.id_materia " +
                     "FROM inscripcion i WHERE i.id_inscripto = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idInscripcion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Alumno alumno = alumnoBD.buscarAlumnoPorId(rs.getInt("id_alumno"));
                Materia materia = materiaBD.buscarMateriaPorId(rs.getInt("id_materia"));

                return new Inscripcion(
                    rs.getInt("id_inscripto"),
                    (Integer) rs.getObject("nota"),
                    alumno,
                    materia
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error buscando inscripción: " + ex.getMessage());
        }

        return null;
    }

    // ACTUALIZAR NOTA
    public void actualizarNota(int idInscripcion, Integer nota) {
        String sql = "UPDATE inscripcion SET nota=? WHERE id_inscripto=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            if (nota != null) {
                ps.setInt(1, nota);
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setInt(2, idInscripcion);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Nota actualizada correctamente.");
            } else {
                System.out.println("No se encontró inscripción con ID: " + idInscripcion);
            }

        } catch (SQLException ex) {
            System.out.println("Error actualizando nota: " + ex.getMessage());
        }
    }

    // ELIMINAR
    public void eliminarInscripcion(int idInscripcion) {
        String sql = "DELETE FROM inscripcion WHERE id_inscripto=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            ps.setInt(1, idInscripcion);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Inscripción eliminada correctamente.");
            } else {
                System.out.println("No se encontró inscripción con ID: " + idInscripcion);
            }

        } catch (SQLException ex) {
            System.out.println("Error eliminando inscripción: " + ex.getMessage());
        }
    }

    // VERIFICAR SI YA EXISTE INSCRIPCIÓN
    public boolean existeInscripcion(int idAlumno, int idMateria) {
        String sql = "SELECT COUNT(*) as total FROM inscripcion WHERE id_alumno=? AND id_materia=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error verificando inscripción: " + ex.getMessage());
        }

        return false;
    }
}
