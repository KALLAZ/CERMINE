package pl.edu.icm.coansys.metaextr.metadata.zoneclassification.features;

import pl.edu.icm.coansys.metaextr.tools.classification.features.FeatureCalculator;
import pl.edu.icm.coansys.metaextr.structure.model.BxChunk;
import pl.edu.icm.coansys.metaextr.structure.model.BxLine;
import pl.edu.icm.coansys.metaextr.structure.model.BxPage;
import pl.edu.icm.coansys.metaextr.structure.model.BxWord;
import pl.edu.icm.coansys.metaextr.structure.model.BxZone;

/**
 *
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class UppercaseWordCountFeature implements FeatureCalculator<BxZone, BxPage> {

    private static String featureName = "UppercaseWordCount";

    @Override
    public String getFeatureName() {
        return featureName;
    }

    @Override
    public double calculateFeatureValue(BxZone zone, BxPage page) {
        int count = 0;
        for (BxLine line : zone.getLines()) {
            for (BxWord word : line.getWords()) {
                String s = "";
                for (BxChunk chunk : word.getChunks()) {
                    s += chunk.getText();
                }
                if (!s.isEmpty() && Character.isUpperCase(s.charAt(0))) {
                    count++;
                }
            }
        }
        return (double) count;
    }

}