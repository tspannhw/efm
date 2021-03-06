/*
 * Copyright (c) 2018-2019 Cloudera, Inc. All rights reserved.
 *
 * This code is provided to you pursuant to your written agreement with Cloudera, which may be the terms of the
 * Affero General Public License version 3 (AGPLv3), or pursuant to a written agreement with a third party authorized
 * to distribute this code.  If you do not have a written agreement with Cloudera or with an authorized and
 * properly licensed third party, you do not have any rights to this code.
 *
 * If this code is provided to you under the terms of the AGPLv3:
 *   (A) CLOUDERA PROVIDES THIS CODE TO YOU WITHOUT WARRANTIES OF ANY KIND;
 *   (B) CLOUDERA DISCLAIMS ANY AND ALL EXPRESS AND IMPLIED WARRANTIES WITH RESPECT TO THIS CODE, INCLUDING BUT NOT
 *       LIMITED TO IMPLIED WARRANTIES OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE;
 *   (C) CLOUDERA IS NOT LIABLE TO YOU, AND WILL NOT DEFEND, INDEMNIFY, OR HOLD YOU HARMLESS FOR ANY CLAIMS ARISING
 *       FROM OR RELATED TO THE CODE; AND
 *   (D) WITH RESPECT TO YOUR EXERCISE OF ANY RIGHTS GRANTED TO YOU FOR THE CODE, CLOUDERA IS NOT LIABLE FOR ANY
 *       DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES INCLUDING, BUT NOT LIMITED
 *       TO, DAMAGES RELATED TO LOST REVENUE, LOST PROFITS, LOSS OF INCOME, LOSS OF BUSINESS ADVANTAGE OR
 *       UNAVAILABILITY, OR LOSS OR CORRUPTION OF DATA.
 */
package com.cloudera.cem.efm.mapper;

import com.cloudera.cem.efm.db.entity.EventEntity;
import com.cloudera.cem.efm.model.Event;
import org.modelmapper.Condition;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
public class EventConverterFactory {

    @Bean
    public static PropertyMap<Event, EventEntity> eventToEntityMap() {
        return new PropertyMap<Event, EventEntity>() {
            @Override
            protected void configure() {
                map().setEventSourceType(source.getEventSource().getType());
                map().setEventSourceId(source.getEventSource().getId());

                map().setEventDetailType(source.getEventDetail().getType());
                map().setEventDetailId(source.getEventDetail().getId());

                // other fields are mapped implicitly
            }
        };
    }

    @Bean
    public static PropertyMap<EventEntity, Event> entityToEventMap() {
        return new PropertyMap<EventEntity, Event>() {
            @Override
            protected void configure() {

                map().getEventSource().setType(source.getEventSourceType());
                map().getEventSource().setId(source.getEventSourceId());

                map().getEventDetail().setType(source.getEventDetailType());
                map().getEventDetail().setId(source.getEventDetailId());

                // if the map of tags is empty, do not set the destination
                // so that this field is omitted when serializing to json
                when((Condition<Map, Map>) context ->
                        context.getSource() != null && !context.getSource().isEmpty())
                        .map().setTags(source.getTags());

                // other fields are mapped implicitly
            }
        };
    }

    /**
     * Returns all property maps this factory is capable of building.
     * Mainly used for test suites.
     */
    public static List<PropertyMap<?,?>> allMaps() {
        List<PropertyMap<?,?>> maps = Arrays.asList(
                EventConverterFactory.eventToEntityMap()
        );
        return maps;
    }


}
