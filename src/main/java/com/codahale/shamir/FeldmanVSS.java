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
package com.codahale.shamir;

import java.util.ArrayList;

public class FeldmanVSS {
  long p; // prime number p as modulus where q|p-1, q is modulus of the polynomial function to
  // generate shares
  long g; // generator g as base
  ArrayList<Long> commitments; // commitments g^polCoeff;

  public FeldmanVSS(long p, long g) {
    this.p = p;
    this.g = g;
    this.commitments = new ArrayList<>();
  }

  public void setCommitments(long M, ArrayList<Long> polCoeff) {
    this.commitments.add((long) Math.pow(g, M));
    for (int i = 0; i < polCoeff.size(); i++) {
      this.commitments.add((long) Math.pow(g, polCoeff.get(i)));
    }
  }

  public String verifyShare(long id, long s) {
    long shareRes = ((long) Math.pow(g, s)) % p;

    long commitmentsRes = 1;
    for (int i = 0; i < this.commitments.size(); i++) {
      commitmentsRes *= ((long) Math.pow(commitments.get(i), Math.pow(id, i)) % p);
    }
    commitmentsRes %= p;
    //        System.out.println("shareRes="+shareRes);
    //        System.out.println("comRes="+commitmentsRes);
    return (shareRes == commitmentsRes) ? "valid" : "invalid";
  }
}
