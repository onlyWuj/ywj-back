/*
 *
 * Copyright (c) 2015 All Rights Reserved
 */
package com.zds.scf;

import com.cp.boot.core.Apps;
import com.cp.boot.core.ZDSBootApplication;
import org.springframework.boot.SpringApplication;

/**
 *
 */
@ZDSBootApplication(sysName = "ywj", httpPort = 8088)
public class Main {

    public static void main(String[] args) {
        Apps.setProfileIfNotExists("sdev");
        new SpringApplication(Main.class).run(args);
    }
}