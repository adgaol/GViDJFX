/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.HashMap;
import java.util.LinkedList;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adgao
 */
public class Nodo {
    
   private String simbolo;
   private Rectangle rectangle;
   private Nodo parent;
   private LinkedList<Nodo> children;
   private Double posX;
   private Double posY;
   private Line line;
   private Label label;
   private HashMap<String , Nodo> hermanos; 
   private Nodo LeftSibling;
   private Rectangle rectRgla;
    public Nodo(String simbolo,Nodo parent) {
        this.simbolo = simbolo;
        this.rectangle = new Rectangle(50,50);
        this.hermanos=new HashMap<>();
        this.parent = parent;
        this.children = new LinkedList<>();
        if(parent!=null && !parent.getChildren().isEmpty())
            this.LeftSibling=parent.getChildren().getLast();
    }
    /**
     * Find the last brother not executed.
     * @return 
     * The last of the brother nodes not executed.
     */
    public Nodo lastSibling(){
        Nodo last=this;
        for(Nodo n: this.hermanos.values()){
            if(last.posX>n.posX)
                last=n;
        }
        return last;
    }
    public String getSimbolo() {
        return simbolo;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Nodo getParent() {
        return parent;
    }

    public LinkedList<Nodo> getChildren() {
        return children;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setParent(Nodo parent) {
        this.parent = parent;
    }

    public void setChildren(LinkedList<Nodo> children) {
        this.children = children;
    }
    
   public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public HashMap<String, Nodo> getHermanos() {
        return hermanos;
    }

    public Nodo getLeftSibling() {
        return LeftSibling;
    }

    public void setLeftSibling(Nodo LeftSibling) {
        this.LeftSibling = LeftSibling;
    }

    public Rectangle getRectRgla() {
        return rectRgla;
    }

    public void setRectRgla(Rectangle rectRgla) {
        this.rectRgla = rectRgla;
    }
    
//   public void setHermanosDelHermano(Nodo hermano) {  
//        Collection<Nodo> aux=hermano.parent.getChildren().getFirst().getHermanos().values();
//        for(Nodo n:aux){
//            if(!n.getSimbolo().equals(hermano.getSimbolo())){
//                hermano.getHermanos().put(n.getSimbolo(), n);
//            }
//        }
//        
//    }
}
