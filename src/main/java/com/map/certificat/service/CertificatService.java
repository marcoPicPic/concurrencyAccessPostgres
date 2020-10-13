package com.map.certificat.service;

import com.map.certificat.domain.Certificat;
import com.map.certificat.domain.GetionCertificat;
import com.map.certificat.repository.CertificatPersistenceContextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CertificatService {

    public static final int RANDOM_TIME_REST = 300;
    private final Logger log = LoggerFactory.getLogger(CertificatService.class);


    @Autowired
    private CertificatPersistenceContextRepository certificatPersistenceContextRepository;

    /**
     * Pacours la liste de certificats de facon asynchrone
     * @param certificats
     * @return
     */
    public String generateCertificats(ArrayList<Certificat> certificats,
                                      ArrayList<GetionCertificat> getionCertificat) {
        AtomicInteger i = new AtomicInteger();
        certificats.parallelStream().forEach(certificat -> {
            generateCertificat(certificat,
                    i.getAndIncrement(),
                    getionCertificat);
        });

        return "Test";
    }

    /**
     *  @param certificat
     * @param andIncrement
     * @param getionCertificat
     */
    @Async("asyncExecutor")
    void generateCertificat(Certificat certificat,
                            int andIncrement,
                            ArrayList<GetionCertificat> getionCertificat) {
        log.info("Execute method asynchronously - {} numero traitement : " ,
                Thread.currentThread().getName(),
                andIncrement);
        mockGunTraceService(certificat);
        addCertificat(certificat, getionCertificat);
    }

    /**
     * Ajout le certificat de facon transactional pour eviter les doublons
     * @param certificat
     * @param getionCertificat
     */
    private void addCertificat(Certificat certificat,
                               ArrayList<GetionCertificat> getionCertificat) {
        certificatPersistenceContextRepository.createCertificatBrexit(certificat, getionCertificat);
    }

    /**
     * Permet de generer un temps aleatoire de traitement comme c'est le cas dans le service REST
     * @param certificat
     */
    private void mockGunTraceService(Certificat certificat) {
        Random rand = new Random();
        int value = rand.nextInt(RANDOM_TIME_REST);
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        log.info("Sleep time in {} ms, mrn : {} ",
                value,
                certificat.getMrn());
    }


}
