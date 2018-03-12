package com.lovehins.web.admin.biz;

import com.lovehins.base.sdk.biz.BaseBiz;
import com.lovehins.web.admin.entity.ResourceAuthority;
import com.lovehins.web.admin.mapper.ResourceAuthorityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lovedrose on 2017/6/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper,ResourceAuthority> {
}
