package com.jordan.dijkstrasalgorithm.view;

import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jordan
 */
public class VentanaMatriz extends javax.swing.JDialog {

    private Boolean[][] datosMatriz;        //Matriz de adyacencia
    private String[] encabezadoMatriz;      //Encabezado de vertices
    
    /**
     * Creates new form VentanaMatriz
     * @param parent
     * @param modal
     * @param vertices
     */
    
    public VentanaMatriz(JDialog parent, boolean modal, int vertices) {
        super(parent, modal);
        initComponents();
        
        datosMatriz = new Boolean[vertices][vertices];
        encabezadoMatriz = new String[vertices];
        
        for (int i=0;i<encabezadoMatriz.length;i++)     //Llena encabezado
            encabezadoMatriz[i] = "Vértice "+(i+1);
            
        DefaultTableModel model = new DefaultTableModel(datosMatriz, encabezadoMatriz); //Genera el modelo de la tabla
        
        tablaEntrada.setModel(model);       //Asigna el modelo
        for (int i =0; i<vertices; i++)
            tablaEntrada.setValueAt(0, i, i);   //Llena la diagonal principal con 0
        
        tablaEntrada.setCellSelectionEnabled(false);
        tablaEntrada.setShowGrid(true);
        
        tablaEntrada.addMouseListener(new java.awt.event.MouseAdapter(){    //Agrega un listener
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento){     
            int fila = tablaEntrada.rowAtPoint(evento.getPoint());          //Fila presionada 
            int columna = tablaEntrada.columnAtPoint(evento.getPoint());    //Columna presionada
                        
            if ( evento.getButton() == MouseEvent.BUTTON1){                 //Si presionó click izquierdo
                if (fila != columna){                                       //Si no se presionó una celda de la diagonal principal
                    tablaEntrada.setValueAt(1, fila, columna);              //Asigna un
                    tablaEntrada.setValueAt(1, columna, fila);              //1 (True)
                }
            }
            if ( evento.getButton() == MouseEvent.BUTTON3 || 
                    evento.getButton() == MouseEvent.BUTTON2){      //Si presionó click derecho
                tablaEntrada.setValueAt(0, fila, columna);          //Asigna un
                tablaEntrada.setValueAt(0, columna, fila);          //0 (False)
            }
        }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEntrada = new javax.swing.JTable();
        botonGuardarMatriz = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        tablaEntrada.setToolTipText("");
        tablaEntrada.setGridColor(new java.awt.Color(51, 51, 255));
        jScrollPane1.setViewportView(tablaEntrada);

        botonGuardarMatriz.setForeground(new java.awt.Color(0, 0, 255));
        botonGuardarMatriz.setText("Guardar");
        botonGuardarMatriz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarMatrizActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("<HTML>Ingrese los datos de la matriz de adyacencia haciendo click en cada celda, utilice el MOUSE únicamente:<br> Click Izquierdo: Celda = 1 (True).<br> Click Derecho: Celda = 0 (False).<br>Presione \"Guardar\" al terminar.</HTML> ");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonGuardarMatriz, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(268, 268, 268))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonGuardarMatriz)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonGuardarMatrizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarMatrizActionPerformed
        //Si presionó botón guardar
        int ingresado, trueEnFila;
        try {
            //Comprueba que los datos sean válidos
            for (int i = 0; i < tablaEntrada.getRowCount(); i++){
                trueEnFila = 0;
                for (int j = 0;j<tablaEntrada.getColumnCount();j++){
                    ingresado = Integer.parseInt(tablaEntrada.getValueAt(i, j).toString());
                    if (ingresado != 1 && ingresado != 0)
                        throw new Exception("Datos inválidos");
                    
                    datosMatriz[i][j] = ingresado == 1;
                    
                    if (datosMatriz[i][j])
                        trueEnFila++;
                }
                if (trueEnFila == 0)
                    throw new Exception("Hay vértices sin aristas, no deje una fila(o columna) sin al menos un valor true");
            }
            
            this.setVisible(false);
        }
        catch(NullPointerException faltaDatos){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos de la matriz de adyacencia", null, JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception datosInvalidos ){
            if ("H".equals(datosInvalidos.getMessage().substring(0, 1)))
                JOptionPane.showMessageDialog(null, datosInvalidos.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Datos inválidos en alguna(s) de la(s) celda(s)", null, JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_botonGuardarMatrizActionPerformed

    public Boolean[][] getDatosMatriz(){
        return datosMatriz;
    }
    
    public String[] getEncabezadoMatriz(){
        return encabezadoMatriz;
    }
    
    public int getCantidadVertices(){
        return encabezadoMatriz.length;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardarMatriz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEntrada;
    // End of variables declaration//GEN-END:variables
}