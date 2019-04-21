package com.philips.hsdp.cfdashboard.controller;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.jayway.jsonpath.*;
import com.philips.hsdp.cfdashboard.config.*;
import com.philips.hsdp.cfdashboard.constants.*;
import com.philips.hsdp.cfdashboard.model.*;
import java.util.*;
import net.minidev.json.*;
import org.cloudfoundry.reactor.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.web.client.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(CfDashBoardConstant.BASE_PATH)
public class CfDashBoardLoginController {
    
    @Value(value = "${http_proxy_enable:false}")
    private Boolean isProxyEnabled;
    @Value(value = "${cf_api_host}")
    private String cfApiHost;
    @Value(value = "${https_proxy_port}")
    private Integer proxyPort;
    @Value(value = "${https_proxy_host}")
    private String proxyHost;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map> doLogin(@RequestBody Login login) {
        CfClientConfig cfClientConfig = new CfClientConfig();
        
        try {
            TokenProvider tokenProvider = cfClientConfig.tokenProvider(login.getUserName(), login.getPassword());
            ConnectionContext connectionContext = cfClientConfig.connectionContext(cfApiHost,
                    proxyPort, isProxyEnabled);
            List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
            httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
            RestTemplate restTemplate = new RestTemplateBuilder().messageConverters(httpMessageConverters).build();
            String bearerToken = tokenProvider.getToken(connectionContext).block();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.cloud.pcftest.com/v3/organizations");
            HttpEntity<?> entity = new HttpEntity<>(headers);
            
            ResponseEntity<OrganizationResponseModel> apiResponseOrganizations = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    OrganizationResponseModel.class);
            
            builder = UriComponentsBuilder.fromHttpUrl("https://api.cloud.pcftest.com/v3/spaces");
            entity = new HttpEntity<>(headers);
            ResponseEntity<SpaceResponseModel> apiResponseSpaces = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    SpaceResponseModel.class);
            ResponseHolder responseHolder = new ResponseHolder();
            responseHolder.setSpaceList(apiResponseSpaces.getBody());
            responseHolder.setOrgList(apiResponseOrganizations.getBody());
            String jsonResponse = objectMapper.writeValueAsString(responseHolder);
            String jsonExpressionPathForResources = "$['spaceList']['resources'][*]";
            List<SpaceJsonHolder> spaceJsonHolders = populateSpaceList(jsonResponse, jsonExpressionPathForResources);
            jsonExpressionPathForResources = "$['orgList']['resources'][*]";
            List<OrgJsonHolder> orgJsonHolders = populateOrgList(jsonResponse, jsonExpressionPathForResources);
            
