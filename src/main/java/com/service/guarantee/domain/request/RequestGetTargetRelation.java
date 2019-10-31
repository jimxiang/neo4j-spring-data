package com.service.guarantee.domain.request;

import javax.validation.constraints.NotBlank;

public class RequestGetTargetRelation {
    @NotBlank(message = "不能为空")
    private String eid;
    private int start;
    private int limit;

    public RequestGetTargetRelation(@NotBlank(message = "不能为空") String eid, int start, int limit) {
        this.eid = eid;
        if (start < 0) {
            this.start = 0;
        } else {
            this.start = start;
        }
        if (limit <= 0) {
            this.limit = 10;
        } else {
            this.limit = limit;
        }
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "GetTargetRelation Request Body {" +
                "eid='" + eid + '\'' +
                ", start=" + start +
                ", limit=" + limit +
                '}';
    }
}
