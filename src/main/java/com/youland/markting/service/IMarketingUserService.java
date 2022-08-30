package com.youland.markting.service;

import com.youland.markting.dao.entity.MarketingUser;

import java.util.List;

/**
 * @author chenning
 */
public interface IMarketingUserService {
    MarketingUser addMarketingUser(MarketingUser marketingUser);

    List<MarketingUser> getMarketingUserList();
}
