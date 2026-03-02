package entities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InterfaceGrafica extends JFrame {
    private final GerenciadorFrota gerenciador = new GerenciadorFrota();
    private final JTextArea areaTexto = new JTextArea(8, 30);
    private final JRadioButton rbCarro = new JRadioButton("Carro");
    private final JRadioButton rbCaminhao = new JRadioButton("Caminhão");
    private final JRadioButton rbMoto = new JRadioButton("Motocicleta");
    private final JTextField campoIndice = new JTextField(5);

    public InterfaceGrafica() {
        super("Gestão de Frota de Veículos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 13);
        areaTexto.setEditable(false);
        areaTexto.setFont(fontePadrao);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200,200,200)), new EmptyBorder(6,6,6,6)));
        scroll.setPreferredSize(new Dimension(480, 260));

        JPanel painelSuperior = new JPanel(new BorderLayout(8, 8));
        painelSuperior.setBorder(new EmptyBorder(10, 10, 0, 10));
        painelSuperior.setOpaque(false);

        JPanel painelSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        painelSelecao.setOpaque(false);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCarro);
        grupo.add(rbCaminhao);
        grupo.add(rbMoto);
        rbCarro.setSelected(true);
        JLabel lblTipo = new JLabel("Tipo de veículo:");
        lblTipo.setFont(fontePadrao);
        painelSelecao.add(lblTipo);
        rbCarro.setFont(fontePadrao);
        rbCaminhao.setFont(fontePadrao);
        rbMoto.setFont(fontePadrao);
        painelSelecao.add(rbCarro);
        painelSelecao.add(rbCaminhao);
        painelSelecao.add(rbMoto);

        JButton btnAdicionar = new JButton("Adicionar Veículo");
        btnAdicionar.setFont(fontePadrao);
        btnAdicionar.addActionListener(e -> adicionarVeiculo());
        JPanel painelAdicionar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelAdicionar.setOpaque(false);
        painelAdicionar.add(btnAdicionar);

        painelSuperior.add(painelSelecao, BorderLayout.WEST);
        painelSuperior.add(painelAdicionar, BorderLayout.EAST);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        painelAcoes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220,220,220)), "Ações"));
        painelAcoes.setBackground(new Color(250,250,250));
        painelAcoes.setFont(fontePadrao);
        painelAcoes.add(new JLabel("Índice:"));
        campoIndice.setFont(fontePadrao);
        painelAcoes.add(campoIndice);
        JButton btnAlternar = new JButton("Ligar/Desligar");
        btnAlternar.setFont(fontePadrao);
        btnAlternar.addActionListener(e -> alternarEstado());
        JButton btnExibir = new JButton("Exibir Frota");
        btnExibir.setFont(fontePadrao);
        btnExibir.addActionListener(e -> exibirFrota());
        JButton btnRemover = new JButton("Remover");
        btnRemover.setFont(fontePadrao);
        btnRemover.addActionListener(e -> removerPorIndice());
        painelAcoes.add(btnAlternar);
        painelAcoes.add(btnExibir);
        painelAcoes.add(btnRemover);

        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.setOpaque(false);
        topo.add(painelSuperior);
        topo.add(painelAcoes);

        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarVeiculo() {
        Veiculo v;
        if (rbCarro.isSelected()) {
            v = new Carro();
        } else if (rbCaminhao.isSelected()) {
            v = new Caminhao();
        } else {
            v = new Motocicleta();
        }
        gerenciador.adicionarVeiculo(v);
        areaTexto.append("Veículo " + v.getTipo() + " adicionado na posição " + (gerenciador.getQuantidade() - 1) + "\n");
    }

    private void alternarEstado() {
        try {
            int idx = Integer.parseInt(campoIndice.getText());
            Veiculo v = gerenciador.getVeiculo(idx);
            if (v == null) {
                areaTexto.append("Índice inválido!\n");
                return;
            }
            if (v.isLigado()) {
                v.desligar();
                areaTexto.append("Veículo " + v.getTipo() + " na posição " + idx + " agora está desligado.\n");
            } else {
                v.ligar();
                areaTexto.append("Veículo " + v.getTipo() + " na posição " + idx + " agora está ligado.\n");
            }
        } catch (NumberFormatException ex) {
            areaTexto.append("Digite um índice válido!\n");
        }
    }

    private void exibirFrota() {
        java.util.List<Veiculo> lista = gerenciador.getFrota();
        if (lista.isEmpty()) {
            areaTexto.append("Frota vazia.\n");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            Veiculo v = lista.get(i);
            areaTexto.append("[" + i + "] " + v.getTipo() + " - " + (v.isLigado() ? "Ligado" : "Desligado") + "\n");
        }
    }

    private void removerPorIndice() {
        try {
            int idx = Integer.parseInt(campoIndice.getText());
            Veiculo removido = gerenciador.removerVeiculo(idx);
            if (removido == null) {
                areaTexto.append("Índice inválido!\n");
                return;
            }
            areaTexto.append("Veículo " + removido.getTipo() + " removido da posição " + idx + ".\n");
        } catch (NumberFormatException ex) {
            areaTexto.append("Digite um índice válido!\n");
        }
    }
}
