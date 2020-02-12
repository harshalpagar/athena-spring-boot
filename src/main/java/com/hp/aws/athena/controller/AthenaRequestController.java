package com.hp.aws.athena.controller;

import com.hp.aws.athena.service.AthenaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.eclipse.collections.api.list.MutableList;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * REST webservice for Athena Operation
 * @author pagarha
 * @date 11-02-2020
 */
@AllArgsConstructor
@RestController(value = "/athena")
@Api(value = "Operations pertaining to Athena")
public class AthenaRequestController {

    private AthenaService service;

    @ApiOperation(value = "Operation To Submit Athena query")
    @PostMapping(value = "/query")
    public String submitAthenaQuery() {
        return service.submitQuery();
    }

    @ApiOperation(value = "Operation To Get Athena Query Execution Status")
    @GetMapping(path = "/query/{queryId}/status")
    public String getQueryStatus(@PathVariable String queryId) {
        return service.getQueryStatus(queryId);
    }

    @ApiOperation(value = "Operation To Get Result By Query Id")
    @GetMapping(path = "/query/{queryId}")
    public MutableList getAlarmEventsByQueryId(@PathVariable String queryId) {
        return service.geQueryResult(queryId);
    }

}
