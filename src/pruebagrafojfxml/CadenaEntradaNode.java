/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adgao
 */
public class CadenaEntradaNode {
    private String element;
    private Label label;
    private Rectangle rectangle;
    private Integer position;

    public CadenaEntradaNode(String element, Label label, Rectangle rectangle, Integer position) {
        this.element = element;
        this.label = label;
        this.rectangle = rectangle;
        this.position = position;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
}
