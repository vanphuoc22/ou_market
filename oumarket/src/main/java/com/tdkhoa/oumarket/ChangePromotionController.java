/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.EditProductController.itemsUnit;
import static com.tdkhoa.oumarket.PrimaryController.pRow;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import pojo.Category;
import pojo.Promotion;
import services.CategoryService;
import services.ProductService;
import services.PromotionService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class ChangePromotionController implements Initializable {

    @FXML
    private ComboBox<Promotion> cbPromotions;
    static ProductService pS = new ProductService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PromotionService promotionService = new PromotionService();
        cbPromotions.setPromptText(pRow.getPromotion_name());
        try {
            List<Promotion> promotions = promotionService.getPromotions(null);
            this.cbPromotions.setItems(FXCollections.observableList(promotions));
        } catch (SQLException ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePromotionHandler(ActionEvent event) throws SQLException {
        if (this.cbPromotions.getValue() != null) {
            pRow.setPromotion_id(this.cbPromotions.getSelectionModel().getSelectedItem().getId());
            pRow.setPromotion_name(this.cbPromotions.getSelectionModel().getSelectedItem().getName());
            try {
                if(pS.updatePromotion(pRow)) {
                    MessageBox.getBox("Mã khuyến mãi", "Thay đổi mã khuyến mãi thành công", Alert.AlertType.INFORMATION).show();
                }
            }
            catch(SQLException ex) {
                MessageBox.getBox("Mã khuyến mãi", "Thay đổi mã khuyến mãi thất bại", Alert.AlertType.ERROR).show();
            }
        }
    }
}
