package com.miao.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户修改信息对象
 *
 * @author miao
 */
@ApiModel(value = "用户修改信息对象", description = "用户修改的昵称/生日/性别等数据封装在此entity中")
@Data
public class ModifiedUserBO {

    @ApiModelProperty(value = "用户id", name = "userId", example = "20190101oweidjaw", required = true)
    private String userId;

    @ApiModelProperty(value = "性别", name = "sex", example = "1")
    private Integer sex;
    @ApiModelProperty(value = "昵称", name = "nickName", example = "NEXT")
    private String nickname;
    @ApiModelProperty(value = "生日", name = "birthday", example = "1900-01-01")
    private String birthday;
}
