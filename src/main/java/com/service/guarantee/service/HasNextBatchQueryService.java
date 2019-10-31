package com.service.guarantee.service;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class HasNextBatchQueryService {
    private final static Logger Log = LoggerFactory.getLogger(GetTargetRelationService.class);
    private final CorpRepository repository;

    @Autowired
    public HasNextBatchQueryService(CorpRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResultEntity hasNextBatchQuery(List<String> nodeIds) {
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
        return result;
    }
}
