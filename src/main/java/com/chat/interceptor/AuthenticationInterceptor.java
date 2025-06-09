package com.chat.interceptor;

import com.chat.exception.CustomException;
import com.chat.exception.ErrorCode;
import com.chat.interceptor.utils.UrlList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        String method = request.getMethod();

        boolean isAuthenticatedList = checkAuthenticatedList(url, method);
        if (isAuthenticatedList) {
            HttpSession session = request.getSession(false);
            if (session == null){
                throw new CustomException(ErrorCode.USER_NOT_AUTHENTICATED);
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean checkAuthenticatedList(String url, String method) {
        if (method == null) {
            return false;
        }

        String[] authenticatedUrls = UrlList.getAuthenticatedList().get(method);
        if (authenticatedUrls == null) {
            return false;
        }

        for (String urlPattern : authenticatedUrls) {
            if (matchUrlPattern(urlPattern, url)) {
                return true;
            }
        }

        return false;
    }

    private boolean matchUrlPattern(String urlPattern, String requestURI) {
        if (urlPattern.endsWith("/**")) {
            String basePattern = urlPattern.substring(0, urlPattern.length() - 3);
            return requestURI.startsWith(basePattern);
        } else {
            return urlPattern.equals(requestURI);
        }
    }
}
