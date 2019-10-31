package com.service.guarantee.domain;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@NodeEntity
public class Corp {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String eid;
    private String nodeName;
    private String nodeId;
    private String nodeType;
    private String corpId;
    private String corpCnt;
    private String corpName;
    @Relationship(type = "CorpAggRel", direction = Relationship.DIRECTION)
    private List<CorpAggRel> corpAggRels;
    @Relationship(type = "GuaranteeRel", direction = Relationship.DIRECTION)
    private List<GuaranteeRel> guaranteeRels;
    @Relationship(type = "CorpRel", direction = Relationship.DIRECTION)
    private List<CorpRel> corpRels;

    public Long getId() {
        return id;
    }

    public String getEid() {
        return eid;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getCorpCnt() {
        return corpCnt;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public List<CorpAggRel> getCorpAggRels() {
        return corpAggRels;
    }

    public List<GuaranteeRel> getGuaranteeRels() {
        return guaranteeRels;
    }

    public List<CorpRel> getCorpRels() {
        return corpRels;
    }

    @Override
    public String toString() {
        return "Corp{" +
                "id=" + id +
                ", corpCnt='" + corpCnt + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpName='" + corpName + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", nodeType='" + nodeType + '\'' +
                '}';
    }
}
