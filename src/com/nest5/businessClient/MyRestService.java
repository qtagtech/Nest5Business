package com.nest5.businessClient;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.GsonHttpMessageConverter;


@Rest(converters = { GsonHttpMessageConverter.class })
//if defined, the url will be added as a prefix to every request
public interface MyRestService extends RestClientHeaders {

 // url variables are mapped to method parameter names.
 @Get(Setup.PROD_BIGDATA_URL+"/databaseOps/salesFromDate?company={company}")
 @Accept(MediaType.APPLICATION_JSON)
 SaleObject[] getTodaySales(String company);
 //EventList getEvents(String location, int year);

}
