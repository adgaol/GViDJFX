/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author adgao
 */
public class PruebaGrafoJFXML extends Application {
    private static Stage stage;
    private static Parameters argument;
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        PruebaGrafoJFXML.stage = stage;
    }

    public static Parameters getArgument() {
        return argument;
    }

    public static void setArgument(Parameters argument) {
        PruebaGrafoJFXML.argument = argument;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.setArgument(getParameters()); 
        System.out.println(this.getArgument().getRaw());
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        
        Scene scene = new Scene(root);
        setStage(stage);
        stage.setScene(scene);
        stage.show();
        verTutorial();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public void verTutorial() throws IOException {
        
        Configuracion configuration=new Configuracion();
        configuration.cargarConfiguracion("./config/configActual.xml");
        if(!configuration.getOcultarTutorial()){
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentVerTutorial.fxml"));

            Scene scene = new Scene(root);
            Stage configurationScreen=new Stage();
            configurationScreen.setScene(scene);
            configurationScreen.initModality(Modality.WINDOW_MODAL);
            configurationScreen.setTitle("Ver Tutorial");
                // Specifies the owner Window (parent) for new window
                Stage p1=PruebaGrafoJFXML.getStage();
            configurationScreen.initOwner(PruebaGrafoJFXML.getStage());
               //configuration.onCloseRequestProperty().addListener();
                // Set position of second window, related to primary window.
                //newWindow.setX(primaryStage.getX() + 200);
                //newWindow.setY(primaryStage.getY() + 100);
            configurationScreen.show();
        }
    }
}
