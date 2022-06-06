package com.deBijenkorf.ImageService.entity;

import lombok.Getter;
import javax.persistence.*;

/**
 * Entity representing an error thrown in the database
 */
@Getter
@Entity
@Table(name = "T_ERROR_LOGS")
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String process;
    private String message;
    private String type;
    private String date;

    public ErrorLog(String process, String message, String type, String date) {
        this.process = process;
        this.message = message;
        this.type = type;
        this.date = date;
    }
}
