package com.bindstone.collector.pom;

import iot.jcypher.database.IDBAccess;
import iot.jcypher.query.JcQuery;
import iot.jcypher.query.JcQueryResult;
import iot.jcypher.query.api.IClause;
import iot.jcypher.query.api.pattern.Node;
import iot.jcypher.query.factories.clause.CREATE;
import iot.jcypher.query.factories.clause.MERGE;
import iot.jcypher.query.values.JcNode;
import iot.jcypher.query.writer.Format;
import iot.jcypher.util.Util;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PomLoader {

    public void run(Model model, IDBAccess db) {

        String pomKey = UUID.randomUUID().toString();
        JcNode nPom = new JcNode("pom");

        Node node = CREATE.node(nPom)
            .label("Project")
            .property("id").value(pomKey)
            .property("artifact_id").value(model.getArtifactId())
            .property("group_id").value(model.getGroupId())
            .property("version").value(model.getVersion());

        List<IClause> list = new ArrayList<>();
        list.add(node);
        int i = 0;
        for (Dependency dependency : model.getDependencies()) {
            JcNode nDep = new JcNode("dep_" + i++);
            Node dep = MERGE.node(nDep)
                .label("Dependency")
                .property("group_id").value(dependency.getGroupId())
                .property("artifact_id").value(dependency.getArtifactId())
                .property("version").value(dependency.getVersion());

            Node relation = MERGE.node(nPom)
                    .relation().out().type("DEPENDENCY")
                    .node(nDep);

            list.add(dep);
            list.add(relation);
        }

        JcQuery neoQuery = new JcQuery();


        IClause[] objects = list.toArray(new IClause[0]);
        neoQuery.setClauses(objects);

        System.out.println("---------------------------------------------------");
        System.out.println(Util.toCypher(neoQuery, Format.PRETTY_3));
        System.out.println("---------------------------------------------------");

        JcQueryResult result = db.execute(neoQuery);

        System.out.println(result);

    }
}
