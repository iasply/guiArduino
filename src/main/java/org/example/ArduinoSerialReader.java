package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.util.function.Consumer;

public class ArduinoSerialReader {
    static final String ARDUINO = "USB-SERIAL CH340";
    private String mensageToSerial = null;
    private SerialPort port;

    public ArduinoSerialReader(SerialPort port) {
        this.port = port;
    }

    static public void listarPortas() {
        // Listar todas as portas seriais disponíveis
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Portas seriais disponíveis:");

        for (SerialPort port : ports) {
            System.out.println("Nome da porta: " + port.getSystemPortName());
            System.out.println("Descrição da porta: " + port.getDescriptivePortName());
            System.out.println("Porta aberta: " + port.openPort());
            port.closePort();
            System.out.println("-------------------------------");
        }
    }

    static public SerialPort identificarArduino() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
               if (port.getDescriptivePortName().contains(ARDUINO)) {
                System.out.println(port);
                return port;
            }
        }
        System.out.println("Arduino não identificado.");
        return null;
    }

    public void abrirCanal(Consumer<String> messageConsumer) {

        port.setBaudRate(9600);
        if (port.openPort()) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    while (true) {
                        byte[] readBuffer = new byte[1024];
                        int numRead = port.readBytes(readBuffer, readBuffer.length);
                        String message = new String(readBuffer, 0, numRead).trim();
                        messageConsumer.accept(message);

                        if (mensageToSerial != null) {
                            port.writeBytes(mensageToSerial.getBytes(), mensageToSerial.length());
                            mensageToSerial = null;
                        }else {
                            port.writeBytes("3\n\r".getBytes(),"3\n\r".length());
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    port.closePort();
                }
            }).start();
        }
    }


    public void setMensageToSerial(String s) {
        this.mensageToSerial = s;
    }
}