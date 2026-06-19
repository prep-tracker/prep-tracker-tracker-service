package com.prept.tracker.context;

import java.util.List;

public class UserContext {
    private static final ThreadLocal<Long> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> username = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> roles = new ThreadLocal<>();
    private static final ThreadLocal<String> plan = new ThreadLocal<>();

    public static void setUserId(Long id) {
        userId.set(id);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void setUsername(String name) {
        username.set(name);
    }

    public static String getUsername() {
        return username.get();
    }

    public static void setRoles(List<String> userRoles) {
        roles.set(userRoles);
    }

    public static List<String> getRoles() {
        return roles.get();
    }

    public static void setPlan(String userPlan) {
        plan.set(userPlan);
    }

    public static String getPlan() {
        return plan.get();
    }

    public static void clear() {
        userId.remove();
        username.remove();
        roles.remove();
        plan.remove();
    }
}
