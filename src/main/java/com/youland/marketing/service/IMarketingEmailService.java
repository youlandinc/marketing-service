package com.youland.marketing.service;

/**
 * @author chenning
 */
public interface IMarketingEmailService {
    boolean sendEmail();

    boolean unSubscribe(String email);

    boolean findErrorEmail();

}
