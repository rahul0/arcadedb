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
/* Generated by: JJTree: Do not edit this line. Field.java Version 1.1 */
/* ParserGeneratorCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.arcadedb.graphql.parser;

public class Field extends AbstractField {

  protected Arguments    arguments;
  protected SelectionSet selectionSet;

  public Field(final Name name, final int line, final int column, final int tokenId) {
    this(-1);
    this.name = name;
  }

  public Field(final int id) {
    super(id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(final GraphQLParserVisitor visitor, final Object data) {
    return visitor.visit(this, data);
  }

  public Arguments getArguments() {
    return arguments;
  }

  public SelectionSet getSelectionSet() {
    return selectionSet;
  }
}
/* ParserGeneratorCC - OriginalChecksum=2b182b10a025776d444c1f179f3e7ff4 (do not edit this line) */
