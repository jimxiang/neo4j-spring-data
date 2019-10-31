package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.service.HasNextBatchQueryService;

import java.util.List;
import java.util.concurrent.Callable;

public class HasNextBatchQueryCallback implements Callable<ResultEntity> {
    private HasNextBatchQueryService service;
    private List<String> nodeIds;

    public HasNextBatchQueryCallback(HasNextBatchQueryService service, List<String> nodeIds) {
        this.service = service;
        this.nodeIds = nodeIds;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.hasNextBatchQuery(nodeIds);
    }
}
