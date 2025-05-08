package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private JButton btnCadastro, btnVisualizar;

    public MenuPrincipal() {
        setTitle("Sistema de Ingressos - Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        btnCadastro = new JButton("Cadastrar Evento");
        btnCadastro.setBounds(60, 40, 160, 30);
        add(btnCadastro);

        btnVisualizar = new JButton("Comprar Ingressos");
        btnVisualizar.setBounds(60, 90, 160, 30);
        add(btnVisualizar);

        // exibe a tela antes de fechar o menu
        btnCadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCadastro().setVisible(true); // mostra a tela
                dispose(); // fecha o menu
            }
        });

        btnVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCompra().setVisible(true); // mostra a tela
                dispose(); // fecha o menu
            }
        });

        setLocationRelativeTo(null); // centraliza a janela
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}
