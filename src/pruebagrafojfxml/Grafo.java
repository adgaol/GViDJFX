/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adgao
 */
public class Grafo {
private HashMap<Integer,Nodo> nodos;
private HashSet<Nodo> nodosNotExec;
private FicheroXML ejemplo;
private double posXAnterior;
    public Grafo(FicheroXML xml) {
      nodos=new HashMap<>();
      this.ejemplo=xml;
      this.posXAnterior=(ejemplo.getNumNodos()-1)*50/2;
      nodosNotExec=new HashSet();
    }
    /**
     * insert one node
     * @param parent
     * parent of the node
     * @param panelPadre
     * ponel to draw the node
     * @param simbolo
     * symbol to the node
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @return the node inserted
     */
    public Nodo insertarNodo(Nodo parent,Pane panelPadre,String simbolo,Double posX,Double posY){
      
      
       Nodo nodo=new Nodo(simbolo);
       nodo.getRectangle().setFill(Color.RED);
       nodo.setParent(parent);
       nodo.setPosX(posX);
       nodo.getRectangle().setX(posX);
       nodo.setPosY(posY);
       nodo.getRectangle().setY(posY);
       Label label=new Label(simbolo);
       label.setLayoutX(posX+nodo.getRectangle().getWidth()/3);
       label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
       nodo.setLabel(label);
       nodos.put(nodos.size(), nodo);
       if(parent!=null){
          Line line=new Line(parent.getPosX()+parent.getRectangle().getWidth()/2, parent.getPosY()+parent.getRectangle().getHeight(), nodo.getPosX()+nodo.getRectangle().getWidth()/2,nodo.getPosY());
          nodo.setLine(line);
          panelPadre.getChildren().addAll(nodo.getRectangle(),line,label);
       }
       else
           panelPadre.getChildren().addAll(nodo.getRectangle(),label);
       
       return nodo; 
    }
    /**
     * insert one node
     * @param regla
     * rule to insert
     * @param panelPadre
     * ponel to draw the node
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @param hijo
     * simbol already inserted
     * @return the node inserted
     */
    public HashSet<Nodo> insertarNodoNotExec(Nodo parent,Pane panelPadre,Regla regla,Double posX,Double posY,Nodo hijo){
      HashSet<Nodo> nodesInserted=new HashSet<>();
    String[] simbolos=regla.getValor().split(" ");
    Nodo nodo=null;
    Double nPosX=posX;
    for(int i =1;i<simbolos.length;i++){
        if(!simbolos[i].equals(hijo.getSimbolo())){
            nodo=new Nodo(simbolos[i]);
            nodo.getRectangle().setFill(Color.RED);
            nodo.getRectangle().setOpacity(0.50);
            nodo.setParent(parent);
            nPosX+=nodo.getRectangle().getWidth()+10;
            nodo.getRectangle().setX(nPosX);
             nodo.setPosX(nPosX);
            nodo.setPosY(posY);
            nodo.getRectangle().setY(posY);
            Label label=new Label(simbolos[i]);
            
            label.setLayoutX(nPosX+nodo.getRectangle().getWidth()/3);
            label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
            nodo.setLabel(label);
            nodosNotExec.add(nodo);
            nodesInserted.add(nodo);
            hijo.getHermanos().put(simbolos[i], nodo);
            panelPadre.getChildren().addAll(nodo.getRectangle(),label);
        }
    }
       
       
       
       
       
       
           
       
       return nodesInserted; 
    }
    /**
     * remove one element of the tree
     * @param nodoElim
     * node to remove
     * @param panelPadre
     * panel from remove
     * @param number 
     * number of the node
     */
    public void eliminarNodo(Nodo nodoElim,Pane panelPadre,int number){
        
         panelPadre.getChildren().removeAll(nodoElim.getRectangle(),nodoElim.getLabel(),nodoElim.getLine());
         
         if(nodoElim.getParent()!=null){
            nodoElim.getParent().getChildren().remove(nodoElim);
            posXAnterior=nodos.get(number-1).getPosX();
         }
         
         nodos.remove(number-1);
    }
    /**
     * remove one element of the tree
     * @param nodoElim
     * node to remove
     * @param panelPadre
     * panel from remove
     * @param number 
     * number of the node
     */
    public Nodo eliminarNodoNotExec(Nodo nodoElim,Pane panelPadre){
        Nodo nodoNotExec=null;
        for(Nodo n:nodosNotExec){
            if((n.getParent()==nodoElim.getParent())&&(n.getSimbolo().equals(nodoElim.getSimbolo()))){
                nodoNotExec=n;
                
            }
        }
        if(nodoNotExec!=null){
         panelPadre.getChildren().removeAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
         nodos.remove(nodoNotExec);
        } 
         return nodoNotExec;
    }
      /**
     * remove a collection of elements of the tree
     * @param nodoElim
     * node to remove
     * @param panelPadre
     * panel from remove
     * @param number 
     * number of the node
     */
    public boolean eliminarNodoNotExec(Collection<Nodo> nodoElim,Pane panelPadre){
        for(Nodo n:nodoElim){
            eliminarNodoNotExec(n, panelPadre);
        }
         return true;
    }
    /**
     * move the nodes no executed
     * @param nodo 
     * new nodo exec
     */
    public void moveSiblings(Nodo nodo){
         Collection<Nodo> hermanos= nodo.getParent().getChildren().getFirst().getHermanos().values();
            for(Nodo n:hermanos){
                
                if (!nodo.getSimbolo().equals(n.getSimbolo())){
                    double distancia=n.getPosX()-nodo.getParent().getChildren().getFirst().getPosX();
                    n.getRectangle().setX(nodo.getPosX()+distancia);
                    n.getLabel().setLayoutX(distancia+nodo.getPosX()+n.getRectangle().getWidth()/3);
                }
            }
    }
    /**
     * build the tree to the solicited step
     * @param contador
     * initial step
     * @param pasoSolicitado
     * last step
     * @param panelPadre 
     * panel to draw
     */
    public void construir(int contador,int pasoSolicitado,Pane panelPadre ) {
        for(int i=contador;i<pasoSolicitado;i++){
            //if is the root
            if(i==0){
               Nodo raiz= insertarNodo(null, panelPadre, ejemplo.getListaPasos().get(contador).getElemento().split(" ")[0], ejemplo.getNumNodos()*50/2.0, 0.0);
               setPosXAnterior(0);
               
            }
            else{
                Nodo parent=this.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(i).getElemento().split(" ")[1]));
                
                //if is the first child
                if(parent.getChildren().isEmpty()){
                    Nodo hijo=insertarNodo(parent, panelPadre, ejemplo.getListaPasos().get(i).getElemento().split(" ")[0], /*(parent.getPosX()+parent.getRectangle().getWidth()/2)/2*/posXAnterior,(parent.getRectangle().getHeight()*2)+parent.getPosY());
                    
                    parent.getChildren().add(hijo);
                    moveSiblings(hijo);
                    eliminarNodoNotExec(hijo, panelPadre);
                    Regla regla=ejemplo.getListaPasos().get(i).getRegla();
                    if (regla!=null){
                        insertarNodoNotExec(parent,panelPadre, regla, posXAnterior, hijo.getPosY(),hijo);
                       
                    }
                    
                    setPosXAnterior(hijo.getPosX());
                }   
                else{
                    Nodo hijo=insertarNodo(parent, panelPadre, ejemplo.getListaPasos().get(i).getElemento().split(" ")[0],posXAnterior+parent.getRectangle().getWidth()+10/* (parent.getRectangle().getWidth()*2+parent.getPosX()/2+10)(parent.getRectangle().getWidth()/2)+(parent.getRectangle().getWidth()*parent.getChildren().size()))/2*/,(parent.getRectangle().getHeight()*2)+parent.getPosY());
                    parent.getChildren().add(hijo);
                    moveSiblings(hijo);
                    eliminarNodoNotExec(hijo, panelPadre);
                    Regla regla=ejemplo.getListaPasos().get(i).getRegla();
                    if (regla!=null){
                        insertarNodoNotExec(parent,panelPadre, regla, posXAnterior, (parent.getRectangle().getHeight()*2)+parent.getPosY(),hijo);
                    }
                    setPosXAnterior(hijo.getPosX());
                }
            }
        }
        
    }
    /**
     * remove until the solicited step 
     * @param contador
     * @param pasoSolicitado
     * @param panelPadre 
     */
    public void eliminar(int contador,int pasoSolicitado,Pane panelPadre ){
        for(int i=contador-1;i>=pasoSolicitado;i-- ){
            if((nodos.get(i).getParent()!=null)&&(nodos.get(i).getParent().getChildren().getFirst()==nodos.get(i)))
                eliminarNodoNotExec(nodos.get(i).getHermanos().values(), panelPadre);
            Nodo nodoNotExec=   this.NodoNotExec(nodos.get(i));
             panelPadre.getChildren().addAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
            eliminarNodo(nodos.get(i), panelPadre, contador);
            }
       
    }
    public Nodo NodoNotExec(Nodo nodo){
        Nodo hermano=nodo.getParent().getChildren().getFirst();
        
        for(int i=0;i<hermano.getHermanos().size();i++){
          Nodo n= hermano.getHermanos().get(i);
          if (nodo.getSimbolo().equals(n.getSimbolo()))
              return n;
        }
        return null;
    }
    public HashMap<Integer, Nodo> getNodos() {
        return nodos;
    }

    public FicheroXML getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(FicheroXML ejemplo) {
        this.ejemplo = ejemplo;
    }

    public double getPosXAnterior() {
        return posXAnterior;
    }

    public void setPosXAnterior(double posXAnterior) {
        this.posXAnterior = posXAnterior;
    }
    
}
