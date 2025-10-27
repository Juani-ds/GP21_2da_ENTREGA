package modelos;
/**
 *
 * @author  Nahuel Guerra
 */
public class Inscripcion {
    private int idInscripto;
    private Integer nota;
    private Alumno alumno;
    private Materia materia;

    // Constructor vacío
    public Inscripcion() {}

    // Constructor completo
    public Inscripcion(int idInscripto, Integer nota, Alumno alumno, Materia materia) {
        this.idInscripto = idInscripto;
        this.nota = nota;
        this.alumno = alumno;
        this.materia = materia;
    }

    // Constructor sin ID (para inserciones)
    public Inscripcion(Integer nota, Alumno alumno, Materia materia) {
        this.nota = nota;
        this.alumno = alumno;
        this.materia = materia;
    }

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
    }
    // Getters y Setters
    public int getIdInscripto() {
        return idInscripto;
    }

    public void setIdInscripto(int idInscripto) {
        this.idInscripto = idInscripto;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return alumno.toString() + " - " + materia.toString() +
               " (Nota: " + (nota != null ? nota : "Sin nota") + ")";
    }
}
