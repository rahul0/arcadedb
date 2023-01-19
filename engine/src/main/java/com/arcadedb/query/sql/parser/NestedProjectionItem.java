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
/* Generated By:JJTree: Do not edit this line. OExpansionItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.query.sql.executor.CommandContext;

import java.util.*;

public class NestedProjectionItem extends SimpleNode {
  protected boolean          exclude       = false;
  protected boolean          star          = false;
  protected Expression       expression;
  protected boolean          rightWildcard = false;
  protected NestedProjection expansion;
  protected Identifier       alias;

  public NestedProjectionItem(final int id) {
    super(id);
  }

  @Override
  public NestedProjectionItem copy() {
    final NestedProjectionItem result = new NestedProjectionItem(-1);
    result.exclude = exclude;
    result.star = star;
    result.expression = expression == null ? null : expression.copy();
    result.rightWildcard = rightWildcard;
    result.expansion = expansion == null ? null : expansion.copy();
    result.alias = alias == null ? null : alias.copy();
    return result;
  }

  /**
   * given a property name, calculates if this property name matches this nested projection item, eg.
   * <ul>
   * <li>this is a *, so it matches any property name</li>
   * <li>the field name for this projection item is the same as the input property name</li>
   * <li>this item has a wildcard and the partial field is a prefix of the input property name</li>
   * </ul>
   *
   * @param propertyName
   *
   * @return
   */
  public boolean matches(final String propertyName) {
    if (star) {
      return true;
    }
    if (expression != null) {
      final String fieldString = expression.getDefaultAlias().getStringValue();
      if (fieldString.equals(propertyName)) {
        return true;
      }
      return rightWildcard && propertyName.startsWith(fieldString);
    }
    return false;
  }

  @Override
  public void toString(final Map<String, Object> params, final StringBuilder builder) {
    if (exclude) {
      builder.append("!");
    }
    if (star) {
      builder.append("*");
    }
    if (expression != null) {
      expression.toString(params, builder);
      if (rightWildcard) {
        builder.append("*");
      }
    }
    if (expansion != null) {
      expansion.toString(params, builder);
    }
    if (alias != null) {
      builder.append(" AS ");
      alias.toString(params, builder);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    final NestedProjectionItem that = (NestedProjectionItem) o;

    if (exclude != that.exclude)
      return false;
    if (star != that.star)
      return false;
    if (rightWildcard != that.rightWildcard)
      return false;
    if (!Objects.equals(expression, that.expression))
      return false;
    if (!Objects.equals(expansion, that.expansion))
      return false;
    return Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = (exclude ? 1 : 0);
    result = 31 * result + (star ? 1 : 0);
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    result = 31 * result + (rightWildcard ? 1 : 0);
    result = 31 * result + (expansion != null ? expansion.hashCode() : 0);
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  public Object expand(final Expression expression, final String name, final Object value, final CommandContext ctx, final int recursion) {
    return expansion.apply(expression, value, ctx);
  }
}
/* JavaCC - OriginalChecksum=606b3fe37ff952934e3e2e3daa9915f2 (do not edit this line) */
