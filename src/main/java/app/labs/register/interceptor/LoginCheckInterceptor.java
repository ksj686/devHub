package app.labs.register.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    // 인증이 필요하지 않은 경로들
    private static final List<String> excludeUrls = Arrays.asList(
        "/members/login",
        "/members/find-username",
        "/members/find-password",
        "/members/insert",
        "/members/check-userid"  // 이 경로에 대한 인증 우회 추가
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();

        // 인증이 필요하지 않은 경로인지 확인
        if (excludeUrls.contains(requestUri)) {
            return true;
        }

        HttpSession session = request.getSession(false); // 이미 존재하는 세션만 가져옴
        if (session == null || session.getAttribute("userid") == null) {
            response.sendRedirect("/members/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // System.out.println("컨트롤러 실행됨.");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // System.out.println("뷰가 처리됨.");
    }
}
