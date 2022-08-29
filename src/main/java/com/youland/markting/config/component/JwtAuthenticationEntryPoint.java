package com.youland.markting.config.component;

import com.youland.commons.constant.CommonResponseEnum;
import com.youland.commons.model.ErrorResponse;
import com.youland.commons.utils.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenning
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        var code = HttpStatus.UNAUTHORIZED;
        ErrorResponse errorResponse = ErrorResponse.getInstance(CommonResponseEnum.NOT_LOGIN,code,request);
        response.setStatus(code.value());
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.write(JsonUtil.stringify(errorResponse));
        }
    }
}
