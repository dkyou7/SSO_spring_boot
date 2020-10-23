package com.ktnet.auth_server.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

}
