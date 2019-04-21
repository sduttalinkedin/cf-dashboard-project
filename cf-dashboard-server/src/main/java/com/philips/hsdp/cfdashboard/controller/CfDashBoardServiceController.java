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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = CfDashBoardConstant.BASE_PATH)
public class CfDashBoardServiceController {
    
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public ResponseEntity<Map> doGetCfServices(@RequestParam("space-guid") String spaceGuids,
                                               @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.cloud.pcftest.com/v3/service_instances")
                                               .queryParam("space_guids", spaceGuids);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/service/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Map> doDeleteCfService(@RequestParam("service-guid") String serviceGuid,
                                                 @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        String url = "https://api.cloud.pcftest.com/v2/service_instances/" + serviceGuid;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("accepts_incomplete", Boolean.TRUE)
                                               .queryParam("async", Boolean.TRUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(value = "/service/shared", method = RequestMethod.GET)
    public ResponseEntity<Map> doGetSharedCfServiceDetails(@RequestParam("service-guid") String serviceGuid,
                                                           @RequestHeader("Authorization") final String authToken) {
        
        
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        String url = "https://api.cloud.pcftest.com/v2/service_instances/" + serviceGuid + "/shared_to";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> apiResponseOrganizations = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class);
        
        
        return new ResponseEntity<Map>(apiResponseOrganizations.getBody(), HttpStatus.OK);
    }
    
}
