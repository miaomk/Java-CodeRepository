package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Message;
import com.techwells.wumei.service.MessageService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ParamCheckUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;


@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 添加通知消息
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/message/addMessage")
    public ResultInfo addMessage(HttpServletRequest request) {
        ResultInfo rsInfo = new ResultInfo();

        String messageName = request.getParameter("messageName");

        if (messageName == null || "".equals(messageName)) {
            rsInfo.setMessage("通知消息名称不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        Message message = new Message();

        int count;
        try {
            count = messageService.addMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("添加通知消息异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("添加通知消息成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("添加通知消息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 修改通知消息信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/message/modifyMessage")
    public ResultInfo modifyMessage(HttpServletRequest request) {
        ResultInfo rsInfo = new ResultInfo();

        String messageId = request.getParameter("messageId");
        String messageName = request.getParameter("messageName");

        if (messageId == null || messageId.equals("")) {
            rsInfo.setMessage("通知消息ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        if (messageName == null || "".equals(messageName)) {
            rsInfo.setMessage("通知消息名称不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        Message message = new Message();

        message.setMessageId(Integer.parseInt(messageId));
        int count;
        try {
            count = messageService.modifyMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改通知消息异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改通知消息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改通知消息成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 删除通知消息
     *
     * @param messageId 通知消息id
     * @return ResultInfo
     */
    @RequestMapping(value = "/message/deleteMessage")
    public ResultInfo deleteMessage(@RequestParam("messageId") Integer messageId) {
        ResultInfo rsInfo = new ResultInfo();

        if (messageId == null) {
            rsInfo.setMessage("通知消息Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        int count;
        try {
            count = messageService.delMessage(messageId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除通知消息异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除通知消息成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除通知消息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }

    /**
     * 查看通知消息详情
     *
     * @param messageId 通知消息id
     * @return ResultInfo
     */
    @RequestMapping(value = "/message/getMessageById")
    public ResultInfo getMessageById(@RequestParam("messageId") Integer messageId) {
        ResultInfo rsInfo = new ResultInfo();

        if (messageId == null) {
            rsInfo.setMessage("通知消息ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        Message message;
        try {
            message = messageService.getMessageByMessageId(messageId);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取通知消息信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (message == null) {
            rsInfo.setMessage("通知消息信息不存在！");
            rsInfo.setData(new Message());
            rsInfo.setTotal(0);
            return rsInfo;
        }
        rsInfo.setData(message);
        rsInfo.setMessage("获取通知消息成功！");
        return rsInfo;
    }

    /**
     * 获取通知消息列表
     *
     * @param pageNum     页数
     * @param pageSize    页大小
     * @param messageName 消息名
     * @param userId      推送的用户id
     * @param type        类型 1表示普通系统消息， 2表示审核消息， 3表示物流消息
     * @return ResultInfo
     */
    @RequestMapping(value = "/message/getMessageList")
    public ResultInfo getMessageList(@RequestParam(value = "pageNum") Integer pageNum,
                                     @RequestParam(value = "pageSize") Integer pageSize,
                                     @RequestParam(value = "messageName", required = false) String messageName,
                                     @RequestParam(value = "userId", required = false) Integer userId,
                                     @RequestParam(value = "type", required = false) Integer type) {
        ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }
        HashMap<String, Object> params = new HashMap<>(16);

        if (messageName != null && !"".equals(messageName)) {
            params.put("messageName", messageName);
        }
        if (userId != null) {
            params.put("userId", userId);
        }
        if (type != null) {
            params.put("type", type);
        }
        PagingTool pageTool = new PagingTool(pageNum, pageSize);
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = messageService.countTotal(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取通知消息总数异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (totalCount == 0) {
            resultInfo.setMessage("通知消息总数量为0！");
            resultInfo.setCode("23211");
            return resultInfo;
        }

        List<Message> messageList;
        try {
            messageList = messageService.getMessageList(pageTool);
        } catch (Exception e) {
            resultInfo.setMessage("获取通知消息列表异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }
        if (messageList.size() == 0) {
            resultInfo.setMessage("通知消息列表为空！");
            resultInfo.setData(new ArrayList<Message>());
            return resultInfo;
        }
        resultInfo.setData(messageList);
        resultInfo.setTotal(totalCount);
        resultInfo.setMessage("获取通知消息列表成功！");
        return resultInfo;
    }

    /**
     * 批量删除通知消息
     *
     * @param messageIds 通知消息id字符串
     * @return ResultInfo
     */
    @RequestMapping(value = "/message/deleteMessageBatch")
    public ResultInfo deleteBatch(@RequestParam("messageIds") String messageIds) {
        ResultInfo rsInfo = new ResultInfo();


        if (StringUtil.isEmpty(messageIds)) {
            rsInfo.setMessage("ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }

        int count;
        try {
            count = messageService.deleteBatch(messageIds.split(","));
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("批量删除异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (count > 0) {
            rsInfo.setMessage("批量删除成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("批量删除失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }
}
