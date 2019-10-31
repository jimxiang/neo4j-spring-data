package com.service.guarantee.service;

import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestExpandNodePath;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ExpandNodePathService {
    private final static Logger Log = LoggerFactory.getLogger(GetTargetRelationService.class);

    private final CorpRepository repository;

    @Autowired
    public ExpandNodePathService(CorpRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResultEntity expandNodePath(RequestExpandNodePath requestData) {
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
        return result;
    }
}
