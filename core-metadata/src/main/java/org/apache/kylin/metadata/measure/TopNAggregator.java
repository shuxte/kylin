/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.apache.kylin.metadata.measure;

import com.google.common.collect.Maps;
import org.apache.kylin.common.topn.Counter;
import org.apache.kylin.common.topn.TopNCounter;
import org.apache.kylin.common.util.ByteArray;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 */
@SuppressWarnings("serial")
public class TopNAggregator extends MeasureAggregator<TopNCounter<ByteArray>> {

    int capacity = 0;
    TopNCounter<ByteArray> sum = null;
    Map<ByteArray, Double> sanityCheckMap;

    @Override
    public void reset() {
        sum = null;
    }

    @Override
    public void aggregate(TopNCounter<ByteArray> value) {
        if (sum == null) {
            capacity = value.getCapacity();
            sum = new TopNCounter<>(capacity);
            sanityCheckMap = Maps.newHashMap();
        }
        sum.merge(value);
    }

    @Override
    public TopNCounter<ByteArray> getState() {
        
        //sum.retain(capacity);
        return sum;
    }

    @Override
    public int getMemBytesEstimate() {
        return 8 * capacity / 4;
    }

}