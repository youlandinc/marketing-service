package com.youland.markting.dao.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

/**
 * @author chenning
 */
@Data
@Entity
@Table(name = "marketing_user")
@Schema
public class MarketingUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
}
