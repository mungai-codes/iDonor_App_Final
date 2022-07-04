package net.mungai.idonor.donor.models;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "donor_id")
    private Long id;
    private String county;
    private String subCounty;
    private String ward;
    @OneToOne
    @MapsId
    @JoinColumn(name = "donor_id")
    private Donor donor;

    public Address() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", county='" + county + '\'' +
                ", subCounty='" + subCounty + '\'' +
                ", ward='" + ward + '\'' +
                ", donor=" + donor +
                '}';
    }
}
