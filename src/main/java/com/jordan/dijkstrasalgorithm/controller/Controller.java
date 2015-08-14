/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jordan.dijkstrasalgorithm.controller;

import com.jordan.dijkstrasalgorithm.model.Grafo;
import com.jordan.dijkstrasalgorithm.view.InterfazGrafo;
import com.jordan.dijkstrasalgorithm.view.VentanaMatriz;
import com.jordan.dijkstrasalgorithm.view.View;
import static java.awt.Color.BLUE;
import static java.awt.Color.GRAY;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 *
 * @author Jordan
 */
public class Controller {
    View view;
    VentanaMatriz ventanaMatriz;
    InterfazGrafo interfazGrafo;
    Grafo grafo;
    int vertices;

    public Controller(View v) {
        view = v;
        initialize();
    }

    private void initialize() {
        ButtonsListener buttonsListener = new ButtonsListener();
        view.getBtn1().addActionListener(buttonsListener);
        view.getBtn2().addActionListener(buttonsListener);
        view.getBtn3().addActionListener(buttonsListener);
        view.getBtn4().addActionListener(buttonsListener);
        view.getBtn5().addActionListener(buttonsListener);
        view.getBtn6().addActionListener(buttonsListener);
        view.getBtn7().addActionListener(buttonsListener);
        view.getBtn8().addActionListener(buttonsListener);
    }

    protected void btn1Actions() {
        try {
            //Si seleccionó opción 1
            vertices = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de vértices:", "Cantidad de vértices", QUESTION_MESSAGE));
            if (vertices < 2) {
                throw new Exception();
            }

            view.getBtn1().setEnabled(false);
            view.getBtn1().setForeground(GRAY);
            view.getBtn2().setForeground(BLUE);
            view.getBtn2().setEnabled(true);

        } catch (NumberFormatException caracteres) {
            JOptionPane.showMessageDialog(null, "No se permiten caracteres.", null, JOptionPane.ERROR_MESSAGE);
        } catch (Exception numeroInvaido) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número mayor a 1.", null, JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void btn2Actions() {
        //Si seleccionó opción 2
        this.ventanaMatriz = new VentanaMatriz(new JDialog(), true, vertices);
        this.ventanaMatriz.setLocationRelativeTo(null);
        this.ventanaMatriz.setTitle("Matriz de adyacencia");
        this.ventanaMatriz.setVisible(true);
        view.getBtn2().setEnabled(false);
        view.getBtn2().setForeground(GRAY);
        view.getBtn3().setForeground(BLUE);
        view.getBtn3().setEnabled(true);
    }

    protected void btn3Actions() {
        view.getLblScreen().setText("<HTML><body>► Arrastre y coloque los vértices a su gusto.<br>► Para ingresar un peso presione "
                + "click derecho sobre una arista.<br>► El peso debe ser un valor entero mayor a 0.<br>"
                + "► Presione el botón \"guardar datos\" después de ingresar todos los pesos.</body></HTML>");
        interfazGrafo = new InterfazGrafo(view.getjPanel1(), ventanaMatriz.getDatosMatriz(),
                ventanaMatriz.getEncabezadoMatriz(), view.getBtn4(), view.getLblScreen());   //Crea la interfaz del grafo
        view.getBtn3().setForeground(GRAY);
        view.getBtn3().setEnabled(false);
    }

    protected void btn4Actions() {
        //Si seleccionó opción 4
        view.getLblScreen().setText("<HTML><body>► Presione click derecho sobre un vértice para seleccionar el inicio.</body></HTML>");
        this.interfazGrafo.setNodo("inicio", view.getBtn5());     //Asigna el vertice de partida
        view.getBtn4().setForeground(GRAY);
        view.getBtn4().setEnabled(false);
    }

    protected void btn5Actions() {
        //Si seleccionó opción 5
        view.getLblScreen().setText("<HTML><body>► Presione click derecho sobre un vértice para seleccionar el final.</body></HTML>");
        this.interfazGrafo.setNodo("llegada", view.getBtn6());    //Asigna el vertice de llegada
        view.getBtn5().setForeground(GRAY);
        view.getBtn5().setEnabled(false);
    }

    protected void btn6Actions() {
        //Si seleccionó opción 6
        grafo = new Grafo(ventanaMatriz.getDatosMatriz(), 
                ventanaMatriz.getCantidadVertices(), interfazGrafo);  //Crea el grafo de calculos
        grafo.dijkstra(view.getLblScreen());                    //Calcula la ruta más corta
        view.getBtn6().setForeground(GRAY);
        view.getBtn6().setEnabled(false);
        
    }

    protected void btn7Actions() {
        //Si seleccionó opción reiniciar
        ventanaMatriz = null;           //Marca..
        interfazGrafo = null;           //..objetos..
        grafo = null;                   //..para recoleccion de basura
        view.setVisible(false);              //
        System.gc();                    //invoca recolector de basura
        //Main.main(null);                //LLama la función principal para reiniciar
    }

    protected void btn8Actions() {
        //Si seleccionó opción salir
        System.exit(0);
    }

    class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(view.getBtn1())) {
                btn1Actions();
            }
            if (e.getSource().equals(view.getBtn2())) {
                btn2Actions();
            }
            if (e.getSource().equals(view.getBtn3())) {
                btn3Actions();
            }
            if (e.getSource().equals(view.getBtn4())) {
                btn4Actions();
            }
            if (e.getSource().equals(view.getBtn5())) {
                btn5Actions();
            }
            if (e.getSource().equals(view.getBtn6())) {
                btn6Actions();
            }
            if (e.getSource().equals(view.getBtn7())) {
                btn7Actions();
            }
            if (e.getSource().equals(view.getBtn8())) {
                btn8Actions();
            }
        }

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Controller(new View());
            }
        });
    }

}
