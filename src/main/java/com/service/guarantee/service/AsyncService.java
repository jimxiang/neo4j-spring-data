package com.service.guarantee.service;

import com.service.guarantee.domain.ResultEntity;
import com.service.guarantee.domain.request.*;
import com.service.guarantee.domain.request.*;

import java.util.List;
import java.util.concurrent.Future;

public interface AsyncService {
    Future<ResultEntity> getTargetRelation(RequestGetTargetRelation requestData);
    Future<ResultEntity> expandNodePath(RequestExpandNodePath requestData);
    Future<ResultEntity> getCorpRelatedDetail(RequestGetCorpRelatedDetail requestData);
    Future<ResultEntity> getRelationInCorp(RequestGetRelationInCorp requestData);
    Future<ResultEntity> hasNextBatchQuery(List<String> nodeIds);
    Future<ResultEntity> pathSearch(RequestPathSearch requestData);
}
