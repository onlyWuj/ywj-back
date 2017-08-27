
package com.zds.scf.biz.common.config;

import com.cp.boot.appservice.filter.AppServiceContext;
import com.cp.boot.filterchain.Filter;
import com.cp.boot.filterchain.FilterChain;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.base.CPRequest;

/**
 *
 */
public class ContextInitFilter implements Filter<AppServiceContext> {
    @Override
    public void doFilter(AppServiceContext context, FilterChain<AppServiceContext> filterChain) {
        CPRequest cpRequest = (CPRequest) context.getMethodInvocation().getArguments()[0];
        CPContext cpContext = CPContext.getContext();
        cpContext.setGid(cpRequest.getGid());
        cpContext.setUserInfo(cpRequest.getUserInfo());
        filterChain.doFilter(context);
    }
}
