package com.superboard.onbrd.global.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {
	@Override
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {

			@Override
			protected Cache createConcurrentMapCache(final String name) {
				return new ConcurrentMapCache(name,
					CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES).build().asMap(), false);
			}
		};

		return cacheManager;
	}
}
