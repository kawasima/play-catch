package net.unit8.playcatch.dao;

import net.unit8.playcatch.DomaConfig;
import net.unit8.playcatch.entity.Journal;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;

@Dao(config = DomaConfig.class)
public interface JournalDao {
    @Insert
    int insert(Journal journal);
}
