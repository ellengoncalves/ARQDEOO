package entities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
        setLayout(new BorderLayout());
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        // Painel de seleção de veículo
        JPanel painelSelecao = new JPanel();
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCarro);
        grupo.add(rbCaminhao);
        grupo.add(rbMoto);
        rbCarro.setSelected(true);
        painelSelecao.add(new JLabel("Tipo de veículo:"));
        painelSelecao.add(rbCarro);
        painelSelecao.add(rbCaminhao);
        painelSelecao.add(rbMoto);

        JButton btnAdicionar = new JButton("Adicionar Veículo");
        btnAdicionar.addActionListener(e -> adicionarVeiculo());

        // Painel de ações
        JPanel painelAcoes = new JPanel();
        painelAcoes.add(new JLabel("Índice:"));
        painelAcoes.add(campoIndice);
        JButton btnAlternar = new JButton("Ligar/Desligar");
        btnAlternar.addActionListener(e -> alternarEstado());
        JButton btnExibir = new JButton("Exibir Frota");
        btnExibir.addActionListener(e -> exibirFrota());
        painelAcoes.add(btnAlternar);
        painelAcoes.add(btnExibir);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelSelecao, BorderLayout.NORTH);
        painelTopo.add(btnAdicionar, BorderLayout.CENTER);
        painelTopo.add(painelAcoes, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
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
}
