package com.ai.projects.cooked.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
	

	private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket() {

        Bandwidth limit = Bandwidth.builder()
                .capacity(3)
                .refillGreedy(3, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
    	
    	if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
    	    filterChain.doFilter(request, response);
    	    return;
    	}
    	
    	if (!request.getRequestURI().equals("/chat/prompt")) {
            filterChain.doFilter(request, response);
            return;
        }


        String ip = request.getRemoteAddr();

        Bucket bucket = buckets.computeIfAbsent(
        		ip,
                k -> {
                    return createBucket();
                }
        );
        
        boolean consumed = bucket.tryConsume(1);

        if (bucket.tryConsume(1)) {

            filterChain.doFilter(request, response);

        } else {

            response.setStatus(429);
            
            response.setHeader(
        	    "Access-Control-Allow-Origin",
        	    "http://localhost:5173"
        	);

        	response.setHeader(
        	    "Access-Control-Allow-Methods",
        	    "*"
        	);

        	response.setHeader(
        	    "Access-Control-Allow-Headers",
        	    "*"
        	);
            
            response.setContentType("application/json");

            response.getWriter().write("""
                {
                    "message":"Too many roast requests. Please wait a minute and try again."
                }
                """);
        }
    }
}
