package net.threadtxtest.service.internal.util;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database utils.
 */
public final class DbUtil {
    /** Hidden ctor */
    private DbUtil() {}


    public static String readAllFromResource(String resourceUri) throws IOException {
        final InputStream inputStream = DbUtil.class.getResourceAsStream(resourceUri);
        if (inputStream == null) {
            throw new UnsupportedOperationException("No such resource: " + resourceUri);
        }

        try {
            int available = inputStream.available();
            if (available >= 0) {
                final byte[] buffer = new byte[available];
                final int bytesRead = inputStream.read(buffer);
                assert bytesRead == available;

                return new String(buffer, "UTF-8");
            }

            throw new UnsupportedOperationException("Unsupported resource: " + resourceUri);
        } finally {
            inputStream.close();
        }
    }

    public static void executeScript(DataSource dataSource, String resourcePath) throws IOException,
            DataAccessException {
        final String createScript = readAllFromResource(resourcePath);
        try {
            final Statement schemaStatement = dataSource.getConnection().createStatement();
            schemaStatement.execute(createScript);
        } catch (SQLException e) {
            throw new InvalidDataAccessResourceUsageException("Can't execute script " + resourcePath, e);
        }
    }
}
