package com.service.guarantee.domain;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@RelationshipEntity(type = "CorpAggRel")
public class CorpAggRel {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String amount;
    private String relType;
    private String times;
    private List<String> attrs;
    private String srcName;
    private String dstName;

    @StartNode
    private Corp startNode;

    @EndNode
    private Corp endNode;

    public Long getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getRelType() {
        return relType;
    }

    public String getTimes() {
        return times;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public Corp getStartNode() {
        return startNode;
    }

    public Corp getEndNode() {
        return endNode;
    }

    public String getSrcName() {
        return srcName;
    }

    public String getDstName() {
        return dstName;
    }

    @Override
    public String toString() {
        return "CorpAggRel{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", relType='" + relType + '\'' +
                ", times='" + times + '\'' +
                ", attrs=" + attrs +
                ", srcName='" + srcName + '\'' +
                ", dstName='" + dstName + '\'' +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                '}';
    }
}
