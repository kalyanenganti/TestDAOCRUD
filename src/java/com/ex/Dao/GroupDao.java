package com.ex.Dao;
import com.ex.entity.Group;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalyan
 */
public interface GroupDao {
    public Group findGroupById(String id);
    public Group findGroupByName(String name);
    public void saveGroup(Group group);
    public void deleteGroupById(String id);
    public List<Group> listAllGroups();
}
