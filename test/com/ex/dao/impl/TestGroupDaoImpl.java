/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ex.dao.impl;

import com.ex.Dao.GroupDao;
import com.ex.entity.Group;
import com.thoughtworks.xstream.XStream;
import java.sql.Timestamp;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author kalyan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"testContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TestGroupDaoImpl {
@Autowired
    private GroupDao groupDao;
    private Group group;

    public TestGroupDaoImpl() {
    }

    @Before
    public void setUp() {
        group = new Group();
        XStream xstream = new XStream();
        group.setId("GRP_1");
        group.setVersion(0l);
        group.setGroupName("FirstGroup");
        group.setCreatedBy("John Doe");
        group.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        String xml = xstream.toXML(group);
        group.setXml(xml);
        groupDao.saveGroup(group);
    }

    @Test
    public void testGroupDaoImplMethods(){
    testFindGroupByName();
    testsaveGroup();
    testListAllGroups();
    testFindGroupById();
    testDeleteGroupById();
    }
    
    
    
    @After
    public void tearDown() {
       groupDao.deleteGroupById("GRP_1");

    }


  
    public void testFindGroupById() {
        Group group1 = groupDao.findGroupById("GRP_2");
        Assert.assertEquals("SecondGroup", group1.getGroupName());
        Assert.assertEquals("Jane Doe", group1.getCreatedBy());
        return;
    }
    // @Test
    public void testDeleteGroupById() {
        groupDao.deleteGroupById("GRP_2");
        Group group = groupDao.findGroupById("GRP_2");
        Assert.assertTrue(group==null);
        return;
    }

    public void testFindGroupByName() {
        Group group2 = groupDao.findGroupByName("FirstGroup");
        Assert.assertEquals("GRP_1", group2.getId());
        Assert.assertEquals("John Doe", group2.getCreatedBy());
        return;
    }

    public void testsaveGroup()
    {
        Group newGroup=new Group();
        XStream xstream1 = new XStream();
        newGroup.setId("GRP_2");
        newGroup.setVersion(0l);
        newGroup.setGroupName("SecondGroup");
        newGroup.setCreatedBy("Jane Doe");
        newGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        String xml = xstream1.toXML(newGroup);
        newGroup.setXml(xml);
        groupDao.saveGroup(newGroup);
        Group savedGroup=groupDao.findGroupByName("SecondGroup");
        Assert.assertEquals("Jane Doe", savedGroup.getCreatedBy());
        Assert.assertEquals("GRP_2", savedGroup.getId());
        return;
    }
   // @Test
    public void testListAllGroups()
    {
      
        List<Group> groupsList = groupDao.listAllGroups();
        Assert.assertEquals(2, groupsList.size());
        Group group1 = groupsList.get(0);

        Assert.assertEquals("John Doe", group1.getCreatedBy());
        Assert.assertEquals("FirstGroup", group1.getGroupName());
        
        Group group2 = groupsList.get(1);

        Assert.assertEquals("Jane Doe", group2.getCreatedBy());
        Assert.assertEquals("SecondGroup", group2.getGroupName());
        return;
    }
}
