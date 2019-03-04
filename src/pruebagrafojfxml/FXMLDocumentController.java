/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author adgao
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ScrollPane grafo;
    @FXML
    private ScrollPane gramatica;
    @FXML
    private ScrollPane cadenaEntrada;

//    @FXML
//    private void handleMouseAction(MouseEvent event) {
//        System.out.println("You clicked me!");
//        //panel.requestFocus();
////        label.setText("Hello World!");
//    }
    
    private void handleKeyAction(KeyEvent event) {
        if (event.getCode()==KeyCode.LEFT){
            if(0<graph.getContador()){
            System.out.println("LEFT");
            graph.eliminar( graph.getContador()-1);
            //contador--;
            // graphPane.requestFocus();
            }
        }
        else if (event.getCode()==KeyCode.RIGHT){
            if(ejemplo.getNumNodos()>graph.getContador()){
                System.out.println("RIGHT");
                graph.construir(graph.getContador()+1);
                //this.contador+=1;
             //graphPane.requestFocus();
            }
        }
        else if (event.getCode()==KeyCode.HOME){
            System.out.println("Inicio");
            graph.eliminar(0);
           // irInicio();
             //graphPane.requestFocus();
            
        }
        else if (event.getCode()==KeyCode.END){
            System.out.println("Fin");
            graph.construir(ejemplo.getNumNodos());
            //irFin();
             //graphPane.requestFocus();
            
        }
        else{
            System.out.println(event.getCode());
        }
    }
    private FicheroXML ejemplo;
    //private int contador=0;
    private Grafo graph;
    private Gramatica grammar;
    private CadenaEntrada entryChain;
    private Pane paneGrafo;
    private Pane paneGramatica;
    private Pane paneCadenaEntrada;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      ejemplo = new FicheroXML();
      ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
  //    HashMap rectangles=new HashMap();
      paneGrafo=new Pane();  
      grafo.setContent(paneGrafo);
      paneGramatica=new Pane();  
      gramatica.setContent(paneGramatica);
      paneCadenaEntrada=new Pane();  
      cadenaEntrada.setContent(paneCadenaEntrada);
//       EventDispatcher scrollPaneEventDispatcher = grafo.getEventDispatcher();
//       grafo.setEventDispatcher((event, tail) -> {
//            if (KeyEvent.ANY.equals(event.getEventType().getSuperType())) {
//                System.out.println("DISPATCH\tScrollPane\tevent=" + event.getEventType());
//            }
//            Event eventToDispatch = scrollPaneEventDispatcher.dispatchEvent(event, tail);
//            if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
//                if (KeyCode.LEFT.equals(((KeyEvent) event).getCode()) || KeyCode.RIGHT.equals(((KeyEvent) event).getCode())) {
//                    if (eventToDispatch == null) {
//                        return event;
//                    }
//                }
//            }
//            return eventToDispatch;
//        });
        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
                event ->handleKeyAction(event));
//         grafo.addEventHandler(KeyEvent.KEY_TYPED,
//                event -> handleKeyAction(event));
        
//      Nodo n1=new Nodo(ejemplo.getListaPasos().get(0).getElemento());
//      Label label1=new Label("patata");
//      Rectangle r1=new Rectangle(100, 100);
//      r1.setLayoutX(500);
//      
//      label1.setLayoutX(500+r1.getWidth()/3);
//      label1.setLayoutY(r1.getWidth()/3);
//      r1.setFill(Paint.valueOf("ff00ff"));
//      Rectangle r2=new Rectangle(100, 100);
//      r2.setLayoutX(r1.getLayoutX()+500);
//      r2.setLayoutY(300);
//      r2.setFill(Paint.valueOf("ff00ff"));
//      Line line=new Line(r1.getLayoutX()+r1.getWidth(), r1.getLayoutY()+r1.getHeight(), r2.getLayoutX(),r2.getLayoutY());
//      r1.setOnMouseEntered((event) -> {
//
//        Rectangle r=new Rectangle(100, 100);
//        r.setLayoutX(r1.getLayoutX());
//        r.setLayoutY(600);
//        r.setOpacity(0.5);
//        rectangles.put(r1, r);
//        pane.getChildren().add(r);
//      });
//      r1.setOnMouseExited((event) -> {
//
//        pane.getChildren().remove(rectangles.get(r1));
//      });
      //pane.getChildren().addAll(n1.getRectangle(),r2,line,label1);
      entryChain=new CadenaEntrada(ejemplo.getCadena(),paneCadenaEntrada);
      entryChain.construir();
      grammar=new Gramatica(ejemplo,paneGramatica);
      grammar.construir(/*paneGramatica*/);
      
      graph=new Grafo(ejemplo,grammar,entryChain,paneGrafo);
      
//      graph.construir(contador, 8, pane);
//      contador=8;
      
//      graph.eliminar(contador, 0, pane);
//      contador=0;
//      graph.construir(contador, 16, pane);
//    n1=  graph.insertarNodo(null, pane, n1.getSimbolo(), 500, 0);
//    
//    Nodo n=graph.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[1]));
//      graph.insertarNodo(n, pane, ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[0], 250, 500);
    }    
    
}
