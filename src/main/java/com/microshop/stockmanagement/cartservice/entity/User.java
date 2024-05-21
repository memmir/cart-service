package com.microshop.stockmanagement.cartservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "stock_management")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_surname")
    private String userSurname;

    @Column(name="user_phoneNumber")
    private String userPhoneNumber;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_address")
    private String userAddress;

    @OneToMany ( mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    @Builder.Default // bu alanı constructor da default olarak her zaman parametre olarak geçmesini sağlayan annottasyon.
    @Column(name = "user_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userUpdatedDate = new Date();


    @Builder.Default
    @Column(name = "user_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userCreatedDate = new Date();

    @Column(name = "is_deleted") // Bu kolon veritabanında soft delete yapmamızı sağlıyor. True ya çektiğimizde silinmiş gibi olacak
    private boolean deleted;
}
