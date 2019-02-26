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
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
private FicheroXML ejemplo;
private double posXAnterior;
private Gramatica gramatica;
    public Grafo(FicheroXML xml,Gramatica gramatica) {
      nodos=new HashMap<>();
      this.ejemplo=xml;
      this.posXAnterior=(ejemplo.getNumNodos()-1)*50/2;
      this.gramatica=gramatica;
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
      
      
       Nodo nodo=new Nodo(simbolo,parent);
       nodo.getRectangle().setFill(Color.RED);
       
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
            nodo=new Nodo(simbolos[i],parent);
            nodo.getRectangle().setFill(Color.RED);
            nodo.getRectangle().setOpacity(0.50);
            
            nPosX+=nodo.getRectangle().getWidth()+10;
            nodo.getRectangle().setX(nPosX);
            nodo.setPosX(nPosX);
            nodo.setPosY(posY);
            nodo.getRectangle().setY(posY);
            Label label=new Label(simbolos[i]);
            
            label.setLayoutX(nPosX+nodo.getRectangle().getWidth()/3);
            label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
            nodo.setLabel(label);
            nodesInserted.add(nodo);
            hijo.getHermanos().put(simbolos[i], nodo);
            panelPadre.getChildren().addAll(nodo.getRectangle(),label);
        }
    }
       
       
       
       
       
       
           
       
       return nodesInserted; 
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
    public HashSet<Nodo> insertarNodoNotExec(Pane panelPadre,Collection<Nodo> nodosHermanos,Double posX,Double posY,Nodo hijo){
      HashSet<Nodo> nodesInserted=new HashSet<>();
    
    
    Double nPosX=posX;
    for(Nodo n:nodosHermanos){
        if(!hijo.getSimbolo().equals(n.getSimbolo())){
            
            
            nPosX+=n.getRectangle().getWidth()+10;
            
            n.setPosX(nPosX);
            n.getRectangle().setX(nPosX);
            n.setPosY(posY);
            n.getRectangle().setY(posY);
           
            
            n.getLabel().setLayoutX(nPosX+n.getRectangle().getWidth()/3);
            n.getLabel().setLayoutY(posY+n.getRectangle().getHeight()/3);
            
            nodesInserted.add(n);
            hijo.getHermanos().put(n.getSimbolo(), n);
            panelPadre.getChildren().addAll(n.getRectangle(),n.getLabel());
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
            if(nodoElim.getParent().getParent()==null && nodoElim.getLeftSibling()==null)
                posXAnterior=10;
            else     
                posXAnterior=nodos.get(number-2).getPosX();
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
    public LinkedList<Nodo> eliminarNodoNotExec(Nodo nodoElim,Pane panelPadre){
        LinkedList<Nodo> nodoNotExec=new LinkedList<>();
        
        for(Nodo n:nodoElim.getHermanos().values()){
            //if((n.getParent()==nodoElim.getParent())&&(n.getSimbolo().equals(nodoElim.getSimbolo()))){
                nodoNotExec.add(n);
                
            //}
        }
        if(!nodoNotExec.isEmpty()){
            for(Nodo n:nodoNotExec){
                panelPadre.getChildren().removeAll(n.getRectangle(),n.getLabel());
                //nodos.remove(n);
            }   
         
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
//    public boolean eliminarNodoNotExec(Collection<Nodo> nodoElim,Pane panelPadre){
//        for(Nodo n:nodoElim){
//            eliminarNodoNotExec(n, panelPadre);
//        }
//         return true;
//    }
    /**
     * move the nodes no executed
     * @param nodo 
     * new nodo exec
     */
//    public void moveSiblings(Nodo nodo){
//         Collection<Nodo> hermanos= nodo.getParent().getChildren().getFirst().getHermanos().values();
//            for(Nodo n:hermanos){
//                
//                if (!nodo.getSimbolo().equals(n.getSimbolo())){
//                    double distancia=n.getPosX()-nodo.getParent().getChildren().getFirst().getPosX();
//                    n.getRectangle().setX(nodo.getPosX()+distancia);
//                    n.getLabel().setLayoutX(distancia+nodo.getPosX()+n.getRectangle().getWidth()/3);
//                }
//            }
//    }
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
                    //moveSiblings(hijo);
                    //eliminarNodoNotExec(hijo, panelPadre);
                    Regla regla=ejemplo.getListaPasos().get(i).getRegla();
                    if (regla!=null){
                        insertarNodoNotExec(parent,panelPadre, regla, posXAnterior, hijo.getPosY(),hijo);
                       
                    }
                    Rectangle rectReg=new Rectangle(hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+10, hijo.getRectangle().getWidth()+20);
                    rectReg.setX(hijo.getPosX()-5);
                    rectReg.setY(hijo.getPosY()-5);
                    rectReg.setOpacity(0.5);
                    hijo.setRectRgla(rectReg);
                    panelPadre.getChildren().add(0,rectReg);
                    gramatica.getRelRectRegla().put(rectReg, regla);
                    rectReg.setOnMouseClicked(new EventHandler<MouseEvent>(){

          @Override
          public void handle(MouseEvent event) {
            
              System.out.println(event.getSource());
              gramatica.cambiarFormaRegla(gramatica.getRelRectRegla().get(event.getSource()));
          }

      });
                    setPosXAnterior(hijo.getPosX());
                }   
                else{
                    Nodo hijo=insertarNodo(parent, panelPadre, ejemplo.getListaPasos().get(i).getElemento().split(" ")[0],posXAnterior+parent.getRectangle().getWidth()+10/* (parent.getRectangle().getWidth()*2+parent.getPosX()/2+10)(parent.getRectangle().getWidth()/2)+(parent.getRectangle().getWidth()*parent.getChildren().size()))/2*/,(parent.getRectangle().getHeight()*2)+parent.getPosY());
                    parent.getChildren().add(hijo);
       
                    //hijo.setHermanosDelHermano(hijo);
                    
                    eliminarNodoNotExec(hijo.getParent().getChildren().getFirst(), panelPadre);
                    
                    insertarNodoNotExec(panelPadre,hijo.getLeftSibling().getHermanos().values(), hijo.getPosX(), (parent.getRectangle().getHeight()*2)+parent.getPosY(),hijo);
                    Rectangle rectReg=hijo.getLeftSibling().getRectRgla();//new Rectangle(hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+10, hijo.getRectangle().getWidth()+20);
                    if(hijo.getParent().getChildren().getFirst().getPosX()!=0)
                         rectReg.setWidth(10+hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+(hijo.getPosX())-hijo.getParent().getChildren().getFirst().getPosX());
          
                    else
                        rectReg.setWidth(10+hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+(hijo.getPosX()));
                    
                    hijo.setRectRgla(rectReg);
                    //panelPadre.getChildren().add(0,rectReg);
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
            Nodo elemElim=nodos.get(i);
            if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()==elemElim)){
                eliminarNodoNotExec(elemElim, panelPadre);
                panelPadre.getChildren().remove(elemElim.getRectRgla());
            }
//            if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()!=elemElim)){
//                elemElim.getRectRgla().setWidth(elemElim.getRectRgla().getWidth()-elemElim.getRectangle().getWidth()-(elemElim.getRectangle().getWidth()+10)*elemElim.getHermanos().size()-(elemElim.getPosX()-elemElim.lastSibling().getPosX()+elemElim.lastSibling().getRectangle().getWidth()));
//            }
            Nodo nodoNotExec=   this.NodoNotExec(elemElim);
            if(nodoNotExec!=null){
                nodoNotExec.getRectangle().setX(nodoNotExec.getPosX());
                nodoNotExec.getLabel().setLayoutX(nodoNotExec.getPosX()+nodoNotExec.getRectangle().getWidth()/3);
                panelPadre.getChildren().addAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
            }
            eliminarNodo(elemElim, panelPadre, contador);
            }
       
    }
    /**
     * Search the not execute node with the same symbol.
     * @param nodo
     * node to found
     * @return 
     *  not execute node with the same symbol or null if not exist
     */
    public Nodo NodoNotExec(Nodo nodo){
        if (nodo.getParent()!=null){
            Nodo hermano=nodo.getParent().getChildren().getFirst();
            Nodo n= hermano.getHermanos().get(nodo.getSimbolo());
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
