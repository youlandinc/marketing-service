package com.youland.marketing.service;

import com.youland.marketing.dao.entity.MarketingUser;

import java.util.List;

/**
 * @author chenning
 */
public interface IMarketingUserService {
    MarketingUser addMarketingUser(MarketingUser marketingUser);

    List<MarketingUser> getMarketingUserList();
}
