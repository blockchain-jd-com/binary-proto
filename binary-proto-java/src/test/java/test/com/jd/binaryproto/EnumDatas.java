package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;

/**
 * Created by zhangshuang3 on 2018/11/29.
 */
@DataContract(code = 0x07, name = "EnumDatas", description = "")
public interface EnumDatas {

    @DataField(order = 1, refEnum = true)
    EnumLevel getLevel();

}
