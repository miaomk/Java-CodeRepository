package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Commodity;
import lombok.Data;

/**
 * @author miao
 */
@Data
public class RsCommodity extends Commodity {

    private String companyName;

    private String companyContact;

    private Integer collectId;
}
