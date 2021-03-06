/*
 * Apache NiFi
 * Copyright 2014-2018 The Apache Software Foundation
 *
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
package com.cloudera.cem.efm.revision;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StandardRevisionClaim implements RevisionClaim {
    private final Set<Revision> revisions;

    public StandardRevisionClaim(final Revision... revisions) {
        this.revisions = new HashSet<>(revisions.length);
        for (final Revision revision : revisions) {
            this.revisions.add(revision);
        }
    }

    public StandardRevisionClaim(final Collection<Revision> revisions) {
        this.revisions = new HashSet<>(revisions);
    }

    @Override
    public Set<Revision> getRevisions() {
        return revisions;
    }

    @Override
    public String toString() {
        return revisions.toString();
    }
}
