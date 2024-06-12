package com.myprojects.myportfolio.core.dto;

import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachmentDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = 5321033400580593050L;

    @NotNull(message = "Attachment: User Id cannot be null", groups = OnUpdate.class)
    Integer userId;

    @NotNull(message = "Attachment: Url cannot be null")
    String url;

    @NotNull(message = "Attachment: Type cannot be null")
    String type;

}
