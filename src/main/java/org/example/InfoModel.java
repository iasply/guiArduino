package org.example;

public class InfoModel {
    private String peso = "";
    private String tara = "";
    private String sensor = "";

    public InfoModel(String peso, String tara, String sensor) {
        this.peso = peso;
        this.tara = tara;
        this.sensor = sensor;
    }

    public InfoModel() {
    }

    public String getPeso() {
        return peso;
    }

    public String getTara() {
        return tara;
    }

    public String getSensor() {
        return sensor;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
