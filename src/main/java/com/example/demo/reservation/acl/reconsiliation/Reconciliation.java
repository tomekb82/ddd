package com.example.demo.reservation.acl.reconsiliation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Reconciliation<R> {
    public void compare(R oldModelResult, R newModelResult) {
        if (oldModelResult.equals(newModelResult)){
           log.info("modules equal");
        } else {
            log.info("modules different");
        }
    }
}
