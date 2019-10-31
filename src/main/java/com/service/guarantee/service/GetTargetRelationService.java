package com.service.guarantee.service;

import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestGetTargetRelation;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class GetTargetRelationService {
    private final static Logger Log = LoggerFactory.getLogger(GetTargetRelationService.class);
    private final CorpRepository corpRepository;

    @Autowired
    public GetTargetRelationService(CorpRepository corpRepository) {
        this.corpRepository = corpRepository;
    }

    @Transactional(readOnly = true)
    public ResultEntity getTargetRelation(RequestGetTargetRelation requestData) {
        ResultEntity result = new ResultEntity();
        Log.info(requestData.toString());

        String eid = requestData.getEid();
        int start = requestData.getStart();
        int limit = requestData.getLimit();
        try {
            Collection<Corp> corpPage = corpRepository.getTargetRelation(eid, start, limit);
            int total = corpRepository.getTargetRelationTotal(eid);
            result.setStatus(200);
            result.setData(ResultFormat.format(corpPage, total));
        } catch (Exception e) {
            result.setMessage("GetTargetRelation Error, " + e.getMessage());
            result.setStatus(500);
        }
        return result;
    }
}
