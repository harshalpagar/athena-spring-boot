package com.hp.aws.athena.repository;

import lombok.AllArgsConstructor;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.athena.model.*;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pagarha
 * @date 23-01-2020
 */
@Repository
@AllArgsConstructor
public class AthenaRepository {

    private AthenaClientFactory factory;

    public String submitAthenaQuery(String query) {
        // Create the StartQueryExecutionRequest to send to Athena which will start the query.
        StartQueryExecutionRequest request = factory.buildStartQueryExecutionRequest().queryString(query).build();
        return factory.createClient().startQueryExecution(request).queryExecutionId();
    }

    public String getQueryStatus(String queryId) {
        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
                .queryExecutionId(queryId).build();

        GetQueryExecutionResponse getQueryExecutionResponse = factory.createClient().getQueryExecution(getQueryExecutionRequest);
        return getQueryExecutionResponse.queryExecution().status().state().toString();
    }

    /**
     * Get the result from athena and build the Map object list
     *
     * @param queryId
     * @return
     */
    public MutableList getQueryResults(String queryId) {
        MutableList list = Lists.mutable.empty();
        GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                .queryExecutionId(queryId).build();
        GetQueryResultsIterable resultsPaginator = factory.createClient().getQueryResultsPaginator(getQueryResultsRequest);
        for (GetQueryResultsResponse resultPage : resultsPaginator) {
            List<String> columns = new ArrayList<>();
            List<Row> results = resultPage.resultSet().rows();
            boolean isFirstRow = true;
            for (Row row : results) {
                MutableMap<String, String> record = Maps.mutable.empty();
                int index = 0;
                if (isFirstRow) {
                    // first row contains column information
                    row.data().forEach(col -> columns.add(col.varCharValue()));
                    isFirstRow = false;
                    continue;
                }
                for (Datum datum : row.data()) {
                    record.put(columns.get(index++), datum.varCharValue());
                }
                list.add(record);
            }
        }
        return list;
    }

}
