package VendaIngressos.src.ui;

import VendaIngressos.src.services.GerenciadorIngresso;
import VendaIngressos.src.services.GerenciadorArquivo;
import VendaIngressos.src.enums.SetorEnum;
import VendaIngressos.src.exceptions.QuantidadeInvalidaException;
import VendaIngressos.src.exceptions.SetorEsgotadoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JanelaCadastroIngresso extends JFrame {
    private GerenciadorIngresso gerenciador;
    private JComboBox<String> cbxSetores;
    private JLabel lblQtde;
    private JLabel lblPreco;

    public JanelaCadastroIngresso(GerenciadorIngresso gerenciador) {
        this.gerenciador = gerenciador;

        setTitle("Cadastro de Ingresso");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(244, 247, 198)); // Floral White

        // Título
        JLabel titleLabel = new JLabel("Cadastro de Ingresso", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 0));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(244, 247, 198));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblSetor = new JLabel("Setor:");
        lblSetor.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lblSetor, gbc);
        cbxSetores = new JComboBox<>();
        for (SetorEnum setor : SetorEnum.values()) {
            cbxSetores.addItem(setor.getNome());
        }
        cbxSetores.setPreferredSize(new Dimension(150, 30));
        cbxSetores.setFont(new Font("Arial", Font.PLAIN, 14));
        cbxSetores.setBackground(new Color(134, 138, 81));
        cbxSetores.setForeground(Color.BLACK);
        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    atualizarPreco();
                }
            }
        });
        // Campo fechado fica branco; na lista aberta, item sob foco ganha destaque.
        cbxSetores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1) {
                    setBackground(new Color(134, 138, 81));
                    setForeground(Color.WHITE);
                } else if (isSelected) {
                    setBackground(new Color(134, 138, 81));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
                return this;
            }
        });
        gbc.gridx = 1;
        formPanel.add(cbxSetores, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lblQuantidade, gbc);
        // Painel para quantidade com botões
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        qtyPanel.setBackground(new Color(244, 247, 198));
        JButton btnMinus = new JButton("-");
        btnMinus.setFont(new Font("Arial", Font.BOLD, 16));
        btnMinus.setPreferredSize(new Dimension(40, 25));
        btnMinus.setBackground(new Color(255, 71, 71)); // Crimson
        btnMinus.setForeground(Color.WHITE);
        btnMinus.setFocusPainted(false);
        btnMinus.setBorder(BorderFactory.createRaisedBevelBorder());
        btnMinus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMinus.addActionListener(e -> {
            int qty = Integer.parseInt(lblQtde.getText());
            if (qty > 1) {
                lblQtde.setText(String.valueOf(qty - 1));
            }
        });
        qtyPanel.add(btnMinus);

        lblQtde = new JLabel("1");
        lblQtde.setFont(new Font("Arial", Font.BOLD, 16));
        lblQtde.setPreferredSize(new Dimension(50, 25));
        lblQtde.setHorizontalAlignment(JLabel.CENTER);
        lblQtde.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblQtde.setOpaque(true);
        lblQtde.setBackground(Color.WHITE);
        qtyPanel.add(lblQtde);

        JButton btnPlus = new JButton("+");
        btnPlus.setFont(new Font("Arial", Font.BOLD, 16));
        btnPlus.setPreferredSize(new Dimension(40, 25));
        btnPlus.setBackground(new Color(79, 196, 106)); // Forest Green
        btnPlus.setForeground(Color.WHITE);
        btnPlus.setFocusPainted(false);
        btnPlus.setBorder(BorderFactory.createRaisedBevelBorder());
        btnPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlus.addActionListener(e -> {
            int qty = Integer.parseInt(lblQtde.getText());
            lblQtde.setText(String.valueOf(qty + 1));
        });
        qtyPanel.add(btnPlus);

        gbc.gridx = 1;
        formPanel.add(qtyPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPrecoUnitario = new JLabel("Preço Unitário:");
        lblPrecoUnitario.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lblPrecoUnitario, gbc);
        lblPreco = new JLabel("R$ 0.00");
        lblPreco.setFont(new Font("Arial", Font.BOLD, 16));
        lblPreco.setForeground(new Color(34, 139, 34)); // Forest Green
        gbc.gridx = 1;
        formPanel.add(lblPreco, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(244, 247, 198));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnSalvar = createStyledButton("Salvar");
        JButton btnVoltar = createStyledButton("Voltar");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarIngresso();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fecharJanela();
            }
        });

        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Atualizar preço inicial
        atualizarPreco();

        // Adicionar listener para fechar janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharJanela();
            }
        });

        setVisible(true);
    }

    private void atualizarPreco() {
        String setorSelecionado = (String) cbxSetores.getSelectedItem();
        for (SetorEnum setor : SetorEnum.values()) {
            if (setor.getNome().equals(setorSelecionado)) {
                lblPreco.setText("R$ " + String.format("%.2f", setor.getValor()).replace(".", ","));
                break;
            }
        }
    }

    private void comprarIngresso() {
        String setor = (String) cbxSetores.getSelectedItem();
        String qtdeStr = lblQtde.getText().trim();

        try {
            int quantidade = Integer.parseInt(qtdeStr);
            boolean sucesso = gerenciador.comprarIngresso(setor, quantidade);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Ingresso(s) comprado(s) com sucesso!");
                lblQtde.setText("1");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite um número inteiro.");
        } catch (QuantidadeInvalidaException | SetorEsgotadoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void fecharJanela() {
        // Serializar antes de fechar
        GerenciadorArquivo.serializar(gerenciador.getIngressos(), "ingressos.ser");
        dispose();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(134, 138, 81));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40)); // Maior tamanho
        return button;
    }
}