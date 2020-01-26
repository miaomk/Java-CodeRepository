package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.PVMapper;
import com.techwells.wumei.domain.PV;
import com.techwells.wumei.service.PvService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 浏览量实现类
 *
 * @author miao
 */
@Service
public class PvServiceImpl implements PvService {
    @Resource
    private PVMapper pvMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPv(PV pv) {
        int count;
        try {
            count = pvMapper.insertSelective(pv);
            if (count < 0) {
                throw new Exception("添加浏览量失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加浏览量异常！");
        }
        return count;
    }

    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = pvMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取浏览总数异常！");
        }
        return count;
    }

}
