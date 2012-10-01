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
public class CharCountRelativeFeature implements FeatureCalculator<BxZone, BxPage> {

    private static String featureName = "CharCountRelative";

    @Override
    public String getFeatureName() {
        return featureName;
    }

    @Override
    public double calculateFeatureValue(BxZone zone, BxPage page) {
        int count = 0;
        for (BxLine line : zone.getLines()) {
            for (BxWord word : line.getWords()) {
                for (BxChunk chunk : word.getChunks()) {
                    count += chunk.getText().length();
                }
            }
        }

        int pCount = 0;
        for (BxZone pZone : page.getZones()) {
            for (BxLine line : pZone.getLines()) {
                for (BxWord word : line.getWords()) {
                    for (BxChunk chunk : word.getChunks()) {
                        pCount += chunk.getText().length();
                    }
                }
            }
        }
        if(((double) count / (double) pCount)<0.0) {
        	System.out.println(count);
        	System.out.println(pCount);
        }
        return (double) count / (double) pCount;
    }
}