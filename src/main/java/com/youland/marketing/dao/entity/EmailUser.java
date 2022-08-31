package com.youland.marketing.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chenning
 */
@Data
@Entity
@Table(name = "email_user")
public class EmailUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String template;
    private Boolean unsubscribe;
}
