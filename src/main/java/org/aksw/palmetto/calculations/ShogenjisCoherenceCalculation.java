/**
 * Copyright (C) 2014 Michael Röder (michael.roeder@unister.de)
 *
 * Licensed under the Creative Commons Attribution-NonCommercial 4.0
 * International Public License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://creativecommons.org/licenses/by-nc/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, a file
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aksw.palmetto.calculations;

import org.aksw.palmetto.subsets.SubsetProbabilities;

public class ShogenjisCoherenceCalculation implements CoherenceCalculation {

    @Override
    public double[] calculateCoherenceValues(SubsetProbabilities subsetProbabilities) {
        int numberOfPairs = 0;
        for (int i = 0; i < subsetProbabilities.segments.length; ++i) {
            numberOfPairs += subsetProbabilities.conditions[i].length;
        }
        double values[] = new double[numberOfPairs];

        double conditionProbability, intersectionProbability;
        int pos = 0;
        for (int i = 0; i < subsetProbabilities.segments.length; ++i) {
            if (subsetProbabilities.probabilities[subsetProbabilities.segments[i]] > 0) {
                for (int j = 0; j < subsetProbabilities.conditions[i].length; ++j) {
                    conditionProbability = subsetProbabilities.probabilities[subsetProbabilities.conditions[i][j]];
                    intersectionProbability = subsetProbabilities.probabilities[subsetProbabilities.segments[i]
                            | subsetProbabilities.conditions[i][j]];
                    if ((conditionProbability > 0) && (intersectionProbability > 0)) {
                        values[pos] = intersectionProbability / Math.pow(conditionProbability, numberOfPairs);
                    }
                    ++pos;
                }
            } else {
                pos += subsetProbabilities.conditions[i].length;
            }
        }
        return values;
    }

    @Override
    public String getCalculationName() {
        return "m_s";
    }
}