            Map<String, List<OrgSpaceMappingModel>> organizationModelMap = new HashMap<>(16);
            List<OrgSpaceMappingModel> orgSpaceMappingModels = new ArrayList<>();
            for (OrgJsonHolder orgJsonHolder : orgJsonHolders) {
                OrgSpaceMappingModel orgSpaceMappingModel = new OrgSpaceMappingModel();
                orgSpaceMappingModel.setGuid(orgJsonHolder.getOrgGuid());
                orgSpaceMappingModel.setName(orgJsonHolder.getOrgName());
                List<NameGUIDModel> spaces = new ArrayList<>();
                for (SpaceJsonHolder spaceJsonHolder : spaceJsonHolders) {
                    if (orgJsonHolder.getOrgGuid().equals(spaceJsonHolder.getOrgGuid())) {
                        NameGUIDModel nameGUIDModel = new NameGUIDModel();
                        nameGUIDModel.setGuid(spaceJsonHolder.getSpaceGuid());
                        nameGUIDModel.setName(spaceJsonHolder.getSpaceName());
                        spaces.add(nameGUIDModel);
                    }
                }
                orgSpaceMappingModel.setSpaces(spaces);
                orgSpaceMappingModels.add(orgSpaceMappingModel);
            }
            organizationModelMap.put("organizations", orgSpaceMappingModels);
            return new ResponseEntity<Map>(organizationModelMap, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private List<SpaceJsonHolder> populateSpaceList(String jsonResponse, String jsonExpressionPathForResources) {
        DocumentContext jsonContext = JsonPath.parse(jsonResponse);
        JSONArray jsonArray = jsonContext.read(jsonExpressionPathForResources);
        List<SpaceJsonHolder> spaceOrgMapping = new ArrayList<>();
        for (Iterator<Object> it = jsonArray.iterator(); it.hasNext(); ) {
            SpaceJsonHolder spaceJsonHolder = new SpaceJsonHolder();
            LinkedHashMap linkedHashMap = (LinkedHashMap) it.next();
            linkedHashMap.forEach((key, value) -> {
                if ("name".equals(key)) {
                    spaceJsonHolder.setSpaceName((String) value);
                }
                if ("guid".equals(key)) {
                    spaceJsonHolder.setSpaceGuid((String) value);
                }
                if ("relationships".equals(key)) {
                    LinkedHashMap relationships = (LinkedHashMap) value;
                    JSONObject jsonObject = new JSONObject(relationships);
                    LinkedHashMap organizationMap = (LinkedHashMap) jsonObject.get("organization");
                    JSONObject orgJson = new JSONObject(organizationMap);
                    LinkedHashMap dataMap = (LinkedHashMap) orgJson.get("data");
                    JSONObject dataJson = new JSONObject(dataMap);
                    String orgGuid = (String) dataJson.get("guid");
                    spaceJsonHolder.setOrgGuid(orgGuid);
                    
                }
            });
            spaceOrgMapping.add(spaceJsonHolder);
            
        }
        return spaceOrgMapping;
    }
    
    private List<OrgJsonHolder> populateOrgList(String jsonResponse, String jsonExpressionPathForResources) {
        DocumentContext jsonContext = JsonPath.parse(jsonResponse);
        JSONArray jsonArray = jsonContext.read(jsonExpressionPathForResources);
        List<OrgJsonHolder> orgJsonHolders = new ArrayList<>();
        for (Iterator<Object> it = jsonArray.iterator(); it.hasNext(); ) {
            OrgJsonHolder orgJsonHolder = new OrgJsonHolder();
            LinkedHashMap linkedHashMap = (LinkedHashMap) it.next();
            linkedHashMap.forEach((key, value) -> {
                if ("name".equals(key)) {
                    orgJsonHolder.setOrgName((String) value);
                }
                if ("guid".equals(key)) {
                    orgJsonHolder.setOrgGuid((String) value);
                }
            });
            orgJsonHolders.add(orgJsonHolder);
            
        }
        return orgJsonHolders;
    }
    
    private static class SpaceJsonHolder {
        public String spaceGuid;
        public String orgGuid;
        public String spaceName;
        
        public String getSpaceName() {
            return spaceName;
        }
        
        public void setSpaceName(String spaceName) {
            this.spaceName = spaceName;
        }
        
        public String getSpaceGuid() {
            return spaceGuid;
        }
        
        public void setSpaceGuid(String spaceGuid) {
            this.spaceGuid = spaceGuid;
        }
        
        public String getOrgGuid() {
            return orgGuid;
        }
        
        public void setOrgGuid(String orgGuid) {
            this.orgGuid = orgGuid;
        }
    }
    
    private static class OrgJsonHolder {
        public String orgGuid;
        public String orgName;
        
        public String getOrgGuid() {
            return orgGuid;
        }
        
        public void setOrgGuid(String orgGuid) {
            this.orgGuid = orgGuid;
        }
        
        public String getOrgName() {
            return orgName;
        }
        
        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }
    
    
    private static class ResponseHolder {
        
        private OrganizationResponseModel orgList;
        private SpaceResponseModel spaceList;
        
        public OrganizationResponseModel getOrgList() {
            return orgList;
        }
        
        public void setOrgList(OrganizationResponseModel orgList) {
            this.orgList = orgList;
        }
        
        public SpaceResponseModel getSpaceList() {
            return spaceList;
        }
    
        public void setSpaceList(SpaceResponseModel spaceList) {
            this.spaceList = spaceList;
        }
    
    }
    
    private static class OrgSpaceMappingModel {
        private String name;
        private String guid;
        private List<NameGUIDModel> spaces = new ArrayList<>();
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getGuid() {
            return guid;
        }
        
        public void setGuid(String guid) {
            this.guid = guid;
        }
        
        public List<NameGUIDModel> getSpaces() {
            return spaces;
        }
        
        public void setSpaces(List<NameGUIDModel> spaces) {
            this.spaces = spaces;
        }
    }
    
    private static class NameGUIDModel {
        private String name;
        private String guid;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getGuid() {
            return guid;
        }
        
        public void setGuid(String guid) {
            this.guid = guid;
        }
    }
    
    private static class OrganizationResponseModel {
        private List<Resource> resources = new ArrayList<>();
        
        public List<Resource> getResources() {
            return resources;
        }
        
        public void setResources(List<Resource> resources) {
            this.resources = resources;
        }
    }
    
    private static class Resource {
        private String guid;
        private String name;
        @JsonProperty("relationships")
        private JsonNode relationships;
        
        public String getGuid() {
            return guid;
        }
        
        public void setGuid(String guid) {
            this.guid = guid;
        }
        
        public void setRelationships(JsonNode relationships) {
            this.relationships = relationships;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
    }
    
    private static class SpaceResponseModel {
        private List<Resource> resources = new ArrayList<>();
        
        public List<Resource> getResources() {
            return resources;
        }
        
        public void setResources(List<Resource> resources) {
            this.resources = resources;
        }
    }
    
    
}
