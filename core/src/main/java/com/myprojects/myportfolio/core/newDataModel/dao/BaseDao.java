package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
public class BaseDao implements Serializable {

    @Id
    @Expose
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "com.myprojects.myportfolio.clients.utils.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @Column(name = "id", updatable = false)
    protected Integer id;

    public BaseDao() {
        super();
    }

    public String toString() {
        return toJson();
    }

    public String toJsonPretty() {
        // NOTE: use excludeFieldsWithoutExposeAnnotation() so in the DAO we need to use @Expose
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public String toJson() {
        // NOTE: use excludeFieldsWithoutExposeAnnotation() so in the DAO we need to use @Expose
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

}