package es.karmadev.gamelib.plugin.manager.communications;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CertGenerator {

    private final static long hundredYears = TimeUnit.DAYS.toMillis(36500);

    static X509Certificate generateSigned(final KeyPair pair, final UUID libId) throws OperatorCreationException, CertIOException,
            CertificateException {
        final Provider provider = new BouncyCastleProvider();

        long now = System.currentTimeMillis();
        Date start = new Date(now);

        X500Name proprietary = new X500Name("CN=" + libId + ",OU=RedDo,O=RedDo,L=Madrid,ST=Madrid,C=Spain");
        BigInteger serial = new BigInteger(Long.toString(now));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.YEAR, 100);

        Date end = calendar.getTime();
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA")
                .build(pair.getPrivate());

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(proprietary, serial, start,
                end, proprietary, pair.getPublic());

        BasicConstraints constraints = new BasicConstraints(true);
        certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, constraints);

        return new JcaX509CertificateConverter().setProvider(provider)
                .getCertificate(certBuilder.build(signer));
    }
}
