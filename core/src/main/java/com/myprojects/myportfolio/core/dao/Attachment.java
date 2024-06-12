package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "attachments")
@SequenceGenerator(name = "default_gen", sequenceName = "attachment_seq", allocationSize = 1)
@Cacheable
@Cache(region = "attachments", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -7163498599343140223L;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String type;

    /**
     * @Create: When Creating an Attachment, we need to pass an existing userId.
     * @Update: When Updating an Attachment, the already connected user is left untouched.
     * @Delete: When Deleting an Attachment, the user is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_attachment_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    @Cache(region = "users", usage = CacheConcurrencyStrategy.READ_ONLY)
    private User user = new User();

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getUserId() {
        if (this.getUser() == null)
            return null;
        return this.getUser().getId();
    }

}
