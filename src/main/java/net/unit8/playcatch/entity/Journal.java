package net.unit8.playcatch.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "journals")
public class Journal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "journal_id")
    private Long id;

    private Long issueId;

    private String note;
    private BallOwner ballOwner;
    private IssueStatus status;
    private Date createdAt;
    private String createdBy;
}
