package com.service.guarantee.repository;

import com.service.guarantee.domain.Corp;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CorpRepository extends Neo4jRepository<Corp, Long> {
    @Query("MATCH (source:Guarantee)-[rel:CorpAggRel|:GuaranteeRel*1]-(target) " +
            "WHERE source.eid = {eid} " +
            "WITH source, target, rel " +
            "UNWIND rel AS r " +
            "MATCH (source)-[r]-(target) " +
            "WHERE NOT (source)-[r]-(target)-[:BelongRel]->(:Corp) " +
            "WITH source, target, r " +
            "ORDER BY r.amount DESC " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN source + COLLECT(DISTINCT(target)), COLLECT(r)")
    Collection<Corp> getTargetRelation(
            @Param("eid") String eid,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (source:Guarantee)-[rel:CorpAggRel|:GuaranteeRel*1]-(target) " +
            "WHERE source.eid = {eid}" +
            "WITH source, target, rel " +
            "UNWIND rel AS r " +
            "MATCH (source)-[r]-(target) " +
            "WHERE NOT (source)-[r]-(target)-[:BelongRel]->(:Corp) " +
            "RETURN count(*)")
    int getTargetRelationTotal(@Param("eid") String eid);

    @Query("MATCH (source:Guarantee)-[rel:GuaranteeRel]-(target:Guarantee) " +
            "WHERE source.nodeId = {nodeId} " +
            "WITH source, target, rel " +
            "ORDER BY rel.amount DESC " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN source + COLLECT(target), COLLECT(rel)")
    Collection<Corp> expandGuarantee(
            @Param("nodeId") String nodeId,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (source:Guarantee)-[rel:GuaranteeRel]-(target:Guarantee) " +
            "WHERE source.nodeId = {nodeId} " +
            "RETURN count(*)")
    int getExpandGuaranteeTotal(@Param("nodeId") String nodeId);

    @Query("MATCH (source)-[rel:CorpAggRel|:CorpRel|:GuaranteeRel]-(target) " +
            "WHERE source.nodeId = {nodeId} " +
            "WITH source, target, rel " +
            "MATCH (source)-[rel]-(target) " +
            "WHERE NOT (source)-[rel]-(target)-[:BelongRel]->(:Corp) " +
            "WITH source, target, rel " +
            "ORDER BY rel.amount DESC " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN source + COLLECT(target), COLLECT(rel)")
    Collection<Corp> expandCorpAgg(
            @Param("nodeId") String nodeId,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (source)-[rel:CorpAggRel|:CorpRel|:GuaranteeRel]-(target) " +
            "WHERE source.nodeId = {nodeId} " +
            "WITH source, target, rel " +
            "MATCH (source)-[rel]-(target) " +
            "WHERE NOT (source)-[rel]-(target)-[:BelongRel]->(:Corp) " +
            "RETURN count(*)")
    int getExpandCorpAggTotal(@Param("nodeId") String nodeId);

    @Query("MATCH (c:Corp) " +
            "WHERE c.corpId = {corpId} " +
            "WITH c.corpList AS NodeList " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]-(n) " +
            "WHERE m.nodeId IN NodeList " +
            "WITH m, n, r " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN m + collect(n), collect(r)")
    Collection<Corp> getRelationInCorp(
            @Param("corpId") String corpId,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (c:Corp) " +
            "WHERE c.corpId = {corpId} " +
            "WITH c.corpList AS nodeList " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]-(n) " +
            "WHERE m.nodeId IN nodeList " +
            "RETURN count(*)")
    int getRelationInCorpTotal(@Param("corpId") String corpId);

    @Query("MATCH (c1:Corp)-[r1:CorpRel]->(c2:Corp) " +
            "WHERE c1.corpId = {src} AND c2.corpId = {dst} " +
            "WITH c1.corpList AS SrcList, c2.corpList AS DstList " +
            "MATCH (m:Guarantee)-[r2:GuaranteeRel]->(n:Guarantee) " +
            "WHERE m.nodeId in SrcList AND n.nodeId in DstList " +
            "WITH m, n, r2 " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN COLLECT(m) + COLLECT(n), collect(r2)")
    Collection<Corp> getCorpsDetail(
            @Param("src") String src,
            @Param("dst") String dst,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (c1:Corp)-[r1:CorpRel]->(c2:Corp) " +
            "WHERE c1.corpId = {src} AND c2.corpId = {dst} " +
            "WITH c1.corpList AS SrcList, c2.corpList AS DstList " +
            "MATCH (m:Guarantee)-[r2:GuaranteeRel]->(n:Guarantee) " +
            "WHERE m.nodeId in SrcList AND n.nodeId in DstList " +
            "RETURN COUNT(*)")
    int getCorpsDetailTotal(
            @Param("src") String src,
            @Param("dst") String dst);

    @Query("MATCH (source:Guarantee)-[rel:CorpAggRel]->(target:Corp) " +
            "WHERE source.nodeId = {src} AND target.nodeId = {dst} " +
            "WITH source.nodeId AS Src, rel.nodeIds AS DstList " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]->(n:Guarantee) " +
            "WHERE m.nodeId = Src AND n.nodeId in DstList " +
            "WITH m, n, r " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN COLLECT(m) + COLLECT(n), COLLECT(r)")
    Collection<Corp> getGuaranteeCorpDetail(
            @Param("src") String src,
            @Param("dst") String dst,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (source:Guarantee)-[rel:CorpAggRel]->(target:Corp) " +
            "WHERE source.nodeId = {src} AND target.nodeId = {dst} " +
            "WITH source.nodeId AS Src, rel.nodeIds AS DstList " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]->(n:Guarantee)  " +
            "WHERE m.nodeId = Src AND n.nodeId in DstList " +
            "RETURN COUNT(*)")
    int getGuaranteeCorpDetailTotal(
            @Param("src") String src,
            @Param("dst") String dst);

    @Query("MATCH (source:Corp)-[rel:CorpAggRel]->(target:Guarantee) " +
            "WHERE source.nodeId = {src} AND target.nodeId = {dst} " +
            "WITH rel.nodeIds AS SrcList, target.nodeId AS Dst " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]->(n:Guarantee) " +
            "WHERE m.nodeId in SrcList AND n.nodeId = Dst " +
            "WITH m, n, r " +
            "SKIP {start} " +
            "LIMIT {limit} " +
            "RETURN COLLECT(m) + COLLECT(n), COLLECT(r)")
    Collection<Corp> getCorpGuaranteeDetail(
            @Param("src") String src,
            @Param("dst") String dst,
            @Param("start") int start,
            @Param("limit") int limit);

    @Query("MATCH (source:Corp)-[rel:CorpAggRel]->(target:Guarantee) " +
            "WHERE source.nodeId = {src} AND target.nodeId = {dst} " +
            "WITH rel.nodeIds AS SrcList, target.nodeId AS Dst " +
            "MATCH (m:Guarantee)-[r:GuaranteeRel]->(n:Guarantee) " +
            "WHERE m.nodeId in SrcList AND n.nodeId = Dst " +
            "RETURN COUNT(*)")
    int getCorpGuaranteeDetailTotal(
            @Param("src") String src,
            @Param("dst") String dst);

    @Query("MATCH (n)-[:CorpAggRel|:CorpRel|:GuaranteeRel]-() " +
            "WHERE n.nodeId in {nodeIds} " +
            "RETURN COUNT(*) AS total, n.nodeId AS nodeId")
    Collection<Map<String, Object>> hasNextBatchQuery(@Param("nodeIds") List<String> nodeIds);

    @Query("MATCH (source:Guarantee {eid: {src}}) " +
            "MATCH (target:Guarantee {eid: {dst}}) " +
            "CALL apoc.algo.allSimplePaths(source, target, 'GuaranteeRel>', 6) YIELD path " +
            "RETURN path ")
    Collection<Corp> pathSearch(@Param("src") String src,
                                @Param("dst") String dst);
}
