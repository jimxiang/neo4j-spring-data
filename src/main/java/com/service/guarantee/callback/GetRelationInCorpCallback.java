package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestGetRelationInCorp;
import com.service.guarantee.service.GetRelationInCorpService;

import java.util.concurrent.Callable;

public class GetRelationInCorpCallback implements Callable<ResultEntity> {
    private GetRelationInCorpService service;
    private RequestGetRelationInCorp requestData;

    public GetRelationInCorpCallback(GetRelationInCorpService service, RequestGetRelationInCorp requestData) {
        this.service = service;
        this.requestData = requestData;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.getRelationInCorp(requestData);
    }
}
