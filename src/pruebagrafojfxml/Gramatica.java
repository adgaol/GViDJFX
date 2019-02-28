/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adgao
 */
public class Gramatica {
    
private FicheroXML ejemplo;
private ArrayList<Regla> reglas;
private int posYAnterior;
private HashMap<Rectangle,Regla>relRectRegla;
private HashMap<String,Rectangle> ruleRectGramm;
private HashMap<String,ArrayList<Label>> reglaLabel;
private HashMap<String,Boolean>formaReglas;//true=forma larga con las acciones.false forma corta
private HashMap<String,Regla> idRegla;//para poder obtener las reglas con simbolos
private Pane panelPadre;
    public Gramatica(FicheroXML ejemplo,Pane panelPadre) {
        this.ejemplo = ejemplo;
        this.reglas = ejemplo.getListaGramatica();
        posYAnterior=10;
        this.panelPadre=panelPadre;
        relRectRegla=new HashMap<>();
        reglaLabel=new HashMap<>();
        formaReglas=new HashMap<>();
        idRegla=new HashMap<>();
        ruleRectGramm=new HashMap<>();
    }
    /**
     * from a rule produce the String correspondent without the actions
     * @param regla
     * rule origing
     * @return 
     * String from the rule
     */
    public ArrayList<Label> formarReglaCorta(Regla regla){
        ArrayList<Label> result=new ArrayList<>();
        regla=idRegla.get(regla.getId());
        for (Simbolo s: regla.getRegla()){
            Label l=new Label(s.getValor()+"  ");
            result.add(l);
        }
        return result;
    }
    /**
     * from a rule produce the String correspondent with the actions 
     * @param regla
     * rule origing
     * @return 
     * String from the rule
     */
    public ArrayList<Label> formarReglaLarga(Regla regla){
        ArrayList<Label> result=new ArrayList<>();
        regla=idRegla.get(regla.getId());
        int i=0;
        int posAccion=0;
        //regla.getAcciones().get(0).;
        for (Simbolo s: regla.getRegla()){
            if((posAccion<regla.getAcciones().size())&&(i==regla.getAcciones().get(posAccion).getPos())){
                Label l=new Label("  "+regla.getAcciones().get(posAccion).getValor()+"  ");
                Label ls=new Label(s.getValor()+" ");
                //result+=+s.getValor()+"  ";
                result.add(l);
                result.add(ls);
                posAccion++;
            }
            else{
                Label l=new Label(s.getValor()+"  ");
                result.add(l);
            }
            i++;
        }
        return result;
    }
    /**
     * build and draw the grammar
     * @param panelPadre 
     * panel to draw the grammar
     */
    public void construir(/*Pane panelPadre*/){
        for(Regla r: reglas){
            idRegla.put(r.getId(), r);
            double posXAnterior=10;
            ArrayList<Label> labels=formarReglaCorta(r);
            for(Label l:labels){
                l.setLayoutX(posXAnterior);
                l.setLayoutY(posYAnterior);
                panelPadre.getChildren().add(l);
                posXAnterior=posXAnterior+l.getFont().getSize()/2*l.getText().length()+20;
            }
            
            posYAnterior+=30;
            
            reglaLabel.put(r.getId(), labels);
            formaReglas.put(r.getId(), false);
            
        }
    }
    public void cambiarFormaRegla(Regla regla){
        ArrayList<Label> l=reglaLabel.get(regla.getId());
        
        double posXAnterior=10;
        Double posY=l.get(0).getLayoutY();
        panelPadre.getChildren().removeAll(l);
        double width=0;
        if(formaReglas.get(regla.getId())){   
            l=formarReglaCorta(regla);
            for(Label ls:l){
                ls.setLayoutX(posXAnterior);
                ls.setLayoutY(posY);
                panelPadre.getChildren().add(ls);
                posXAnterior=posXAnterior+ls.getFont().getSize()/2*ls.getText().length()+20;
                width+=ls.getText().length()*ls.getFont().getSize()/2+20;
            }
            ruleRectGramm.get(regla.getId()).setWidth(width);
            formaReglas.put(regla.getId(), false);
            reglaLabel.put(regla.getId(), l);
        }
        else{
            l=formarReglaLarga(regla);
            for(Label ls:l){
                ls.setLayoutX(posXAnterior);
                ls.setLayoutY(posY);
                panelPadre.getChildren().add(ls);
                posXAnterior=posXAnterior+ls.getFont().getSize()/2*ls.getText().length()+20;
                width+=ls.getText().length()*ls.getFont().getSize()/2+20;
            }
            ruleRectGramm.get(regla.getId()).setWidth(width);
            formaReglas.put(regla.getId(), true); 
            reglaLabel.put(regla.getId(), l);
        }
    }
    public void drawRectangle(Regla regla){
        ArrayList<Label> l=reglaLabel.get(regla.getId());
        Double posY=l.get(0).getLayoutY();
        Double posX=l.get(0).getLayoutX()-5;
        Double width=0.0;
        for(Label label:l){
            width+=label.getText().length()*label.getFont().getSize()/2+20;
        }
        Rectangle rect=new Rectangle(posX, posY, width,l.get(0).getFont().getSize()+5);
        rect.setOpacity(0.3);
        rect.setFill(Color.YELLOW);
        //if (ruleRectGramm.get(regla.getId())==null)
        ruleRectGramm.put(regla.getId(), rect);
        panelPadre.getChildren().add(rect);
    }
    public Rectangle erasedRectangle(Regla regla){
          Rectangle rect=ruleRectGramm.get(regla.getId());
          ruleRectGramm.remove(regla.getId());
//        ArrayList<Label> l=reglaLabel.get(regla.getId());
//        Double posY=l.get(0).getLayoutY();
//        Double posX=l.get(0).getLayoutX()-5;
//        Double width=0.0;
//        for(Label label:l){
//            width+=label.getText().length()*label.getFont().getSize()/2;
//        }
//        Rectangle rect=new Rectangle(posX, posY, width,l.get(0).getFont().getSize()/2 );
        panelPadre.getChildren().remove(rect);
        return rect;
    }
    public FicheroXML getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(FicheroXML ejemplo) {
        this.ejemplo = ejemplo;
    }

    public ArrayList<Regla> getReglas() {
        return reglas;
    }

    public void setReglas(ArrayList<Regla> reglas) {
        this.reglas = reglas;
    }

    public int getPosYAnterior() {
        return posYAnterior;
    }

    public void setPosYAnterior(int posYAnterior) {
        this.posYAnterior = posYAnterior;
    }

    public HashMap<Rectangle, Regla> getRelRectRegla() {
        return relRectRegla;
    }

    public void setRelRectRegla(HashMap<Rectangle, Regla> relRectRegla) {
        this.relRectRegla = relRectRegla;
    }
    
}
