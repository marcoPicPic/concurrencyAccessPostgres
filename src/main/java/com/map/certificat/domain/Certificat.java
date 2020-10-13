package com.map.certificat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "certificat_brexit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Certificat {
    @Id
    private Long id;

    private String nomCertificat;

    private String mrn;

    private Date dateCreation;

    public Certificat(String nomCertificat, String mrn) {
        this.nomCertificat = nomCertificat;
        this.mrn = mrn;
    }
}
