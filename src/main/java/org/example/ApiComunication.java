package org.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiComunication {

        public static void post(InfoModel infoModel){
            String urlString = "http://localhost:8080/serial/"+infoModel.getPeso()+"/"+infoModel.getTara()+"/"+infoModel.getSensor();


            try {
                // Criar a URL
                URL url = new URL(urlString);

                // Abrir conexão
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Configurar a conexão
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // Ler a resposta
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                try(InputStream is = conn.getInputStream()) {
                    StringBuilder response = new StringBuilder();
                    int ch;
                    while ((ch = is.read()) != -1) {
                        response.append((char) ch);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
}
