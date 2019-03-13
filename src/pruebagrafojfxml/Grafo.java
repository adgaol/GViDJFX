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
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
private HashMap<String,Integer>stepProcess;
private int contador;
private Pane panelPadre;
private Configuracion config;
private String tipoTraductor;
private int nivelAnterior;
private HashMap<Integer, Integer> sibling;
//private HashMap<Integer,Rectangle> ruleRect;//relates the level of the node to the corresponding rectangle
private HashMap<Integer,Double>posXAnteriores;
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
      obtainStepsProcess();
      addHandlingListennerChain();
      assignAllSiblings();
    }
    /**
     * Obtain a map with the step where a element of the chain is process
     */
    public void obtainStepsProcess(){
        String comp="";
        int pos=0;
        for(String i:ejemplo.getCadena()){
            String[] pendExec= i.split("pend");
            String last="";
            if(!pendExec[0].equals(""))
                last=pendExec[0].substring(pendExec[0].length()-1);
            if(!last.equals(comp)){
               stepProcess.put(last, pos);
               comp=last;
            }
            pos++;
        }
    }
    /**
     * add a listenner to the rectangle of the chain.When you click in someone the graph go to the
     * step where the element of the chain is process
     */
    public void addHandlingListennerChain(){
        
        HashMap<String,Rectangle> elements=cadena.getRectanglesChain();
        for(Rectangle r:elements.values()){
            r.setOnMouseClicked(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    Rectangle rect=(Rectangle)event.getSource();
                    int step=0;
                    if(rect.getId().equals("EOF"))
                        step=ejemplo.getNumNodos();
                    else
                        step= stepProcess.get(rect.getId()/*cadena.getRectanglesText().get(rect.getId())*/);
                    System.out.println(step);
                    if(contador<step)
                        contador=construir(step);
                    else if(contador>step)
                        eliminar(step);
                }

            }); 
        }
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
    public Nodo insertarNodo(Nodo parent,String simbolo,Double posX,Double posY,String value){
      
      
       Nodo nodo=new Nodo(simbolo,parent,isTerminal(simbolo),config.getLetraArbol(),value);
       nodo.setPosX(posX);
       nodo.getRectangle().setX(posX);
       nodo.setPosY(posY);
       nodo.getRectangle().setY(posY);
       Label label=new Label(simbolo);
       label.setFont(new Font(config.getLetraArbol()));
       label.setLayoutX(posX+nodo.getRectangle().getWidth()/3);
       label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
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
      
      
       Nodo nodo=new Nodo(simbolo,null,isTerminal(simbolo),config.getLetraArbol(),value);
       nodo.setPosX(posX);
       nodo.getRectangle().setX(posX);
       nodo.setPosY(posY);
       nodo.getRectangle().setY(posY);
       Label label=new Label(simbolo);
       label.setFont(new Font(config.getLetraArbol()));
       label.setLayoutX(posX+nodo.getRectangle().getWidth()/3);
       label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
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
    for(int i =1;i<simbolos.length;i++){
        if(!simbolos[i].equals(hijo.getSimbolo())){
            Boolean terminal=isTerminal(simbolos[i]);
            nodo=new Nodo(simbolos[i],parent,terminal,config.getLetraArbol(),"Elemento de la pila");
            nodo.getRectangle().setFill(Color.RED);
            
            nodo.getRectangle().setOpacity(0.50);
            
            nPosX+=nodo.getRectangle().getWidth()+10;
            nodo.getRectangle().setX(nPosX);
            nodo.setPosX(nPosX);
            nodo.setPosY(posY);
            nodo.getRectangle().setY(posY);
            Label label=new Label(simbolos[i]);
            label.setFont(new Font(config.getLetraArbol()));
            label.setLayoutX(nPosX+nodo.getRectangle().getWidth()/3);
            label.setLayoutY(posY+nodo.getRectangle().getHeight()/3);
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
    }
    public int construirAsc(int pasoSolicitado){
         int nivel=0;
            
            
            
            for(int i=contador;i<pasoSolicitado;i++){
                nivel=ejemplo.getMapa().get(ejemplo.getListaPasos().get(i).getId()).getNivel();
                if(i==0){
                    
                    String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                    String value=ejemplo.getListaPasos().get(i).getValor();
                    Nodo primero= insertarNodoA(null, simbolo, 10.0, 300.0,value);
                    nivelAnterior=nivel;
                    //posYAnterior=300.0;
                    width=primero.getRectangle().getWidth();
                    heigth=primero.getRectangle().getHeight();
                    setPosXAnterior(primero.getPosX());
                    posXAnteriores.put(i, primero.getPosX());
                    Rectangle rectReg=new Rectangle(primero.getRectangle().getWidth()+10, primero.getRectangle().getHeight()+10);
                    rectReg.setX(primero.getPosX()-5);
                    rectReg.setY(primero.getPosY()-5);
                    rectReg.setOpacity(0.5);
                    primero.setRectRgla(rectReg);
                    panelPadre.getChildren().add(0,rectReg);
                    gramatica.getRelRectRegla().put(rectReg, ejemplo.getListaPasos().get(i).getRegla());
                    assingRectanglesEvents(rectReg);
                    primero.setWidthRectRgla(rectReg.getWidth());
                    //this.ruleRect.put(nivel,rectReg);
                }
                else{
                    if(nivelAnterior>nivel){
                        posYAnterior=posYAnterior-(2*heigth+10)*(nivelAnterior-nivel); 
                    }
                    else if(nivelAnterior<nivel){
                        posYAnterior=posYAnterior+(2*heigth+10)*(nivel-nivelAnterior);    
                    }
                    
                    if(ejemplo.getListaPasos().get(i).getElemento().split(" ").length==1){

                        posXAnterior+=10.0+width;
                        posXAnteriores.put(i, posXAnterior);
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo node= insertarNodoA(null, simbolo, posXAnterior, posYAnterior,value);
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
                            r.setWidth(r.getWidth()+node.getRectangle().getWidth()+(node.getPosX()-node.getLeftSibling().getPosX()-node.getRectangle().getWidth()));
                            node.setRectRgla(r);
                            node.setWidthRectRgla(r.getWidth());
                        }
                        else{
                            Rectangle rectReg=new Rectangle(node.getRectangle().getWidth()+10, node.getRectangle().getHeight()+10);
                            rectReg.setX(node.getPosX()-5);
                            rectReg.setY(node.getPosY()-5);
                            rectReg.setOpacity(0.5);
                            node.setRectRgla(rectReg);
                            node.setWidthRectRgla(rectReg.getWidth());
                            panelPadre.getChildren().add(0,rectReg);
                            gramatica.getRelRectRegla().put(rectReg, ejemplo.getListaPasos().get(i).getRegla());
                            assingRectanglesEvents(rectReg);
                            node.setRectRgla(rectReg);
                           // this.ruleRect.put(nivel,rectReg);
                        }
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
                            posX=firstParent.getPosX()+((firstParent.getRectangle().getWidth()+(parentsNumber-1)*(firstParent.getRectangle().getWidth()+10))/parentsNumber);
                       else
                            posX=firstParent.getPosX()+((parentsNumber-1)*(firstParent.getRectangle().getWidth()+10)/parentsNumber);
                       String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                       String value=ejemplo.getListaPasos().get(i).getValor();
                       Nodo node= insertarNodoA(ejemplo.getListaPasos().get(i).getElemento(), simbolo, posX, posYAnterior,value);
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
                   Nodo raiz= insertarNodo(null, simbolo, ejemplo.getNumNodos()*50/2.0, 10.0,null);
                   setPosXAnterior(0);

                }
                else{
                    Nodo parent=this.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(i).getElemento().split(" ")[1]));

                    //if is the first child
                    if(parent.getChildren().isEmpty()){
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo hijo=insertarNodo(parent, simbolo, posXAnterior,(parent.getRectangle().getHeight()*2)+parent.getPosY(),value);

                        parent.getChildren().add(hijo);
                        //moveSiblings(hijo);
                        //eliminarNodoNotExec(hijo, panelPadre);
                        Regla regla=ejemplo.getListaPasos().get(i).getRegla();
                        if (regla!=null){
                            insertarNodoNotExec(parent, regla, posXAnterior, hijo.getPosY(),hijo);

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
                        rectReg.setOnMouseEntered(new EventHandler<MouseEvent>(){

                            @Override
                            public void handle(MouseEvent event) {

                                System.out.println(event.getSource());
                                Rectangle rect=(Rectangle)event.getSource();
                                rect.setFill(Color.YELLOW);

                                gramatica.drawRectangle(gramatica.getRelRectRegla().get(event.getSource()));
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
                        setPosXAnterior(hijo.getPosX());
                    }   
                    else{
                        String simbolo=ejemplo.getListaPasos().get(i).getElemento().split(" ")[0];
                        String value=ejemplo.getListaPasos().get(i).getValor();
                        Nodo hijo=insertarNodo(parent, simbolo,posXAnterior+parent.getRectangle().getWidth()+10,(parent.getRectangle().getHeight()*2)+parent.getPosY(),value);
                        parent.getChildren().add(hijo);

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

                    }
                }
                updatedValues(ejemplo.getListaPasos().get(i).getSimbolosActualizados());
            }
        return pasoSolicitado;
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
                    panelPadre.getChildren().addAll(nodoNotExec.getRectangle(),nodoNotExec.getLabel());
                }
                eliminarNodo(elemElim, i);
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
               posXAnterior=10;
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
    
}
