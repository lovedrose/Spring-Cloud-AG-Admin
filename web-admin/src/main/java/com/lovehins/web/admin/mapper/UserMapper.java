package com.lovehins.web.admin.mapper;

import com.lovehins.web.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> selectMemberByGroupId(@Param("groupId") int groupId);
    List<User> selectLeaderByGroupId(@Param("groupId") int groupId);
}