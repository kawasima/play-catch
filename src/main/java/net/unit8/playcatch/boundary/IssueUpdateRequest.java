package net.unit8.playcatch.boundary;

import lombok.Data;
import net.unit8.playcatch.entity.BallOwner;
import net.unit8.playcatch.entity.IssueStatus;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class IssueUpdateRequest implements Serializable {
    private String id;
    @Size(max = 100)
    private String subject;
    private String description;
    private BallOwner ballOwner;
    private IssueStatus status;
    private String note;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
}
