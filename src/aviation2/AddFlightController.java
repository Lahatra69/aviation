/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aviation2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class AddFlightController implements Initializable {

    @FXML
    private TextField numVolField;
    @FXML
    private ComboBox<String> villeDepartComboBox;
    @FXML
    private ComboBox<String> villeArriveeComboBox;
    @FXML
    private TextField fraisField;
    @FXML
    private TableView<Vol> volTable;
    @FXML
    private TableColumn<Vol, String> numVolCol;
    @FXML
    private TableColumn<Vol, String> villeDepartCol;
    @FXML
    private TableColumn<Vol, String> villeArriveeCol;
    @FXML
    private TableColumn<Vol, Integer> fraisCol;
    
    private ObservableList<Vol> volData;
     
     //constructeur
    private EditFlightController editFlightController;

    public AddFlightController(EditFlightController editFlightController) {
        this.editFlightController = editFlightController;
    }
    
    public AddFlightController() {
    // constructeur par défaut sans paramètre
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ComboBox
        villeDepartComboBox.setItems(FXCollections.observableArrayList("Antananarivo", "Diego", "Fianarantsoa", "Mahajanga", "Toamasina", "Toliara"));
        villeArriveeComboBox.setItems(FXCollections.observableArrayList("Antananarivo", "Diego", "Fianarantsoa", "Mahajanga", "Toamasina", "Toliara"));
        
        //Tableaux
        
        // Configure les colonnes du TableView
        
        numVolCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVolId()));
        villeDepartCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVilleDepart()));
        villeArriveeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVilleArrivee()));
        fraisCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFrais()).asObject());

        // Récupère les données de la base de données et les met dans l'ObservableList
        volData = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aviation2", "root", "");
            ResultSet rs = conn.createStatement().executeQuery("SELECT vol_id, ville_depart, ville_arrivee, frais FROM vol");
            while (rs.next()) {
                volData.add(new Vol(rs.getString("vol_id"), rs.getString("ville_depart"), rs.getString("ville_arrivee"), rs.getInt("frais")));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Met l'ObservableList dans le TableView
        volTable.setItems(volData);
    }    

    @FXML
    private void ajouterVolBtn(ActionEvent event) throws IOException{
        Connection conn = null;
        PreparedStatement stmt = null;

    // Récupération des données saisies par l'utilisateur
        String numVol = numVolField.getText();
        String villeDepart = villeDepartComboBox.getValue();
        String villeArrivee = villeArriveeComboBox.getValue();
        int frais;
        if (fraisField.getText().isEmpty()) {
            frais = 0;
        } else {
            frais = Integer.parseInt(fraisField.getText());
        }
        
        if(numVol.isEmpty() || villeDepart == null || villeArrivee == null || frais == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs .");
            alert.showAndWait();
            
            return;
        }

        try {
        // Connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aviation2", "root", "");

        // Vérification si le numéro de vol existe déjà dans la base de données
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM vol WHERE vol_id = ?");
            stmt.setString(1, numVol);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
            // Le numéro de vol existe déjà, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Numéro de vol existant.");
                alert.showAndWait();
            } else {
            // Insertion des données dans la base de données
                stmt = conn.prepareStatement("INSERT INTO vol (vol_id, ville_depart, ville_arrivee, frais) VALUES (?, ?, ?, ?)");
                stmt.setString(1, numVol);
                stmt.setString(2, villeDepart);
                stmt.setString(3, villeArrivee);
                stmt.setInt(4, frais);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 1) {
                // Affichage d'un message de confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Vol ajouté avec succes.");
                    alert.showAndWait();
                    reloadAddFlight(event);
                } else {
                // Affichage d'un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Une erreur s'est produite lors de l'ajout des données dans la base de données.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
        // Gestion de l'exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de la connexion à la base de données.");
            alert.showAndWait();
            e.printStackTrace();
        } finally {
        // Fermeture de la connexion et des ressources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void annulerVolBtn(ActionEvent event) {
    }

    @FXML
    private void onClickModif(ActionEvent event)throws IOException {
        // Récupérer la ligne sélectionnée
        Vol selectedItem = volTable.getSelectionModel().getSelectedItem();
        // Vérifiez si la ligne a été sélectionnée
        if (selectedItem != null) {
            // Charger le fichier FXML pour la fenêtre d'édition
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editFlight.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("editFlight.css").toExternalForm());

            // Obtenir l'instance de EditFlightController
            EditFlightController editFlightController = loader.getController();

            // Transférer les données de la ligne sélectionnée aux champs de la fenêtre d'édition
            editFlightController.setNumVolField2(selectedItem.getVolId());
            editFlightController.getNumVolField2().setEditable(false); // Rendre non modificable

            editFlightController.setVilleDepartComboBox2(selectedItem.getVilleDepart());
            editFlightController.setVilleArriveeComboBox2(selectedItem.getVilleArrivee());
            editFlightController.setFraisField2(String.valueOf(selectedItem.getFrais()));
            
            // Ouvrir la fenêtre d'édition
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            
        }
    }
    
    // Classe interne qui représente un vol
    public static class Vol {
        private final String volId;
        private final String villeDepart;
        private final String villeArrivee;
        private final Integer frais;

        public Vol(String volId, String villeDepart, String villeArrivee, Integer frais) {
            this.volId = volId;
            this.villeDepart = villeDepart;
            this.villeArrivee = villeArrivee;
            this.frais = frais;
        }

        public String getVolId() {
            return volId;
        }

        public String getVilleDepart() {
            return villeDepart;
        }

        public String getVilleArrivee() {
            return villeArrivee;
        }

        public Integer getFrais() {
            return frais;
        }

    }
    
    private void reloadAddFlight(ActionEvent event) throws IOException{
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
    
}
