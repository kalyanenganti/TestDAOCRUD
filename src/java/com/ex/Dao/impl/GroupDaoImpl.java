/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ex.Dao.impl;

import com.ex.Dao.GroupDao;
import com.ex.entity.Group;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 *
 * @author kalyan
 */
public class GroupDaoImpl implements GroupDao {

    private final String dbName = "GMS_INFO_TEMP";
    private final DataSource datasource = getDataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

    @Override
    public Group findGroupById(String id) {
       String sql = "SELECT * FROM " + dbName + " WHERE GROUP_ID = '" + id+ "'";        
return jdbcTemplate.query(sql, new ResultSetExtractor<Group>() {
            @Override
            public Group extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    Group group = new Group();
                    group.setId(rs.getString("group_id"));
                    group.setVersion(rs.getLong("version"));
                    group.setXml(rs.getString("xml"));
                    group.setGroupName(rs.getString("group_name"));
                    group.setCreatedBy(rs.getString("created_by"));
                    group.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return group;
                }

                return null;
        }
        });
    }

    @Override
    public Group findGroupByName(String name) {
        String sql = "SELECT * FROM " + dbName + " WHERE GROUP_NAME = '" + name+ "'";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Group>() {
            @Override
            public Group extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    Group group = new Group();
                    group.setId(rs.getString("group_id"));
                    group.setVersion(rs.getLong("version"));
                    group.setXml(rs.getString("xml"));
                    group.setGroupName(rs.getString("group_name"));
                    group.setCreatedBy(rs.getString("created_by"));
                    group.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return group;
                }

                return null;
            }

        });
    }

    @Override
    public void saveGroup(Group group) {
     long version;
        // insert
        String sql = "INSERT INTO " + dbName + " (GROUP_ID,VERSION,XML,GROUP_NAME,CREATED_BY,UPDATED_AT)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        version=(group.getVersion()!=null)?group.getVersion()+1:0l;
        int res_code = jdbcTemplate.update(sql, group.getId(), group.getVersion(),
                group.getXml(), group.getGroupName(), group.getCreatedBy(), group.getUpdatedAt());
        if (res_code != 0) {
            System.out.println("Successfully created a group with id " + group.getId());
        } else {
            System.out.println("Error encountered while creating a group with id " + group.getId());
        }
    }

    @Override
    public void deleteGroupById(String id) {
        String sql = "DELETE FROM " + dbName + " WHERE GROUP_ID = ?";
        int res_code = jdbcTemplate.update(sql, id);
        if (res_code != 0) {
            System.out.println("Successfully deleted a group with id " + id);
        } else {
            System.out.println("Error encountered while deleting a group with id " + id);
        }
    }

    @Override
    public List<Group> listAllGroups() {
        String sql = "SELECT * FROM " + dbName +" ORDER BY GROUP_NAME";
        List<Group> groupsList = jdbcTemplate.query(sql, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
                Group group = new Group();
                group.setId(rs.getString("group_id"));
                group.setVersion(rs.getLong("version"));
                group.setXml(rs.getString("xml"));
                group.setGroupName(rs.getString("GROUP_NAME"));
                group.setCreatedBy(rs.getString("created_by"));
                group.setUpdatedAt(rs.getTimestamp("updated_at"));
                return group;
            }
        });
        return groupsList;
    }

    private DataSource getDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriver(new oracle.jdbc.driver.OracleDriver());
        simpleDriverDataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
        simpleDriverDataSource.setUsername("root");
        simpleDriverDataSource.setPassword("killer");
        return simpleDriverDataSource;

    }
}
