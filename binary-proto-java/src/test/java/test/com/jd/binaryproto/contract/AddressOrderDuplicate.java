package test.com.jd.binaryproto.contract;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;

/**
 * Created by zhangshuang3 on 2018/7/9.
 */
@DataContract(code=0x03, name="Address" , description="")
public interface AddressOrderDuplicate {

    @DataField(order=1, primitiveType= PrimitiveType.TEXT)
    String getStreet();

    @DataField(order=1, primitiveType=PrimitiveType.INT32)
    int getNumber();

}
