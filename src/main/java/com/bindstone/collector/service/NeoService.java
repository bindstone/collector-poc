package com.bindstone.collector.service;

import iot.jcypher.database.IDBAccess;

public interface NeoService {

    IDBAccess getAccess();
}
