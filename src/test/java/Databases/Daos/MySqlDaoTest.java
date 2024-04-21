package Databases.Daos;

import Databases.Exceptions.DaoException;
import junit.framework.TestCase;

public class MySqlDaoTest extends TestCase {

    public void testGetConnection() throws DaoException {
        MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao.getConnection());
    }
}