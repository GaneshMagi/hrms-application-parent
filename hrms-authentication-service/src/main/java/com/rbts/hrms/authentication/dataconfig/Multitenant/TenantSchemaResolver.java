
package com.rbts.hrms.authentication.dataconfig.Multitenant;




import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver{

    private String defaultTenant ="public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {

            HttpServletRequest request = attributes.getRequest();
            String tenantId = request.getHeader("X-TenantID");



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
