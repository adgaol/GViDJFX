/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.apache.xalan.xsltc.runtime.BasisLibrary;

/**
 *
 * @author adgao
 */
public class Grafo {
private HashMap<Integer,Nodo> nodos;
private FicheroXML ejemplo;
private double posXAnterior;
private double posYAnterior;
private double heigth;
private double width;
private Gramatica gramatica;
private CadenaEntrada cadena;
private HashMap<Integer,Integer>stepProcess;//posicion en la cadena ,paso en el que se procesa
private int contador;
private Pane panelPadre;
private Configuracion config;
private String tipoTraductor;
private int nivelAnterior;
private HashMap<Integer, Integer> sibling;
//private HashMap<Integer,Rectangle> ruleRect;//relates the level of the node to the corresponding rectangle
private HashMap<Integer,Double>posXAnteriores;
private HashMap<Integer,HashMap<String, Double>>posSiblings;

private Double posXMax=0.0;
public Grafo(FicheroXML xml,Gramatica gramatica,CadenaEntrada cadena,Pane panelPadre,Configuracion config,String tipoTraductor) {
      nodos=new HashMap<>();
      this.ejemplo=xml;
      this.posXAnterior=(ejemplo.getNumNodos()-1)*50/2;
      this.posYAnterior=300;
      this.gramatica=gramatica;
      this.cadena=cadena;
      this.contador=0;
      this.stepProcess=new HashMap<>();
      this.panelPadre=panelPadre;
      this.config=config;
      this.tipoTraductor=tipoTraductor;
      this.nivelAnterior=0;
      this.heigth=0;
      this.width=0;
     // this.ruleRect=new HashMap<>();
      this.sibling=new HashMap<>();
      this.posXAnteriores=new HashMap<>();
      this.posSiblings=new HashMap<>();
      obtainStepsProcess();
      //addHandlingListennerChain();
      assignAllSiblings();
    }
    /**
     * Obtain a map with the step where a element of the chain is process
     */
    public void obtainStepsProcess(){
        String comp="";
        int pos=0;
        int paso=0;
        for(String i:ejemplo.getCadena()){
            String[] pendExec= i.split("pend");
            String last="";
            if(!pendExec[0].equals(""))
                last=pendExec[0].substring(pendExec[0].length()-1);
            if(!last.equals(comp)){
               stepProcess.put(pos, paso);
               pos++;
               comp=last;
            }
            paso++;
        }
        paso--;
        stepProcess.put(pos, paso);
    }
 
