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
/* Generated By:JJTree: Do not edit this line. OInsertStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Database;
import com.arcadedb.query.sql.executor.BasicCommandContext;
import com.arcadedb.query.sql.executor.CommandContext;
import com.arcadedb.query.sql.executor.InsertExecutionPlan;
import com.arcadedb.query.sql.executor.InsertExecutionPlanner;
import com.arcadedb.query.sql.executor.ResultSet;

import java.util.*;

public class InsertStatement extends Statement {

  Identifier      targetType;
  Identifier      targetBucketName;
  Bucket          targetBucket;
  IndexIdentifier targetIndex;
  InsertBody      insertBody;
  Projection      returnStatement;
  SelectStatement selectStatement;
  boolean         selectInParentheses = false;
  boolean         selectWithFrom      = false;
  boolean         unsafe              = false;

  public InsertStatement(final int id) {
    super(id);
  }

  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    builder.append("INSERT INTO ");
    if (targetType != null) {
      targetType.toString(params, builder);
      if (targetBucketName != null) {
        builder.append(" BUCKET ");
        targetBucketName.toString(params, builder);
      }
    }
    if (targetBucket != null) {
      targetBucket.toString(params, builder);
    }
    if (targetIndex != null) {
      targetIndex.toString(params, builder);
    }
    if (insertBody != null) {
      builder.append(" ");
      insertBody.toString(params, builder);
    }
    if (returnStatement != null) {
      builder.append(" RETURN ");
      returnStatement.toString(params, builder);
    }
    if (selectStatement != null) {
      builder.append(" ");
      if (selectWithFrom) {
        builder.append("FROM ");
      }
      if (selectInParentheses) {
        builder.append("(");
      }
      selectStatement.toString(params, builder);
      if (selectInParentheses) {
        builder.append(")");
      }

    }
    if (unsafe) {
      builder.append(" UNSAFE");
    }
  }

  @Override
  public InsertStatement copy() {
    final InsertStatement result = new InsertStatement(-1);
    result.targetType = targetType == null ? null : targetType.copy();
    result.targetBucketName = targetBucketName == null ? null : targetBucketName.copy();
    result.targetBucket = targetBucket == null ? null : targetBucket.copy();
    result.targetIndex = targetIndex == null ? null : targetIndex.copy();
    result.insertBody = insertBody == null ? null : insertBody.copy();
    result.returnStatement = returnStatement == null ? null : returnStatement.copy();
    result.selectStatement = selectStatement == null ? null : selectStatement.copy();
    result.selectInParentheses = selectInParentheses;
    result.selectWithFrom = selectWithFrom;
    result.unsafe = unsafe;
    return result;
  }

  @Override
  public ResultSet execute(final Database db, final Object[] args, final CommandContext parentCtx, final boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null)
      ctx.setParentWithoutOverridingChild(parentCtx);

    ctx.setDatabase(db);
    ctx.setInputParameters(args);
    final InsertExecutionPlan executionPlan = createExecutionPlan(ctx, false);
    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  @Override
  public ResultSet execute(final Database db, final Map params, final CommandContext parentCtx, final boolean usePlanCache) {
    final BasicCommandContext ctx = new BasicCommandContext();
    if (parentCtx != null)
      ctx.setParentWithoutOverridingChild(parentCtx);

    ctx.setDatabase(db);
    ctx.setInputParameters(params);
    final InsertExecutionPlan executionPlan = createExecutionPlan(ctx, false);
    executionPlan.executeInternal();
    return new LocalResultSet(executionPlan);
  }

  public InsertExecutionPlan createExecutionPlan(final CommandContext ctx, final boolean enableProfiling) {
    final InsertExecutionPlanner planner = new InsertExecutionPlanner(this);
    return planner.createExecutionPlan(ctx, enableProfiling);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final InsertStatement that = (InsertStatement) o;

    if (selectInParentheses != that.selectInParentheses)
      return false;
    if (selectWithFrom != that.selectWithFrom)
      return false;
    if (unsafe != that.unsafe)
      return false;
    if (!Objects.equals(targetType, that.targetType))
      return false;
    if (!Objects.equals(targetBucketName, that.targetBucketName))
      return false;
    if (!Objects.equals(targetBucket, that.targetBucket))
      return false;
    if (!Objects.equals(targetIndex, that.targetIndex))
      return false;
    if (!Objects.equals(insertBody, that.insertBody))
      return false;
    if (!Objects.equals(returnStatement, that.returnStatement))
      return false;
    return Objects.equals(selectStatement, that.selectStatement);
  }

  @Override
  public int hashCode() {
    int result = targetType != null ? targetType.hashCode() : 0;
    result = 31 * result + (targetBucketName != null ? targetBucketName.hashCode() : 0);
    result = 31 * result + (targetBucket != null ? targetBucket.hashCode() : 0);
    result = 31 * result + (targetIndex != null ? targetIndex.hashCode() : 0);
    result = 31 * result + (insertBody != null ? insertBody.hashCode() : 0);
    result = 31 * result + (returnStatement != null ? returnStatement.hashCode() : 0);
    result = 31 * result + (selectStatement != null ? selectStatement.hashCode() : 0);
    result = 31 * result + (selectInParentheses ? 1 : 0);
    result = 31 * result + (selectWithFrom ? 1 : 0);
    result = 31 * result + (unsafe ? 1 : 0);
    return result;
  }

  public Identifier getTargetType() {
    return targetType;
  }

  public Identifier getTargetBucketName() {
    return targetBucketName;
  }

  public Bucket getTargetBucket() {
    return targetBucket;
  }

  public IndexIdentifier getTargetIndex() {
    return targetIndex;
  }

  public InsertBody getInsertBody() {
    return insertBody;
  }

  public Projection getReturnStatement() {
    return returnStatement;
  }

  public SelectStatement getSelectStatement() {
    return selectStatement;
  }

  public boolean isSelectInParentheses() {
    return selectInParentheses;
  }

  public boolean isSelectWithFrom() {
    return selectWithFrom;
  }

  public boolean isUnsafe() {
    return unsafe;
  }
}
/* JavaCC - OriginalChecksum=ccfabcf022d213caed873e6256cb26ad (do not edit this line) */
