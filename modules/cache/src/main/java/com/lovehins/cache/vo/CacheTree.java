package com.lovehins.cache.vo;

import com.lovehins.cache.entity.CacheBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovedrose on 23/04/2018.
 */
public class CacheTree extends CacheBean {
    private String id;
    private String parentId;
    private String text = null;
    private List<CacheTree> nodes = new ArrayList();

    public CacheTree(CacheBean cache) {
        this.setKey(cache.getKey());
        this.setDesc(cache.getDesc());
        this.setExpireTime(cache.getExpireTime());
    }

    public CacheTree() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.text = id;
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CacheTree> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<CacheTree> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheTree cacheTree = (CacheTree) o;

        if (!id.equals(cacheTree.id)) return false;
        return parentId.equals(cacheTree.parentId);

    }

    @Override
    public int hashCode() {
        byte result0 = 1;
        int result1 = 31 * result0 + (this.id == null ? 0 : this.id.hashCode());
        result1 = 31 * result1 + (this.parentId == null ? 0 : this.parentId.hashCode());
        return result1;
    }
}
