/*
 * Copyright 2011-2021 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.core.javaapi;

import io.gatling.core.action.builder.ActionBuilder;
import io.gatling.core.javaapi.internal.StructureBuilder;

import java.util.List;
import java.util.function.Function;

import static io.gatling.core.javaapi.internal.ScalaHelpers.toScalaSeq;

public class ChainBuilder extends StructureBuilder<ChainBuilder, io.gatling.core.structure.ChainBuilder> {

  public static final ChainBuilder EMPTY = new ChainBuilder(io.gatling.core.structure.ChainBuilder.Empty());

  protected ChainBuilder(io.gatling.core.structure.ChainBuilder wrapped) {
    super(wrapped);
  }

  @Override
  public ChainBuilder chain(List<ActionBuilder> newActionBuilders) {
    return new ChainBuilder(wrapped.chain(toScalaSeq(newActionBuilders)));
  }

  @Override
  public ChainBuilder make(Function<io.gatling.core.structure.ChainBuilder, io.gatling.core.structure.ChainBuilder> f) {
    return new ChainBuilder(f.apply(wrapped));
  }
}