/**
 * Copyright (C) 2014 Michael Röder (michael.roeder@unister.de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.aksw.palmetto.calculations.direct;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.palmetto.calculations.direct.LogJaccardConfirmationMeasure;
import org.aksw.palmetto.subsets.OneOne;
import org.aksw.palmetto.subsets.Segmentator;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LogJaccardCoherenceCalculationTest extends AbstractProbabilityBasedCalculationTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                /*
                 * word1 1 1 1
                 * 
                 * word2 0 1 1
                 * 
                 * word3 0 1 1
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(2/3 / 1) + log(2/3 / 1) + log(2/3 /
                 * 2/3)) = 1/3 * (2*log(2/3))
                 */
                { new OneOne(), 3,
                        new double[] { 0, 1.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0 },
                        (2.0 * Math.log(2.0 / 3.0)) / 3.0 },

                /*
                 * word1 0 1 1
                 * 
                 * word2 1 0 1
                 * 
                 * word3 1 1 0
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(1/3 / 1) + log(1/3 / 1) + log(1/3 / 1))
                 * = log(1/3)
                 */{ new OneOne(), 3,
                        new double[] { 0, 2.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0, 0 },
                        Math.log(1.0 / 3.0) },
                /*
                 * word1 0 0 0 1
                 * 
                 * word2 0 1 0 1
                 * 
                 * word3 0 0 1 1
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(1/4 / 1/2) + log(1/4 / 3/4)) + log(1/4 /
                 * 1/2)) = 1/3 * (log(1/2) + log(1/3) + log(1/2)) = (2*log(0.5)
                 * + log(1/3))/3.0
                 */
                { new OneOne(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 },
                        (2 * Math.log(0.5) + Math.log(1.0 / 3.0)) / 3.0 } });
    }

    public LogJaccardCoherenceCalculationTest(Segmentator subsetCreator, int wordsetSize, double[] probabilities,
            double expectedCoherence) {
        super(new LogJaccardConfirmationMeasure(), subsetCreator, wordsetSize, probabilities, expectedCoherence);
    }
}