package com.map.certificat.rest;

import com.map.certificat.domain.Certificat;
import com.map.certificat.domain.GetionCertificat;
import com.map.certificat.service.CertificatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Random;

@Controller
public class CertificatRest {


    public static final String CERTIFICAT = "certificat";
    public static final int BOUND = 3;
    @Autowired
    private CertificatService certificatService;


    @GetMapping("/certificat")
    public String doneCertificat() {

        return certificatService.generateCertificats(generateRandomCertificat(),
                createGestionCertificat());

    }

    /**
     * creation jeu de données certificat
     * @return
     */
    private ArrayList<Certificat> generateRandomCertificat() {
        ArrayList<Certificat> alist = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            String mrn = "mrn" + rand.nextInt(400);
            String certificat = CERTIFICAT + rand.nextInt(BOUND);

            alist.add(new Certificat(certificat, mrn));
        }
        return alist;

    }

    private ArrayList<GetionCertificat> createGestionCertificat() {
        ArrayList<GetionCertificat> alist = new ArrayList<>();
        for (int i = 1; i <= BOUND; i++)
            alist.add(new GetionCertificat(CERTIFICAT + i, 100));

        return alist;
    }

}



