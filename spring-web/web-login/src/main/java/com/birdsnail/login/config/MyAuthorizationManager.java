package com.birdsnail.login.config;

import cn.hutool.core.collection.CollectionUtil;
import com.birdsnail.login.dao.RoleResourceRepository;
import com.birdsnail.login.dao.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * 动态权限拦截
 */
@Component
public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        Authentication userAuth = authentication.get();
        Collection<? extends GrantedAuthority> authorities = userAuth.getAuthorities();
        if (CollectionUtil.isEmpty(authorities) || userAuth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }

        List<Long> roleIds = authorities.stream().map(GrantedAuthority::getAuthority).map(Long::parseLong).toList();
        List<ResourceEntity> resourceList = roleResourceRepository.findAllByRoleId(roleIds);
        if (CollectionUtil.isEmpty(resourceList)) {
            return new AuthorizationDecision(false);
        }

        String requestURI = requestContext.getRequest().getRequestURI();
        for (ResourceEntity resourceEntity : resourceList) {
            // 接口权限匹配
            if (antPathMatcher.match(resourceEntity.getPath(), requestURI)) {
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }

}
