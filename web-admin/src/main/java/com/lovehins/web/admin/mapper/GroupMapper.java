package com.lovehins.web.admin.mapper;

import com.lovehins.web.admin.entity.Group;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface GroupMapper extends Mapper<Group> {
    void deleteGroupMembersById (@Param("groupId") int groupId);
    void deleteGroupLeadersById (@Param("groupId") int groupId);
    void insertGroupMembersById (@Param("groupId") int groupId,@Param("userId") int userId);
    void insertGroupLeadersById (@Param("groupId") int groupId,@Param("userId") int userId);
}