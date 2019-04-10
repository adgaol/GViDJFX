/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebagrafojfxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author adgao
 */
public class FXMLVerTutorialController implements Initializable {
    @FXML
    public void handleAceptar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentTutorial.fxml"));

        Scene scene = new Scene(root);
        Stage configurationScreen=new Stage();
        configurationScreen.setScene(scene);
        configurationScreen.initModality(Modality.WINDOW_MODAL);
        configurationScreen.setTitle("Ver Tutorial");
            // Specifies the owner Window (parent) for new window
        configurationScreen.initOwner(PruebaGrafoJFXML.getStage());
           //configuration.onCloseRequestProperty().addListener();
            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);
        configurationScreen.show();
        Stage stage = (Stage) aceptButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleCancelar(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) aceptButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public CheckBox ocultar;
    @FXML
    public Button aceptButton;
    @FXML
    public Button cancelarButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     //https://stackoverflow.com/questions/13726824/javafx-event-triggered-when-selecting-a-check-box
     ocultar.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              Configuracion config=FXMLDocumentController.getConfiguration();
              config.guardarConfiguracionOcultarTutorial(config.getRuta(), newValue);
               

            }

        });
    } 
    
}
