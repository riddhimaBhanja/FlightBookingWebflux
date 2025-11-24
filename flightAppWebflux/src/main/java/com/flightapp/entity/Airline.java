/*
 * package com.flightapp.entity;
 * 
 * import jakarta.persistence.*; import org.springframework.data.annotation.Id;
 * import org.springframework.data.mongodb.core.mapping.Document; import
 * lombok.Data;
 * 
 * @Data
 * 
 * @Document(collection = "airline") public class Airline {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * @Column(nullable = false, unique = true, length = 10) private String code;
 * 
 * @Column(nullable = false, length = 100) private String name;
 * 
 * private String logoUrl; }
 */