package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "new_diaries")
public class NewDiary extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    /*@Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;*/

    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_user_diary_fk"
            )
    )
    private NewUser user;
}
