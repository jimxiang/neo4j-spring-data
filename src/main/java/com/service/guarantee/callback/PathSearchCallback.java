package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestPathSearch;
import com.service.guarantee.service.PathSearchService;

import java.util.concurrent.Callable;

public class PathSearchCallback implements Callable<ResultEntity> {
    private PathSearchService service;
    private RequestPathSearch requestData;

    public PathSearchCallback(PathSearchService service, RequestPathSearch requestData) {
        this.service = service;
        this.requestData = requestData;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.pathSearch(requestData);
    }
}
