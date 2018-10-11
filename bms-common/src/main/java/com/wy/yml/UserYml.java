package com.wy.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration("userDataSources")
@ConfigurationProperties(prefix = "user")
@Getter
@Setter
public class UserYml {
}