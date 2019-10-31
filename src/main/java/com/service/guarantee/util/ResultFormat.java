package com.service.guarantee.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.service.guarantee.domain.Attribute;
import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.CorpAggRel;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultFormat {
    private static Map<String, Object> parse(Iterable<Corp> iterator) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        for (Corp entity : iterator) {
            Map<String, Object> node = new HashMap<>();
            node.put("nodeId", entity.getNodeId());
            node.put("eid", entity.getEid());
            node.put("nodeName", entity.getNodeName());
            node.put("nodeType", entity.getNodeType());

            String corpCnt = entity.getCorpCnt();
            if (!StringUtils.isEmpty(corpCnt)) {
                node.put("corpCnt", Integer.parseInt(corpCnt));
            }
            node.put("corpId", entity.getCorpId());
            node.put("corpName", entity.getCorpName());
            nodes.add(node);
            if (entity.getGuaranteeRels() != null) {
                for (CorpAggRel relation : entity.getGuaranteeRels()) {
                    getAttributes(edges, relation);
                }
            }
            if (entity.getCorpAggRels() != null) {
                for (CorpAggRel relation : entity.getCorpAggRels()) {
                    getAttributes(edges, relation);
                }
            }
        }
        result.put("nodes", nodes);
        result.put("edges", edges);
        return result;
    }

    public static Map<String, Object> format(Iterable<Corp> iterator, int total) {
        Map<String, Object> result;
        result = parse(iterator);
        result.put("total", total);
        return result;
    }

    public static Map<String, Object> format(Iterable<Corp> iterator) {
        Map<String, Object> result;
        result = parse(iterator);
        return result;
    }

    public static List<Map<String, Object>> formatHasNext(Iterable<Map<String, Object>> iterator) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> iter : iterator) {
            Map<String, Object> entity = new HashMap<>();
            Long total = (Long) iter.get("total");
            String nodeId = (String) iter.get("nodeId");
            boolean hasNext = total > 1;
            entity.put("nodeId", nodeId);
            entity.put("hasNext", hasNext);
            result.add(entity);
        }
        return result;
    }

    private static void getAttributes(List<Map<String, Object>> edges, CorpAggRel relation) {
        Map<String, Object> rel = new HashMap<>();
        rel.put("srcId", relation.getStartNode().getNodeId());
        rel.put("dstId", relation.getEndNode().getNodeId());
        rel.put("srcName", relation.getSrcName());
        rel.put("dstName", relation.getDstName());

        String amount = relation.getAmount();
        if (!StringUtils.isEmpty(amount)) {
            rel.put("amount", Float.parseFloat(relation.getAmount()));
        }

        rel.put("relType", relation.getRelType());

        String times = relation.getTimes();
        if (!StringUtils.isEmpty(times)) {
            rel.put("times", Integer.parseInt(relation.getTimes()));
        }

        List<String> attrs = relation.getAttrs();
        if (!CollectionUtils.isEmpty(attrs)) {
            List<Attribute> attributes = new ArrayList<>();
            for (String attr : attrs) {
                JSONObject attrJsonObject = JSON.parseObject(attr);

                float cnvCnyGuaranteeAmount = Float.parseFloat(attrJsonObject.get("cnv_cny_guaranteeamount").toString());
                int guaranteeType = Integer.parseInt(attrJsonObject.get("guaranteetype").toString());
                int dataSrc = Integer.parseInt(attrJsonObject.get("data_src").toString());
                String sgnDt = attrJsonObject.get("sgn_dt").toString();

                Attribute attribute = new Attribute();
                attribute.setCnvCnyGuaranteeAmount(cnvCnyGuaranteeAmount);
                attribute.setGuaranteeType(guaranteeType);
                attribute.setDataSrc(dataSrc);
                attribute.setSgnDt(sgnDt);
                attributes.add(attribute);
            }
            rel.put("attrs", attributes);
        }
        edges.add(rel);
    }
}
