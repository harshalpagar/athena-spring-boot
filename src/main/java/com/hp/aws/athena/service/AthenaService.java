package com.hp.aws.athena.service;

import com.hp.aws.athena.AthenaProperties;
import com.hp.aws.athena.repository.AthenaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.springframework.stereotype.Service;

/**
 * @author pagarha
 * @date 20-01-2020
 */
@Slf4j
@Service
@AllArgsConstructor
public class AthenaService {

    private AthenaProperties athenaProperties;
    private AthenaRepository repository;

    /**
     * build the athena sql query and submit for processing
     *
     * @return athenaQueryId  needed for data retrieval
     */
    public String submitQuery() {
        // build business query, here we will get data from cloud trail logs
        // you can accept required parameter from user and build sql here
        String sql = "SELECT eventid, eventname, eventsource, awsregion, eventtime FROM cloudtrail_logs limit 10;";
        return repository.submitAthenaQuery(sql);
    }

    /**
     * get query status by query id
     * RUNNING indicates that the query has been submitted to the service, and Athena will execute the query as soon as resources are available.
     * SUCCEEDED indicates that the query completed without errors.
     * FAILED indicates that the query experienced an error and did not complete processing.
     * CANCELLED indicates that a user input interrupted query execution
     *
     * @param queryId
     * @return status
     */
    public String getQueryStatus(String queryId) {
        log.debug("action=get_query_status , query_id=" + queryId);
        return repository.getQueryStatus(queryId);
    }

    /**
     * get result by queryId
     *
     * @param queryId
     * @return
     */
    public MutableList geQueryResult(String queryId) {
        log.debug("action=get_query_result , query_id=" + queryId);
        return repository.getQueryResults(queryId);
    }


}
