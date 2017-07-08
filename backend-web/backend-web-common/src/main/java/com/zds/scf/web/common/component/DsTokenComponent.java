package com.zds.scf.web.common.component;

import com.zds.scf.web.common.CPConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy at 2016/12/7 15:59
 */
@Component
public class DsTokenComponent {

    @Autowired
    private RedisTemplate<String, String> dsTokenSet;

    /**
     * 获取防重复提交token
     * @param httpServletRequest
     * @return
     */
    public String getDsToken(HttpServletRequest httpServletRequest){
         String token=nextToken();
        String sessionId= httpServletRequest.getSession().getId();
        String key= CPConstants.DUPLICATE_SUBMIT_TOKEN_SET+sessionId;
        dsTokenSet.boundSetOps(key).add(token);
        dsTokenSet.expire(key,60, TimeUnit.MINUTES);
        return token;
    }
    private String nextToken() {
        long seed = System.currentTimeMillis();
        Random r = new Random();
        r.setSeed(seed);
        return Long.toString(seed) + Long.toString(r.nextLong());
    }
}
