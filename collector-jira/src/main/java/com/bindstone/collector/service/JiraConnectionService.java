package com.bindstone.collector.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;

public interface JiraConnectionService {

    JiraRestClient getJiraRestClient();

    SearchRestClient getSearchClient();
}
