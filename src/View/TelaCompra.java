package View;

import Dao.IngressosDAO;
import Ingressos.*;
import java.awt.*;
import java.sql.Date;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class TelaCompra extends JFrame {
    private JComboBox<String> cmbEventos, cmbTipoIngresso;
    private JTextField txtNomeEvento, txtDataEvento, txtValorNormal, txtValorMeia, txtValorVIP;
    private JButton btnImprimir, btnVoltar;
    private SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat formatoMoeda = new DecimalFormat("R$ #,##0.00");
    
        //Front da tela de compra
    
    public TelaCompra() {
        setTitle("Compra de Ingressos");
        setSize(650, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        Font fonteMaior = new Font("Arial", Font.PLAIN, 14);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Componentes
        panel.add(criarLabel("Selecione o Evento:", fonteMaior));
        cmbEventos = new JComboBox<>();
        cmbEventos.setFont(fonteMaior);
        panel.add(cmbEventos);
        
        panel.add(criarLabel("Nome do Evento:", fonteMaior));
        txtNomeEvento = criarTextFieldNaoEditavel(fonteMaior);
        panel.add(txtNomeEvento);
        
        panel.add(criarLabel("Data do Evento:", fonteMaior));
        txtDataEvento = criarTextFieldNaoEditavel(fonteMaior);
        panel.add(txtDataEvento);
        
        panel.add(criarLabel("Valor Normal:", fonteMaior));
        txtValorNormal = criarTextFieldNaoEditavel(fonteMaior);
        panel.add(txtValorNormal);
        
        panel.add(criarLabel("Valor Meia:", fonteMaior));
        txtValorMeia = criarTextFieldNaoEditavel(fonteMaior);
        panel.add(txtValorMeia);
        
        panel.add(criarLabel("Valor VIP:", fonteMaior));
        txtValorVIP = criarTextFieldNaoEditavel(fonteMaior);
        panel.add(txtValorVIP);
        
        panel.add(criarLabel("Tipo de Ingresso:", fonteMaior));
        cmbTipoIngresso = new JComboBox<>(new String[]{"Normal", "Meia", "VIP"});
        cmbTipoIngresso.setFont(fonteMaior);
        panel.add(cmbTipoIngresso);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnImprimir = new JButton("Imprimir Ingressos");
        btnImprimir.setFont(fonteMaior);
        btnImprimir.setPreferredSize(new Dimension(180, 35));
        panelBotoes.add(btnImprimir);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(fonteMaior);
        btnVoltar.setPreferredSize(new Dimension(180, 35));
        panelBotoes.add(btnVoltar);
        
        add(panel, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        
        // Inicialização
        carregarEventos();
        configurarListeners();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JLabel criarLabel(String texto, Font fonte) {
        JLabel label = new JLabel(texto);
        label.setFont(fonte);
        return label;
    }
    
    private JTextField criarTextFieldNaoEditavel(Font fonte) {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(fonte);
        return textField;
    }
    
    //metodo da lista de eventos cadastrados
    
    private void carregarEventos() {
        IngressosDAO dao = new IngressosDAO();
        Set<String> eventos = dao.listarEventosUnicos();
        
        for (String evento : eventos) {
            cmbEventos.addItem(evento);
        }
    }
    
    private void configurarListeners() {
        cmbEventos.addActionListener(e -> {
            String eventoSelecionado = (String) cmbEventos.getSelectedItem();
            if (eventoSelecionado != null) {
                carregarDetalhesEvento(eventoSelecionado);
            }
        });
        
        btnImprimir.addActionListener(e -> imprimirIngresso());
        btnVoltar.addActionListener(e -> voltarAoMenu());
    }
    
    //metodo de carregar dados dos eventos
    
private void carregarDetalhesEvento(String nomeEvento) {
    IngressosDAO dao = new IngressosDAO();
        var ingressos = dao.buscarIngressosPorEvento(nomeEvento);
    
    // Zerar Valores
    txtValorNormal.setText("");
    txtValorMeia.setText("");
    txtValorVIP.setText("");
    
    if (!ingressos.isEmpty()) {
        txtNomeEvento.setText(ingressos.get(0).getEvento());
        txtDataEvento.setText(formatoBrasileiro.format(ingressos.get(0).getData()));
        
        for (Ingresso ingresso : ingressos) {
            // Calculo do via polimorfismo
            double valorFinal = ingresso.calcularValor();
            
            if (ingresso instanceof IngressoNormal) {
                txtValorNormal.setText(formatoMoeda.format(valorFinal));
            } else if (ingresso instanceof IngressoMeia) {
                txtValorMeia.setText(formatoMoeda.format(valorFinal));
            } else if (ingresso instanceof IngressoVIP) {
                txtValorVIP.setText(formatoMoeda.format(valorFinal));
            }
        }
    }
}

//metodo da impressao de ingressos

    private void imprimirIngresso() {
        if (cmbEventos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um evento primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String tipo = (String) cmbTipoIngresso.getSelectedItem();
        String valor = "";
        
        switch (tipo) {
            case "Normal":
                valor = txtValorNormal.getText();
                break;
            case "Meia":
                valor = txtValorMeia.getText();
                break;
            case "VIP":
                valor = txtValorVIP.getText();
                break;
        }
        
        String mensagem = String.format(
            "Compra realizada com sucesso!\n\n" +
            "Evento: %s\n" +
            "Data: %s\n" +
            "Tipo: %s\n" +
            "Valor: %s",
            txtNomeEvento.getText(),
            txtDataEvento.getText(),
            tipo,
            valor
        );
        
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    //metodo  do botao de voltar a tela inicial
    
    private void voltarAoMenu() {
        this.dispose();
        new MenuPrincipal().setVisible(true);
    }
}