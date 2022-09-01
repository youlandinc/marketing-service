package com.youland.marketing.controller;

import com.youland.marketing.dao.entity.MarketingUser;
import com.youland.marketing.service.IMarketingEmailService;
import com.youland.marketing.service.IMarketingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author chenning
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MarketingUserController {
    private final IMarketingUserService marketingUserService;
    private final IMarketingEmailService marketingEmailService;

    @PostMapping("/info")
    public MarketingUser addInfo(@RequestBody MarketingUser marketingUser) {
        return marketingUserService.addMarketingUser(marketingUser);
    }

    @GetMapping(value = "/info")
    public List<MarketingUser> getMarketingUserList() {
        return marketingUserService.getMarketingUserList();
    }

    @PutMapping("/unsubscribe/{email}")
    public void unsubscribe(@PathVariable String email) {
        marketingEmailService.unSubscribe(email);
    }
}
