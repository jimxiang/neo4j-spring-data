package com.service.guarantee.callback;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.RequestExpandNodePath;
import com.service.guarantee.service.ExpandNodePathService;

import java.util.concurrent.Callable;

public class ExpandNodePathCallback implements Callable<ResultEntity> {
    private ExpandNodePathService service;
    private RequestExpandNodePath requestData;

    public ExpandNodePathCallback(ExpandNodePathService service, RequestExpandNodePath requestData) {
        this.service = service;
        this.requestData = requestData;
    }

    @Override
    public ResultEntity call() throws Exception {
        return service.expandNodePath(requestData);
    }
}
