/*
 * Copyright 2015-2018 the original author or authors
 *
 * This software is licensed under the Apache License, Version 2.0,
 * the GNU Lesser General Public License version 2 or later ("LGPL")
 * and the WTFPL.
 * You may choose either license to govern your use of this software only
 * upon the condition that you accept all of the terms of either
 * the Apache License 2.0, the LGPL 2.1+ or the WTFPL.
 */
package org.minidns.dnssec.algorithms;

import org.minidns.constants.DNSSECConstants.SignatureAlgorithm;
import org.minidns.dnssec.DNSSECValidationFailedException;
import org.junit.Test;

import static org.minidns.dnssec.DNSSECWorld.generatePrivateKey;
import static org.minidns.dnssec.DNSSECWorld.publicKey;
import static org.minidns.dnssec.DNSSECWorld.sign;

public class DSASingatureVerifierTest extends SignatureVerifierTest {
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.DSA;

    @Test
    public void testDSA1024Valid() {
        verifierTest(1024, ALGORITHM);
    }

    @Test
    public void testDSA512Valid() {
        verifierTest(512, ALGORITHM);
    }


    @Test(expected = DNSSECValidationFailedException.class)
    public void testDSAIllegalSignature() {
        assertSignatureValid(publicKey(ALGORITHM, generatePrivateKey(ALGORITHM, 1024)), ALGORITHM, new byte[]{0x0});
    }

    @Test(expected = DNSSECValidationFailedException.class)
    public void testDSAIllegalPublicKey() {
        assertSignatureValid(new byte[]{0x0}, ALGORITHM, sign(generatePrivateKey(ALGORITHM, 1024), ALGORITHM, sample));
    }

    @Test
    public void testDSAWrongSignature() {
        assertSignatureInvalid(publicKey(ALGORITHM, generatePrivateKey(ALGORITHM, 1024)), ALGORITHM, sign(generatePrivateKey(ALGORITHM, 1024), ALGORITHM, sample));
    }

}
