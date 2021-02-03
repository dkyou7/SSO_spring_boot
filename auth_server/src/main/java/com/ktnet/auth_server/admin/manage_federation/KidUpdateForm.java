package com.ktnet.auth_server.admin.manage_federation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KidUpdateForm {
    private String kid;
    private String gid;
    private String uid;

    public static KidUpdateForm toDto(Federation byUid) {
        return KidUpdateForm.builder().kid(byUid.getKid())
                .gid(byUid.getGid())
                .uid(byUid.getUid())
                .build();
    }
}
