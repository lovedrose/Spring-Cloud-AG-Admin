package com.lovehins.web.admin.biz;

import com.lovehins.base.sdk.biz.BaseBiz;
import org.springframework.stereotype.Service;

import com.lovehins.web.admin.entity.GroupType;
import com.lovehins.web.admin.mapper.GroupTypeMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper,GroupType> {
}
