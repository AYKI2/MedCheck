package com.example.medcheckb8.db.api;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class IndexAPI {

    private static final Logger logger = Logger.getLogger(IndexAPI.class);

    static {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
    }
    @GetMapping
    public String indexPage() {
        logger.debug("Loading index page ...");
        logger.debug("debug level message");
        logger.info("info level message");
        logger.warn("warn level message");
        logger.error("ERROR level message");
        logger.fatal("Fatal level message");
        logger.trace("Trace level message");

        return "index";
    }
}
