/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aviation2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class EditFlightController implements Initializable {
    @FXML
    private Button valideBtn;
    @FXML
    private TextField numVolField2;
    @FXML
    private TextField fraisField2;
    @FXML
    private ComboBox<String> villeDepartComboBox2;
    @FXML
    private ComboBox<String> villeArriveeComboBox2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ComboBox
        villeDepartComboBox2.setItems(FXCollections.observableArrayList("Antananarivo", "Diego", "Fianarantsoa", "Mahajanga", "Toamasina", "Toliara"));
        villeArriveeComboBox2.setItems(FXCollections.observableArrayList("Antananarivo", "Diego", "Fianarantsoa", "Mahajanga", "Toamasina", "Toliara"));
        
    }    
    
    //Setteur
    
    public void setNumVolField2(String text) {
        numVolField2.setText(text);
    }

    public void setVilleDepartComboBox2(String value) {
        villeDepartComboBox2.setValue(value);
    }

    public void setVilleArriveeComboBox2(String value) {
        villeArriveeComboBox2.setValue(value);
    }

    public void setFraisField2(String text) {
        fraisField2.setText(text);
    }
    
    @FXML
    private void onClickValide(ActionEvent event) {
    }
    
    //Getter
    public TextField getNumVolField2() {
        return numVolField2;
    }
    
}
