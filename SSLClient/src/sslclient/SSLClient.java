/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslclient;

/**
 *
 * @author stepin
 */
import javax.net.ssl.*;
import java.io.*;

public class SSLClient {

    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.keyStore", "C:/_project/git/_Auth/javaSSL/SSLClient/client.ks");
        System.setProperty("javax.net.ssl.keyStorePassword", "clikspass");        
        
        System.setProperty("javax.net.ssl.trustStore", "C:/_project/git/_Auth/javaSSL/SSLClient/client.ts");
        System.setProperty("javax.net.ssl.trustStorePassword", "clikspswd");

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) factory.createSocket("localhost", 6789);
            sslSocket.addHandshakeCompletedListener(new MyHandshakeCompletedListener());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);

            String line;
            System.out.println("READEY TO COMMUNICATE:");
            while ((line = br.readLine()) != null) {
                out.println(line);
                System.out.println("sent --> " + line);
                System.out.println("received<-- " + in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
