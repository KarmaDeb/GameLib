package es.karmadev.gamelib.plugin.manager.communications;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * Plugin certificate manager
 */
public class CertManager {

    private final JavaPlugin plugin;

    public CertManager(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public X509Certificate generateCertificate() throws NoSuchAlgorithmException, CertificateException, SignatureException,
            InvalidKeyException, NoSuchProviderException, IOException {
        Path certFile = plugin.getDataFolder().toPath().resolve("gamelib.crt");
        if (Files.exists(certFile)) {
            return getCertificate(certFile);
        }

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair pair = generator.generateKeyPair();
        X509Certificate certificate = CertGenerator.generateSigned(pair, UUID.randomUUID());

        Files.write(certFile, certificate.getEncoded());
        return certificate;
    }

    private X509Certificate getCertificate(final Path file) throws CertificateException, IOException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        try (InputStream stream = Files.newInputStream(file)) {
            return (X509Certificate) factory.generateCertificate(stream);
        }
    }
}
