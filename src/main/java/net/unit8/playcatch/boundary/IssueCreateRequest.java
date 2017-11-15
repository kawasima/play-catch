package net.unit8.playcatch.boundary;

import kotowari.data.Validatable;
import lombok.Data;
import net.unit8.playcatch.entity.BallOwner;
import net.unit8.playcatch.entity.IssueStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class IssueCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;
    @NotBlank
    private String description;

    @NotNull
    private BallOwner ballOwner;

    @NotNull
    private IssueStatus status;

    private Date createdAt;
    private String createdBy;
}
