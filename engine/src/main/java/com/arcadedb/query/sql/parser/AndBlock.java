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
/* Generated By:JJTree: Do not edit this line. OAndBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.schema.DocumentType;

import java.util.*;

public class AndBlock extends BooleanExpression {
  final List<BooleanExpression> subBlocks = new ArrayList<>();

  public AndBlock(final int id) {
    super(id);
  }

  public AndBlock(final SqlParser p, final int id) {
    super(p, id);
  }

  @Override
  public boolean evaluate(final Identifiable currentRecord, final CommandContext ctx) {
    if (getSubBlocks() == null)
      return true;

    for (final BooleanExpression block : subBlocks) {
      if (!block.evaluate(currentRecord, ctx)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean evaluate(final Result currentRecord, final CommandContext ctx) {
    if (getSubBlocks() == null)
      return true;

    for (final BooleanExpression block : subBlocks) {
      if (!block.evaluate(currentRecord, ctx)) {
        return false;
      }
    }
    return true;
  }

  public List<BooleanExpression> getSubBlocks() {
    return subBlocks;
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (subBlocks == null || subBlocks.size() == 0)
      return;

    boolean first = true;
    for (BooleanExpression expr : subBlocks) {
      if (!first)
        builder.append(" AND ");

      expr.toString(params, builder);
      first = false;
    }
  }

  public List<BinaryCondition> getIndexedFunctionConditions(final DocumentType iSchemaClass, final Database database) {
    if (subBlocks == null) {
      return null;
    }
    final List<BinaryCondition> result = new ArrayList<>();
    for (final BooleanExpression exp : subBlocks) {
      final List<BinaryCondition> sub = exp.getIndexedFunctionConditions(iSchemaClass, database);
      if (sub != null && sub.size() > 0) {
        result.addAll(sub);
      }
    }
    return result.size() == 0 ? null : result;
  }

  public List<AndBlock> flatten() {
    List<AndBlock> result = new ArrayList<>();
    boolean first = true;
    for (final BooleanExpression sub : subBlocks) {
      final List<AndBlock> subFlattened = sub.flatten();
      final List<AndBlock> oldResult = result;
      result = new ArrayList<>();
      for (final AndBlock subAndItem : subFlattened) {
        if (first) {
          result.add(subAndItem);
        } else {
          for (final AndBlock oldResultItem : oldResult) {
            final AndBlock block = new AndBlock(-1);
            block.subBlocks.addAll(oldResultItem.subBlocks);
            for (final BooleanExpression resultItem : subAndItem.subBlocks) {
              block.subBlocks.add(resultItem);
            }
            result.add(block);
          }
        }
      }
      first = false;
    }
    return result;
  }

  protected AndBlock encapsulateInAndBlock(final BooleanExpression item) {
    if (item instanceof AndBlock) {
      return (AndBlock) item;
    }
    final AndBlock result = new AndBlock(-1);
    result.subBlocks.add(item);
    return result;
  }

  public AndBlock copy() {
    final AndBlock result = new AndBlock(-1);
    for (final BooleanExpression exp : subBlocks) {
      result.subBlocks.add(exp.copy());
    }
    return result;
  }

  @Override
  public boolean isEmpty() {
    if (subBlocks.isEmpty()) {
      return true;
    }
    for (final BooleanExpression block : subBlocks) {
      if (!block.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void extractSubQueries(final SubQueryCollector collector) {
    for (final BooleanExpression exp : subBlocks) {
      exp.extractSubQueries(collector);
    }
  }

  @Override
  public List<String> getMatchPatternInvolvedAliases() {
    final List<String> result = new ArrayList<>();
    for (final BooleanExpression exp : subBlocks) {
      final List<String> x = exp.getMatchPatternInvolvedAliases();
      if (x != null) {
        result.addAll(x);
      }
    }
    return result.isEmpty() ? null : result;
  }

  @Override
  protected Object[] getIdentityElements() {
    return getCacheableElements();
  }

  @Override
  protected SimpleNode[] getCacheableElements() {
    return subBlocks.toArray(new SimpleNode[subBlocks.size()]);
  }

  @Override
  public boolean isAlwaysTrue() {
    if (subBlocks.isEmpty())
      return true;

    for (BooleanExpression exp : subBlocks) {
      if (!exp.isAlwaysTrue()) {
        return false;
      }
    }
    return true;
  }
}
/* JavaCC - OriginalChecksum=cf1f66cc86cfc93d357f9fcdfa4a4604 (do not edit this line) */
