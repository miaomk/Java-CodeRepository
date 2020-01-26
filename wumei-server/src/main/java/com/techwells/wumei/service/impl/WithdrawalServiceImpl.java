package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.WithdrawalRecordMapper;
import com.techwells.wumei.domain.WithdrawalRecord;
import com.techwells.wumei.domain.rs.BillVO;
import com.techwells.wumei.service.WithdrawalService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miao
 */
@Service(value = "WithdrawalService")
public class WithdrawalServiceImpl implements WithdrawalService {

    private final static String SPLIT = ",";

    @Resource
    private WithdrawalRecordMapper withdrawalRecordMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addWithdrawal(WithdrawalRecord withdrawalRecord) {
        int count;
        try {
            count = withdrawalRecordMapper.insertSelective(withdrawalRecord);
            if (count < 0) {

                throw new Exception("申请提现失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("申请提现异常！");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int auditWithdrawal(String recordId, String status) {
        int count;
        try {

            if (recordId.contains(SPLIT)) {

                WithdrawalRecord withdrawalRecord = new WithdrawalRecord();
                withdrawalRecord.setRecordId(Integer.parseInt(recordId));
                withdrawalRecord.setActivated(Integer.parseInt(status));
                count = withdrawalRecordMapper.updateByPrimaryKey(withdrawalRecord);
            } else {
                String[] recordIds = recordId.split(SPLIT);

                count = withdrawalRecordMapper.batchAudit(recordIds, status);
            }

            //TODO 往账户中打钱


            if (count < 0) {
                throw new Exception("审核提现申请失败!");
            }
        } catch (Exception e) {

            throw new ApplicationContextException("审核提现申请异常");
        }

        return count;
    }

    @Override
    public List<BillVO> getWithdrawalList(PagingTool pageTool) {
        List<BillVO> withdrawalRecordList;

        try {
            withdrawalRecordList = withdrawalRecordMapper.getWithdrawalList((String) pageTool.getParams().get("userId"), 1);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取用户提现列表异常");
        }

        return withdrawalRecordList;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = withdrawalRecordMapper.countTotal((String) pageTool.getParams().get("userId"), 1);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取提现总数异常！");
        }
        return count;
    }


}
