package com.appacademia.controller;

import com.appacademia.dao.TreinoDAO;
import com.appacademia.model.Treino;
import com.appacademia.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class MainAlunoController {

    @FXML private TableView<Treino> tabelaTreinos;
    @FXML private TableColumn<Treino, String> colTipoTreino;
    @FXML private TableColumn<Treino, String> colData;
    @FXML private TableColumn<Treino, String> colObs;
    @FXML private Button btnSair;

    private final TreinoDAO treinoDAO = new TreinoDAO();
    private Usuario usuarioLogado;

    @FXML
    public void initialize() {
        colTipoTreino.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTipoTreino()));
        colData.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDataCriacao().toLocalDate().toString()));
        colObs.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getObservacoes()));
    }

    // ✅ Chamado após o login
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        System.out.println("🎓 Aluno logado: " + usuario.getNome() + " (ID: " + usuario.getId() + ")");
        carregarTreinos();
    }

    private void carregarTreinos() {
        if (usuarioLogado == null) return;

        try {
            List<Treino> treinos = treinoDAO.findAllByUsuario(usuarioLogado.getId());
            tabelaTreinos.getItems().setAll(treinos);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar treinos!").showAndWait();
        }
    }

    @FXML
    private void logout() {
        // Fecha a tela atual e volta para o login
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
