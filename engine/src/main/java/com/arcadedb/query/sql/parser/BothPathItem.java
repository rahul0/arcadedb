/*
 * Copyright © 2021-present Arcade Data Ltd (info@arcadedata.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-FileCopyrightText: 2021-present Arcade Data Ltd (info@arcadedata.com)
 * SPDX-License-Identifier: Apache-2.0
 */
/* Generated By:JJTree: Do not edit this line. OBothPathItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;

import java.util.*;

public class BothPathItem extends MatchPathItem {
  public BothPathItem(final int id) {
    super(id);
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("-");
    boolean first = true;
    if (this.method.params != null) {
      for (final Expression exp : this.method.params) {
        if (!first) {
          builder.append(", ");
        }
        builder.append(exp.execute((Result) null, null));
        first = false;
      }
    }
    builder.append("-");
    if (filter != null) {
      filter.toString(params, builder);
    }
  }

}
/* JavaCC - OriginalChecksum=061ff26f18cfa0c561ce9b98ef919173 (do not edit this line) */
