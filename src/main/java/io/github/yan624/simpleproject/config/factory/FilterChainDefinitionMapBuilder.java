package io.github.yan624.simpleproject.config.factory;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterChainDefinitionMapBuilder {

    public Map<String, String> buildFilterChainDefinitionMap() {
        Map<String, String> permissionMap = new LinkedHashMap<>();
        permissionMap.put("/login.html", "anon");
        permissionMap.put("/register.html", "anon");
        permissionMap.put("/login", "anon");
        permissionMap.put("/register", "anon");
        permissionMap.put("/css/**", "anon");
        permissionMap.put("/js/**", "anon");
        permissionMap.put("/images/**", "anon");
        permissionMap.put("/img/**", "anon");
        permissionMap.put("/**", "user");
        return permissionMap;
    }
}
