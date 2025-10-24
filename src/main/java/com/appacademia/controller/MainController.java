package com.appacademia.controller;

import com.appacademia.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML private StackPane contentPane;
    @FXML private Button btnUsuarios;
    @FXML private Button btnAcademias;
    @FXML private Button btnTreinos;
    @FXML private TextField searchField;

    private Usuario usuarioLogado;
    private static final String VIEW_PATH = "/com/appacademia/view/";

    // Recebe o usuário logado do LoginController
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
        System.out.println("✅ Usuário logado: " + usuario.getNome() + " (" + usuario.getTipoUsuario() + ")");
        configurarInterfacePorTipo();
    }

    private void configurarInterfacePorTipo() {
        if (usuarioLogado == null) return;

        // Se for cliente (aluno)
        if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("cliente")) {
            btnUsuarios.setVisible(false);
            btnAcademias.setVisible(false);
            loadView("treinosaluno_view.fxml");
        }
        // Se for funcionário (admin)
        else if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("funcionario")) {
            btnUsuarios.setVisible(true);
            btnAcademias.setVisible(true);
            loadView("treinosadm_view.fxml");
        }

        // Configura ações dos botões (depois de saber o tipo)
        btnUsuarios.setOnAction(e -> loadView("users_view.fxml"));
        btnAcademias.setOnAction(e -> loadView("academias_view.fxml"));
        btnTreinos.setOnAction(e -> {
            if (usuarioLogado.getTipoUsuario().equalsIgnoreCase("cliente"))
                loadView("treinosaluno_view.fxml");
            else
                loadView("treinosadm_view.fxml");
        });
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_PATH + fxmlFile));
            Node view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar: " + fxmlFile);
            e.printStackTrace();
        }
    }
}
