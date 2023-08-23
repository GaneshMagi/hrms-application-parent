package com.rbts.hrms.candidateonboarding.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FeignTenantInterceptor implements RequestInterceptor {

    private static final ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }

    public static String getTenantId() {
        return tenantIdThreadLocal.get();
    }

    public static void clearTenantId() {
        tenantIdThreadLocal.remove();
    }
    @Override
    public void apply(RequestTemplate template) {
        // Set the X-TenantID header with your desired value
        String tenantId = getTenantId();
        template.header("X-TenantID", tenantId);
    }
}
