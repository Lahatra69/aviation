/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aviation2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class AdminHomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickAddPlane(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("addFlight.fxml"));
        Parent root = loader.load();

    // Créer une nouvelle scène avec la vue chargée 
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("addFlight.css").toExternalForm());
    // Obtenir la référence de la scène actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour la nouvelle scène 
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickAccueil(ActionEvent event) {
    }
    
}
