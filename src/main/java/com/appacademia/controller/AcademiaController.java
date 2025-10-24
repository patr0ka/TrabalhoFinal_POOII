package com.appacademia.controller;

import com.appacademia.dao.AcademiaDAO;
import com.appacademia.model.Academia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class AcademiaController {

    @FXML private TableView<Academia> tableAcademias;
    @FXML private TableColumn<Academia, String> colNome;
    @FXML private TableColumn<Academia, String> colCnpj;
    @FXML private TableColumn<Academia, String> colLocal;

    private AcademiaDAO academiaDAO = new AcademiaDAO();
    private ObservableList<Academia> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colCnpj.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCnpj()));
        colLocal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocalizacao()));
        loadAcademias();
    }

    private void loadAcademias() {
        list.clear();
        try {
            List<Academia> l = academiaDAO.findAll();
            list.addAll(l);
            tableAcademias.setItems(list);
        } catch (SQLException e) {
            showError(e);
        }
    }

    @FXML
    private void onNovoAcademia() {
        Dialog<Academia> d = new Dialog<>();
        d.setTitle("Nova Academia");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField tfNome = new TextField();
        TextField tfCnpj = new TextField();
        TextField tfLocal = new TextField();
        TextField tfTel = new TextField();
        javafx.scene.layout.GridPane g = new javafx.scene.layout.GridPane();
        g.setHgap(10); g.setVgap(10);
        g.add(new Label("Nome"),0,0); g.add(tfNome,1,0);
        g.add(new Label("CNPJ"),0,1); g.add(tfCnpj,1,1);
        g.add(new Label("Localização"),0,2); g.add(tfLocal,1,2);
        g.add(new Label("Telefone"),0,3); g.add(tfTel,1,3);
        d.getDialogPane().setContent(g);
        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                Academia a = new Academia();
                a.setNome(tfNome.getText()); a.setCnpj(tfCnpj.getText()); a.setLocalizacao(tfLocal.getText()); a.setTelefone(tfTel.getText());
                return a;
            }
            return null;
        });
        d.showAndWait().ifPresent(a -> {
            try {
                academiaDAO.insert(a);
                loadAcademias();
            } catch (SQLException e) { showError(e); }
        });
    }

    @FXML
    private void onExcluirAcademia() {
        Academia sel = tableAcademias.getSelectionModel().getSelectedItem();
        if (sel == null) { new Alert(Alert.AlertType.WARNING, "Selecione uma academia.").showAndWait(); return; }
        Alert c = new Alert(Alert.AlertType.CONFIRMATION, "Excluir academia " + sel.getNome() + "?", ButtonType.YES, ButtonType.NO);
        c.showAndWait().ifPresent(b -> { if (b == ButtonType.YES) {
            try { academiaDAO.deleteById(sel.getId()); loadAcademias(); } catch (SQLException e) { showError(e); }
        }});
    }

    private void showError(Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
}
