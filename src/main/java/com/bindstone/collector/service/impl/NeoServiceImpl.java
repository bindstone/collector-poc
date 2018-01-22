package com.bindstone.collector.service.impl;

import com.bindstone.collector.service.NeoService;
import iot.jcypher.database.DBAccessFactory;
import iot.jcypher.database.DBProperties;
import iot.jcypher.database.DBType;
import iot.jcypher.database.IDBAccess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NeoServiceImpl implements NeoService {

    @Value("${collector.neo4j.url}")
    String url;

    @Value("${collector.neo4j.user}")
    String user;

    @Value("${collector.neo4j.password}")
    String password;

    @Override
    public IDBAccess getAccess() {
        Properties props = new Properties();
        props.setProperty(DBProperties.SERVER_ROOT_URI, url);

        IDBAccess remote =
                DBAccessFactory.createDBAccess(DBType.REMOTE, props, user, password);
        return remote;
    }


}
