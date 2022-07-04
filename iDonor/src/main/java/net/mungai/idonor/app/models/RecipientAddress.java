package net.mungai.idonor.app.models;


import net.mungai.idonor.app.models.DonationAppeal;

import javax.persistence.*;

@Entity
public class RecipientAddress {

    @Id
    @Column(name = "recipient_id")
    private Long id;

    private String hospital;
    private String county;
    private String subCounty;
    private String ward;
    @OneToOne
    @MapsId
    @JoinColumn(name = "recipient_id")
    private DonationAppeal appeal;

    public RecipientAddress(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public DonationAppeal getAppeal() {
        return appeal;
    }

    public void setAppeal(DonationAppeal appeal) {
        this.appeal = appeal;
    }
}
