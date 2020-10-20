package ink.fujisann.learning.configure.mysql;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.stereotype.Component;

@Component
public class mysqlConfig extends MySQL8Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=INNODB DEFAULT CHARSET=UTF8";
    }
}
