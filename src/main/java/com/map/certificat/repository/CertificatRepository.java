package com.map.certificat.repository;

import com.map.certificat.domain.Certificat;
import org.springframework.data.repository.CrudRepository;

//https://stackoverflow.com/questions/31023050/concurrency-scenarios-with-inserts
public interface CertificatRepository extends CrudRepository<Certificat, Long> {
}
