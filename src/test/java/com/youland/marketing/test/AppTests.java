package com.youland.marketing.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

public class AppTests {

    boolean enable() { return true; }

    @Test
    @EnabledIf("enable")
    void test() throws Exception {

    }
}
