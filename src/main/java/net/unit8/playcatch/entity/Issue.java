package net.unit8.playcatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "issues")
@Data
@JsonPropertyOrder(value = {
        "id", "subject", "description", "ballOwner",
        "status", "createdAt", "createdBy"
})
public class Issue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    private String subject;
    private String description;
    private BallOwner ballOwner;
    private IssueStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    private String createdBy;
}
