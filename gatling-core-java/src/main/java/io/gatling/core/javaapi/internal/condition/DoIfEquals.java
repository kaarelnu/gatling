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

package io.gatling.core.javaapi.internal.condition;

import io.gatling.commons.validation.Validation;
import io.gatling.core.javaapi.ChainBuilder;
import io.gatling.core.javaapi.Session;
import io.gatling.core.javaapi.internal.StructureBuilder;
import scala.Function1;

import java.util.function.Function;

import static io.gatling.core.javaapi.internal.ScalaHelpers.*;

public interface DoIfEquals<T extends StructureBuilder<T, W>, W extends io.gatling.core.structure.StructureBuilder<W>> {

  T make(Function<W, W> f);

  // Gatling EL actual
  default Then<T> doIfEquals(String actual, String expected) {
    return new Then<>(this, toAnyExpression(actual), toAnyExpression(expected));
  }

  default Then<T> doIfEquals(String actual, Object expected) {
    return new Then<>(this, toAnyExpression(actual), toUntypedGatlingSessionFunction(unused -> expected));
  }

  default Then<T> doIfEquals(String actual, Function<Session, Object> expected) {
    return new Then<>(this, toAnyExpression(actual), toUntypedGatlingSessionFunction(expected));
  }

  // Function actual
  default Then<T> doIfEquals(Function<Session, Object> actual, String expected) {
    return new Then<>(this, toUntypedGatlingSessionFunction(actual), toAnyExpression(expected));
  }

  default Then<T> doIfEquals(Function<Session, Object> actual, Object expected) {
    return new Then<>(this, toUntypedGatlingSessionFunction(actual), toUntypedGatlingSessionFunction(unused -> expected));
  }

  default Then<T> doIfEquals(Function<Session, Object> actual, Function<Session, Object> expected) {
    return new Then<>(this, toUntypedGatlingSessionFunction(actual), toUntypedGatlingSessionFunction(expected));
  }

  final class Then<T extends StructureBuilder<T, ?>> {
    private final DoIfEquals<T, ?> context;
    private final Function1<io.gatling.core.session.Session, Validation<Object>> actual;
    private final Function1<io.gatling.core.session.Session, Validation<Object>> expected;

    Then(DoIfEquals<T, ?> context, Function1<io.gatling.core.session.Session, Validation<Object>> actual, Function1<io.gatling.core.session.Session, Validation<Object>> expected) {
      this.context = context;
      this.actual = actual;
      this.expected = expected;
    }

    public T then(ChainBuilder chain) {
      return context.make(wrapped -> wrapped.doIfEquals(actual, expected, chain.wrapped));
    }
  }
}