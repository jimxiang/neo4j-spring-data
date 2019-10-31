package com.service.guarantee.service;

import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestPathSearch;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PathSearchService {
    private final static Logger Log = LoggerFactory.getLogger(GetTargetRelationService.class);

    private final CorpRepository repository;

    @Autowired
    public PathSearchService(CorpRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResultEntity pathSearch(RequestPathSearch requestData) {
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
        return result;
    }
}
