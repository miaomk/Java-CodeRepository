package com.miao.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信用户信息
 *
 * @author miao
 */
@ApiModel(value = "微信小程序用户对象", description = "从客户端，获取到微信用户的数据封装在此entity中")
@Data
public class MPWXUserBO {

    @ApiModelProperty(value = "昵称", name = "nickName", example = "123456", required = true)
    private String nickName;

    @ApiModelProperty(value = "头像", name = "avatarUrl", example = "http://wx.qlogo.cn/", required = true)
    private String avatarUrl;

    @ApiModelProperty(value = "使用哪个小程序[0:NEXT超英预告][1:超英预告][2:NEXT学院电影预告]", name = "whichMP", example = "0")
    private Integer whichMP;

    @ApiModelProperty(value = "性别 0：未知、1：男、2：女", name = "gender", example = "1", required = true)
    private Integer gender;
}
