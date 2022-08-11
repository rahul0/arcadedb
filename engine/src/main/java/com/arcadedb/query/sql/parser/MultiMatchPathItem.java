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
/* Generated By:JJTree: Do not edit this line. OMultiMatchPathItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_USERTYPE_VISIBILITY_PUBLIC=true */
package com.arcadedb.query.sql.parser;

import com.arcadedb.database.Identifiable;
import com.arcadedb.query.sql.executor.CommandContext;

import java.util.*;
import java.util.stream.Collectors;

public class MultiMatchPathItem extends MatchPathItem {
  protected List<MatchPathItem> items = new ArrayList<MatchPathItem>();

  public MultiMatchPathItem(int id) {
    super(id);
  }

  public MultiMatchPathItem(SqlParser p, int id) {
    super(p, id);
  }

  public boolean isBidirectional() {
    return false;
  }

  public void toString(Map<String, Object> params, StringBuilder builder) {
    builder.append(".(");
    for (MatchPathItem item : items) {
      item.toString(params, builder);
    }
    builder.append(")");
    if (filter != null) {
      filter.toString(params, builder);
    }
  }

  protected Iterable<Identifiable> traversePatternEdge(MatchStatement.MatchContext matchContext, Identifiable startingPoint, CommandContext iCommandContext) {
    Set<Identifiable> result = new HashSet<Identifiable>();
    result.add(startingPoint);
    for (MatchPathItem subItem : items) {
      Set<Identifiable> startingPoints = result;
      result = new HashSet<Identifiable>();
      for (Identifiable sp : startingPoints) {
        Iterable<Identifiable> subResult = subItem.executeTraversal(matchContext, iCommandContext, sp, 0);
        if (subResult instanceof Collection) {
          result.addAll((Collection) subResult);
        } else {
          for (Identifiable id : subResult) {
            result.add(id);
          }
        }
      }
    }
    return result;
  }

  @Override
  public MultiMatchPathItem copy() {
    MultiMatchPathItem result = (MultiMatchPathItem) super.copy();
    result.items = items == null ? null : items.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  @Override
  public boolean equals( final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;

    final MultiMatchPathItem that = (MultiMatchPathItem) o;

    return Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (items != null ? items.hashCode() : 0);
    return result;
  }

  public List<MatchPathItem> getItems() {
    return items;
  }

  public void setItems(List<MatchPathItem> items) {
    this.items = items;
  }
}
/* JavaCC - OriginalChecksum=f18f107768de80b8941f166d7fafb3c0 (do not edit this line) */
