package com.map.certificat.service;

import com.map.certificat.domain.Certificat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
public class CertificatServiceTest {

    @Autowired
    private CertificatService certificatService;

    @Test
    public void test() {
        // given
        ArrayList<Certificat> alist=new ArrayList<>();
        alist.add(new Certificat("certificat1", "mrn1"));
        alist.add(new Certificat("certificat1", "mrn2"));
        alist.add(new Certificat("certificat3", "mrn3"));


        // when
        certificatService.generateCertificat(alist.get(0), 0, null);

        // then
        //assertThat
    }

}