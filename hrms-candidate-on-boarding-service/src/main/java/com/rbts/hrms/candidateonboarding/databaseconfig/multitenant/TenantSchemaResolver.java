
package com.rbts.hrms.candidateonboarding.databaseconfig.multitenant;


import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver{

    private String defaultTenant ="public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {

            HttpServletRequest request = attributes.getRequest();
            String tenantId = request.getHeader("X-TenantID");
            System.out.println("&&&&&&&&&&&&&&&"+tenantId);

            // You can perform any necessary processing or validation on the tenantId value

            return tenantId;
        }
        return defaultTenant;
    }




//    @Override
//    public String resolveCurrentTenantIdentifier() {
//
//        String t =  TenantContext.getCurrentTenant();
//        if(t!=null){
//            System.out.println("@@@@@@@@@@@@@@");
//            return t;
//        } else {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!");
//            return defaultTenant;
//        }
//    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
