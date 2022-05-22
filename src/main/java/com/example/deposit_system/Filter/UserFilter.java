package com.example.deposit_system.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 欧皇表哥
 * @create 2022/5/18 0:26
 */

@WebFilter(filterName = "UserFilter", urlPatterns = "/user/*")
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //把一般的请求对象request强转成HttpServletRequest
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        //获得当前会话对象
        HttpSession session = req.getSession();
        // User loginUser = (User)session.getAttribute("loginUser");
        String name = (String) session.getAttribute("username");

        if(name == null){
            //HttpServletResponse resp = (HttpServletResponse)servletResponse;
            HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            System.out.println("用户过滤器启动了");
            wrapper.sendRedirect("/login");

        }
    }
}
