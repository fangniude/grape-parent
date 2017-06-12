package account.interceptor;

import account.AccountPlugin;
import account.domain.Account;
import account.token.Tokens;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class TokenInterceptor extends HandlerInterceptorAdapter {
    private final String authHeader = AccountPlugin.authHeader();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith(authHeader)) {
            final String token = authorization.substring(authHeader.length()); // The part after "Bearer "

            Account.setCurrent(Tokens.accountId(token));
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Account.removeCurrent();
        super.postHandle(request, response, handler, modelAndView);
    }
}
