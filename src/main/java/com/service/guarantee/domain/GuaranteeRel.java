package com.service.guarantee.domain;

import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "GuaranteeRel")
class GuaranteeRel extends CorpAggRel {
}
