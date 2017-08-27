package com.zds.scf.web.out.fliter;

import com.zds.scf.web.common.acl.CPACLFilter;
import com.zds.scf.web.out.security.MyRSA;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/20.
 */
public class OutFilter extends CPACLFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException,
            IOException {

        //检查加密
        String tokenEncoded = request.getHeader("ywj_out_token");

        if(tokenEncoded==null){
            return ;
        }
        if(tokenEncoded.indexOf('_')<0){
            return;
        }
        String[] tokens = tokenEncoded.split("_");
        String token1 = tokens[0];
        String token2 = MyRSA.decoded(tokens[1]);

        if(!token1.equals(token2)){
            return;
        }

        filterChain.doFilter(request, response);

    }
}
