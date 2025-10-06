package modelos;

import java.time.LocalDate;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaNacimiento;
    private boolean estado;

    // Constructor vacío
    public Alumno() {}

    // Constructor completo
    public Alumno(int idAlumno, String nombre, String apellido, int dni, LocalDate fechaNacimiento, boolean estado) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    // Constructor sin ID (para inserciones)
    public Alumno(String nombre, String apellido, int dni, LocalDate fechaNacimiento, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (DNI: " + dni + ")";
    }
}
