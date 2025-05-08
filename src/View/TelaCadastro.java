package View;

import Dao.IngressosDAO;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class TelaCadastro extends JFrame {
    private JTextField txtEvento, txtData, txtValorNormal;
    private JButton btnSalvar, btnVoltar;
    private SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");

    public TelaCadastro() {
        setTitle("Cadastro de Ingressos");
        setSize(300, 250); // Tamanho reduzido
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Ingressos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Campo Evento
        JPanel pnlEvento = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlEvento.add(new JLabel("Evento:"));
        panel.add(pnlEvento);
        txtEvento = new JTextField();
        txtEvento.setMaximumSize(new Dimension(250, 25)); 
        panel.add(txtEvento);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Campo Data
        JPanel pnlData = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlData.add(new JLabel("Data (dd/MM/yyyy):"));
        panel.add(pnlData);
        txtData = new JTextField();
        txtData.setMaximumSize(new Dimension(250, 25));
        panel.add(txtData);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Campo Valor Normal
        JPanel pnlValor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlValor.add(new JLabel("Valor Normal:"));
        panel.add(pnlValor);
        txtValorNormal = new JTextField();
        txtValorNormal.setMaximumSize(new Dimension(250, 25)); 
        panel.add(txtValorNormal);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 25)); 
        panelBotoes.add(btnSalvar);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setPreferredSize(new Dimension(120, 25));
        panelBotoes.add(btnVoltar);
        
        add(panel, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        
        configurarListeners();
        setLocationRelativeTo(null);
    }
    
    private void configurarListeners() {
        btnSalvar.addActionListener(e -> salvarIngresso());
        btnVoltar.addActionListener(e -> voltarAoMenu());
    }
    //metodo de cadastrar ingressos e alteração de preço
    private void salvarIngresso() {
        try {
            if (validarCampos()) {
                String evento = txtEvento.getText();
                java.util.Date dataUtil = formatoBrasileiro.parse(txtData.getText());
                java.sql.Date dataSQL = new java.sql.Date(dataUtil.getTime());
                double valorBase = Double.parseDouble(txtValorNormal.getText());
                
                new IngressosDAO().inserirIngressos(
                    evento, 
                    dataSQL,
                    valorBase,
                    valorBase,
                    valorBase
                );
                
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    //metodo de validação dos campos
    
    private boolean validarCampos() {
        if (txtEvento.getText().isEmpty() || txtData.getText().isEmpty() || txtValorNormal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return false;
        }
        return true;
    }
    //metodo de limpar os campos
    private void limparCampos() {
        txtEvento.setText("");
        txtData.setText("");
        txtValorNormal.setText("");
    }
    //metodo de voltar a tela inicial
    private void voltarAoMenu() {
        dispose();
        new MenuPrincipal().setVisible(true);
    }
}