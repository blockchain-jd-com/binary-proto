package test.com.jd.binaryproto;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;

/**
 * Created by zhangshuang3 on 2018/11/29.
 */
@DataContract(code = 0xa, name = "SubOperation", description = "")
public interface SubOperation extends Operation {

    @DataField(order=1, primitiveType = PrimitiveType.TEXT)
    String getUserName();

}
