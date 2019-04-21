package com.philips.hsdp.cfdashboard.controller;

import com.philips.hsdp.cfdashboard.constants.*;
import java.util.*;
import org.springframework.boot.web.client.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@RestController
@RequestMapping(value = CfDashBoardConstant.BASE_PATH)
public class CfDashBoardAppController {
    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public ResponseEntity<Map> doGetCfApps(@RequestParam("org-guid") String orgGuid,
                                           @RequestParam("space-guids") String spaceGuids,
                                           @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.cloud.pcftest.com/v3/apps")
                                               .queryParam("organization_guids", orgGuid)
                                               .queryParam("space_guids", spaceGuids);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/app", method = RequestMethod.DELETE)
    public ResponseEntity<Map> doGetCfApp(@RequestParam("app-guid") String appGuid,
                                          @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        String url = "https://api.cloud.pcftest.com/v3/apps" + appGuid;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/app/start", method = RequestMethod.GET)
    public ResponseEntity<Map> doStartCfApp(@RequestParam("app-guid") String appGuid,
                                            @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        String url = "https://api.cloud.pcftest.com/v3/apps/" + appGuid + "/actions/start";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/app/stop", method = RequestMethod.GET)
    public ResponseEntity<Map> doStopCfApp(@RequestParam("app-guid") String appGuid,
                                           @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        String url = "https://api.cloud.pcftest.com/v3/apps/" + appGuid + "/actions/stop";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
}
