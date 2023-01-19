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
/* Generated By:JJTree: Do not edit this line. OPositionalParameter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import java.util.*;

public class PositionalParameter extends InputParameter {

  protected int paramNumber;

  public PositionalParameter(final int id) {
    super(id);
  }

  @Override
  public String toString() {
    return "?";
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    final Object finalValue = bindFromInputParams(params);
    if (finalValue == this) {
      builder.append("?");
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
      result = params.get(String.valueOf(paramNumber));
    }
    return result;
  }

  public Object bindFromInputParams(final Map<String, Object> params) {
    if (params != null) {
      final Object value = params.get(String.valueOf(paramNumber));
      final Object result = toParsedTree(value);
      return result;
    }
    return this;
  }

  @Override
  public PositionalParameter copy() {
    final PositionalParameter result = new PositionalParameter(-1);
    result.paramNumber = paramNumber;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final PositionalParameter that = (PositionalParameter) o;

    return paramNumber == that.paramNumber;
  }

  @Override
  public int hashCode() {
    return paramNumber;
  }
}
/* JavaCC - OriginalChecksum=f73bea7d9b3994a9d4e79d2c330d8ba2 (do not edit this line) */
