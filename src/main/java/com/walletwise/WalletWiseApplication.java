package com.walletwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class WalletWiseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletWiseApplication.class, args);
    }
}
