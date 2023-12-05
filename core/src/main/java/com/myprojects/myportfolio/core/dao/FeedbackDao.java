package com.myprojects.myportfolio.core.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "feedback")
public class FeedbackDao extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -2559244600778034510L;

    private String ip;
    private String newFeatures;
    private String registerForm;

}
