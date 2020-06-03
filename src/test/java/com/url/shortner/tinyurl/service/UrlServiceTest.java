package com.url.shortner.tinyurl.service;

import com.url.shortner.tinyurl.dto.UrlLongRequest;
import com.url.shortner.tinyurl.entity.Url;
import com.url.shortner.tinyurl.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockurlRepository;

    @Mock
    BaseConversion mockbaseConversion;

    @InjectMocks
    UrlService urlService;

    @Test
    public void convertToShortUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/njitrishabh/URL-Shortner-RESTService");
        url.setId(5);
        url.setCreatedDate(new Date());

        Mockito.when(mockurlRepository.save(any(Url.class))).thenReturn(url);
        Mockito.when(mockbaseConversion.encode(url.getId())).thenReturn("f");

        UrlLongRequest urlLongRequest = new UrlLongRequest();
        urlLongRequest.setLongUrl("https://github.com/njitrishabh/URL-Shortner-RESTService");

        assertEquals("f", urlService.convertToShortUrl(urlLongRequest));

    }

    @Test
    public void getOriginalUrlTest() {
        Mockito.when(mockbaseConversion.decode("h")).thenReturn((long) 7);

        Url url = new Url();
        url.setLongUrl("https://github.com/njitrishabh/URL-Shortner-RESTService");
        url.setId(7);
        url.setCreatedDate(new Date());

        Mockito.when(mockurlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));

        assertEquals("https://github.com/njitrishabh/URL-Shortner-RESTService", urlService.getOriginalUrl("h"));
    }
}
