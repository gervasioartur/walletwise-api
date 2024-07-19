package com.walletwise.application.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private Object body;
}
