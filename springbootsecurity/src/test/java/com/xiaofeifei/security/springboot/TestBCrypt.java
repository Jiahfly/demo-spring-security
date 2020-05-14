package com.xiaofeifei.security.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestBCrypt {

    @Test
    public void testBCrype() {
        String hashpw_123 = BCrypt.hashpw("123", BCrypt.gensalt()); //BCrypt的作用，由于加盐的原因，每次生成的字符串都不一样，但是校验都一样
        System.out.println(hashpw_123);
        String hashpw_secret = BCrypt.hashpw("secret", BCrypt.gensalt()); //BCrypt的作用，由于加盐的原因，每次生成的字符串都不一样，但是校验都一样
        System.out.println(hashpw_secret);
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$W7dHYM3EXxhj.DeyaFtCBuwXTJ83xCdBto64kgVWNMgN9Gp9rKPIe");
        boolean checkpw1 = BCrypt.checkpw("123", "$2a$10$CwwagzM3CItSyTE8sw1pHeJy4E9ql0.jrRUA/g8Pdd1ry5DDe1bhq");

        System.out.println(checkpw);
        System.out.println(checkpw1);

    }
}
