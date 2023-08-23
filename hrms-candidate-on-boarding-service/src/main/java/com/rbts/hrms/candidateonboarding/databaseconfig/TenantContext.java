package com.rbts.hrms.candidateonboarding.databaseconfig;

public class TenantContext {
    private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(String tenant) {
        System.out.println("(((((((((((((((((((((");

        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.set(null);
    }
}
