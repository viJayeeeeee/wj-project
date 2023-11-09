package com.evan.wj.filter;

import com.evan.wj.service.AdminPermissionService;
import com.evan.wj.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {

//    @Autowired
    AdminPermissionService adminPermissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//        options请求放行
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        if (adminPermissionService == null)
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);

        String requestAPI = getPathWithinApplication(request);
        System.out.println("访问接口：" + requestAPI);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            System.out.println("需要登录！");
            return false;
        }

        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if (!needFilter) {
            System.out.printf("接口：%s，无需权限", requestAPI);
            return true;
        } else {
            System.out.println("验证访问权限：" + requestAPI);
            boolean hasPermission = false;
            String username = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
            for (String api : permissionAPIs) {
                if (api.equals(requestAPI)) {
                    hasPermission = true;
                    break;
                }

            }
            if (hasPermission) {
                System.out.printf("访问权限：%s，验证成功！", requestAPI);
                return true;
            } else {
                System.out.printf("用户没有访问接口（%s）的权限", requestAPI);
                return false;
            }
        }
    }
}
