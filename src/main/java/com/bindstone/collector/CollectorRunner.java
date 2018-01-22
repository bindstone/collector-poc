package com.bindstone.collector;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.bindstone.collector.nodefactory.IssueNodeFactory;
import com.bindstone.collector.service.JiraConnectionService;
import com.bindstone.collector.service.NeoService;
import com.bindstone.collector.tools.Report;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import iot.jcypher.query.api.IClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;


@Component
public class CollectorRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JiraConnectionService jiraService;

    @Autowired
    NeoService neoService;

    @Override
    public void run(String... args) {
        String jiraQuery = "project = CLOUD";
        SearchResult ret;
        try {
            ret = jiraService.getSearchClient().searchJql(jiraQuery).get();
            neoService.getAccess().clearDatabase();
            for (Issue issue : ret.getIssues()) {
                JcQuery neoQuery = new JcQuery();
                IClause[] expression = IssueNodeFactory.create(issue);
                neoQuery.setClauses(expression);
                Report.print(neoQuery);
                JcQueryResult result = neoService.getAccess().execute(neoQuery);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}