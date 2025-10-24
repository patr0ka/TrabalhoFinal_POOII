package com.appacademia.controller;

import com.appacademia.dao.UsuarioDAO;
import com.appacademia.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtId;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnEntrar;
    @FXML private Label lblErro;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void btnEntrarOnAction() {
        try {
            String idStr = txtId.getText().trim();
            String senha = txtSenha.getText().trim();

            if (idStr.isEmpty() || senha.isEmpty()) {
                lblErro.setText("Preencha todos os campos!");
                lblErro.setVisible(true);
                return;
            }

            int idUsuario;
            try {
                idUsuario = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                lblErro.setText("O ID deve ser um número!");
                lblErro.setVisible(true);
                return;
            }

            Usuario usuario = usuarioDAO.autenticarPorId(idUsuario, senha);
            if (usuario == null) {
                lblErro.setText("ID ou senha incorretos!");
                lblErro.setVisible(true);
                return;
            }

            abrirTelaPrincipal(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            lblErro.setText("Erro ao tentar logar!");
            lblErro.setVisible(true);
        }
    }

    private void abrirTelaPrincipal(Usuario usuario) {
        try {
            FXMLLoader loader;
            Parent root;

            if (usuario.getTipoUsuario().equalsIgnoreCase("cliente")) {
                loader = new FXMLLoader(getClass().getResource("/com/appacademia/view/main_aluno_view.fxml"));
                root = loader.load();

                // passa o usuário logado para o controller do aluno
                MainAlunoController controller = loader.getController();
                controller.setUsuarioLogado(usuario);

            } else {
                loader = new FXMLLoader(getClass().getResource("/com/appacademia/view/main_view.fxml"));
                root = loader.load();

                // passa o usuário logado para o controller do admin
                MainController controller = loader.getController();
                controller.setUsuarioLogado(usuario);
            }

            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
