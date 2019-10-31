package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestGetCorpRelatedDetail;
import com.service.guarantee.service.GetCorpRelatedDetailService;

import java.util.concurrent.Callable;

public class GetCorpRelatedDetailCallback implements Callable<ResultEntity> {
    private GetCorpRelatedDetailService service;
    private RequestGetCorpRelatedDetail requestData;

    public GetCorpRelatedDetailCallback(GetCorpRelatedDetailService service, RequestGetCorpRelatedDetail requestData) {
        this.service = service;
        this.requestData = requestData;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.getCorpRelatedDetail(requestData);
    }
}
