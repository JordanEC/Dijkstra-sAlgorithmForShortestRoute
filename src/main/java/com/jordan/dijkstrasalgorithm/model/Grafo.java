package com.jordan.dijkstrasalgorithm.model;

import java.util.ArrayList;
import javax.swing.JLabel;
import com.jordan.dijkstrasalgorithm.view.InterfazGrafo;

/**
 *
 * @author Jordan
 */
public class Grafo {
    private InterfazGrafo interfazGrafo;                    //Objeto de la interfaz del grafo
    private Boolean[][] matrizAdyacencia;                   //Matriz boleana de adyancencia
    private ArrayList <Integer> ruta = new ArrayList <>();  //Ruta mas corta desde vertice origen al destino
    private final int INFINITO = 9999999;                   //Representa el peso de los vertices que no están conectados
    private int vertices;                                   //Numero de vertices del grafo
    private int verticeAMarcar;                             //vertice que se maracará
    private int verticePesoMenor;                           //Vertice sin marcar y con menor peso
    private int[] distancia;                                //Peso de cada vertice desde el origen
    int[] verticeAnterior;
    private boolean[] marcado;                              //Estado de los vertices (visitado y no visitado)
   
    public Grafo(Boolean[][] matrizAdyacencia, int vertices, InterfazGrafo interfazGrafo) {
        this.matrizAdyacencia = matrizAdyacencia;
        this.vertices = vertices;
        this.interfazGrafo = interfazGrafo;         //Copia el objeto intefaz grafo a un objeto local
        distancia = new int[vertices];              //Crea con el numero de vertices
        marcado = new boolean[vertices];            //Crea con el numero de vertices
        verticeAnterior = new int[vertices];        //Almacena para cada vertice el vertice anterior con el menor peso
    }

    public void dijkstra(JLabel jLabel1){
        for (int i = 0; i<marcado.length;i++){       //Inicializa las matrices
            marcado[i] = false;
            distancia[i] = INFINITO;
            verticeAnterior[i] = -1;
        }    
        
        distancia[interfazGrafo.getNodoInicio()-1] = 0;
        marcado[interfazGrafo.getNodoInicio()-1] = true;
        int verticeActual;                          //Vertice que se esta evaluando
        verticeActual = interfazGrafo.getNodoInicio() -1;
        
        while(marcado[interfazGrafo.getNodoLlegada() - 1] == false){    //Mientras no se haya llegado al vertice de llegada
            for (int i=0; i< vertices; i++){    //Verifica los pesos de todos los vertices
                if (marcado[i]==false)          //Si no se ha visitado el vertice
                    //Si la distancia del vertice es mayor que la anterior mas la que se esta evaluando
                    if (distancia[i] > distancia[verticeActual] + interfazGrafo.getMatrizPesos()[verticeActual][i]){
                        //Copia la distancia que se esta evaluando
                        distancia[i] = distancia[verticeActual] + interfazGrafo.getMatrizPesos()[verticeActual][i];
                        verticeAnterior[i] = verticeActual;             //Ajusta el nuevo vertice
                    }
            }
                
            verticeAMarcar = getVertConPesoMin();       //Obtiene el vertice con menor peso y que no haya sido visitado
            marcado[verticeAMarcar] = true;             //Marca el vertice obtenido
            verticeActual = verticeAMarcar;             //Actualiza el vertice actual con el que se acaba de marcar
            
        }
        //Copia la ruta de vertices más corta desde el vertice de llegada hasta el vertice de inicio
        int i = interfazGrafo.getNodoLlegada() - 1;     //Inicializa con el vertice de llegada
        ruta.add(i);                                    //Copia el vertice actual al arreglo
        
        do {
            i = verticeAnterior[i];                         //Actualiza con el vertice anterior de menor peso
            ruta.add(i);                                    //Agrega el vertice al arreglo
        } while(i != interfazGrafo.getNodoInicio() - 1);    //mientras no se haya agregado el vertice de inicio
        //La ruta se copió inversamente (vertice llegada al vertice inicio)
        invierteRuta();     //Invierte la ruta (vertice inicio al vertice llegada)
        //Actuliza lainterfaz del grafo mostrando la ruta más corta
        this.interfazGrafo.actualizarRutaGrafo(ruta, distancia[interfazGrafo.getNodoLlegada() - 1], jLabel1);
    }
    
    private void invierteRuta(){
        ArrayList<Integer> buffer = new ArrayList<>();
        for (int i = ruta.size()-1; i>= 0; i--)     //copia los elementos desde la ultima posicion hasta la primera
            buffer.add(ruta.get(i));  
            
        ruta = buffer;              //Copia la ruta invertida
        buffer = null;
    }
    
    private int getVertConPesoMin(){
        verticePesoMenor = 0;
        int menor = INFINITO;
        for (int i = 0; i < vertices; i++){                     //Recorre los vertices
            if (marcado[i] == false && menor > distancia[i]){   //Si el vertice esta sin marcar y su peso es el menor 
                verticePesoMenor = i;                           //Nuevo vertice que tiene un peso menor
                menor = distancia[i];                           //Acualiza el menor peso
            }
        }
        return verticePesoMenor;
    }
}