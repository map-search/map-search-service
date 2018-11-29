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



    public Mono<List<MapSearch>> searchWithKeywordAndLatLon(String keyword, Double latitude, Double longitude) {

        return  Mono.create(sink -> {
            try {
                searchQueryByKeywordAndLatLon(keyword, latitude, longitude, listenerToSink(sink));
            } catch(Exception e) {
                sink.error(e);
            }
        });
    }

    private void searchQueryByKeywordAndLatLon(String keyword, Double latitude, Double longitude,
                                               ActionListener<SearchTemplateResponse> listener)throws Exception{

        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(INDEX));
        request.setScriptType(ScriptType.INLINE);
        String searchQuery = "";
        searchQuery += "{";
        searchQuery += "    'query' : {";
        searchQuery += "        'function_score' : {";
        searchQuery += "            'query' : {";
        searchQuery += "                'bool' : {";
        searchQuery += "                    'should' : [";
        searchQuery += "                        {";
        searchQuery += "                            'term' : {";
        searchQuery += "                                'location' : '{{keyword}}'";
        searchQuery += "                            }";
        searchQuery += "                        },";
        searchQuery += "                        {";
        searchQuery += "                            'term' : {";
        searchQuery += "                                'location_tokens' : '{{keyword}}'";
        searchQuery += "                            }";
        searchQuery += "                        }";
        searchQuery += "                    ]";
        searchQuery += "                }";
        searchQuery += "            },";
        searchQuery += "            'script_score' : {";
        searchQuery += "                'script' : {";
        searchQuery += "                    'params' : {";
        searchQuery += "                        'lat' : {{latitude}},";
        searchQuery += "                        'lon' : {{longitude}}";
        searchQuery += "                    },";
        searchQuery += "                    'source' : '1 / (0.2 * doc[?latlon?].planeDistance(params.lat,params.lon) + 1)'";
        searchQuery += "                }";
        searchQuery += "            }";
        searchQuery += "        }";
        searchQuery += "    },";
        searchQuery += "    'size' : {{size}}";
        searchQuery += "}";
        searchQuery = searchQuery.replace("'","\"");
        searchQuery = searchQuery.replace("?","\'");



        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("keyword", keyword);
        scriptParams.put("latitude", latitude);
        scriptParams.put("longitude", longitude);
        scriptParams.put("size", 100);
        request.setScriptParams(scriptParams);
        System.out.println(scriptParams);
        request.setScript(searchQuery);
        System.out.println(searchQuery);

        restHighLevelClient.searchTemplateAsync(request, RequestOptions.DEFAULT, listener);
    }

    private ActionListener<SearchTemplateResponse> listenerToSink(MonoSink<List<MapSearch>> sink) {
        return new ActionListener<SearchTemplateResponse>() {
            @Override
            public void onResponse(SearchTemplateResponse response) {

                SearchResponse searchResponse = response.getResponse();
                SearchHits hits = searchResponse.getHits();
                SearchHit[] searchHits = hits.getHits();
                System.out.println(searchHits);

                List<MapSearch> mapSearchList = new ArrayList<>();
                for (SearchHit hit : searchHits) {

                    // do something with the SearchHit
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    System.out.println(sourceAsMap);
                    String map_location = (String) sourceAsMap.get("location");
                    List<String> map_location_tokens = (List<String>) sourceAsMap.get("location_tokens");
                    Map<String, Double> map_latlon = (Map<String, Double>) sourceAsMap.get("latlon");
                    Double map_latitude = map_latlon.get("lat");
                    Double map_longitude = map_latlon.get("lon");
                    System.out.println(map_latitude);
                    System.out.println(map_longitude);
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
