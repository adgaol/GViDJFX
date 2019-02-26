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
private HashMap<Regla,Label> reglaLabel;
private HashMap<Regla,Boolean>formaReglas;//true=forma corta.false forma larga con las acciones
    public Gramatica(FicheroXML ejemplo) {
        this.ejemplo = ejemplo;
        this.reglas = ejemplo.getListaGramatica();
        posYAnterior=10;
        relRectRegla=new HashMap<>();
        reglaLabel=new HashMap<>();
        formaReglas=new HashMap<>();
    }
    /**
     * from a rule produce the String correspondent without the actions
     * @param regla
     * rule origing
     * @return 
     * String from the rule
     */
    public String formarReglaCorta(Regla regla){
        String result="";
        for (Simbolo s: regla.getRegla()){
            result+=s.getValor()+"  ";
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
    public String formarReglaLarga(Regla regla){
        String result="";
        int i=0;
        int posAccion=0;
        //regla.getAcciones().get(0).;
        for (Simbolo s: regla.getRegla()){
            if((posAccion<regla.getAcciones().size())&&(i==regla.getAcciones().get(posAccion).getPos())){
                result+="  "+regla.getAcciones().get(posAccion).getValor()+"  ";
                posAccion++;
            }
            else
                result+=s.getValor()+"  "; 
            i++;
        }
        return result;
    }
    /**
     * build and draw the grammar
     * @param panelPadre 
     * panel to draw the grammar
     */
    public void construir(Pane panelPadre){
        for(Regla r: reglas){
            Label l=new Label(formarReglaCorta(r));
            l.setLayoutX(10);
            l.setLayoutY(posYAnterior);
            posYAnterior+=30;
            panelPadre.getChildren().add(l);
            reglaLabel.put(r, l);
        }
    }
    public void cambiarFormaRegla(Regla regla){
        Label l=reglaLabel.get(regla);
        if(formaReglas.get(regla)){
            
            l.setText(formarReglaLarga(regla));
            formaReglas.put(regla, false);
            
        }
        else{
            l.setText(formarReglaCorta(regla));
            formaReglas.put(regla, true); 
        }
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
