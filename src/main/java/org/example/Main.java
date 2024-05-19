package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class Main {


    public static void main(String[] args) {

        ArduinoSerialReader.listarPortas();
        SerialPort portaArduino = ArduinoSerialReader.identificarArduino();

        ArduinoSerialReader arduinoSerialReader = new ArduinoSerialReader(portaArduino);

        ArduinoGui gui = new ArduinoGui(arduinoSerialReader);

        arduinoSerialReader.abrirCanal(gui::appendMessage);

    }
}