    /**
     * insert one node
     * @param id
     * id of the new node
     * @param parent
     * parent of the node
     * @param simbolo
     * symbol to the node
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @param value
     * value of the new node
     * @return the node inserted
     */
    public Nodo insertarNodo(int id,Nodo parent,String simbolo,Double posX,Double posY,String value){
      
      
       Nodo nodo=new Nodo(id,simbolo,parent,isTerminal(simbolo),config.getLetraArbol(),value);
       nodo.setPosX(posX);
       nodo.getRectangle().setX(posX);
       nodo.setPosY(posY);
       nodo.getRectangle().setY(posY);
       Label label=new Label(simbolo);
       label.setFont(new Font(config.getLetraArbol()));
       label.setLayoutX(posX+nodo.getRectangle().getWidth()/2-(label.getText().length()*(config.getLetraArbol()/2)/2));
       label.setLayoutY(posY+nodo.getRectangle().getHeight()/2-config.getLetraArbol());
       if(!isTerminal(simbolo)){
          Color colorRectangle=Color.web(config.getColorNoTerminal());
          nodo.getRectangle().setFill(colorRectangle);
          Color colorText=Color.web(config.getLetraNoTerminal());
          label.setTextFill(colorText);
          
       }
       else{
          Color colorRectangle=Color.web(config.getColorTerminal());
          nodo.getRectangle().setFill(colorRectangle);
          Color colorText=Color.web(config.getLetraTerminal());
          label.setTextFill(colorText);
       }
       
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
     * @param parent
     * parent of the node
     * @param simbolo
     * symbol to the node
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @return the node inserted
     */
    public Nodo insertarNodoA(String parents,String simbolo,Double posX,Double posY,String value){
      
      
       Nodo nodo=new Nodo(contador,simbolo,null,isTerminal(simbolo),config.getLetraArbol(),value);
       nodo.setPosX(posX);
       nodo.getRectangle().setX(posX);
       nodo.setPosY(posY);
       nodo.getRectangle().setY(posY);
       Label label=new Label(simbolo);
       label.setFont(new Font(config.getLetraArbol()));
       label.setLayoutX(posX+nodo.getRectangle().getWidth()/2-(label.getText().length()*(config.getLetraArbol()/2)/2));
       label.setLayoutY(posY+nodo.getRectangle().getHeight()/2-config.getLetraArbol());
       if(!isTerminal(simbolo)){
          Color colorRectangle=Color.web(config.getColorNoTerminal());
          nodo.getRectangle().setFill(colorRectangle);
          Color colorText=Color.web(config.getLetraNoTerminal());
          label.setTextFill(colorText);

       }
       else{
          Color colorRectangle=Color.web(config.getColorTerminal());
          nodo.getRectangle().setFill(colorRectangle);
          Color colorText=Color.web(config.getLetraTerminal());
          label.setTextFill(colorText);
       }
       
            nodo.setLabel(label);
       
       nodos.put(nodos.size(), nodo);
       
           if(parents!=null){
              for(int i=1;i<parents.split(" ").length;i++){
                  Nodo parent=this.getNodos().get(Integer.parseInt(parents.split(" ")[i]));
                  Line line=new Line(parent.getPosX()+parent.getRectangle().getWidth()/2, parent.getPosY(), nodo.getPosX()+nodo.getRectangle().getWidth()/2,nodo.getPosY()+parent.getRectangle().getHeight());
                  nodo.getLines().add(line);
                  panelPadre.getChildren().addAll(line);
              }
                panelPadre.getChildren().addAll(nodo.getRectangle(),label);
           }
           else
               panelPadre.getChildren().addAll(nodo.getRectangle(),label);
       
       return nodo; 
    }
    /**
     * Decide if the symbol is terminal
     * @param symbol
     * symbol to evaluate
     * @return 
     * if es terminal true else false
     */
    public Boolean isTerminal(String symbol){
        boolean terminal=false;
        for(Simbolo s:ejemplo.getMapa().values()){
            if(s.getValor().equals(symbol))
                terminal=s.isTerminal();
        }
        return terminal;
    }
    /**
     * insert one node
     * @param parent
     * parent of the node to insert
     * @param regla
     * rule to insert
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @param hijo
     * simbol already inserted
     * @return the node inserted
     */
    public HashSet<Nodo> insertarNodoNotExec(Nodo parent,Regla regla,Double posX,Double posY,Nodo hijo){
      HashSet<Nodo> nodesInserted=new HashSet<>();
    String[] simbolos=regla.getValor().split(" ");
    Nodo nodo=null;
    Double nPosX=posX;
    double widthAux=0.0;
    HashMap<String,Double> siblings=new HashMap<>();
    posSiblings.put(hijo.getId(), siblings);
    for(int i =1;i<simbolos.length;i++){
        if(i==2){
            widthAux=hijo.getRectangle().getWidth();
        }
        else if(i>2)
            widthAux=hijo.getHermanos().get(simbolos[i-1]).getRectangle().getWidth();
        if(!simbolos[i].equals(hijo.getSimbolo())){
            Boolean terminal=isTerminal(simbolos[i]);
            nodo=new Nodo(hijo.getId(),simbolos[i],parent,terminal,config.getLetraArbol(),"Elemento de la pila");
            nodo.getRectangle().setFill(Color.RED);
            
            nodo.getRectangle().setOpacity(0.50);
            
            nPosX+=widthAux+10;
            nodo.getRectangle().setX(nPosX);
            nodo.setPosX(nPosX);
            siblings.put(nodo.getSimbolo(), nPosX);
            nodo.setPosY(posY);
            nodo.getRectangle().setY(posY);
            Label label=new Label(simbolos[i]);
            label.setFont(new Font(config.getLetraArbol()));
            label.setLayoutX(nPosX+nodo.getRectangle().getWidth()/2-(label.getText().length()*(config.getLetraArbol()/2)/2));
            label.setLayoutY(posY+nodo.getRectangle().getHeight()/2-config.getLetraArbol());
            if(!terminal){
              Color colorRectangle=Color.web(config.getColorNoTerminal());
              nodo.getRectangle().setFill(colorRectangle);
              Color colorText=Color.web(config.getLetraNoTerminal());
              label.setTextFill(colorText);

            }
            else{
                Color colorRectangle=Color.web(config.getColorTerminal());
                nodo.getRectangle().setFill(colorRectangle);
                Color colorText=Color.web(config.getLetraTerminal());
                label.setTextFill(colorText);
            }
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
     * @param nodosHermanos
     * collection of not exec siblings nodes to insert
     * @param posX
     * position in axis X
     * @param posY
     * position in axis Y
     * @param hijo
     * simbol already inserted
     * @return the node inserted
     */
    public HashSet<Nodo> insertarNodoNotExec(Collection<Nodo> nodosHermanos,Double posX,Double posY,Nodo hijo){
      HashSet<Nodo> nodesInserted=new HashSet<>();
    
    Double widthAux=hijo.getRectangle().getWidth();
    Double nPosX=posX;
    HashMap<String,Double> siblings=new HashMap<>();
    posSiblings.put(hijo.getId(), siblings);
    for(Nodo n:nodosHermanos){
        
        if(!hijo.getSimbolo().equals(n.getSimbolo())){
            
            
            nPosX+=widthAux+10;
            siblings.put(n.getSimbolo(), nPosX);
            n.setPosX(nPosX);
            n.getRectangle().setX(nPosX);
            n.setPosY(posY);
            n.getRectangle().setY(posY);
           
            
            n.getLabel().setLayoutX(nPosX+n.getRectangle().getWidth()/2-(n.getLabel().getText().length()*(config.getLetraArbol()/2)/2));
            n.getLabel().setLayoutY(posY+n.getRectangle().getHeight()/2-config.getLetraArbol());
            
            nodesInserted.add(n);
            hijo.getHermanos().put(n.getSimbolo(), n);
            panelPadre.getChildren().addAll(n.getRectangle(),n.getLabel());
            widthAux=n.getRectangle().getWidth();
        }
    }
       
       
       
       
       
       
           
       
       return nodesInserted; 
    }
    /**
     * remove one element of the tree
     * @param nodoElim
     * node to remove
     * @param number 
     * number of the node
     */
    public void eliminarNodo(Nodo nodoElim,int number){
        
         panelPadre.getChildren().removeAll(nodoElim.getRectangle(),nodoElim.getLabel(),nodoElim.getLine());
         
         if(nodoElim.getParent()!=null){
            nodoElim.getParent().getChildren().remove(nodoElim);
            if(nodoElim.getParent().getParent()==null && nodoElim.getLeftSibling()==null)
                posXAnterior=10;
            else     
                posXAnterior=nodos.get(number-1).getPosX();
         }
         
         nodos.remove(number);
    }
    /**
     * remove one element of the tree
     * @param nodoElim
     * node to remove
     * @return 
     * A list of not exec siblings nodes
     */
    public LinkedList<Nodo> eliminarNodoNotExec(Nodo nodoElim){
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
     * add the respectives left siblings to the nodes in one step
     * @param siblings 
     * String with the nodes that produce another node
     */
    public void assignSiblings(String[] siblings){
        for(int i=1;i<siblings.length;i++){
            
            if(i>1)
                sibling.put(Integer.parseInt(siblings[i]), Integer.parseInt(siblings[i-1]));
            else
                sibling.put(Integer.parseInt(siblings[i]),null );
        }
    }
    /**
     * assign the siblings of the nodes in all steps
     */
    public void assignAllSiblings(){
        
        List<Informacion> pasos=ejemplo.getListaPasos();
        for(Informacion info:pasos){
           if(info.getElemento().split(" ").length>1){
               assignSiblings(info.getElemento().split(" "));
           } 
        }
    }
    /**
     * Assign the events to the respective rectangle
     * @param rectReg 
     * rectangle to assign the events.
     */
    public void assingRectanglesEvents(Rectangle rectReg){
        rectReg.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {

                System.out.println(event.getSource());
                gramatica.cambiarFormaRegla(gramatica.getRelRectRegla().get(event.getSource()));
            }

        });
        rectReg.setOnKeyPressed(new EventHandler<KeyEvent>(){

                @Override
                public void handle(KeyEvent event) {
                    Rectangle rect=(Rectangle)event.getSource();
                    if(event.getCode()==KeyCode.ENTER){
                       System.out.println(event.getSource());
                       gramatica.cambiarFormaRegla(gramatica.getRelRectRegla().get(event.getSource()));
                    }    
                    else{
                        
                    }    
                    
                }
                

            }); 
        rectReg.setOnMouseEntered(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {

                System.out.println(event.getSource());
                Rectangle rect=(Rectangle)event.getSource();
                
                rect.setFill(Color.YELLOW);

                gramatica.drawRectangle(gramatica.getRelRectRegla().get(event.getSource()));
                Tooltip t = new Tooltip(gramatica.getRelRectRegla().get(event.getSource()).getValor());
                Tooltip.install(rect, t);
                
            }

        });
        rectReg.setOnMouseExited(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                Rectangle rect=(Rectangle)event.getSource();
                rect.setFill(Color.BLACK);
                System.out.println(event.getSource());
                gramatica.erasedRectangle(gramatica.getRelRectRegla().get(event.getSource()));
            }

        });
        rectReg.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if (newPropertyValue)
                    {
//                        System.out.println(event.getSource());
//                        Rectangle rect=(Rectangle)event.getSource();
//                         System.out.println(event.getSource());
                         rectReg.setFill(Color.YELLOW);
                       
                      
                    }
                    else
                    {
                         rectReg.setFill(Color.BLACK); 
                    }
                }
            });
    }
    public int construirAsc(int pasoSolicitado){
         int nivel=0;
            
            
            
            for(int i=contador;i<pasoSolicitado;i++){
                nivel=ejemplo.getMapa().get(ejemplo.getListaPasos().get(i).getId()).getNivel();
                if(i==0){
                    
                    String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                    String value=ejemplo.getListaPasos().get(i).getValor();
                    Nodo primero= insertarNodoA(null, simbolo, 20.0, 300.0,value);
                    nivelAnterior=nivel;
                    
                    posXMax=primero.getPosX();
                    //posYAnterior=300.0;
                    width=primero.getRectangle().getWidth();
                    heigth=primero.getRectangle().getHeight();
                    setPosXAnterior(primero.getPosX());
                    posXAnteriores.put(i, primero.getPosX());
                    Rectangle rectReg=new Rectangle(primero.getRectangle().getWidth()+10, primero.getRectangle().getHeight()+10);
                    rectReg.setX(primero.getPosX()-5);
                    rectReg.setY(primero.getPosY()-5);
                    rectReg.setOpacity(0.5);
                    rectReg.setFocusTraversable(true);
                    primero.setRectRgla(rectReg);
                    panelPadre.getChildren().add(0,rectReg);
                    gramatica.getRelRectRegla().put(rectReg, ejemplo.getListaPasos().get(i).getRegla());
                    assingRectanglesEvents(rectReg);
                    primero.setWidthRectRgla(rectReg.getWidth());
                    //this.ruleRect.put(nivel,rectReg);
                    posXAnterior+=10+primero.getRectangle().getWidth();
                    posXAnteriores.put(i, posXAnterior);
                    Tooltip t = new Tooltip(primero.getValue());
                    Tooltip.install(primero.getRectangle(), t);
                }
                else{
                    if(nivelAnterior>nivel){
                        posYAnterior=posYAnterior-(2*heigth+10)*(nivelAnterior-nivel); 
                    }
                    else if(nivelAnterior<nivel){
                        posYAnterior=posYAnterior+(2*heigth+10)*(nivel-nivelAnterior);    
                    }
                    
                    if(ejemplo.getListaPasos().get(i).getElemento().split(" ").length==1){

                        
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo node= insertarNodoA(null, simbolo, posXAnterior, posYAnterior,value);
                        
                        posXMax=Math.max(node.getPosX(), posXMax);
                        node.setLeftSibling(nodos.get(sibling.get(i)));
                        Rectangle r=null;
                        //Rectangle r=this.ruleRect.get(nivel);
//                        if(ejemplo.getListaPasos().get(i).getRegla()!=null){
//                            if(r!=null){
//                                ruleRect.remove(nivel);
//                                r=null;
//                            }
//                        }
                        if(node.getLeftSibling()!=null)
                            r=node.getLeftSibling().getRectRgla();
                        if (r!=null){
                            System.out.println(r.getWidth()+node.getRectangle().getWidth());
                            System.out.println((node.getPosX()/*-node.getLeftSibling().getPosX()/*-node.getRectangle().getWidth()*/));
                            System.out.println(r.getWidth()+node.getRectangle().getWidth()+(node.getPosX()-node.getLeftSibling().getPosX()-node.getRectangle().getWidth()));
                            r.setWidth(r.getWidth()+node.getRectangle().getWidth()+10+(node.getPosX()-(node.getLeftSibling().getPosX()+node.getRectangle().getWidth())));
                            
                            node.setRectRgla(r);//130+
                            node.setWidthRectRgla(r.getWidth());
                        }
                        else{
                            Rectangle rectReg=new Rectangle(node.getRectangle().getWidth()+10, node.getRectangle().getHeight()+10);
                            rectReg.setX(node.getPosX()-5);
                            rectReg.setY(node.getPosY()-5);
                            rectReg.setFocusTraversable(true);
                            rectReg.setOpacity(0.5);
                            node.setRectRgla(rectReg);
                            node.setWidthRectRgla(rectReg.getWidth());
                            panelPadre.getChildren().add(0,rectReg);
                            gramatica.getRelRectRegla().put(rectReg, ejemplo.getListaPasos().get(i).getRegla());
                            assingRectanglesEvents(rectReg);
                            node.setRectRgla(rectReg);
                           // this.ruleRect.put(nivel,rectReg);
                        }
                        posXAnterior+=10.0+node.getRectangle().getWidth();
                        posXAnteriores.put(i, posXAnterior);
                        //width=node.getRectangle().getWidth();
                        Tooltip t = new Tooltip(node.getValue());
                        Tooltip.install(node.getRectangle(), t);
                    }
                    else{
                       Nodo firstParent=nodos.get(Integer.parseInt(ejemplo.getListaPasos().get(i).getElemento().split(" ")[1]));
                       
                       int parentsNumber=ejemplo.getListaPasos().get(i).getElemento().split(" ").length-1;
                       double aux1=firstParent.getPosX();
                       double aux2=firstParent.getRectangle().getWidth()+(parentsNumber-1)*(firstParent.getRectangle().getWidth()+10);
                       Double aux3=aux2/parentsNumber;
                       Double aux4=aux1+aux3;
                       double posX=0.0;
                       
                       if(ejemplo.getListaPasos().get(i).getElemento().split(" ").length>2)
                            posX=firstParent.getPosX()+((firstParent.getRectangle().getWidth()+(parentsNumber-1)*(firstParent.getRectangle().getWidth()+10))/2);//parentsNumber);
                       else
                            posX=firstParent.getPosX()+((parentsNumber-1)*(firstParent.getRectangle().getWidth()+10)/2);//parentsNumber);
                       String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                       String value=ejemplo.getListaPasos().get(i).getValor();
                       Nodo node= insertarNodoA(ejemplo.getListaPasos().get(i).getElemento(), simbolo, posX, posYAnterior,value);
                       
                       posXMax=Math.max(node.getPosX(), posXMax);
                       posXAnteriores.put(i, posXAnterior);
                       node.setLeftSibling(nodos.get(sibling.get(i)));
                       Rectangle r=null;
                       if(node.getLeftSibling()!=null)
                            r=node.getLeftSibling().getRectRgla();//this.ruleRect.get(nivel);
//                       if(ejemplo.getListaPasos().get(i).getRegla()!=null){
//                            if(r!=null){
//                                ruleRect.remove(nivel);
//                                r=null;
//                            }
//                        }
                       if(i<ejemplo.getListaPasos().size()-1){
                           if (r!=null){
                                r.setWidth(r.getWidth()+node.getRectangle().getWidth()+(node.getPosX()-node.getLeftSibling().getPosX()-node.getRectangle().getWidth()));
                                node.setRectRgla(r);
                                node.setWidthRectRgla(r.getWidth());
                            }
                            else{
                                Rectangle rectReg=new Rectangle(node.getRectangle().getWidth()+10, node.getRectangle().getHeight()+10);
                                rectReg.setFocusTraversable(true);
                                rectReg.setX(node.getPosX()-5);
                                rectReg.setY(node.getPosY()-5);
                                rectReg.setOpacity(0.5);
                                node.setRectRgla(rectReg);
                                panelPadre.getChildren().add(0,rectReg);
                                gramatica.getRelRectRegla().put(rectReg, ejemplo.getListaPasos().get(i).getRegla());
                                assingRectanglesEvents(rectReg);
                                node.setRectRgla(rectReg);
                                node.setWidthRectRgla(rectReg.getWidth());
                               // this.ruleRect.put(nivel,rectReg);
                            }
                       }
                        Tooltip t = new Tooltip(node.getValue());
                        Tooltip.install(node.getRectangle(), t);
                    }
                    nivelAnterior=nivel;
                    
                }
            updatedValues(ejemplo.getListaPasos().get(i).getSimbolosActualizados());
            }
          return pasoSolicitado;
    }
    
    public int construirDesc(int pasoSolicitado){
        for(int i=contador;i<pasoSolicitado;i++){
                //if is the root
                if(i==0){
                   String simbolo=ejemplo.getListaPasos().get(contador).getElemento().split(" ")[0];
                   Nodo raiz= insertarNodo(i,null, simbolo, ejemplo.getNumNodos()*50/2.0, 10.0,null);
                   setPosXAnterior(20);

                }
                else{
                    Nodo parent=this.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(i).getElemento().split(" ")[1]));

                    //if is the first child
                    if(parent.getChildren().isEmpty()){
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo hijo=insertarNodo(i,parent, simbolo, posXAnterior,(parent.getRectangle().getHeight()*2)+parent.getPosY(),value);
                        
                        posXMax=Math.max(posXMax, hijo.getPosX());
                        parent.getChildren().add(hijo);
                        //moveSiblings(hijo);
                        //eliminarNodoNotExec(hijo, panelPadre);
                        Regla regla=ejemplo.getListaPasos().get(i).getRegla();
                        if (regla!=null){
                            insertarNodoNotExec(parent, regla, posXAnterior, hijo.getPosY(),hijo);

                        }
                        Rectangle rectReg=new Rectangle(hijo.getRectangle().getWidth()+widthNoExecuteNodes(hijo)+10, hijo.getRectangle().getHeight()+20);
                        rectReg.setX(hijo.getPosX()-5);
                        rectReg.setY(hijo.getPosY()-5);
                        rectReg.setOpacity(0.5);
                        rectReg.setFocusTraversable(true);
                        hijo.setRectRgla(rectReg);
                        panelPadre.getChildren().add(0,rectReg);
                        gramatica.getRelRectRegla().put(rectReg, regla);
                        assingRectanglesEvents(rectReg);
                        setPosXAnterior(hijo.getPosX());
                        Tooltip t = new Tooltip(hijo.getValue());
                        Tooltip.install(hijo.getRectangle(), t);
                    }   
                    else{
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo hijo=insertarNodo(i,parent, simbolo,posXAnterior+parent.getChildren().getLast().getRectangle().getWidth()+10,(parent.getRectangle().getHeight()*2)+parent.getPosY(),value);
                        parent.getChildren().add(hijo);
                        
                        posXMax=Math.max(posXMax, hijo.getPosX());
                        //hijo.setHermanosDelHermano(hijo);

                        eliminarNodoNotExec(hijo.getParent().getChildren().getFirst());

                        insertarNodoNotExec(hijo.getLeftSibling().getHermanos().values(), hijo.getPosX(), (parent.getRectangle().getHeight()*2)+parent.getPosY(),hijo);
                        Rectangle rectReg=hijo.getLeftSibling().getRectRgla();//new Rectangle(hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+10, hijo.getRectangle().getWidth()+20);
                        if(hijo.getParent().getChildren().getFirst().getPosX()!=0)
                             rectReg.setWidth(10+hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+(hijo.getPosX())-hijo.getParent().getChildren().getFirst().getPosX());

                        else
                            rectReg.setWidth(10+hijo.getRectangle().getWidth()+(hijo.getRectangle().getWidth()+10)*hijo.getHermanos().size()+(hijo.getPosX()));

                        hijo.setRectRgla(rectReg);
                        //panelPadre.getChildren().add(0,rectReg);

                        setPosXAnterior(hijo.getPosX());
                        Tooltip t = new Tooltip(hijo.getValue());
                        Tooltip.install(hijo.getRectangle(), t);

                    }
                }
                updatedValues(ejemplo.getListaPasos().get(i).getSimbolosActualizados());
            }
        return pasoSolicitado;
    }
    /**
     * 
     * @param nodo
     * the node with the siblings
     * @return 
     * the width of the siblings nodes
     */
    public Double widthNoExecuteNodes(Nodo nodo){
        Double width=0.0;
        for(Nodo hermano:nodo.getHermanos().values()){
            width+=hermano.getRectangle().getWidth()+10;
        }
        return width;
    }
    /**
     * build the tree to the solicited step
     * @param pasoSolicitado
     * last step
     * @return 
     * the step where it has finished
     */
    public int construir(int pasoSolicitado ) {
        if(tipoTraductor.equals("Ascendente")){
            construirAsc(pasoSolicitado);
            
        }
        else{
            construirDesc(pasoSolicitado);
        }
        contador=pasoSolicitado;
        cadena.actualizarCadena(pasoSolicitado);
        return contador;
    }
    public void eliminarDesc(int pasoSolicitado){
       for(int i=contador-1;i>=pasoSolicitado;i-- ){
                Nodo elemElim=nodos.get(i);
                if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()==elemElim)){
                    eliminarNodoNotExec(elemElim);
                    panelPadre.getChildren().remove(elemElim.getRectRgla());
                }
    //            if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()!=elemElim)){
    //                elemElim.getRectRgla().setWidth(elemElim.getRectRgla().getWidth()-elemElim.getRectangle().getWidth()-(elemElim.getRectangle().getWidth()+10)*elemElim.getHermanos().size()-(elemElim.getPosX()-elemElim.lastSibling().getPosX()+elemElim.lastSibling().getRectangle().getWidth()));
    //            }
                Nodo nodoNotExec=   this.NodoNotExec(elemElim);
                if(nodoNotExec!=null){
                    nodoNotExec.getRectangle().setX(nodoNotExec.getPosX());
                    
                    nodoNotExec.getLabel().setLayoutX(nodoNotExec.getPosX()+nodoNotExec.getRectangle().getWidth()/3);
                    recover(elemElim);
                    panelPadre.getChildren().addAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
                }
                eliminarNodo(elemElim, i);
                } 
    }
    /**
     * Recover the position of the not execute siblings of the node
     * @param elemElim 
     * node to remove
     */
    public void recover(Nodo elemElim){
        Nodo leftsibling=elemElim.getLeftSibling();
        for(Nodo hermano:leftsibling.getHermanos().values()){
           if(!posSiblings.get(leftsibling.getId()).isEmpty()) {
                hermano.getRectangle().setX(posSiblings.get(leftsibling.getId()).get(hermano.getSimbolo()));
                hermano.getLabel().setLayoutX(posSiblings.get(leftsibling.getId()).get(hermano.getSimbolo())+hermano.getRectangle().getWidth()/2-(hermano.getLabel().getText().length()*(config.getLetraArbol()/2)/2));
           }
        }
    }
    public void eliminarAsc(int pasoSolicitado ){
        for(int i=contador-1;i>=pasoSolicitado;i-- ){
            
            Nodo elemElim=nodos.get(i);
            //int nivel=ejemplo.getMapa().get(ejemplo.getListaPasos().get(i).getId()).getNivel();
            if(ejemplo.getListaPasos().get(i).getRegla()!=null){
               panelPadre.getChildren().remove(elemElim.getRectRgla());
               //this.ruleRect.remove(nivel);
            }
            else{
                 if(i<ejemplo.getNumNodos()-1)
                    elemElim.getRectRgla().setWidth(elemElim.getLeftSibling().getWidthRectRgla());
            }
            if(i==0){
               panelPadre.getChildren().removeAll(elemElim.getRectangle(),elemElim.getLabel());
               posXAnterior=20;
               posYAnterior=300;
               nivelAnterior=0;
            }
            else if(elemElim.getLines().isEmpty()){
               panelPadre.getChildren().removeAll(elemElim.getRectangle(),elemElim.getLabel());
               posXAnterior=posXAnteriores.get(i-1);
               posYAnterior=nodos.get(i-1).getPosY();
               nivelAnterior=ejemplo.getMapa().get(i-1).getNivel();
            }
            else{
                panelPadre.getChildren().removeAll(elemElim.getRectangle(),elemElim.getLabel());
                panelPadre.getChildren().removeAll(elemElim.getLines());
                posXAnterior=posXAnteriores.get(i-1);;
                posYAnterior=nodos.get(i-1).getPosY();  
                nivelAnterior=ejemplo.getMapa().get(i-1).getNivel();
            }
            eliminarNodo(elemElim, i);
        }
//                if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()==elemElim)){
//                    eliminarNodoNotExec(elemElim);
//                    panelPadre.getChildren().remove(elemElim.getRectRgla());
//                }
//    //            if((elemElim.getParent()!=null)&&(elemElim.getParent().getChildren().getFirst()!=elemElim)){
//    //                elemElim.getRectRgla().setWidth(elemElim.getRectRgla().getWidth()-elemElim.getRectangle().getWidth()-(elemElim.getRectangle().getWidth()+10)*elemElim.getHermanos().size()-(elemElim.getPosX()-elemElim.lastSibling().getPosX()+elemElim.lastSibling().getRectangle().getWidth()));
//    //            }
//                Nodo nodoNotExec=   this.NodoNotExec(elemElim);
//                if(nodoNotExec!=null){
//                    nodoNotExec.getRectangle().setX(nodoNotExec.getPosX());
//                    nodoNotExec.getLabel().setLayoutX(nodoNotExec.getPosX()+nodoNotExec.getRectangle().getWidth()/3);
//                    panelPadre.getChildren().addAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
//                }
//                eliminarNodo(elemElim, i);
//                } 
        
    }
    /**
    * update the value of the symbols in the list
    * @param symbolsUpdated 
    * list of symbols to update the value
    */
    public void updatedValues(List<Simbolo> symbolsUpdated){
        for(Simbolo s:symbolsUpdated){
           Nodo n=nodos.get(s.getId());
           n.setValue(s.getValor());
           Tooltip t = new Tooltip(n.getValue());
           Tooltip.install(n.getRectangle(), t);
        }
        
    }
    /**
     * remove until the solicited step 
     * @param pasoSolicitado 
     * @return  
     * the step where it has finished
     */
    public int eliminar(int pasoSolicitado ){
        if(tipoTraductor.equals("Ascendente"))
            eliminarAsc(pasoSolicitado);
        else    
            eliminarDesc(pasoSolicitado);
        cadena.actualizarCadena(pasoSolicitado);
        contador=pasoSolicitado;
        return contador-1;
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
    /**
     * update the color and the size of the letter
     */
    public void updateGraph(){
        int cont=contador;
        eliminar(0);
        construir(cont);
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

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public HashMap<Integer, Integer> getStepProcess() {
        return stepProcess;
    }

    public void setStepProcess(HashMap<Integer, Integer> stepProcess) {
        this.stepProcess = stepProcess;
    }

   

    public Double getPosXMax() {
        return posXMax;
    }
    
}
