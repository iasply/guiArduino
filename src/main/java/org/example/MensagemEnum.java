package org.example;

import javax.swing.*;

public enum MensagemEnum {
    SENSOR("sensor", "Sensor", new JLabel()),
    PESO("peso", "Peso", new JLabel()),
    TARA("tara", "Tara", new JLabel()),
    PINO_COMANDO("retornoPinoComando", "Pino comando", new JLabel());

    private String nomePadrao;
    private String nomeDisplay;
    private JLabel jLabel;

    MensagemEnum(String nomePadrao, String nomeDisplay, JLabel jLabel) {
        this.nomePadrao = nomePadrao;
        this.nomeDisplay = nomeDisplay;
        this.jLabel = jLabel;
    }

    static public MensagemEnum padraoToEnum(String nomePadrao) {
        MensagemEnum[] values = MensagemEnum.values();

        for (MensagemEnum value : values) {
            if (value.getNomePadrao().equals(nomePadrao)) {
                return value;
            }
        }
        throw new RuntimeException("desconhecido");
    }

    public String getNomePadrao() {
        return nomePadrao;
    }

    public String getNomeDisplay() {
        return nomeDisplay;
    }

    public JLabel getjLabel() {
        return jLabel;
    }

    public void setjLabel(String s) {
        jLabel.setText(s);
    }
}
