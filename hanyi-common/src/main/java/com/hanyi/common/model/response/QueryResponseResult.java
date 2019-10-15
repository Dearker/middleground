package com.hanyi.common.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author hanyi
 */
@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    Object queryResult;

    public QueryResponseResult(ResultCode resultCode,Object queryResult){
        super(resultCode);
        this.queryResult = queryResult;
    }
}
