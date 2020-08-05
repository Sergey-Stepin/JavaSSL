/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslserver;

/**
 *
 * @author stepin
 */
import javax.net.ssl.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSLServer {

    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.keyStore", "C:/_project/git/_Auth/javaSSL/SSLServer/server.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "serkspass");

        System.setProperty("javax.net.ssl.trustStore", "C:/_project/git/_Auth/javaSSL/SSLServer/server.ts");
        System.setProperty("javax.net.ssl.trustStorePassword", "sertspswd");

        try {
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket) factory.createServerSocket(6789);

            while (true) {
                try (SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept()) {
                    sslSocket.setNeedClientAuth(true);
                    sslSocket.addHandshakeCompletedListener(new MyHandshakeCompletedListener());

                    BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
                    
                    String line;
                    System.out.println("READEY TO COMMUNICATE:");
                    while ((line = in.readLine()) != null) {
                        System.out.println("received<-- " + line);
                        out.println(line);
                        System.out.println("sent --> " + line);
                    }
                } catch (SSLHandshakeException ex) {
                    Logger.getLogger(SSLServer.class.getName()).log(Level.SEVERE, "SSLHandshakeException:" + ex.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
