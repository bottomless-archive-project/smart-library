package com.github.bottomlessarchive.smartlibrary.book.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("smartlibrary.book")
public class BookProperties {

    private String location;
}
