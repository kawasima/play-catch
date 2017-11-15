package net.unit8.playcatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import java.util.Date;

@Entity
@Data
public class QueriedIssue {
    @Column(name = "issue_id")
    private Long id;

    private String subject;
    private String description;
    private String note;
    private BallOwner ballOwner;
    private IssueStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    private String createdBy;

    private Date updatedAt;
    private String updatedBy;
}
