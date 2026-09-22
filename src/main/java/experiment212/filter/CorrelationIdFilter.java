package experiment212.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "X-Correlation-ID";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put("correlationId", correlationId);
        response.setHeader(CORRELATION_ID, correlationId);

        long startTime = System.currentTimeMillis();

        try {
            System.out.println(
                    "Request started: " +
                    request.getMethod() + " " +
                    request.getRequestURI() +
                    " | Correlation ID: " + correlationId
            );

            filterChain.doFilter(request, response);

        } finally {

            long duration = System.currentTimeMillis() - startTime;

            System.out.println(
                    "Request completed: " +
                    request.getMethod() + " " +
                    request.getRequestURI() +
                    " | Duration: " + duration + " ms" +
                    " | Correlation ID: " + correlationId
            );

            MDC.remove("correlationId");
        }
    }
}