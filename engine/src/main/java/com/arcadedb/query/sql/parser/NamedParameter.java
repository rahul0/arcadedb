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
/* Generated By:JJTree: Do not edit this line. ONamedParameter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;

public class NamedParameter extends InputParameter {

  protected int    paramNumber;
  protected String paramName;

  public NamedParameter(final int id) {
    super(id);
  }

  public NamedParameter(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public String toString() {
    return ":" + paramName;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    final Object finalValue = bindFromInputParams(params);
    if (finalValue == this) {
      builder.append(":" + paramName);
    } else if (finalValue instanceof String) {
      builder.append("\"");
      builder.append(Expression.encode(finalValue.toString()));
      builder.append("\"");
    } else if (finalValue instanceof SimpleNode) {
      ((SimpleNode) finalValue).toString(params, builder);
    } else {
      builder.append(finalValue);
    }
  }

  public Object getValue(final Map<String, Object> params) {
    Object result = null;
    if (params != null) {
      final String key = paramName;
      if (params.containsKey(":" + key)) {
        result = params.get(":" + key);
      } else if (params.containsKey(key)) {
        result = params.get(key);
      } else {
        result = params.get(String.valueOf(paramNumber));
      }
    }
    return result;
  }

  public Object bindFromInputParams(final Map<String, Object> params) {
    if (params != null) {
      final String key = paramName;
      if (params.containsKey(key)) {
        return toParsedTree(params.get(key));
      }
      return toParsedTree(params.get(String.valueOf(paramNumber)));
    }
    return this;
  }

  @Override
  public NamedParameter copy() {
    final NamedParameter result = new NamedParameter(-1);
    result.paramName = paramName;
    result.paramNumber = paramNumber;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final NamedParameter that = (NamedParameter) o;

    if (paramNumber != that.paramNumber)
      return false;
    return Objects.equals(paramName, that.paramName);
  }

  @Override
  public int hashCode() {
    int result = paramNumber;
    result = 31 * result + (paramName != null ? paramName.hashCode() : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=8a00a9cf51a15dd75202f6372257fc1c (do not edit this line) */
