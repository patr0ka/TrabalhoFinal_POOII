package com.appacademia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class UsuariosController {

    @FXML private TableView<?> tabelaUsuarios;
    @FXML private TableColumn<?, ?> colNome;
    @FXML private TableColumn<?, ?> colEmail;
    @FXML private TableColumn<?, ?> colTelefone;
    @FXML private TextField txtPesquisa;
    @FXML private Button btnAdicionarUsuario;

    @FXML
    public void initialize() {
        System.out.println("✅ Tela de usuários carregada com sucesso!");
    }

    @FXML
    private void abrirTelaAdicionarUsuario() {
        System.out.println("🔸 Clique detectado no botão Adicionar Usuário");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appacademia/view/incluirusuario_view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Adicionar Novo Usuário");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            System.out.println("🟢 Tela de cadastro de usuário aberta!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erro ao abrir a tela de cadastro de usuário: " + e.getMessage());
        }
    }
}
