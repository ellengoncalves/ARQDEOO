package entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaUI extends JFrame {
    private GerenciaDispositivo gerencia;
    private JTextArea textArea;
    private JRadioButton tvButton, notebookButton, smartphoneButton;
    private JTextField indiceField;

    public JanelaUI() {
        gerencia = new GerenciaDispositivo();
        setTitle("Gerenciamento de Dispositivos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior
        JPanel topPanel = new JPanel();
        tvButton = new JRadioButton("TV");
        notebookButton = new JRadioButton("Notebook");
        smartphoneButton = new JRadioButton("Smartphone");
        ButtonGroup group = new ButtonGroup();
        group.add(tvButton);
        group.add(notebookButton);
        group.add(smartphoneButton);
        topPanel.add(tvButton);
        topPanel.add(notebookButton);
        topPanel.add(smartphoneButton);

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> adicionarDispositivo());
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        // Painel central
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Painel inferior
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Índice:"));
        indiceField = new JTextField(5);
        bottomPanel.add(indiceField);

        JButton toggleButton = new JButton("Ligar/Desligar");
        toggleButton.addActionListener(e -> alternarEstado());
        bottomPanel.add(toggleButton);

        JButton showButton = new JButton("Mostrar Estados");
        showButton.addActionListener(e -> mostrarEstados());
        bottomPanel.add(showButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void adicionarDispositivo() {
        if (tvButton.isSelected()) {
            gerencia.adicionarDispositivo(new TV());
            textArea.append("TV adicionada.\n");
        } else if (notebookButton.isSelected()) {
            gerencia.adicionarDispositivo(new Notebook());
            textArea.append("Notebook adicionado.\n");
        } else if (smartphoneButton.isSelected()) {
            gerencia.adicionarDispositivo(new Smartphone());
            textArea.append("Smartphone adicionado.\n");
        } else {
            textArea.append("Selecione um dispositivo para adicionar.\n");
        }
    }

    private void alternarEstado() {
        try {
            int indice = Integer.parseInt(indiceField.getText());
            Dispositivo dispositivo = gerencia.getDispositivo(indice);
            if (dispositivo != null) {
                if (dispositivo.isLigado()) {
                    dispositivo.desligar();
                    textArea.append(dispositivo.getNome() + " desligado.\n");
                } else {
                    dispositivo.ligar();
                    textArea.append(dispositivo.getNome() + " ligado.\n");
                }
            } else {
                textArea.append("Dispositivo não encontrado no índice informado.\n");
            }
        } catch (NumberFormatException e) {
            textArea.append("Índice inválido.\n");
        }
    }

    private void mostrarEstados() {
        textArea.append("Estados dos dispositivos:\n");
        for (int i = 0; i < gerencia.getDispositivos().size(); i++) {
            Dispositivo dispositivo = gerencia.getDispositivo(i);
            textArea.append(i + ": " + dispositivo.getNome() + " - " + (dispositivo.isLigado() ? "Ligado" : "Desligado") + "\n");
        }
    }
}