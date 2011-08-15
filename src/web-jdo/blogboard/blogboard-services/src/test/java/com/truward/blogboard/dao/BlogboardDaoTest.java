package com.truward.blogboard.dao;

import com.truward.blogboard.domain.ProfileData;
import com.truward.blogboard.domain.SipAccount;
import com.truward.blogboard.domain.SocialProfileData;
import com.truward.blogboard.domain.UserAccount;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/hibernate-dao-context.xml" })
public class BlogboardDaoTest {
    @Resource
    private BlogboardDao blogboardDao;

    @Resource
    private DataSource dataSource;

    @Test
    public void testAccountsRetrieval() {
        final List<UserAccount> accounts = blogboardDao.getAccounts(0, false, null);
        Assert.assertNotNull(accounts);
    }

    @Test
    public void testFriends() throws Exception {
        final Logger log = Logger.getLogger(BlogboardDaoTest.class);
        final List<ProfileData> profiles = blogboardDao.getProfileDataList();

        for (ProfileData p : profiles) {
            String friends;
            if (p.getFriends().size() == 0) {
                friends = "<no friends>";
            } else {
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('(');
                boolean next = false;
                for (final ProfileData fp : p.getFriends()) {
                    if (next) {
                        stringBuilder.append(", ");
                    } else {
                        next = true;
                    }

                    stringBuilder.append(fp.getUsername());
                }

                stringBuilder.append(')');
                friends = stringBuilder.toString();
            }


            log.info(MessageFormat.format("user: {0}, {1} {2}",
                    p.getId(), p.getUsername(), friends));
        }


        final List<SocialProfileData> socialProfiles = blogboardDao.getProfileDataList(0);
        log.info("social profiles for user #0");
        for (final SocialProfileData sp : socialProfiles) {
            log.info(String.format("sp name: %s, friend: %s", sp.getUsername(), sp.isFriend()));
        }

//        profiles.get(0).getFriends().add(profiles.get(1));
//        profiles.get(0).getFriends().add(profiles.get(2));
//        profiles.get(0).getFriends().add(profiles.get(3));
//        profiles.get(3).getFriends().add(profiles.get(1));
//        profiles.get(4).getFriends().add(profiles.get(2));

//        blogboardDao.saveProfileData(profiles.get(0));
//        blogboardDao.saveProfileData(profiles.get(3));
//        blogboardDao.saveProfileData(profiles.get(4));

        final Statement st = dataSource.getConnection().createStatement();
        final ResultSet rs = st.executeQuery("select * from FRIEND");
        final ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("friend rel:");
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                final Object value = rs.getObject(rsmd.getColumnName(i));
                stringBuilder.append(" ");
                stringBuilder.append(value.toString());
            }
            log.info(stringBuilder.toString());
        }
    }

    @Test
    public void testSqlMapping() throws Exception {
        // add account
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        final Logger log = Logger.getLogger(BlogboardDaoTest.class);

        final List<SipAccount> accounts = template.query(
                "select * from SIP_ACCOUNT order by ID;\n", new RowMapper<SipAccount>() {
                    @Override
                    public SipAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final SipAccount account = new SipAccount();
                        account.setId(rs.getInt("ID"));
                        account.setNumber(rs.getString("SIP_NUMBER"));
                        account.setPassword(rs.getString("SIP_PASSWORD"));
                        return account;
                    }
                });

        for (final SipAccount a : accounts) {
            log.info(MessageFormat.format("RS : ID={0}, SIP_ACCOUNT={1}, SIP_PASSWORD={2}",
                    a.getId(), a.getNumber(), a.getPassword()));
        }

        // get data using JPA-powered DAO
        {
            final SipAccount a = blogboardDao.getSipAccount(1);
            log.info(MessageFormat.format("JPA: ID={0}, SIP_ACCOUNT={1}, SIP_PASSWORD={2}",
                    a.getId(), a.getNumber(), a.getPassword()));
        }
    }
}
