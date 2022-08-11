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
/* Generated By:JJTree: Do not edit this line. OLetItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.Result;
import com.arcadedb.query.sql.executor.ResultInternal;

import java.util.*;

public class LetItem extends SimpleNode {

  Identifier varName;
  Expression expression;
  Statement  query;

  public LetItem(int id) {
    super(id);
  }

  public LetItem(SqlParser p, int id) {
    super(p, id);
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    varName.toString(params, builder);
    builder.append(" = ");
    if (expression != null) {
      expression.toString(params, builder);
    } else if (query != null) {
      builder.append("(");
      query.toString(params, builder);
      builder.append(")");
    }
  }

  public LetItem copy() {
    LetItem result = new LetItem(-1);
    result.varName = varName.copy();
    result.expression = expression == null ? null : expression.copy();
    result.query = query == null ? null : query.copy();
    return result;
  }

  public void setVarName(Identifier varName) {
    this.varName = varName;
  }

  public void setExpression(Expression expression) {
    this.expression = expression;
  }

  public void setQuery(Statement query) {
    this.query = query;
  }

  @Override
  public boolean equals( final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final  LetItem oLetItem = (LetItem) o;

    if (!Objects.equals(varName, oLetItem.varName))
      return false;
    if (!Objects.equals(expression, oLetItem.expression))
      return false;
    return Objects.equals(query, oLetItem.query);
  }

  @Override
  public int hashCode() {
    int result = varName != null ? varName.hashCode() : 0;
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    result = 31 * result + (query != null ? query.hashCode() : 0);
    return result;
  }

  public boolean refersToParent() {
    if (expression != null && expression.refersToParent()) {
      return true;
    }
    return query != null && query.refersToParent();
  }

  public Identifier getVarName() {
    return varName;
  }

  public Expression getExpression() {
    return expression;
  }

  public Statement getQuery() {
    return query;
  }

  public void extractSubQueries(SubQueryCollector collector) {
    //this is to transform LET expressions with subqueries in simple LET, plus LET with query only, so the direct query is ignored
    if (expression != null) {
      expression.extractSubQueries(varName, collector);
    }
  }

  public Result serialize() {
    ResultInternal result = new ResultInternal();
    if (varName != null) {
      result.setProperty("varName", varName.serialize());
    }
    if (expression != null) {
      result.setProperty("expression", expression.serialize());
    }
    if (query != null) {
      result.setProperty("query", query.serialize());
    }

    return result;
  }

  public void deserialize(Result fromResult) {
    if (fromResult.getProperty("varName") != null) {
      varName = new Identifier(-1);
      Identifier.deserialize(fromResult.getProperty("varName"));
    }
    if (fromResult.getProperty("expression") != null) {
      expression = new Expression(-1);
      expression.deserialize(fromResult.getProperty("expression"));
    }
    if (fromResult.getProperty("query") != null) {
      query = Statement.deserializeFromOResult(fromResult.getProperty("expression"));
    }
  }

  public boolean isCacheable() {
    if (expression != null) {
      return expression.isCacheable();
    }
    if (query != null) {
      return expression.isCacheable();
    }

    return true;
  }
}
/* JavaCC - OriginalChecksum=bb3cd298d79f50d72f6842e6d6ea4fb2 (do not edit this line) */
