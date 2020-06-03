package com.url.shortner.tinyurl.service;

import com.url.shortner.tinyurl.dto.UrlLongRequest;
import com.url.shortner.tinyurl.entity.Url;
import com.url.shortner.tinyurl.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
public class UrlService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UrlRepository urlRepository;
    private final BaseConversion conversion;

    public UrlService(UrlRepository urlRepository, BaseConversion conversion) {
        this.urlRepository = urlRepository;
        this.conversion = conversion;
    }

    public String convertToShortUrl(UrlLongRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        Url entity = urlRepository.save(url);

        return conversion.encode(entity.getId());

    }

    public String getOriginalUrl(String shortUrl) {
        long id = conversion.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        logger.info("Url id -> {}", urlRepository.findById(id));

        if (entity.getExpiresDate() != null
        && entity.getExpiresDate().before(new Date())) {
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link Expired!");
        }
        return entity.getLongUrl();
    }
}
