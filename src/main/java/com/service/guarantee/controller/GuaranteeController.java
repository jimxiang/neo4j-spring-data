package com.service.guarantee.controller;

import com.alibaba.fastjson.JSONObject;
import com.service.guarantee.callback.*;
import com.service.guarantee.callback.*;
import com.service.guarantee.configuration.ThreadConfiguration;
import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.service.*;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api")
public class GuaranteeController {
    private static final Logger logger = LoggerFactory.getLogger(GuaranteeController.class);
    private final GetTargetRelationService getTargetRelationService;
    private final ExpandNodePathService expandNodePathService;
    private final GetRelationInCorpService getRelationInCorpService;
    private final GetCorpRelatedDetailService getCorpRelatedDetailService;
    private final HasNextBatchQueryService hasNextBatchQueryService;
    private final PathSearchService pathSearchService;
    private final ThreadConfiguration threadConfiguration;

    @Autowired
    public GuaranteeController(GetTargetRelationService getTargetRelationService,
                               ExpandNodePathService expandNodePathService,
                               GetRelationInCorpService getRelationInCorpService,
                               GetCorpRelatedDetailService getCorpRelatedDetailService,
                               HasNextBatchQueryService hasNextBatchQueryService,
                               PathSearchService pathSearchService,
                               ThreadConfiguration threadConfiguration) {
        this.getTargetRelationService = getTargetRelationService;
        this.expandNodePathService = expandNodePathService;
        this.getRelationInCorpService = getRelationInCorpService;
        this.getCorpRelatedDetailService = getCorpRelatedDetailService;
        this.hasNextBatchQueryService = hasNextBatchQueryService;
        this.pathSearchService = pathSearchService;
        this.threadConfiguration = threadConfiguration;
    }

    @RequestMapping(value = "/getTargetRelation", method = RequestMethod.POST)
    public ResultEntity getTargetRelation(@RequestBody @Valid RequestGetTargetRelation data, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        GetTargetRelationCallback callback = new GetTargetRelationCallback(getTargetRelationService, data);
        return extracted(callback, requestURI);
    }

    @RequestMapping(value = "/expandNodePath", method = RequestMethod.POST)
    public ResultEntity expandNodePath(@RequestBody @Valid RequestExpandNodePath data, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        ExpandNodePathCallback callback = new ExpandNodePathCallback(expandNodePathService, data);
        return extracted(callback, requestURI);
    }

    @RequestMapping(value = "/getRelationInCorp", method = RequestMethod.POST)
    public ResultEntity getRelationInCorp(@RequestBody @Valid RequestGetRelationInCorp data, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        GetRelationInCorpCallback callback = new GetRelationInCorpCallback(getRelationInCorpService, data);
        return extracted(callback, requestURI);
    }

    @RequestMapping(value = "/getCorpRelatedDetail", method = RequestMethod.POST)
    public ResultEntity getCorpRelatedDetail(@RequestBody @Valid RequestGetCorpRelatedDetail data, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        GetCorpRelatedDetailCallback callback = new GetCorpRelatedDetailCallback(getCorpRelatedDetailService, data);
        return extracted(callback, requestURI);
    }

    @RequestMapping(value = "/hasNextBatchQuery", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResultEntity hasNextBatchQuery(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        try {
            String requestURI = request.getRequestURI();
            @SuppressWarnings("unchecked")
            List<String> nodeIds = (List<String>) jsonObject.get("nodeIds");
            HasNextBatchQueryCallback callback = new HasNextBatchQueryCallback(hasNextBatchQueryService, nodeIds);
            return extracted(callback, requestURI);
        } catch (Exception e) {
            ResultEntity responseResult;
            String message = "Query exception, " + e.getMessage();
            responseResult = new ResultEntity(null, message, 500);
            return responseResult;
        }
    }

    @RequestMapping(value = "/pathSearch", method = RequestMethod.POST)
    public ResultEntity pathSearch(@RequestBody @Valid RequestPathSearch data, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        PathSearchCallback callback = new PathSearchCallback(pathSearchService, data);
        return extracted(callback, requestURI);
    }

    private ResultEntity extracted(Callable<ResultEntity> callback, String requestURI) {
        long startTime = System.currentTimeMillis();

        ResultEntity responseResult = new ResultEntity();
        int status = 200;

        String message;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            try {
                Future<ResultEntity> future = executor.submit(callback);
                responseResult = future.get(threadConfiguration.getTimeout(), TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                message = String.format("Compute Timeout, processed exceed %d ms", threadConfiguration.getTimeout());
                status = 500;
                responseResult = new ResultEntity(null, message, status);
            }
        } catch (Exception e) {
            message = "Thread Executor Exception, " + e.getMessage();
            status = 500;
            responseResult = new ResultEntity(null, message, status);
        } finally {
            executor.shutdownNow();
        }

        logger.info(String.format("Interface: %s, Response Status: %d, Process Time: %d", requestURI, status,
                System.currentTimeMillis() - startTime));

        return responseResult;
    }

}
