package com.map.certificat.repository;

import com.map.certificat.domain.Certificat;
import com.map.certificat.domain.GetionCertificat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificatPersistenceContextRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * permet de faire un lock virtuelle
     * @param certificat
     * @return
     */
    private List findAnLock(String certificat) {
        Query q = em.createNativeQuery("SELECT t.* FROM certificat_brexit t" +
                        " WHERE nom_certificat = :name  for update",
                Certificat.class);
        q.setParameter("name", certificat);
        return q.getResultList();
    }

    /**
     * creation du certificat
     * je lock la table le temps de la transaction
     * @param certificat
     * @param getionCertificat
     */
    @Transactional()
    public void createCertificatBrexit(Certificat certificat,
                                       ArrayList<GetionCertificat> getionCertificat) {
        //Lock table
        findAnLock(certificat.getNomCertificat());
        //Compte je verifie le nombre de certificat est toujours bon ?
        int resteCertificat = countCertificatBrexit(certificat);
        //insert le certificat
        if(resteCertificat < nombreCetificatDisponible(getionCertificat, certificat.getNomCertificat())) {
            String sqlquery = "insert into certificat_brexit (nom_certificat, date_creation ,mrn ) " +
                    "values (:nomcertificat, current_timestamp,:mrn);";
            Query q = em.createNativeQuery(sqlquery, Certificat.class);

            q.setParameter("nomcertificat", certificat.getNomCertificat());
            q.setParameter("mrn", certificat.getMrn());
            q.executeUpdate();

        }
    }

    /**
     * Donne le nombre de certificat disponible
     * @param getionCertificatList
     * @param certificatName
     * @return
     */
    private long nombreCetificatDisponible(ArrayList<GetionCertificat> getionCertificatList,
                                           String certificatName) {
        for (GetionCertificat getionCertificat : getionCertificatList) {
            if (getionCertificat.getNomCertificat().equals(certificatName)) {
                return getionCertificat.getNombreMax();
            }
        }
        return 0;
    }


    /**
     * Compte le nombre de certificats utilisés
     * @param certificat
     * @return
     */
    public int countCertificatBrexit(Certificat certificat) {
        Query query = em.createNativeQuery("select count(*) from certificat_brexit where nom_certificat = :name");
        query.setParameter("name", certificat.getNomCertificat());

        return ((Number) query.getSingleResult()).intValue();
    }


}
