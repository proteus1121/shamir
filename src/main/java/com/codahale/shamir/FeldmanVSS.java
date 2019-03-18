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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FeldmanVSS {
  long p; // prime number p as modulus where q|p-1, q is modulus of the polynomial function to
  // generate shares
  long g; // generator g as base
  ArrayList<BigInteger> commitments; // commitments g^polCoeff;

  public FeldmanVSS(long p, long g) {
    this.p = p;
    this.g = g;
    this.commitments = new ArrayList<>();
  }

  public void setCommitments(int M, List<Double> polCoeff) {
    BigInteger gB = new BigInteger(String.valueOf(g));
    BigInteger mB = new BigInteger(String.valueOf(M));
//    this.commitments.add(pow(gB, mB));
    for (int i = 0; i < polCoeff.size(); i++) {
      BigInteger bytes = BigInteger.valueOf(polCoeff.get(i).longValue());
      this.commitments.add(bytes);
    }
  }

  BigInteger pow(BigInteger base, BigInteger exponent) {
//   return base.pow(exponent.intValue());
    BigInteger result = BigInteger.ONE;
    while (exponent.signum() > 0) {
      if (exponent.testBit(0)) result = result.multiply(base);
      base = base.multiply(base);
      exponent = exponent.shiftRight(1);
    }
    return result;
  }

  public String verifyShare(long id, byte[] s) {
    BigInteger idB = new BigInteger(String.valueOf(id));
    BigInteger gB = new BigInteger(String.valueOf(g));
    BigInteger sB = new BigInteger(s);
    BigInteger pB = new BigInteger(String.valueOf(p));

    BigInteger sh = gB.modPow(sB, pB);
    System.out.println(sh);
//    long shareRes = ((long) Math.pow(g, s)) % p;

    BigInteger commitmentsRes = new BigInteger("1");
    for (int i = 0; i < this.commitments.size(); i++) {
//      commitmentsRes *= ((long) Math.pow(commitments.get(i), Math.pow(id, i)) % p);
      commitmentsRes = commitmentsRes .multiply(gB.pow(commitments.get(i).intValue()).pow(Double.valueOf(Math.pow(id, i)).intValue()));
//      BigInteger bigInteger = commitments.get(i).modPow(pow(idB, new BigInteger(String.valueOf(i))), pB);
//      commitmentsRes = commitmentsRes.multiply(bigInteger);
    }
    commitmentsRes = commitmentsRes.mod(pB);
    //        System.out.println("shareRes="+shareRes);
    //        System.out.println("comRes="+commitmentsRes);
    return (sh.equals(commitmentsRes)) ? "valid" : "invalid";
  }
}
