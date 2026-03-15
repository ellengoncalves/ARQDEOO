package venda_ingresso.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import venda_ingresso.entities.Ingresso;
import venda_ingresso.services.GerenciadorArquivo;
import venda_ingresso.services.GerenciadorIngresso;

public class TelaInicial extends JFrame {
    private GerenciadorIngresso gerenciador;

    public TelaInicial() {
        gerenciador = new GerenciadorIngresso();
        List<Ingresso> ingressosCarregados = GerenciadorArquivo.desserializar("ingressos.ser");
        gerenciador.setIngressos(ingressosCarregados);

        setTitle("Sistema de Venda de Ingressos");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(244, 247, 198));

        JLabel titleLabel = new JLabel("Sistema de Venda de Ingressos", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(244, 247, 198));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnCadastrarIngresso = createStyledButton("Cadastrar Ingresso");
        JButton btnGerarRelatorio = createStyledButton("Gerar Relatorio");
        JButton btnExportarTxt = createStyledButton("Exportar Relatorio TXT");

        btnCadastrarIngresso.addActionListener(e -> new JanelaCadastroIngresso(gerenciador));
        btnGerarRelatorio.addActionListener(e -> {
            gerenciador.imprimirRelatorio();
            JOptionPane.showMessageDialog(this, "Relatorio gerado no console.");
        });
        btnExportarTxt.addActionListener(e -> {
            GerenciadorArquivo.exportarTxt(gerenciador.getIngressos(), "relatorio.txt");
            JOptionPane.showMessageDialog(this, "Relatorio exportado para relatorio.txt");
        });

        buttonPanel.add(btnCadastrarIngresso);
        buttonPanel.add(btnGerarRelatorio);
        buttonPanel.add(btnExportarTxt);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(134, 138, 81));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
