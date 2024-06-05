package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.MensagemEnum.*;

public class ArduinoGui {


    private JTextArea textArea;

    private JLabel pesoLabel;
    private JLabel taraLabel;
    private JLabel sensorLabel;
    private ArduinoSerialReader arduinoSerialReader;

    private float pesoGlobal = 0;

    public ArduinoGui(ArduinoSerialReader arduinoSerialReader) {
        this.arduinoSerialReader = arduinoSerialReader;
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Monitor Serial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Painel para exibir o log
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Painel para os botões e informações
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton zerarTara = new JButton("ZERAR TARA");
        zerarTara.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arduinoSerialReader.setMensageToSerial("2");
            }
        });
        buttonPanel.add(zerarTara, gbc);

        JButton adicionarTara = new JButton("ADICIONAR TARA");
        adicionarTara.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arduinoSerialReader.setMensageToSerial("1");
            }
        });
        gbc.gridy++;
        buttonPanel.add(adicionarTara, gbc);

        gbc.gridy++;

        buttonPanel.add(PESO.getjLabel(), gbc);
        PESO.setjLabel("Peso: N/A");

        gbc.gridy++;

        buttonPanel.add(TARA.getjLabel(), gbc);
        TARA.setjLabel("tara: N/A");

        gbc.gridy++;

        buttonPanel.add(SENSOR.getjLabel(), gbc);
        SENSOR.setjLabel("sensor: N/A");

        gbc.gridy++;

        buttonPanel.add(PINO_COMANDO.getjLabel(), gbc);
        PINO_COMANDO.setjLabel("Pino Comando: N/A");

        // Adicionando os painéis ao frame
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, buttonPanel);
        splitPane.setDividerLocation(300);

        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
            System.out.println(message);

            if (!message.isEmpty()) {
                String substring = message.substring(3, message.length());

                String[] vetorString = substring.split(",");

                for (String s : vetorString) {
                    String[] split = s.split("=");
                    if (split[0].equals(PESO.getNomePadrao())) {
                        boolean b = deveAlterar(Float.valueOf(split[1]));
                        if (b) {
                            MensagemEnum mensagemEnum = padraoToEnum(split[0]);
                            mensagemEnum.setjLabel(mensagemEnum.getNomeDisplay() + ": " + split[1]);
                        }
                    } else {
                        MensagemEnum mensagemEnum = padraoToEnum(split[0]);
                        mensagemEnum.setjLabel(mensagemEnum.getNomeDisplay() + ": " + split[1]);
                    }

                }
            }
        });
    }

    private boolean deveAlterar(float peso) {

        if (peso < 0L) {
            return false;
        }

        float fivePercent = pesoGlobal * 0.05f;

        // Calcula os limites superior e inferior
        float lowerLimit = pesoGlobal - fivePercent;
        float upperLimit = pesoGlobal + fivePercent;

        // Verifica se o valor está dentro do intervalo de 5%
        if (peso >= lowerLimit && peso <= upperLimit) {
            return false;
        } else {
            return true;
        }
    }
}
