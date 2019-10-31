package com.service.guarantee.controller;

import com.alibaba.fastjson.JSONObject;
import com.service.guarantee.configuration.ThreadConfiguration;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.service.*;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/async-api")
public class AsyncController {
    private final ThreadConfiguration threadConfiguration;

    @Autowired
    public AsyncController(ThreadConfiguration threadConfiguration, AsyncService asyncService) {
        this.threadConfiguration = threadConfiguration;
        this.asyncService = asyncService;
    }

    private final AsyncService asyncService;

    @RequestMapping(value = "/getTargetRelation", method = RequestMethod.POST)
    public ResultEntity asyncGetTargetRelation(@RequestBody @Valid RequestGetTargetRelation data) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        responseResult = asyncService.getTargetRelation(data).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }

    @RequestMapping(value = "/expandNodePath", method = RequestMethod.POST)
    public ResultEntity asyncExpandNodePath(@RequestBody @Valid RequestExpandNodePath data) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        responseResult = asyncService.expandNodePath(data).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }

    @RequestMapping(value = "/getRelationInCorp", method = RequestMethod.POST)
    public ResultEntity asyncGetRelationInCorp(@RequestBody @Valid RequestGetRelationInCorp data) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        responseResult = asyncService.getRelationInCorp(data).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }

    @RequestMapping(value = "/getCorpRelatedDetail", method = RequestMethod.POST)
    public ResultEntity asyncGetCorpRelatedDetail(@RequestBody @Valid RequestGetCorpRelatedDetail data) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        responseResult = asyncService.getCorpRelatedDetail(data).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }

    @RequestMapping(value = "/hasNextBatchQuery", method = RequestMethod.POST)
    public ResultEntity asyncHasNextBatchQuery(@RequestBody JSONObject jsonObject) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        @SuppressWarnings("unchecked")
        List<String> nodeIds = (List<String>) jsonObject.get("nodeIds");
        responseResult = asyncService.hasNextBatchQuery(nodeIds).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }

    @RequestMapping(value = "/pathSearch", method = RequestMethod.POST)
    public ResultEntity asyncPathSearch(@RequestBody @Valid RequestPathSearch data) throws InterruptedException, ExecutionException, TimeoutException {
        ResultEntity responseResult = new ResultEntity();
        responseResult = asyncService.pathSearch(data).get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
        return responseResult;
    }
}
