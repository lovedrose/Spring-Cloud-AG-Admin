
package com.lovehins.tool.search.service;


import com.lovehins.base.sdk.msg.TableResultResponse;
import com.lovehins.base.sdk.vo.IndexObject;

/**
 * lucense 接口
 * @author lovedrose
 */
public interface LuceneService {

    void save(IndexObject indexObject);

    void update(IndexObject indexObject);

    void delete(IndexObject indexObject);

    void deleteAll();

    TableResultResponse page(Integer pageNumber, Integer pageSize, String keyword);
}
