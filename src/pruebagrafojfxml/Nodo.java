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
    public Nodo(String simbolo) {
        this.simbolo = simbolo;
        this.rectangle = new Rectangle(50,50);
        this.hermanos=new HashMap<>();
        this.parent = null;
        this.children = new LinkedList<>();
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
   
}
