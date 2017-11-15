package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V1__CreateIssues implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("issues"))
                    .column(field("issue_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("subject", SQLDataType.VARCHAR(100).nullable(false)))
                    .column(field("description", SQLDataType.CLOB.nullable(false)))
                    .column(field("ball_owner", SQLDataType.VARCHAR(5).nullable(false)))
                    .column(field("status", SQLDataType.VARCHAR(5).nullable(false)))
                    .column(field("created_by", SQLDataType.VARCHAR(100).nullable(false)))
                    .column(field("created_at", SQLDataType.TIMESTAMP.nullable(false)))
                    .constraints(
                            constraint().primaryKey(field("issue_id"))
                    ).getSQL();
            stmt.execute(ddl);
        }
    }
}
