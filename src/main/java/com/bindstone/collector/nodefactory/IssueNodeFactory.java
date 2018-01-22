package com.bindstone.collector.nodefactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.factories.clause.MERGE;
import iot.jcypher.query.values.JcNode;

public class IssueNodeFactory {
    // tag::build[]
    public static IClause[] create(Issue issue) {
        JcNode nIssue = new JcNode("iss_1");
        JcNode nStatus = new JcNode("stat_1");
        JcNode nType = new JcNode("type_1");

        Node nodeIssue = CREATE.node(nIssue)
                .label("Issue")
                .property("id").value(issue.getKey())
                .property("summary").value(issue.getSummary())
                .property("description").value(issue.getDescription());

        Node nodeStatus = MERGE.node(nStatus)
                .label(issue.getStatus().getName())
                .property("id").value(issue.getStatus().getName());

        Node nodeType = MERGE.node(nType)
                .label(issue.getIssueType().getName())
                .property("id").value(issue.getIssueType().getName());

        Node relStatus = CREATE.node(nIssue)
                .relation().out().type("STATUS")
                .node(nStatus);

        Node relType = CREATE.node(nIssue)
                .relation().out().type("TYPE")
                .node(nType);

        return new IClause[]{nodeIssue, nodeStatus, nodeType, relStatus, relType};
    }
    // end::build[]
}
