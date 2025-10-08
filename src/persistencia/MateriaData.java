
package persistencia;
import conector.Conexion;
import modelos.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author tizia
 */
public class MateriaData {
    // INSERTAR
    public void insertarMateria(Materia materia) {
        String sql = "INSERT INTO materia(nombre_materia, anio, estado) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, materia.getNombreMateria());
            ps.setInt(2, materia.getAnio());
            ps.setInt(3, materia.isEstado() ? 1 : 0);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                materia.setIdMateria(rs.getInt(1));
            }

            System.out.println("Materia insertada correctamente.");

        } catch (SQLException ex) {
            System.out.println("Error insertando materia: " + ex.getMessage());
        }
    }

    // LISTAR TODAS
    public List<Materia> listarMaterias() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materia";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Materia materia = new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre_materia"),
                    rs.getInt("anio"),
                    rs.getInt("estado") == 1
                );
                materias.add(materia);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando materias: " + ex.getMessage());
        }

        return materias;
    }

    // LISTAR MATERIAS ACTIVAS
    public List<Materia> listarMateriasActivas() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE estado = 1";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Materia materia = new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre_materia"),
                    rs.getInt("anio"),
                    rs.getInt("estado") == 1
                );
                materias.add(materia);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando materias activas: " + ex.getMessage());
        }

        return materias;
    }

    // BUSCAR POR ID
    public Materia buscarMateriaPorId(int idMateria) {
        String sql = "SELECT * FROM materia WHERE id_materia=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre_materia"),
                    rs.getInt("anio"),
                    rs.getInt("estado") == 1
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error buscando materia: " + ex.getMessage());
        }

        return null;
    }

    // LISTAR POR AÑO
    public List<Materia> listarMateriasPorAnio(int anio) {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE anio = ? AND estado = 1";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, anio);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia materia = new Materia(
                    rs.getInt("id_materia"),
                    rs.getString("nombre_materia"),
                    rs.getInt("anio"),
                    rs.getInt("estado") == 1
                );
                materias.add(materia);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando materias por año: " + ex.getMessage());
        }

        return materias;
    }

    // ACTUALIZAR
    public void actualizarMateria(Materia materia) {
        String sql = "UPDATE materia SET nombre_materia=?, anio=?, estado=? WHERE id_materia=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            ps.setString(1, materia.getNombreMateria());
            ps.setInt(2, materia.getAnio());
            ps.setInt(3, materia.isEstado() ? 1 : 0);
            ps.setInt(4, materia.getIdMateria());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Materia actualizada correctamente.");
            } else {
                System.out.println("No se encontró materia con ID: " + materia.getIdMateria());
            }

        } catch (SQLException ex) {
            System.out.println("Error actualizando materia: " + ex.getMessage());
        }
    }

    // CAMBIAR ESTADO
    public void cambiarEstadoMateria(int idMateria, boolean estado) {
        String sql = "UPDATE materia SET estado=? WHERE id_materia=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, estado ? 1 : 0);
            ps.setInt(2, idMateria);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Estado de la materia actualizado correctamente.");
            } else {
                System.out.println("No se encontró materia con ID: " + idMateria);
            }

        } catch (SQLException ex) {
            System.out.println("Error cambiando estado de la materia: " + ex.getMessage());
        }
    }

    // ELIMINAR
    public void eliminarMateria(int idMateria) {
        String sql = "DELETE FROM materia WHERE id_materia=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            ps.setInt(1, idMateria);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Materia eliminada correctamente.");
            } else {
                System.out.println("No se encontró materia con ID: " + idMateria);
            }

        } catch (SQLException ex) {
            System.out.println("Error eliminando materia: " + ex.getMessage());
        }
    }
}
