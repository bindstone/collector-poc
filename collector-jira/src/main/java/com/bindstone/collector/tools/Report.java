package com.bindstone.collector.tools;

import iot.jcypher.query.JcQuery;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Report {
    private static final Logger log = LoggerFactory.getLogger("CYPER:");

    public static void print(JcQuery neoQuery) {
        log.info("---------------------------------------------------");
        log.info(Util.toCypher(neoQuery, Format.PRETTY_3));
        log.info("---------------------------------------------------");
    }
}
