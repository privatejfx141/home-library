package com.hl.generics;

import java.util.EnumMap;

public enum MovieRoles {
    D, P, S, C, CO, E, CD;

    private static EnumMap<MovieRoles, String> roleMap;

    private static void initalizeRoleMap() {
        roleMap = new EnumMap<MovieRoles, String>(MovieRoles.class);
        roleMap.put(D, "Director");
        roleMap.put(P, "Producer");
        roleMap.put(S, "Script writer");
        roleMap.put(C, "Cast");
        roleMap.put(CO, "Composer");
        roleMap.put(E, "Editor");
        roleMap.put(CD, "Costume designer");
    }

    public static String getRoleName(String role) {
        return getRoleName(MovieRoles.valueOf(role));
    }

    public static String getRoleName(MovieRoles role) {
        if (roleMap == null) {
            initalizeRoleMap();
        }
        return roleMap.get(role);
    }

}
