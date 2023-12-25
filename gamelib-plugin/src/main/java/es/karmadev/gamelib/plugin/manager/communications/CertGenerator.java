package es.karmadev.gamelib.plugin.manager.communications;

import sun.security.x509.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CertGenerator {

    private final static long hundredYears = TimeUnit.DAYS.toMillis(36500);

    static X509Certificate generateSigned(final KeyPair pair, final UUID libId) throws CertificateException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchProviderException, SignatureException, IOException {
        X509CertInfo info = new X509CertInfo();
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + hundredYears);

        CertificateValidity validity = new CertificateValidity(startDate, endDate);
        BigInteger serial = new BigInteger(64, new SecureRandom());

        X500Name proprietary = new X500Name("CN=" + libId + ",OU=RedDo,O=RedDo,L=Madrid,ST=Madrid,C=Spain");

        info.set(X509CertInfo.VALIDITY, validity);
        info.set(X509CertInfo.SERIAL_NUMBER, serial);
        info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(proprietary));
        info.set(X509CertInfo.ISSUER, new CertificateIssuerName(proprietary));
        info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));

        AlgorithmId algorithmId = new AlgorithmId(AlgorithmId.SHA256withECDSA_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algorithmId));

        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(pair.getPrivate(), "SHA256withRSA");

        return cert;
    }
}
