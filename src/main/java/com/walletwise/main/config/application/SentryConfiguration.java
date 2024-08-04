package com.walletwise.main.config.application;

import io.sentry.spring.jakarta.EnableSentry;
import org.springframework.context.annotation.Configuration;

@EnableSentry(dsn = "https://00f67566c5d3c701f164f333b4d274cd@o4507622884900864.ingest.de.sentry.io/4507711164317776")
@Configuration
class SentryConfiguration {
}