package com.isliujiao.ssoappa.filter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
@Component
@WebFilter(urlPatterns = "/**")
public class LoginFilter implements Filter {

    @Value("${sso_server}")
    private String serverHost;

    @Value("${local_server}")
    private String localHost;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getParameter("token");
        String requestURI = localHost + httpServletRequest.getRequestURI();
        // 前端存在，则从请求头的 Authorization 取token
//        String token = httpServletRequest.getHeader("token");
        if (this.check(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // token过期后再使用refreshToken处理
            String refreshToken = httpServletRequest.getHeader("refreshToken");
            if (check(refreshToken)) {
                // 重新生成token和refreshtoken给客户端保存 下次传递token参数的时候使用这个重新生成的
                System.out.println("更新后的token和refreshToken:" + refreshToken(refreshToken));
                filterChain.doFilter(servletRequest, servletResponse);
            }
            // 如果refreshToken也过期 那么需要重新登录
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String redirect = serverHost + "/login";
            response.sendRedirect(redirect);
        }
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws IOException
     */
    private boolean check(String token) throws IOException {
        if (token == null || token.trim().length() == 0) {
            return false;
        }
        OkHttpClient client = new OkHttpClient();
        // 去SSO服务验证token的合法性
        String url = serverHost + "/checkJwt?token=" + token;
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return Boolean.parseBoolean(response.body().string());
    }

    /**
     * 重新获取token和refreshToken
     *
     * @param refreshToken
     * @return
     * @throws IOException
     */
    private String refreshToken(String refreshToken) throws IOException {
        if (refreshToken == null || refreshToken.trim().length() == 0) {
            return null;
        }
        OkHttpClient client = new OkHttpClient();
        // 请求重新获取token和refreshToken
        String url = serverHost + "/refreshJwt?refreshToken=" + refreshToken;
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
}
