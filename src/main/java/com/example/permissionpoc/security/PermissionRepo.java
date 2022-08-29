package com.example.permissionpoc.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PermissionRepo {

    public Map<String, PermissionDoc> permissionDocMap = new HashMap<>();

     {
        permissionDocMap.put( "a", PermissionDoc.builder().organizations( "Family Dollar" ).retailers( "retailer 1" ).retailers( "retailer 2" ).build() );
        permissionDocMap.put( "b", PermissionDoc.builder().organizations( "Walmart" ).retailers( "retailer 2" ).retailers( "retailer 3" ).build() );
    }

    public PermissionDoc getUserPermission(String user) {
         return permissionDocMap.get( user );
    }

}
