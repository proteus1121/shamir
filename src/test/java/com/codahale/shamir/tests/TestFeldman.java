/*
 * Copyright © 2017 Coda Hale (coda.hale@gmail.com)
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
 */
package com.codahale.shamir.tests;

import com.codahale.shamir.ByteUtils;
import com.codahale.shamir.FeldmanVSS;
import com.codahale.shamir.Scheme;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

public class TestFeldman {

  @Test
  public void testFeldman() {
    final Scheme scheme = new Scheme(new SecureRandom(), 5, 3);
    int msg = 2560;
    final byte[] secret = ByteUtils.intToBytes(msg);
    Pair<Map<Integer, byte[]>, List<byte[]>> integerPairMap = scheme.splitByFeldman(secret);
    Map<Integer, byte[]> collect =
            integerPairMap
                .getKey();

    FeldmanVSS F = new FeldmanVSS(11, 3);
    F.setCommitments(msg, integerPairMap.getValue());
    String result = F.verifyShare(0, ByteUtils.intToBytes(4));
    System.out.println("hasil verifikasi: " + result);
  }
}
