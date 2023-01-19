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
/* Generated By:JJTree: Do not edit this line. OArraySelector.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;

import java.lang.reflect.*;
import java.util.*;

public class ArraySelector extends SimpleNode {

  protected Rid            rid;
  protected InputParameter inputParam;
  protected Expression     expression;
  protected PInteger       integer;

  public ArraySelector(final int id) {
    super(id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (rid != null) {
      rid.toString(params, builder);
    } else if (inputParam != null) {
      inputParam.toString(params, builder);
    } else if (expression != null) {
      expression.toString(params, builder);
    } else if (integer != null) {
      integer.toString(params, builder);
    }
  }

  public Integer getValue(final Identifiable iCurrentRecord, final Object iResult, final CommandContext ctx) {
    Object result = null;
    if (inputParam != null) {
      result = inputParam.getValue(ctx.getInputParameters());
    } else if (expression != null) {
      result = expression.execute(iCurrentRecord, ctx);
    } else if (integer != null) {
      result = integer;
    }

    if (result == null) {
      return null;
    }
    if (result instanceof Number) {
      return ((Number) result).intValue();
    }
    return null;
  }

  public Object getValue(final Result iCurrentRecord, final Object iResult, final CommandContext ctx) {
    Object result = null;
    if (inputParam != null) {
      result = inputParam.getValue(ctx.getInputParameters());
    } else if (expression != null) {
      result = expression.execute(iCurrentRecord, ctx);
    } else if (integer != null) {
      result = integer;
    }

    if (result == null) {
      return null;
    }
    if (result instanceof Number) {
      return ((Number) result).intValue();
    }
    return result;
  }

  public ArraySelector copy() {
    final ArraySelector result = new ArraySelector(-1);
    result.rid = rid == null ? null : rid.copy();
    result.inputParam = inputParam == null ? null : inputParam.copy();
    result.expression = expression == null ? null : expression.copy();
    result.integer = integer == null ? null : integer.copy();
    return result;
  }

  public void extractSubQueries(final SubQueryCollector collector) {
    if (expression != null) {
      expression.extractSubQueries(collector);
    }
  }

  @Override
  protected Object[] getIdentityElements() {
    return getCacheableElements();
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return new SimpleNode[] { inputParam, expression, inputParam };
  }

  public void setValue(final Result currentRecord, final Object target, final Object value, final CommandContext ctx) {
    Object idx = null;
    if (this.rid != null) {
      idx = this.rid.toRecordId(currentRecord, ctx);
    } else if (inputParam != null) {
      idx = inputParam.getValue(ctx.getInputParameters());
    } else if (expression != null) {
      idx = expression.execute(currentRecord, ctx);
    } else if (integer != null) {
      idx = integer.getValue();
    }

    if (target instanceof Set && idx instanceof Number) {
      setValue((Set) target, ((Number) idx).intValue(), value, ctx);
    } else if (target instanceof List && idx instanceof Number) {
      setValue((List) target, ((Number) idx).intValue(), value, ctx);
    } else if (target instanceof Map) {
      setValue((Map) target, idx, value, ctx);
    } else if (target.getClass().isArray() && idx instanceof Number) {
      setArrayValue(target, ((Number) idx).intValue(), value, ctx);
    }
  }

  public void setValue(final List target, final int idx, final Object value, final CommandContext ctx) {
    final int originalSize = target.size();
    for (int i = originalSize; i <= idx; i++) {
      if (i >= originalSize) {
        target.add(null);
      }
    }
    target.set(idx, value);
  }

  public void setValue(final Set target, final int idx, final Object value, final CommandContext ctx) {
    final Set result = new LinkedHashSet<>();
    final int originalSize = target.size();
    final int max = Math.max(idx, originalSize - 1);
    final Iterator targetIterator = target.iterator();
    for (int i = 0; i <= max; i++) {
      Object next = null;
      if (targetIterator.hasNext()) {
        next = targetIterator.next();
      }
      if (i == idx) {
        result.add(value);
      } else if (i < originalSize) {
        result.add(next);
      } else {
        result.add(null);
      }
      target.clear();
      target.addAll(result);
    }
  }

  public void setValue(final Map target, final Object idx, final Object value, final CommandContext ctx) {
    target.put(idx, value);
  }

  private void setArrayValue(final Object target, final int idx, final Object value, final CommandContext ctx) {
    if (idx >= 0 && idx < Array.getLength(target)) {
      Array.set(target, idx, value);
    }
  }
}
/* JavaCC - OriginalChecksum=f87a5543b1dad0fb5f6828a0663a7c9e (do not edit this line) */
