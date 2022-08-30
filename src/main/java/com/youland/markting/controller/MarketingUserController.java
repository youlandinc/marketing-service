package com.youland.markting.controller;

import com.youland.markting.dao.entity.MarketingUser;
import com.youland.markting.service.IMarketingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author chenning
 */
@RestController
@RequestMapping("/contact/user")
@RequiredArgsConstructor
public class MarketingUserController {
    private final IMarketingUserService marketingUserService;

    @PostMapping("/info")
    public MarketingUser addInfo(@RequestBody MarketingUser marketingUser) {
        return marketingUserService.addMarketingUser(marketingUser);
    }

    @GetMapping(value = "/info")
    public List<MarketingUser> getMarketingUserList() {
        return marketingUserService.getMarketingUserList();
    }

}
