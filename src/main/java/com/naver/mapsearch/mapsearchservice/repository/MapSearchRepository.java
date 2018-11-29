package com.naver.mapsearch.mapsearchservice.repository;

import com.naver.mapsearch.mapsearchservice.domain.LatLon;
import com.naver.mapsearch.mapsearchservice.domain.MapSearch;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MapSearchRepository {

    private final String INDEX = "tests";
    private final String TYPE = "test";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Mono<List<MapSearch>> searchWithKeyword(String keyword) {

        return  Mono.create(sink -> {
            try {
                searchQueryByKeyword(keyword, listenerToSink(sink));
            } catch(Exception e) {
                sink.error(e);
            }
        });
    }

    private void searchQueryByKeyword(String keyword, ActionListener<SearchTemplateResponse> listener)
            throws Exception{

        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(INDEX));
        request.setScriptType(ScriptType.INLINE);
        String searchQuery = "";
        searchQuery += "{";
        searchQuery += "    'query' : {";
        searchQuery += "        'term' : {";
        searchQuery += "            '{{field}}' : '{{value}}'";
        searchQuery += "        }";
        searchQuery += "    },";
        searchQuery += "    'size' : '{{size}}'";
        searchQuery += "}";
        searchQuery = searchQuery.replace("'","\"");
        request.setScript(searchQuery);
        System.out.println(searchQuery);


        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "location");
        scriptParams.put("value", keyword);
        scriptParams.put("size", 100);
        request.setScriptParams(scriptParams);

        restHighLevelClient.searchTemplateAsync(request, RequestOptions.DEFAULT, listener);
    }

    private ActionListener<SearchTemplateResponse> listenerToSink(MonoSink<List<MapSearch>> sink) {
        return new ActionListener<SearchTemplateResponse>() {
            @Override
            public void onResponse(SearchTemplateResponse response) {

                SearchResponse searchResponse = response.getResponse();
                SearchHits hits = searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();

                List<MapSearch> mapSearchList = new ArrayList<>();
                for (SearchHit hit : searchHits) {

                    // do something with the SearchHit
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //System.out.println(sourceAsMap);
                    String map_location = (String) sourceAsMap.get("location");
                    List<String> map_location_tokens = (List<String>) sourceAsMap.get("location_tokens");
                    Map<String, Double> map_latlon = (Map<String, Double>) sourceAsMap.get("latlon");
                    Double map_latitude = map_latlon.get("lat");
                    Double map_longitude = map_latlon.get("lon");
                    //System.out.println(map_latitude);
                    //System.out.println(map_longitude);
                    MapSearch mapSearch  = new MapSearch(map_location, map_location_tokens,map_latitude, map_longitude);

                    mapSearchList.add(mapSearch);
                }


                System.out.println(mapSearchList);

                sink.success(mapSearchList);
            }

            @Override
            public void onFailure(Exception e) {
                sink.error(e);
            }
        };
    }

}
