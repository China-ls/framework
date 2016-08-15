package com.infinite.water.core.cros;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CROSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, PUT, DELETE");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.addHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}