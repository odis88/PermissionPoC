package com.example.permissionpoc;

import com.example.permissionpoc.controller.SecuredController;
import com.example.permissionpoc.security.PermissionChecker;
import com.example.permissionpoc.security.PermissionDoc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class TestControllerPermissionSecured {

    @Test
    void testGetOrganizationInfo() {
        Assertions.assertTrue(
            PermissionUtil.checkPermission( Set.of( "ROLE_USER" ),
                PermissionDoc.builder().organizations( "Family Dollar" ).retailers( "retailer 1" ).retailers( "retailer 2" ).build(),
                SecuredController.class, Map.of( "organizationId", "Family Dollar" ), "getOrganizationInfo" )
        );


        Assertions.assertFalse(
            PermissionUtil.checkPermission( Set.of( "ROLE_USER" ),
                PermissionDoc.builder().organizations( "Family Dollar" ).retailers( "retailer 1" ).retailers( "retailer 2" ).build(),
                SecuredController.class, Map.of( "organizationId", "Walmart" ), "getOrganizationInfo" )
        );
    }
}
