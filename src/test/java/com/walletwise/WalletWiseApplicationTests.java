package com.walletwise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalletWiseApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

    @Test
    void mainMethodRunsSuccessfully() {
        WalletWiseApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }
}