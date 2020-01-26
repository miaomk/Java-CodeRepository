package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.CollectMapper;
import com.techwells.wumei.domain.Collect;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.service.CollectService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("CollectService")
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCollect(Collect collect) {
        int count;
        try {
            count = collectMapper.insertSelective(collect);
            if (count < 0) {
                throw new Exception("添加收藏失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加收藏失败！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delCollect(int collectId) {
        int count;
        try {
            count = collectMapper.deleteByPrimaryKey(collectId);
            if (count < 0) {
                throw new Exception("删除收藏失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除收藏异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCollect(Collect collect) {
        int count;
        try {
            count = collectMapper.updateByPrimaryKeySelective(collect);
            if (count < 0) {
                throw new Exception("修改收藏失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改收藏异常！");
        }
        return count;
    }

    @Override
    public Collect getCollectByCollectId(int collectId) {
        Collect collect;
        try {
            collect = collectMapper.selectByPrimaryKey(collectId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏详情异常！");
        }
        return collect;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = collectMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏总数异常！");
        }
        return count;
    }

    @Override
    public List<RsCollect> getCollectList(PagingTool pagingTool) {
        List<RsCollect> collectList;

        try {
            collectList = collectMapper.selectCollectList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏列表异常");
        }
        return collectList;
    }

    @Override
    public int batchDelete(String[] ids, Integer userId) {
        int count;

        try {
            count = collectMapper.batchDelete(ids, userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏总数异常！");
        }
        return count;
    }

    @Override
    public Integer getCollectId(String relationId, String userId, Integer collectType) {
        int collectId;
        try {
            collectId = collectMapper.getCollectId(relationId, userId, collectType);

        } catch (Exception e) {
            e.printStackTrace();

            throw new ApplicationContextException("获取收藏id异常！");
        }
        return collectId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCollect(String userId, String relationId, String collectType) {
        int count;

        try {
            count = collectMapper.deleteCollect(userId, relationId, collectType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏总数异常！");
        }
        return count;
    }

    @Override
    public List<Integer> getRelationIdList(String userId, Integer collectType) {

        List<Integer> relationIdList;

        try {
            relationIdList = collectMapper.getRelationIdList(userId, collectType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取被收藏者id列表异常");
        }
        return relationIdList;
    }

    @Override
    public Collect getCollectByCollect(Collect collect) {

        Collect c;
        try {
            c = collectMapper.selectByCollect(collect);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏详情异常！");
        }
        return c;
    }


}
