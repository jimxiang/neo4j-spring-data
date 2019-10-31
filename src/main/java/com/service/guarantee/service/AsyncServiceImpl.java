package com.service.guarantee.service;

import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import com.service.guarantee.domain.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger Log = LoggerFactory.getLogger(AsyncServiceImpl.class);
    private final CorpRepository repository;

    @Autowired
    public AsyncServiceImpl(CorpRepository repository) {
        this.repository = repository;
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> getTargetRelation(RequestGetTargetRelation requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String eid = requestData.getEid();
        int start = requestData.getStart();
        int limit = requestData.getLimit();
        try {
            Collection<Corp> corpPage = repository.getTargetRelation(eid, start, limit);
            int total = repository.getTargetRelationTotal(eid);
            result.setStatus(200);
            result.setData(ResultFormat.format(corpPage, total));
        } catch (Exception e) {
            result.setMessage("GetTargetRelation Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> expandNodePath(RequestExpandNodePath requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String nodeId = requestData.getNodeId();
        String mode = requestData.getMode();
        int start = requestData.getStart();
        int limit = requestData.getLimit();
        try {
            if (mode.equals("1")) {
                Collection<Corp> corps = repository.expandGuarantee(nodeId, start, limit);
                int total = repository.getExpandGuaranteeTotal(nodeId);
                result.setStatus(200);
                result.setData(ResultFormat.format(corps, total));
            }
            if (mode.equals("2")) {
                Collection<Corp> corps = repository.expandCorpAgg(nodeId, start, limit);
                int total = repository.getExpandCorpAggTotal(nodeId);
                result.setStatus(200);
                result.setData(ResultFormat.format(corps, total));
            }
        } catch (Exception e) {
            result.setMessage("ExpandNodePath Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> getCorpRelatedDetail(RequestGetCorpRelatedDetail requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String src = requestData.getSrc();
        String dst = requestData.getDst();
        String mode = requestData.getMode();
        int start = requestData.getStart();
        int limit = requestData.getLimit();
        try {
            if (mode.equals("1")) {
                Collection<Corp> corps = repository.getCorpsDetail(src, dst, start, limit);
                int total = repository.getCorpsDetailTotal(src, dst);
                result.setStatus(200);
                result.setData(ResultFormat.format(corps, total));
            }
            if (mode.equals("2")) {
                Collection<Corp> corps = repository.getGuaranteeCorpDetail(src, dst, start, limit);
                int total = repository.getGuaranteeCorpDetailTotal(src, dst);
                result.setStatus(200);
                result.setData(ResultFormat.format(corps, total));
            }
            if (mode.equals("3")) {
                Collection<Corp> corps = repository.getCorpGuaranteeDetail(src, dst, start, limit);
                int total = repository.getCorpGuaranteeDetailTotal(src, dst);
                result.setStatus(200);
                result.setData(ResultFormat.format(corps, total));
            }
        } catch (Exception e) {
            result.setMessage("ExpandNodePath Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> getRelationInCorp(RequestGetRelationInCorp requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String corpId = requestData.getCorpId();
        int start = requestData.getStart();
        int limit = requestData.getLimit();
        try {
            Collection<Corp> corps = repository.getRelationInCorp(corpId, start, limit);
            int total = repository.getRelationInCorpTotal(corpId);
            result.setStatus(200);
            result.setData(ResultFormat.format(corps, total));
        } catch (Exception e) {
            result.setMessage("ExpandNodePath Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> hasNextBatchQuery(List<String> nodeIds) {
        ResultEntity result = new ResultEntity();
        Log.info("HasNextBatchQuery Request Body " + nodeIds.toString());
        try {
            Collection<Map<String, Object>> hasNext = repository.hasNextBatchQuery(nodeIds);
            result.setStatus(200);
            result.setData(ResultFormat.formatHasNext(hasNext));
        } catch (Exception e) {
            result.setMessage("ExpandNodePath Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }

    @Override
    @Async("asyncServiceExecutor")
    @Transactional(readOnly = true)
    public Future<ResultEntity> pathSearch(RequestPathSearch requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String src = requestData.getSrc();
        String dst = requestData.getDst();
        try {
            Collection<Corp> corps = repository.pathSearch(src, dst);
            result.setStatus(200);
            result.setData(ResultFormat.format(corps));
        } catch (Exception e) {
            result.setMessage("ExpandNodePath Error, " + e.getMessage());
            result.setStatus(500);
        }
        return AsyncResult.forValue(result);
    }
}
