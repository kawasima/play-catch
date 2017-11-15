package net.unit8.playcatch.dao;

import net.unit8.playcatch.DomaConfig;
import net.unit8.playcatch.entity.Issue;
import net.unit8.playcatch.entity.QueriedIssue;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

@Dao(config = DomaConfig.class)
public interface IssueDao {
    @Select
    List<QueriedIssue> selectAll(SelectOptions options);

    @Select(ensureResult = true)
    Issue selectById(Long id);

    @Insert
    int insert(Issue issue);

    @Update
    int update(Issue issue);

    @Delete
    int delete(Issue issue);
}
