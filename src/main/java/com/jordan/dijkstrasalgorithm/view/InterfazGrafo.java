package com.jordan.dijkstrasalgorithm.view;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import static com.mxgraph.util.mxConstants.SHAPE_ELLIPSE;
import static com.mxgraph.util.mxConstants.STYLE_SHAPE;
import com.mxgraph.view.mxGraph;
import static java.awt.Color.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JPanel;

/**
 *
 * @author Jordan
 */
public class InterfazGrafo extends Thread{
    private mxGraph itfzGrafo = new mxGraph();          //Grafo
    private HashMap coleccionVertices = new HashMap();  //Mapa de vertices
    private HashMap coleccionAristas = new HashMap();   //Mapa de aristas
    private mxCell celda;                               //celda (vertice o arista)
    private mxCell celdaSeleccionada;                   //celda seleccionada (vertice o arista)
    private JPanel panel;
    private JLabel jLabel1;
    private Boolean[][] matrizAdyacencia;
    private Integer matrizPesos[][];
    private String[] encabezadoMatriz;
    private String identificador;                       //Identificador de la celda (vertice: "x", arista: "xy" y "yx")
    private int vertices;
    private int peso;
    private int verticeInicio = 0;
    private int verticeLlegada = 0;
    private JButton botonConfirmar = new JButton();
    private JButton jButton4 = new JButton();
    private mxGraphComponent componenteGrafo;            //Componente para el grafo
    private MouseAdapter accionMouse;                    //Accion que realizará el mouse
    
