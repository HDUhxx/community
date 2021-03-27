package com.nowcoder.community.config;


import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.aspectj.weaver.ast.And;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import sun.misc.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源的访问
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests()
                .antMatchers(
                        /**
                         * 授权访问的路径
                         */
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                )
                .hasAnyAuthority(
                        /*
                        * 谁有这个权限
                        */
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR,
                        AUTHORITY_USER
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful"
                )
                .hasAnyAuthority(
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete",
                        "/data/**",
                        "/actuator/**"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )

                .anyRequest().permitAll()
                .and().csrf().disable();//禁止cdrf令牌


        //权限不够时的处理
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest Request, HttpServletResponse Response, AuthenticationException e) throws IOException, ServletException {
                        String xRequestedWith = Request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)){
                            Response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = Response.getWriter();
                            writer.write(CommunityUtil.getJsonString(403,"你还没有登录"));
                        }else {
                            Response.sendRedirect(Request.getContextPath() + "/login");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    //权限不足
                    @Override
                    public void handle(HttpServletRequest Request, HttpServletResponse Response, AccessDeniedException e) throws IOException, ServletException {
                        String xRequestedWith = Request.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)){
                            Response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = Response.getWriter();
                            writer.write(CommunityUtil.getJsonString(403,"没有访问权限"));
                        }else {
                            Response.sendRedirect(Request.getContextPath() + "/denied");
                        }
                    }
                });
        //security底层默认会拦截/login请求，进行退出处理
        //覆盖它的逻辑，执行我们自己的退出程序
        http.logout().logoutUrl("/securitylogout");
    }
}
