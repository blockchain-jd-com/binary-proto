package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;

/**
 * Created by zhangshuang3 on 2018/11/29.
 */
@DataContract(code = 0x08, name = "RefContractDatas", description = "")
public interface RefContractDatas {

    @DataField(order = 1, refContract = true)
    PrimitiveDatas getPrimitive();
}
