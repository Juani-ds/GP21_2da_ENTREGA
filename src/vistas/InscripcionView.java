/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author MSI
 */
public class InscripcionView extends javax.swing.JInternalFrame {

    private persistencia.AlumnoData alumnoData;
    private persistencia.MateriaData materiaData;
    private persistencia.InscripcionData inscripcionData;
    private List<modelos.Alumno> listaAlumnos;
    private List<modelos.Materia> listaMaterias;
    /**
     * Creates new form InscripcionView
     */
    
    
    public InscripcionView() {
        
        initComponents();
        alumnoData = new persistencia.AlumnoData();
        materiaData = new persistencia.MateriaData();
        inscripcionData = new persistencia.InscripcionData();
        configurarTabla();
        cargarAlumnos();
        configurarEventos();
        jRadioButtonNoInscripto.setSelected(true);
    }

    private void configurarTabla() {
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Materia", "Año", "Estado"}
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    
    private void cargarAlumnos() {
        try {
            jComboBox1.removeAllItems();
            listaAlumnos = alumnoData.listarAlumnosActivos();

            if (listaAlumnos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay alumnos activos registrados");
                return;
            }

            for (modelos.Alumno alumno : listaAlumnos) {
                jComboBox1.addItem(alumno.toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar alumnos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void configurarEventos() {
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarMaterias();
            }
        });
        jRadioButtonNoInscripto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarMaterias();
            }
        });
    }
    
    private void cargarMaterias() {
        try {
            int indexAlumno = jComboBox1.getSelectedIndex();
            if (indexAlumno == -1) {
                return;
            }

            modelos.Alumno alumnoSeleccionado = listaAlumnos.get(indexAlumno);
            javax.swing.table.DefaultTableModel modelo = 
                (javax.swing.table.DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);

            if (jRadioButtonInscripto.isSelected()) {
                cargarMateriasInscriptas(alumnoSeleccionado, modelo);
            } else {
                cargarMateriasNoInscriptas(alumnoSeleccionado, modelo);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar materias: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarMateriasInscriptas(modelos.Alumno alumno, javax.swing.table.DefaultTableModel modelo) {
        try {
            List<modelos.Inscripcion> inscripciones = 
                inscripcionData.listarInscripcionesPorAlumno(alumno.getIdAlumno());

            if (inscripciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El alumno no está inscripto en ninguna materia");
                return;
            }

            for (modelos.Inscripcion insc : inscripciones) {
                modelo.addRow(new Object[]{
                    insc.getMateria().getIdMateria(),
                    insc.getMateria().getNombreMateria(),
                    insc.getMateria().getAnio(),
                    insc.getMateria().isEstado() ? "Activa" : "Inactiva"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar materias inscriptas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cargarMateriasNoInscriptas(modelos.Alumno alumno, javax.swing.table.DefaultTableModel modelo) {
        try {
            List<modelos.Materia> todasLasMaterias = materiaData.listarMateriasActivas();
            if (todasLasMaterias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay materias activas disponibles");
                return;
            }
            for (modelos.Materia materia : todasLasMaterias) {
                boolean yaInscripto = inscripcionData.existeInscripcion(
                    alumno.getIdAlumno(), 
                    materia.getIdMateria()
                );
                if (!yaInscripto) {
                    modelo.addRow(new Object[]{
                        materia.getIdMateria(),
                        materia.getNombreMateria(),
                        materia.getAnio(),
                        "Disponible"
                    });
                }
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "El alumno ya está inscripto en todas las materias activas");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar materias disponibles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroupIscrito = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jRadioButtonInscripto = new javax.swing.JRadioButton();
        jRadioButtonNoInscripto = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BtnInscribir = new javax.swing.JButton();
        BtnAnularSuscricion = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        buttonGroupIscrito.add(jRadioButtonInscripto);
        buttonGroupIscrito.add(jRadioButtonNoInscripto);

        jLabel1.setText("FORMULARIO DE INSCRIPCION");

        jLabel2.setText("Seleccione un Alumno:");

        jLabel3.setText("LISTADO DE MATERIAS");

        jRadioButtonInscripto.setText("Materia inscriptas");
        jRadioButtonInscripto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInscriptoActionPerformed(evt);
            }
        });

        jRadioButtonNoInscripto.setText("Materia no inscriptas");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Materias"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BtnInscribir.setText("Inscribir");
        BtnInscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInscribirActionPerformed(evt);
            }
        });

        BtnAnularSuscricion.setText("Anular inscripcion");
        BtnAnularSuscricion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnularSuscricionActionPerformed(evt);
            }
        });

        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonInscripto)
                                .addGap(39, 39, 39)
                                .addComponent(jRadioButtonNoInscripto))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnInscribir)
                .addGap(18, 18, 18)
                .addComponent(BtnAnularSuscricion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnSalir)
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonInscripto)
                    .addComponent(jRadioButtonNoInscripto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnInscribir)
                    .addComponent(BtnAnularSuscricion)
                    .addComponent(BtnSalir))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonInscriptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInscriptoActionPerformed
        cargarMaterias();
    }//GEN-LAST:event_jRadioButtonInscriptoActionPerformed

    private void BtnAnularSuscricionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnularSuscricionActionPerformed
        try {
            int indexAlumno = jComboBox1.getSelectedIndex();
            if (indexAlumno == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno");
                return;
            }
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una materia de la tabla");
                return;
            }
            if (jRadioButtonNoInscripto.isSelected()) {
                JOptionPane.showMessageDialog(this, 
                    "Cambie a 'Materias inscriptas' para anular una inscripción");
                return;
            }

            modelos.Alumno alumnoSeleccionado = listaAlumnos.get(indexAlumno);
            int idMateria = (int) jTable1.getValueAt(filaSeleccionada, 0);
            String nombreMateria = (String) jTable1.getValueAt(filaSeleccionada, 1);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea anular la inscripción del alumno en '" + nombreMateria + "'?",
                "Confirmar anulación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
            List<modelos.Inscripcion> inscripciones = 
                inscripcionData.listarInscripcionesPorAlumno(alumnoSeleccionado.getIdAlumno());

            for (modelos.Inscripcion insc : inscripciones) {
                if (insc.getMateria().getIdMateria() == idMateria) {
                    inscripcionData.eliminarInscripcion(insc.getIdInscripto());
                    JOptionPane.showMessageDialog(this, "Inscripción anulada correctamente");
                    cargarMaterias();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No se pudo encontrar la inscripción");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al anular inscripción: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_BtnAnularSuscricionActionPerformed

    private void BtnInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInscribirActionPerformed
        try {
            int indexAlumno = jComboBox1.getSelectedIndex();
            if (indexAlumno == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno");
                return;
            }
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una materia de la tabla");
                return;
            }
            if (jRadioButtonInscripto.isSelected()) {
                JOptionPane.showMessageDialog(this, 
                    "Cambie a 'Materias no inscriptas' para inscribir al alumno");
                return;
            }
            modelos.Alumno alumnoSeleccionado = listaAlumnos.get(indexAlumno);
            int idMateria = (int) jTable1.getValueAt(filaSeleccionada, 0);
            modelos.Inscripcion nuevaInscripcion = new modelos.Inscripcion(alumnoSeleccionado,  materiaData.buscarMateriaPorId(idMateria));
            inscripcionData.insertarInscripcion(nuevaInscripcion);
            JOptionPane.showMessageDialog(this, "¡Alumno inscripto exitosamente en la materia!");
            cargarMaterias();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al inscribir: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_BtnInscribirActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_BtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnularSuscricion;
    private javax.swing.JButton BtnInscribir;
    private javax.swing.JButton BtnSalir;
    private javax.swing.ButtonGroup buttonGroupIscrito;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JRadioButton jRadioButtonInscripto;
    private javax.swing.JRadioButton jRadioButtonNoInscripto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
