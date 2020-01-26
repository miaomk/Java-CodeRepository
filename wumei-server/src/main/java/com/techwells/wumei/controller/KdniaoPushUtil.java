package com.techwells.wumei.controller;

import net.sf.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping(value = "/kdniao")
public class KdniaoPushUtil extends HttpServlet {

    // 电商ID
    private String EBusinessID = "1603095";

    @RequestMapping(value = "/push")
    public void push(HttpServletRequest request,
                     HttpServletResponse response) {
//    public void push(String RequestData,String RequestType,String DataSign,HttpServletResponse response) {
        try {
            String RequestData = request.getParameter("RequestData");
            parseJson(RequestData);
            returnMessageToKuaiDiNiao(response);
        } catch (Exception e) {
            returnMessageToKuaiDiNiao(response);//保证快递鸟公司能够接收到信息
            e.printStackTrace();
        }
    }

    /**
     * @param returnJson 解析json数据
     * @throws JSONException
     */
    private void parseJson(String returnJson) throws JSONException {
        org.json.JSONObject obj = new org.json.JSONObject(returnJson);
        JSONArray array = obj.getJSONArray("Data");// 存放着订阅的多个订单信息
        for (int i = 0; i < array.length(); i++) {
            org.json.JSONObject json = array.getJSONObject(i);
            String LogisticCode = json.getString("LogisticCode");// 订单号
            String ShipperCode = json.getString("ShipperCode");// 快递公司编号
            JSONArray traces = json.getJSONArray("Traces");
            //将接收到的信息按照公司的业务进行处理.....

            System.out.println("LogisticCode" + LogisticCode);
            System.out.println("ShipperCode" + ShipperCode);
            System.out.println("traces" + traces);

        }
    }

    /**
     * @param response 返回信息给快递鸟公司
     */
    private void returnMessageToKuaiDiNiao(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JSONObject obj = new JSONObject();
            obj.put("EBusinessID", EBusinessID);
            obj.put("UpdateTime", sdf.format(new Date()));
            obj.put("Success", true);
            obj.put("Reason", "");
            out.write(obj.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}