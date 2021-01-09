package test.com.jd.binaryproto;

/**
 * Created by zhangshuang3 on 2018/11/29.
 */
public class RefContractDatasImpl implements RefContractDatas{
    PrimitiveDatas primitiveDatas;

    @Override
    public PrimitiveDatas getPrimitive() {
        return this.primitiveDatas;
    }

    public void setPrimitiveDatas(PrimitiveDatas primitiveDatas) {
        this.primitiveDatas = primitiveDatas;
    }

}