    public InterfazGrafo(JPanel panel, Boolean[][] matrizAdyacencia, String[] encabezadoMatriz, JButton jButton4, JLabel jLabel1){
        this.panel = panel;
        this.matrizAdyacencia = matrizAdyacencia;
        this.encabezadoMatriz = encabezadoMatriz;
        this.jButton4 = jButton4;
        this.jLabel1 = jLabel1;
        vertices = encabezadoMatriz.length;
        matrizPesos = new Integer[vertices][vertices];
        
        for (int i = 0; i < vertices; i++)          //Inicializa matriz de pesos
            for (int j = 0; j < vertices; j++)
                matrizPesos[i][j] = 9999999;
        
        iniciar();
    }
    private void iniciar(){
        
        componenteGrafo = new mxGraphComponent(itfzGrafo);      //Agrega el grafo al componente
        componenteGrafo.setSize(panel.getSize());           
        componenteGrafo.setBackground(RED);
        botonConfirmar.setText("Guardar datos");
        botonConfirmar.setForeground(BLUE);
        botonConfirmar.setSize(180, 25);
        panel.add(botonConfirmar);                              //Agrega el boton confirmar al panel
        botonConfirmar.setLocation(270, 361);
        
        panel.add(componenteGrafo);                             //Agrega el componente al panel
        agregarNodos();                                         //Genera los vertices que indicó el usuario
        enlazarNodos();                                         //Genera las aristas con base en la matriz de adyacencia
        
        componenteGrafo.getGraphControl().addMouseListener(accionMouse = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento){
                try{
                    if( evento.getButton() == MouseEvent.BUTTON2 || 
                            evento.getButton() == MouseEvent.BUTTON3){      //Si presionó click derecho
                        celdaSeleccionada = (mxCell) componenteGrafo.getCellAt(evento.getX(), evento.getY());   //Asigna el objeto que se presionó 
                        identificador = celdaSeleccionada.getId();      //Obtiene el identificador del objeto
                        
                        if (celdaSeleccionada != null){             //Si se hizo click sobre alguna celda (vertice o arista)
                            if (celdaSeleccionada.isEdge()){        //Si la celda es una arista
                                itfzGrafo.getModel().beginUpdate(); 
                                do {
                                    peso = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el peso de la arista:", "Peso de la arista", QUESTION_MESSAGE));
                                    if (peso < 1)
                                        JOptionPane.showMessageDialog(null, "No se permiten pesos menores a 1", null, JOptionPane.ERROR_MESSAGE);
                                } while(peso < 1);

                                celdaSeleccionada.setValue(peso);   //Asigna el peso a la arista
                                itfzGrafo.cellLabelChanged(celdaSeleccionada, peso, true);  //Agrega el peso a la etiqueta de la arista
                                coleccionAristas.put(identificador, celdaSeleccionada); //Actualiza la arista en el mapa de aristas
                                coleccionAristas.put(identificador.substring(1).concat(
                                        identificador.substring(0,1)), celdaSeleccionada);  //Actualiza la arista en el mapa de aristas (ambos ejes de la matriz)
                                itfzGrafo.getModel().endUpdate();
                                botonConfirmar.repaint();
                                componenteGrafo.refresh();                  //Actualiza componente
                                identificador = celdaSeleccionada.getId();  //Obtiene el identificador de la arista
                                //Copia el peso en la matriz.
                                matrizPesos[Integer.parseInt(identificador.substring(0, 1))][Integer.parseInt(identificador.substring(1))] = peso;
                                matrizPesos[Integer.parseInt(identificador.substring(1))][Integer.parseInt(identificador.substring(0, 1))] = peso;
                            }
                        }
                    }
                }
                catch(NumberFormatException errorNoNumeros){
                    JOptionPane.showMessageDialog(null, "Solo se permiten números en este campo", null, JOptionPane.ERROR_MESSAGE);
                }
                catch(NullPointerException e){
                    
                }
            }});
        
        botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConfirmarActionPerformed(evt);
            }
        });
    }
    
    private void botonConfirmarActionPerformed(java.awt.event.ActionEvent evt){
        String llave;
        mxCell cell;
        Object objetoCelda;
        //Verifica que se haya ingresado el peso para cada arista
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++){
                llave = i+""+j;
                cell = (mxCell) coleccionAristas.get(llave);
                if (cell != null){
                    objetoCelda = ((mxCell) cell).getValue();
                    if ( objetoCelda == null){
                        JOptionPane.showMessageDialog(null, "Debe ingresar el peso de todas la aristas.", null, JOptionPane.ERROR_MESSAGE);
                    return;
                    }
                }
            }
       }
       
       botonConfirmar.setVisible(false);
       jButton4.setForeground(BLUE);
       jButton4.setEnabled(true);
       jLabel1.setText("Seleccione una opción del menú principal.");
    }
            
    private void agregarNodos(){
        itfzGrafo.getModel().beginUpdate();
        celda = new mxCell();
        //Deshabilita opciones de edición de la interfaz del grafo
        itfzGrafo.setCellsDisconnectable(false);
        itfzGrafo.setConnectableEdges(false);
        itfzGrafo.setCellsBendable(false);
        itfzGrafo.setSplitEnabled(false);
        itfzGrafo.setCellsCloneable(false);
        itfzGrafo.setCellsEditable(false);
        itfzGrafo.setEdgeLabelsMovable(false);
        itfzGrafo.selectVertices(false);
        componenteGrafo.setConnectable(false);
        
        for(int i = 0; i < vertices; i++){  //Agrega vertices al grafo
            identificador = Integer.toString(i);
            celda = (mxCell) itfzGrafo.insertVertex(itfzGrafo.getDefaultParent(), identificador, encabezadoMatriz[i], 10, 10, 50, 50, STYLE_SHAPE+"="+SHAPE_ELLIPSE+";"+mxConstants.STYLE_FONTSTYLE+"="+mxConstants.FONT_BOLD+";");
            coleccionVertices.put(encabezadoMatriz[i], celda);  //copia al mapa de vertices
        }
        
        itfzGrafo.getModel().endUpdate();
    }
    
    private void enlazarNodos(){
        Object nodo1, nodo2;
        
        for (int i = 0; i < vertices; i++){
            for (int j = 0; j < vertices; j++){
                if (matrizAdyacencia[i][j] == true){                    //Si existe una arista en esa posicion
                    nodo1=coleccionVertices.get(encabezadoMatriz[i]);   //Obtiene el primer vertices
                    nodo2=coleccionVertices.get(encabezadoMatriz[j]);   //Obtiene el segundo vertices
                    identificador = i+""+j;                             //Genera el identificador para la arista
                    celda = (mxCell) itfzGrafo.insertEdge(itfzGrafo.getDefaultParent(), identificador, null, nodo1, nodo2, "startArrow=none;endArrow=none;strokeWidth=1;"
                            + "strokeColor=#2F4F4F;"+mxConstants.STYLE_FONTSTYLE+"="+mxConstants.FONT_BOLD+";"+mxConstants.STYLE_FONTSIZE+"=22"+";fontColor=blue");
                    coleccionAristas.put(identificador, celda);         //Agrega la arista al mapa de aristas
                }
            }
        }
    }
    
    public void setNodo(final String texto, final JButton botonSiguiente){
        componenteGrafo.getGraphControl().removeMouseListener(accionMouse); //Borra la accion que tenia asignada el mouse
        componenteGrafo.getGraphControl().addMouseListener(accionMouse = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento){
                if (evento.getButton() == MouseEvent.BUTTON2 || 
                        evento.getButton() == MouseEvent.BUTTON3){          //Si presionó click derecho
                    celdaSeleccionada = (mxCell) componenteGrafo.getCellAt(evento.getX(), evento.getY());   //Obtiene celda en la posicion que se hizo click
                    if (celdaSeleccionada != null)                  //Si se presionó una celda
                        if (celdaSeleccionada.isVertex()){          //Si la celda era un vertice
                            if ("inicio".equals(texto)){            //Si es el vertice de inicio
                                verticeInicio = Integer.parseInt(celdaSeleccionada.getValue().toString().substring(celdaSeleccionada.getValue().toString().length()-1));
                                componenteGrafo.getGraphControl().removeMouseListener(accionMouse); //Borra la accion del mouse
                                JOptionPane.showMessageDialog(null, "Se estableció el vértice "+verticeInicio+" como "+texto);
                                botonSiguiente.setEnabled(true);
                                botonSiguiente.setForeground(BLUE);
                                jLabel1.setText("Seleccione una opción del menú principal.");
                            }else {                     //Si es el vertice de llegada
                                verticeLlegada = Integer.parseInt(celdaSeleccionada.getValue().toString().substring(celdaSeleccionada.getValue().toString().length()-1));
                                if (verticeLlegada != 0 && verticeLlegada == verticeInicio){    //Si selecciona el mismo vertice que el de inicio
                                    JOptionPane.showMessageDialog(null, "Vértice inválido, seleccione otro", null, JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "Se estableció el vértice "+verticeLlegada+" como "+texto);
                                    botonSiguiente.setEnabled(true);
                                    botonSiguiente.setForeground(BLUE);
                                    jLabel1.setText("Seleccione una opción del menú principal.");
                                    componenteGrafo.getGraphControl().removeMouseListener(accionMouse);     //Borra accion del mouse
                                }
                            }
                                                        
                            itfzGrafo.setCellsEditable(true);
                            celdaSeleccionada.setStyle(celdaSeleccionada.getStyle()+";fillColor=green;fontColor=white");
                            itfzGrafo.setCellsEditable(false);
                            componenteGrafo.refresh();
                        }
                }
            }
        });
    }
    
    public void actualizarRutaGrafo(ArrayList<Integer> ruta, int sumaPesos, JLabel jLabel1){
        itfzGrafo.setCellsEditable(true);
        itfzGrafo.getModel().beginUpdate();
        
        for (int i = 0; i < ruta.size()-1; i++){                    //Recorre la ruta
            identificador = ruta.get(i)+""+ruta.get(i+1);           //Obtiene el identificador de cada arsita
            celda = (mxCell) coleccionAristas.get(identificador);   //Obtiene la arista del mapa
            celda.setStyle("startArrow=none;endArrow=none;strokeWidth=3;strokeColor=#FF4500;"+mxConstants.STYLE_FONTSTYLE+"="+mxConstants.FONT_BOLD+";"
                    +mxConstants.STYLE_FONTSIZE+"=22"+";fontColor=blue");   //Cambia el estilo de la arista a color rojo
            coleccionAristas.put(i, celda);                     //Guarda la arista con su nuevo estilo en el mapa
        }

        itfzGrafo.getModel().endUpdate();
        componenteGrafo.refresh();
        itfzGrafo.setCellsEditable(false);
        String rutaMasCorta;                        //Cadena para mostrar la ruta númericamente
        int nodo = ruta.get(0) + 1;                 //
        rutaMasCorta = Integer.toString(nodo);
        
        for (int i = 1; i < ruta.size(); i++){
            nodo = ruta.get(i) + 1;
            rutaMasCorta = rutaMasCorta+", "+Integer.toString(nodo);
        }
        jLabel1.setText("<HTML><body>Ruta más corta: "+rutaMasCorta+"<br>Peso total: "+sumaPesos+"</body></HTML>");
    }
        
    public int getNodoInicio(){
        return verticeInicio;
    }
    
    public int getNodoLlegada(){
        return verticeLlegada;
    }
    
    public Integer[][] getMatrizPesos(){
        return matrizPesos;
    }
}
