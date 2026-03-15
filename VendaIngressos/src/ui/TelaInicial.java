package VendaIngressos.src.ui;

import VendaIngressos.src.services.GerenciadorIngresso;
import VendaIngressos.src.services.GerenciadorArquivo;
import VendaIngressos.src.enums.SetorEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaInicial extends JFrame {
    private GerenciadorIngresso gerenciador;

    public TelaInicial() {
        gerenciador = new GerenciadorIngresso();
        // Tentar desserializar
        List<VendaIngressos.src.entities.Ingresso> ingressosCarregados = GerenciadorArquivo.desserializar("ingressos.ser");
        if (ingressosCarregados != null) {
            gerenciador.setIngressos(ingressosCarregados);
        }

        setTitle("Sistema de Venda de Ingressos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Alice Blue

        // Título
        JLabel titleLabel = new JLabel("Sistema de Venda de Ingressos", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112)); // Midnight Blue
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnCadastrarIngresso = createStyledButton("Cadastrar Ingresso");
        JButton btnGerarRelatorio = createStyledButton("Gerar Relatório");
        JButton btnExportarTxt = createStyledButton("Exportar Relatório TXT");

        btnCadastrarIngresso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaCadastroIngresso(gerenciador);
            }
        });

        btnGerarRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciador.imprimirRelatorio();
                JOptionPane.showMessageDialog(TelaInicial.this, "Relatório gerado no console.");
            }
        });

        btnExportarTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GerenciadorArquivo.exportarTxt(gerenciador.getIngressos(), "relatorio.txt");
                JOptionPane.showMessageDialog(TelaInicial.this, "Relatório exportado para relatorio.txt");
            }
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
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}