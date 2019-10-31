package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestGetTargetRelation;
import com.service.guarantee.service.GetTargetRelationService;

import java.util.concurrent.Callable;

public class GetTargetRelationCallback implements Callable<ResultEntity> {
    private GetTargetRelationService service;
    private RequestGetTargetRelation requestData;

    public GetTargetRelationCallback(GetTargetRelationService service, RequestGetTargetRelation requestData) {
        this.service = service;
        this.requestData = requestData;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.getTargetRelation(requestData);
    }
}
