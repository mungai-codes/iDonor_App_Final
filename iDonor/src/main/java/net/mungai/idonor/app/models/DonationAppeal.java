package net.mungai.idonor.app.models;

import javax.persistence.*;

@Entity
@Table(name = "appeal_details")
public class DonationAppeal extends AppealBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;
    private Integer recipientAge;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appeal_bloodtype_id")
    private BloodType bloodType;
    private String description;
    private String phoneNumber;
    private Integer numberOfPints;

    @OneToOne(mappedBy = "appeal", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RecipientAddress recipientAddress;
    @Column(name = "isApproved",nullable = false)
    private boolean approved;

    public DonationAppeal(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Integer getRecipientAge() {
        return recipientAge;
    }

    public void setRecipientAge(Integer recipientAge) {
        this.recipientAge = recipientAge;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType requiredBloodType) {
        this.bloodType = requiredBloodType;
    }

    public RecipientAddress getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(RecipientAddress recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNumberOfPints() {
        return numberOfPints;
    }

    public void setNumberOfPints(Integer numberOfPints) {
        this.numberOfPints = numberOfPints;
    }
}
