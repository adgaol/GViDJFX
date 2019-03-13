/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    @FXML
    private Button masZoom;
    @FXML
    private Slider sliderZoom;
    @FXML
    private void handleMasMenosZoom(ActionEvent event) {
        if(event.getSource().equals(masZoom)){
            
            zoom((sliderZoom.getValue()+10)/100);
            sliderZoom.setValue((int)(sliderZoom.getValue()+10));
        }
        else{
          
           zoom((sliderZoom.getValue()-10)/100);
           sliderZoom.setValue((int)(sliderZoom.getValue()-10));
        }
        configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),(int)sliderZoom.getValue());
           
    }
    @FXML
    public void handleOpenConfiguration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentConfig.fxml"));

        Scene scene = new Scene(root);
        Stage configurationScreen=new Stage();
        configurationScreen.setScene(scene);
        configurationScreen.initModality(Modality.WINDOW_MODAL);
        configurationScreen.setTitle("Configuración");
            // Specifies the owner Window (parent) for new window
        configurationScreen.initOwner(PruebaGrafoJFXML.getStage());
           //configuration.onCloseRequestProperty().addListener();
            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);
        configurationScreen.show();
        configurationScreen.setOnHiding(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
            //int cont=contador;
            configuration.cargarConfiguracion("./config/configActual.xml");
            graph.updateGraph();
            grammar.updateGrammar();
            entryChain.actualizarCadena(graph.getContador());
            
          }
      }); 


    }
    @FXML
    public void handleDefaultConfiguration(ActionEvent event) throws IOException {
        
            //int cont=contador;
            configuration.cargarConfiguracion("./config/configDefecto.xml");
            graph.updateGraph();
            grammar.updateGrammar();
            entryChain.actualizarCadena(graph.getContador());
            zoom(configuration.getZoom()/100);
            sliderZoom.setValue(configuration.getZoom());
            configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),configuration.getZoom());


    }
    @FXML
    private void handleCargarArchivo(ActionEvent event) {
        elegirArchivo("xml");
                 

    }
    @FXML
    private void handleAnteriorAction(ActionEvent event) {
         if(0<graph.getContador()){
            System.out.println("LEFT");
            graph.eliminar( graph.getContador()-1);
         }
//        label.setText("Hello World!");
    }
    @FXML
    private void handleSiguienteAction(ActionEvent event) {
        if(ejemplo.getNumNodos()>graph.getContador()){
                System.out.println("RIGHT");
                graph.construir(graph.getContador()+1);
        }
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrFinAction(ActionEvent event) {
        System.out.println("Fin");
        graph.construir(ejemplo.getNumNodos());
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrInicioAction(ActionEvent event) {
        System.out.println("Inicio");
            graph.eliminar(0);
//        label.setText("Hello World!");
    }

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
    private Grafo graph;
    private Gramatica grammar;
    private CadenaEntrada entryChain;
    private Pane paneGrafo;
    private Pane paneGramatica;
    private Pane paneCadenaEntrada;
    private Configuracion configuration;
    private Group graphGroup;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      elegirArchivo("xml");  
//      ejemplo = new FicheroXML();
//      ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
//  //    HashMap rectangles=new HashMap();
//      configuration=new Configuracion();
//      configuration.cargarConfiguracion("./config/configActual.xml");
//      paneGrafo=new Pane();  
//      graphGroup=new Group();
//      graphGroup.getChildren().add(paneGrafo);
//      grafo.setContent(graphGroup);
//      paneGramatica=new Pane();  
//      gramatica.setContent(paneGramatica);
//      paneCadenaEntrada=new Pane();  
//      cadenaEntrada.setContent(paneCadenaEntrada);
////       EventDispatcher scrollPaneEventDispatcher = grafo.getEventDispatcher();
////       grafo.setEventDispatcher((event, tail) -> {
////            if (KeyEvent.ANY.equals(event.getEventType().getSuperType())) {
////                System.out.println("DISPATCH\tScrollPane\tevent=" + event.getEventType());
////            }
////            Event eventToDispatch = scrollPaneEventDispatcher.dispatchEvent(event, tail);
////            if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
////                if (KeyCode.LEFT.equals(((KeyEvent) event).getCode()) || KeyCode.RIGHT.equals(((KeyEvent) event).getCode())) {
////                    if (eventToDispatch == null) {
////                        return event;
////                    }
////                }
////            }
////            return eventToDispatch;
////        });
//        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
//                event ->handleKeyAction(event));
////         grafo.addEventHandler(KeyEvent.KEY_TYPED,
////                event -> handleKeyAction(event));
//        
////      Nodo n1=new Nodo(ejemplo.getListaPasos().get(0).getElemento());
////      Label label1=new Label("patata");
////      Rectangle r1=new Rectangle(100, 100);
////      r1.setLayoutX(500);
////      
////      label1.setLayoutX(500+r1.getWidth()/3);
////      label1.setLayoutY(r1.getWidth()/3);
////      r1.setFill(Paint.valueOf("ff00ff"));
////      Rectangle r2=new Rectangle(100, 100);
////      r2.setLayoutX(r1.getLayoutX()+500);
////      r2.setLayoutY(300);
////      r2.setFill(Paint.valueOf("ff00ff"));
////      Line line=new Line(r1.getLayoutX()+r1.getWidth(), r1.getLayoutY()+r1.getHeight(), r2.getLayoutX(),r2.getLayoutY());
////      r1.setOnMouseEntered((event) -> {
////
////        Rectangle r=new Rectangle(100, 100);
////        r.setLayoutX(r1.getLayoutX());
////        r.setLayoutY(600);
////        r.setOpacity(0.5);
////        rectangles.put(r1, r);
////        pane.getChildren().add(r);
////      });
////      r1.setOnMouseExited((event) -> {
////
////        pane.getChildren().remove(rectangles.get(r1));
////      });
//      //pane.getChildren().addAll(n1.getRectangle(),r2,line,label1);
//      entryChain=new CadenaEntrada(ejemplo.getCadena(),paneCadenaEntrada,configuration);
//      entryChain.construir();
//      grammar=new Gramatica(ejemplo,paneGramatica,configuration);
//      grammar.construir(/*paneGramatica*/);
//      
//      graph=new Grafo(ejemplo,grammar,entryChain,paneGrafo,configuration);
//      sliderZoom.valueChangingProperty().addListener(new ChangeListener<Boolean>(){
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//               System.out.println(sliderZoom.getValue());
//                zoom((sliderZoom.getValue())/100);
//                
//                configuration.guardarConfiguracion(".//config//configActual.xml",
//                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
//                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),(int)sliderZoom.getValue());
//        
//            }
//           
//       });
//        System.out.println(sliderZoom.getValue());
//        sliderZoom.setValue(configuration.getZoom());
//        zoom((sliderZoom.getValue())/100);
//        
////        paneGrafo.setScaleX((sliderZoom.getValue())/100);
////        paneGrafo.setScaleY((sliderZoom.getValue())/100);
////        sliderZoom.setValue((int)(sliderZoom.getValue()));
////      graph.construir(contador, 8, pane);
////      contador=8;
//      
////      graph.eliminar(contador, 0, pane);
////      contador=0;
////      graph.construir(contador, 16, pane);
////    n1=  graph.insertarNodo(null, pane, n1.getSimbolo(), 500, 0);
////    
////    Nodo n=graph.getNodos().get(Integer.parseInt(ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[1]));
////      graph.insertarNodo(n, pane, ejemplo.getListaPasos().get(contador+1).getElemento().split(" ")[0], 250, 500);
    } 
    /**
     * scale the pane with the graph
     * @param zoom 
     * percentage of scaling
     */
    public void zoom(double zoom ){
        paneGrafo.setScaleX(zoom);
        paneGrafo.setScaleY(zoom);
        //sliderZoom.setValue((int)(sliderZoom.getValue()));
    }

    private void elegirArchivo(String tipo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(".//traductores"));
        String path=null;
        //if(tipo.equals("xml")){
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
        File file=fileChooser.showOpenDialog(PruebaGrafoJFXML.getStage());
        path=file.getAbsolutePath();
        //}
        ejemplo = new FicheroXML();
        //ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\VisTDS\\traductores\\descend.xml"); 
        ejemplo.cargarXml(path); 

        configuration=new Configuracion();
        configuration.cargarConfiguracion("./config/configActual.xml");
        paneGrafo=new Pane();  
        graphGroup=new Group();
        graphGroup.getChildren().add(paneGrafo);
        grafo.setContent(graphGroup);
        paneGramatica=new Pane();  
        gramatica.setContent(paneGramatica);
        paneCadenaEntrada=new Pane();  
        cadenaEntrada.setContent(paneCadenaEntrada);
//       
//        
        grafo.addEventFilter(KeyEvent.KEY_RELEASED,
                event ->handleKeyAction(event));
//      
        entryChain=new CadenaEntrada(ejemplo.getCadena(),paneCadenaEntrada,configuration);
        entryChain.construir();
        grammar=new Gramatica(ejemplo,paneGramatica,configuration);
        grammar.construir(/*paneGramatica*/);
      
        graph=new Grafo(ejemplo,grammar,entryChain,paneGrafo,configuration,ejemplo.getTipoTraductor());
        sliderZoom.valueChangingProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               System.out.println(sliderZoom.getValue());
                zoom((sliderZoom.getValue())/100);

                configuration.guardarConfiguracion(".//config//configActual.xml",
                         configuration.getLetraArbol(),configuration.getLetraTraductor(),configuration.getLetraCadena(),
                         configuration.getColorTerminal(),configuration.getColorNoTerminal(),configuration.getLetraTerminal(),configuration.getLetraNoTerminal(),configuration.getColorLeido(),configuration.getColorPend(),configuration.getColorAccSem(),configuration.getTipoLetra(),configuration.getSizeAcciones(),(int)sliderZoom.getValue());

            }

        });
        System.out.println(sliderZoom.getValue());
        sliderZoom.setValue(configuration.getZoom());
        zoom((sliderZoom.getValue())/100);
    }
}
