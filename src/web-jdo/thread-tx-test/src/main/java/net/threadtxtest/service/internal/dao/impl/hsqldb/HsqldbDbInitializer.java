package net.threadtxtest.service.internal.dao.impl.hsqldb;

import net.threadtxtest.service.internal.util.DbUtil;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;

/**
 * Database initalizer.
 */
public final class HsqldbDbInitializer implements InitializingBean {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        DbUtil.executeScript(dataSource, "/sql/hsqldb/create-tables.sql");
        DbUtil.executeScript(dataSource, "/sql/hsqldb/apply-constraints.sql");
    }
}
