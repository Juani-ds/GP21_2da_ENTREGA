package persistencia;

import conector.Conexion;
import modelos.Alumno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoData {

    // INSERTAR
    public void insertarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno(nombre, apellido, dni, fecha_nacimiento, estado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getDni());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setInt(5, alumno.isEstado() ? 1 : 0);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt(1));
            }

            System.out.println("Alumno insertado correctamente.");

        } catch (SQLException ex) {
            System.out.println("Error insertando alumno: " + ex.getMessage());
        }
    }

    // LISTAR TODOS
    public List<Alumno> listarAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumno";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno(
                    rs.getInt("id_alumno"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getInt("estado") == 1
                );
                alumnos.add(alumno);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando alumnos: " + ex.getMessage());
        }

        return alumnos;
    }

    // LISTAR ALUMNOS ACTIVOS
    public List<Alumno> listarAlumnosActivos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumno WHERE estado = 1";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno(
                    rs.getInt("id_alumno"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getInt("estado") == 1
                );
                alumnos.add(alumno);
            }

        } catch (SQLException ex) {
            System.out.println("Error listando alumnos activos: " + ex.getMessage());
        }

        return alumnos;
    }

    // BUSCAR POR ID
    public Alumno buscarAlumnoPorId(int idAlumno) {
        String sql = "SELECT * FROM alumno WHERE id_alumno = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Alumno(
                    rs.getInt("id_alumno"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getInt("estado") == 1
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error buscando alumno: " + ex.getMessage());
        }

        return null;
    }

    // BUSCAR POR DNI
    public Alumno buscarAlumnoPorDni(int dni) {
        String sql = "SELECT * FROM alumno WHERE dni = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Alumno(
                    rs.getInt("id_alumno"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getInt("estado") == 1
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error buscando alumno por DNI: " + ex.getMessage());
        }

        return null;
    }

    // ACTUALIZAR
    public void actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE alumno SET nombre=?, apellido=?, dni=?, fecha_nacimiento=?, estado=? WHERE id_alumno=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getDni());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setInt(5, alumno.isEstado() ? 1 : 0);
            ps.setInt(6, alumno.getIdAlumno());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno actualizado correctamente.");
            } else {
                System.out.println("No se encontró alumno con ID: " + alumno.getIdAlumno());
            }

        } catch (SQLException ex) {
            System.out.println("Error actualizando alumno: " + ex.getMessage());
        }
    }

    // CAMBIAR ESTADO
    public void cambiarEstadoAlumno(int idAlumno, boolean estado) {
        String sql = "UPDATE alumno SET estado=? WHERE id_alumno=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, estado ? 1 : 0);
            ps.setInt(2, idAlumno);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Estado del alumno actualizado correctamente.");
            } else {
                System.out.println("No se encontró alumno con ID: " + idAlumno);
            }

        } catch (SQLException ex) {
            System.out.println("Error cambiando estado del alumno: " + ex.getMessage());
        }
    }

    // ELIMINAR
    public void eliminarAlumno(int idAlumno) {
        String sql = "DELETE FROM alumno WHERE id_alumno=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {

            ps.setInt(1, idAlumno);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No se encontró alumno con ID: " + idAlumno);
            }

        } catch (SQLException ex) {
            System.out.println("Error eliminando alumno: " + ex.getMessage());
        }
    }
}
