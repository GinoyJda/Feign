package com.test;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/12/13.
 */
@Service("testRmi")
public class RmiTestImpl implements RmiTest{
    public String test(String s) {
        return "rmi test str is"+s;
    }
}
