/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.admin.disruptor.subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.shenyu.admin.service.register.ShenyuClientRegisterService;
import org.apache.shenyu.register.common.dto.ApiDocRegisterDTO;
import org.apache.shenyu.register.common.type.DataType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test cases for {@link ApiDocExecutorSubscriber}.
 */
public class ApiDocExecutorSubscriberTest {

    @InjectMocks
    private ApiDocExecutorSubscriber apiDocExecutorSubscriber;

    @Mock
    private Map<String, ShenyuClientRegisterService> shenyuClientRegisterService;

    @Test
    public void getTypeTest() {
        assertEquals(DataType.API_DOC, apiDocExecutorSubscriber.getType());
    }

    @Test
    public void executorTest() {
        List<ApiDocRegisterDTO> list = new ArrayList<>();
        apiDocExecutorSubscriber.executor(list);
        assertTrue(list.isEmpty());
        list.add(ApiDocRegisterDTO.builder().contextPath("/test").apiPath("/test").build());
        ShenyuClientRegisterService service = mock(ShenyuClientRegisterService.class);
        when(shenyuClientRegisterService.get(any())).thenReturn(service);
        apiDocExecutorSubscriber.executor(list);
        verify(service).registerApiDoc(any());
    }
}
