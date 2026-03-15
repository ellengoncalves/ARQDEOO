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
    private JTextField txtQtde;
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
        mainPanel.setBackground(new Color(255, 250, 240)); // Floral White

        // Título
        JLabel titleLabel = new JLabel("Cadastro de Ingresso", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 250, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Setor:"), gbc);
        cbxSetores = new JComboBox<>();
        for (SetorEnum setor : SetorEnum.values()) {
            cbxSetores.addItem(setor.getNome());
        }
        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    atualizarPreco();
                }
            }
        });
        gbc.gridx = 1;
        formPanel.add(cbxSetores, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Quantidade:"), gbc);
        txtQtde = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(txtQtde, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Preço Unitário:"), gbc);
        lblPreco = new JLabel("R$ 0.00");
        lblPreco.setFont(new Font("Arial", Font.BOLD, 14));
        lblPreco.setForeground(new Color(34, 139, 34)); // Forest Green
        gbc.gridx = 1;
        formPanel.add(lblPreco, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 250, 240));
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
                lblPreco.setText("R$ " + setor.getValor());
                break;
            }
        }
    }

    private void comprarIngresso() {
        String setor = (String) cbxSetores.getSelectedItem();
        String qtdeStr = txtQtde.getText().trim();

        try {
            int quantidade = Integer.parseInt(qtdeStr);
            boolean sucesso = gerenciador.comprarIngresso(setor, quantidade);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Ingresso(s) comprado(s) com sucesso!");
                txtQtde.setText("");
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
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}