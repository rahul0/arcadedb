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
/* Generated By:JJTree: Do not edit this line. OExplainStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.*;

import java.util.*;

public class ExplainStatement extends Statement {

  protected Statement statement;

  public ExplainStatement(int id) {
    super(id);
  }

  public ExplainStatement(SqlParser p, int id) {
    super(p, id);
  }

  @Override
  public void toString(Map<String, Object> params, StringBuilder builder) {
    builder.append("EXPLAIN ");
    statement.toString(params, builder);
  }

  @Override
  public ResultSet execute(Database db, Object[] args, CommandContext parentCtx, boolean usePlanCache) {
    BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(args);

    ExecutionPlan executionPlan = statement.createExecutionPlan(ctx, false);

    ExplainResultSet result = new ExplainResultSet(executionPlan);
    return result;
  }

  @Override
  public ResultSet execute(Database db, Map args, CommandContext parentCtx, boolean usePlanCache) {
    BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null) {
      ctx.setParentWithoutOverridingChild(parentCtx);
    }
    ctx.setDatabase(db);
    ctx.setInputParameters(args);

    ExecutionPlan executionPlan = statement.createExecutionPlan(ctx, false);

    ExplainResultSet result = new ExplainResultSet(executionPlan);
    return result;
  }

  @Override
  public InternalExecutionPlan createExecutionPlan(CommandContext ctx, boolean enableProfiling) {
    return statement.createExecutionPlan(ctx, enableProfiling);
  }

  @Override
  public ExplainStatement copy() {
    ExplainStatement result = new ExplainStatement(-1);
    result.statement = statement == null ? null : statement.copy();
    return result;
  }

  @Override
  public boolean equals( final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final ExplainStatement that = (ExplainStatement) o;

    return Objects.equals(statement, that.statement);
  }

  @Override
  public int hashCode() {
    return statement != null ? statement.hashCode() : 0;
  }

  @Override
  public boolean isIdempotent() {
    return true;
  }
}
/* JavaCC - OriginalChecksum=9fdd24510993cbee32e38a51c838bdb4 (do not edit this line) */
