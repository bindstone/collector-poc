package com.bindstone.collector.service.impl;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.bindstone.collector.service.JiraConnectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class JiraConnectionServiceImpl implements JiraConnectionService {

    @Value("${collector.jira.url}")
    String url;

    @Value("${collector.jira.user}")
    String user;

    @Value("${collector.jira.password}")
    String password;

    @Override
    public JiraRestClient getJiraRestClient() {

        URI uri;
        try {
            uri = new URI(url);
        } catch (Exception e) {
            throw new RuntimeException("URI exception for [" + url + "]");
        }

        AuthenticationHandler authHandler;

        if (user == null || user.isEmpty()) {
            authHandler = new AnonymousAuthenticationHandler();

        } else {
            authHandler = new BasicHttpAuthenticationHandler(user, password);
        }

        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        JiraRestClient jiraRestClient = factory.create(uri, authHandler);

        return jiraRestClient;
    }

    @Override
    public SearchRestClient getSearchClient() {
        return getJiraRestClient().getSearchClient();
    }
}
