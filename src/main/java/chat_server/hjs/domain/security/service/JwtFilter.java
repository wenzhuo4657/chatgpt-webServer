package chat_server.hjs.domain.security.service;

import chat_server.hjs.domain.security.model.vo.JwtToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: JwtFilter
 * @author: wenzhuo4657
 * @date: 2024/5/18 16:36
 * @Version: 1.0
 * @description: 过滤器
 */
public class JwtFilter extends AccessControlFilter {
    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    /**
     * isAccessAllowed 判断是否携带有效的 JwtToken
     * 所以这里直接返回一个 false，让它走 onAccessDenied 方法流程
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }


    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest realRequest = (HttpServletRequest)request;
        JwtToken jwtToken=new JwtToken(realRequest.getParameter("token"));//从前端传来的请求中获取token，

        try {
            // 鉴权认证
            getSubject(request, response).login(jwtToken);
            return true;
        } catch (Exception e) {
            logger.error("鉴权认证失败", e);
            onLoginFail(response);
            return false;
        }
    }

    /**
     * 鉴权认证失败时默认返回 401 状态码
     */

    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Auth Err!");

    }
}