/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslclient;

import java.security.cert.Certificate;
import java.security.Principal;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 *
 * @author stepin
 */
public class MyHandshakeCompletedListener implements HandshakeCompletedListener {

    @Override
    public void handshakeCompleted(HandshakeCompletedEvent event) {
        System.out.println("### HAND SHAKE COMPLETED");

        try {

            System.out.println("event=" + event);
            Principal localPrincipal = event.getLocalPrincipal();

            if (localPrincipal != null) {
                System.out.println("localPrincipal=" + localPrincipal);
            }else {
                System.out.println("### No local Principal");
            }

            Principal peerPrincipal = event.getPeerPrincipal();
            System.out.println("peerPrincipal=" + peerPrincipal.getName());

            Certificate[] peerCertificates = event.getPeerCertificates();
            Stream.of(peerCertificates)
                    .forEach(this::printCert);

        } catch (SSLPeerUnverifiedException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
        }
        
        System.out.println("###################################################################");
    }

    private void printCert(Certificate certificate) {
        System.out.println("----- Certificate: -------------");
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("publicKey.Algorithm=" + publicKey.getAlgorithm());
        System.out.println("publicKey.Format=" + publicKey.getFormat());
        System.out.println();
        System.out.println("Cert.Typpe=" + certificate.getType());
        System.out.println("Cert:" + certificate);
        System.out.println();
    }

}
