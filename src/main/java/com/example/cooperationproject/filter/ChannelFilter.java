package com.example.cooperationproject.filter;



import com.example.cooperationproject.wrapper.RequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * @author:
 */
@Component
@WebFilter(filterName = "channelFilter", urlPatterns = {"/*"})
public class ChannelFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    // 伪造request
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            ServletRequest requestWrapper = null;
            if (request instanceof HttpServletRequest) {
                System.out.println("11111111");
                requestWrapper = new RequestWrapper((HttpServletRequest) request);
            }
            if (requestWrapper == null) {
                System.out.println("22222222");
                chain.doFilter(request, response);
            } else {
                System.out.println("44444444");
                chain.doFilter(requestWrapper, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
    }

}

