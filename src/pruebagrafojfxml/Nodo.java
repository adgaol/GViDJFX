/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
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
   private Boolean isTerminal;//true=terminal false=no terminal
   private int font; 
   private ArrayList<Line> lines;
   private Double widthRectRgla;
   private String value;
    public Nodo(String simbolo,Nodo parent,Boolean isTerminal,int font,String value) {
        this.simbolo = simbolo;
        this.font=font;
//        if((font<=15) && (simbolo.length()<=3))
        this.rectangle = new Rectangle(100,50);
        this.rectangle.setStroke(Paint.valueOf("000000"));
//        else
//            this.rectangle=new Rectangle(50+this.simbolo.length()-3+font,50);
//        if((font>15) && (simbolo.length()>=3)){
//            rectangle.setWidth(50+this.simbolo.length()-3+font);
//        }
        this.hermanos=new HashMap<>();
        this.parent = parent;
        this.children = new LinkedList<>();
        this.isTerminal=isTerminal;
        this.lines=new ArrayList<>();
        this.value=value;
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

    public Boolean getIsTerminal() {
        return isTerminal;
    }

    public void setIsTerminal(Boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public Double getWidthRectRgla() {
        return widthRectRgla;
    }

    public void setWidthRectRgla(Double widthRectRgla) {
        this.widthRectRgla = widthRectRgla;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}

