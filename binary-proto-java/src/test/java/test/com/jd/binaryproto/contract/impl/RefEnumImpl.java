package test.com.jd.binaryproto.contract.impl;

import test.com.jd.binaryproto.contract.Level;
import test.com.jd.binaryproto.contract.RefEnum;

/**
 * Created by zhangshuang3 on 2018/7/11.
 */
public class RefEnumImpl implements RefEnum {

    private Level level;

    @Override
    public Level getLevel() {
        // TODO Auto-generated method stub
        return this.level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
}

