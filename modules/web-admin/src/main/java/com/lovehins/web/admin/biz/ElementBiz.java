package com.lovehins.web.admin.biz;

import com.lovehins.base.sdk.biz.BaseBiz;
import com.lovehins.cache.annotation.Cache;
import com.lovehins.cache.annotation.CacheClear;
import com.lovehins.web.admin.entity.Element;
import com.lovehins.web.admin.mapper.ElementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * Created by lovedrose
 * @create 2017-06-23 20:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElementBiz extends BaseBiz<ElementMapper,Element> {
    @Cache(key="permission:ele:u{1}")
    public List<Element> getAuthorityElementByUserId(String userId){
       return mapper.selectAuthorityElementByUserId(userId);
    }
    public List<Element> getAuthorityElementByUserId(String userId,String menuId){
        return mapper.selectAuthorityMenuElementByUserId(userId,menuId);
    }

    @Cache(key="permission:ele")
    public List<Element> getAllElementPermissions(){
        return mapper.selectAllElementPermissions();
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void insertSelective(Element entity) {
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void updateSelectiveById(Element entity) {
        super.updateSelectiveById(entity);
    }
}
