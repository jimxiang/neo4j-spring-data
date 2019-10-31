package com.service.guarantee.service;

import com.service.guarantee.domain.Corp;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestGetCorpRelatedDetail;
import com.service.guarantee.repository.CorpRepository;
import com.service.guarantee.util.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class GetCorpRelatedDetailService {
    private final static Logger Log = LoggerFactory.getLogger(GetTargetRelationService.class);
    private final CorpRepository repository;

    @Autowired
    public GetCorpRelatedDetailService(CorpRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResultEntity getCorpRelatedDetail(RequestGetCorpRelatedDetail requestData) {
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
        return result;
    }
}
