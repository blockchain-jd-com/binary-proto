package test.com.jd.binaryproto.contract;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;

/**
 * Created by zhangshuang3 on 2018/7/30.
 */
@DataContract(code=0x0f, name="PrivilegeModelSetting", description ="Privilege Model setting")
public interface PrivilegeModelSetting {

    @DataField(order=1, primitiveType= PrimitiveType.INT64)
    long getLatestVersion();

    //@DataField(order=2, refContract=true)
    //Privilege getPrivilege(long version);

}

