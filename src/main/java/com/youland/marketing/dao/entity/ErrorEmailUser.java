package com.youland.marketing.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chenning
 */
@Data
@Entity
@Table(name = "error_email_user")
public class ErrorEmailUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String template;
    private Boolean unsubscribe;
    private String errorInfo;
}
